package com.cairone.bc.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import lombok.Builder;

public class TxBlock extends AbstractBlock<List<Tx>> {
    
    @Builder
    private TxBlock(String prevId, Payload<List<Tx>> payload) {
        this.id = null;
        this.prevId = prevId;
        this.createdAt = Timestamp.from(Instant.now()).getTime();
        this.nonce = 0;
        this.payload = payload;
    }
}
