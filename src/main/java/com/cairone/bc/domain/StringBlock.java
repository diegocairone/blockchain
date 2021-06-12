package com.cairone.bc.domain;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.Builder;

public class StringBlock extends AbstractBlock<String> {
    
    @Builder
    private StringBlock(String prevId, Payload<String> payload) {
        this.id = null;
        this.prevId = prevId;
        this.createdAt = Timestamp.from(Instant.now()).getTime();
        this.nonce = 0;
        this.payload = payload;
    }
}
