package com.cairone.bc.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Blockchain<T> {
    
    private List<Block<T>> blocks;

    public Blockchain() {
        this.blocks = new ArrayList<>();
    }
}
