package com.cairone.bc.service;

import java.util.List;

import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Blockchain;

public abstract class AbstractBlockchainService<T> {

    protected Blockchain<T> blockchain;

    public AbstractBlockchainService(Blockchain<T> blockchain) {
        this.blockchain = blockchain;
    }

    public abstract Block<T> newBlock(T data);

    public List<Block<T>> getBlocks() {
        return blockchain.getBlocks();
    }

    public AbstractBlockchainService<T> addBlock(Block<T> block) {
        blockchain.getBlocks().add(block);
        return this;
    }

    public Block<T> lastBlock() {
        if (blockchain.getBlocks() == null || blockchain.getBlocks().size() == 0) {
            return null;
        }
        return blockchain.getBlocks().get(blockchain.getBlocks().size() - 1);
    }

}