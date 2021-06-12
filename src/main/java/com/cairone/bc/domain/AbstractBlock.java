package com.cairone.bc.domain;

import lombok.Data;

@Data
public abstract class AbstractBlock<T> implements Block<T> {

    protected String id;
    protected String prevId;
    protected long createdAt;
    protected int nonce;
    protected Payload<T> payload;

}
