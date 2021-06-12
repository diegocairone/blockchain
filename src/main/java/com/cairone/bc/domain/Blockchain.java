package com.cairone.bc.domain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain<T> {
    
    private List<Block<T>> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
    }

    public Blockchain<T> addBlock(Block<T> block) {
        this.chain.add(block);
        return this;
    }

    public Block<T> lastBlock() {
        if (chain == null) {
            return null;
        }
        return chain.get(chain.size() - 1);
    }
}
