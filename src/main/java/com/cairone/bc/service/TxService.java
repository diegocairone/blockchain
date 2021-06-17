package com.cairone.bc.service;

import java.security.KeyPair;
import java.security.PublicKey;

import com.cairone.bc.domain.Tx;
import com.cairone.bc.domain.Tx.TxBuilder;
import com.cairone.bc.ui.ctrl.form.NewTxFrm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TxService {
    
    @Autowired private KeyStoreService keyStoreService;
    @Autowired private TxBlockchainService txBlockchainService;

    public Tx create(NewTxFrm txFrm) {

        KeyPair senderKP = keyStoreService.findByPubKey(txFrm.getSender())
            .orElseThrow(() -> new RuntimeException(
                "Sender Public key: " + txFrm.getSender() + " does not exists!"));

        PublicKey sender = senderKP.getPublic();

        PublicKey recipient = keyStoreService.findByPubKey(txFrm.getRecipient())
            .orElseThrow(() -> new RuntimeException(
                "Recipient Public key: " + txFrm.getRecipient() + " does not exists!"))
            .getPublic();

        Tx tx = TxBuilder.builder()
            .from(sender)
            .to(recipient)
            .send(txFrm.getAmount())
            .build()
            .sign(senderKP.getPrivate());

        log.info("Se agrega transaccion: {}", tx);
        txBlockchainService.addTransaction(tx);

        return tx;
    }
}
