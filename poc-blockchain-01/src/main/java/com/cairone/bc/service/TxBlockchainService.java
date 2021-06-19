package com.cairone.bc.service;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Payload;
import com.cairone.bc.domain.Tx;
import com.cairone.bc.domain.TxBlock;
import com.cairone.bc.domain.TxBlockchain;
import com.cairone.bc.util.CryptoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TxBlockchainService extends AbstractBlockchainService<List<Tx>> {
    
    private TxBlock currentBlock = null;

    @Autowired
    private KeyStoreService keyStoreService;

    @Value("${app.block-size:5}")
    private int blockSize;

    public TxBlockchainService(TxBlockchain blockchain, int miningDifficulty) {
        super(blockchain, miningDifficulty);
    }

    public TxBlockchainService addTransaction(Tx transaction) {

        if (currentBlock == null) {
            log.info("Se requiere crear un nuevo bloque!");
            newEmptyBlock();
        }

        List<Tx> transactions = currentBlock.getPayload().getData();
        
        String senderPubKey = CryptoUtil.encodeToString(transaction.getSender());
        KeyPair senderKP = keyStoreService.findByPubKey(senderPubKey)
            .orElseThrow(() -> new RuntimeException(
                "No se reconoce la clave publica: " + senderPubKey));

        String from = CryptoUtil.encodeToString(transaction.getSender());
        String to = CryptoUtil.encodeToString(transaction.getRecipient());
        String sent = transaction.getAmount().toString();
        String data = String.format("%s:%s:%s", from, to, sent);

        boolean isValid = CryptoUtil.verifyECDSASig(
            senderKP.getPublic(), 
            data, 
            transaction.getSignature());

        if (isValid) {
            log.info("Transaction ID {} is valid!", transaction.getId());
            transactions.add(transaction);
        } else {
            throw new RuntimeException("Invalid transaction with ID " + transaction.getId());
        }

        if (transactions.size() == blockSize) {
            log.info(
                "Cantidad maxima de transacciones alcanzada en el bloque actual: ", 
                currentBlock.getId());
            String id = calculateHash(currentBlock);
            currentBlock.setId(id);
            addBlock(currentBlock);
            this.currentBlock = null;
        }

        return this;
    }

    public Block<List<Tx>> newEmptyBlock() {
        List<Tx> transactions =new ArrayList<>();
        return newBlock(transactions);
    }

    @Override
    public Block<List<Tx>> newBlock(List<Tx> transactions) {
        
        Block<List<Tx>> prevBlock = lastBlock();
        
        Payload<List<Tx>> payload = new Payload<>(transactions);
        String prevId = prevBlock == null ? "0" : prevBlock.getId();
        
        TxBlock block = TxBlock.builder()
            .prevId(prevId)
            .payload(payload)
            .build();

        this.currentBlock = block;
        
        return block;
    }

    public TxBlock getCurrentBlock() {
        return this.currentBlock;
    }
}
