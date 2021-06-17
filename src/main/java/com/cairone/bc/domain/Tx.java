package com.cairone.bc.domain;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;


import com.cairone.bc.util.CryptoUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Tx {
    
    @EqualsAndHashCode.Include
    @ToString.Include
    private String id;

    private PublicKey sender;

    private PublicKey recipient;

    private BigDecimal amount;

    private byte[] signature;

    private LocalDateTime createdAt;

    public Tx(PublicKey sender, PublicKey recipient, BigDecimal amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.signature = null;
        this.createdAt = LocalDateTime.now();
        calculateId();
    }

    public Tx sign(PrivateKey privateKey) {
        String from = CryptoUtil.encodeToString(sender);
        String to = CryptoUtil.encodeToString(recipient);
        String sent = amount.toString();
        String data = String.format("%s:%s:%s", from, to, sent);
        this.signature = CryptoUtil.applyECDSASig(privateKey, data);
        return this;
    }

    private void calculateId() {
        String from = CryptoUtil.encodeToString(sender);
        String to = CryptoUtil.encodeToString(recipient);
        String transfer = amount.toString();
        String timestamp = Long.toString(Timestamp.valueOf(createdAt).getTime());
        String data = String.format("%s:%s:%s:%s", from, to, transfer, timestamp);
        String hash = CryptoUtil.sha256Hash(data);
        this.id = hash;
    }

    public static class TxBuilder {

        private PublicKey sender;

        private PublicKey recipient;
    
        private BigDecimal amount;
        
        private TxBuilder() {
        }

        public static TxBuilder builder() {
            return new TxBuilder();
        }

        public TxBuilder from(PublicKey sender) {
            this.sender = sender;
            return this;
        }

        public TxBuilder to(PublicKey recipient) {
            this.recipient = recipient;
            return this;
        }

        public TxBuilder send(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Tx build() {
            return new Tx(sender, recipient, amount);
        }
    }
}
