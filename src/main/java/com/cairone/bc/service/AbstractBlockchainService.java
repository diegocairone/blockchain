package com.cairone.bc.service;

import java.io.Serializable;

import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Blockchain;

public abstract class AbstractBlockchainService<T extends Serializable> {

    protected Blockchain<T> blockchain;

    public Blockchain<T> getChain() {
        return blockchain;
    }

    public AbstractBlockchainService(Blockchain<T> blockchain) {
        this.blockchain = blockchain;
    }

    public abstract Block<T> newBlock(T data);
}