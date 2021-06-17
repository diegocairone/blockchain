package com.cairone.bc.service;

import java.util.ArrayList;
import java.util.List;

import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Payload;
import com.cairone.bc.domain.Tx;
import com.cairone.bc.domain.TxBlock;
import com.cairone.bc.domain.TxBlockchain;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TxBlockchainService extends AbstractBlockchainService<List<Tx>> {
    
    private TxBlock currentBlock = null;

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
        transactions.add(transaction);

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
