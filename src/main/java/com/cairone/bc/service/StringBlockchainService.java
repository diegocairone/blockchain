package com.cairone.bc.service;

import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Payload;
import com.cairone.bc.domain.StringBlock;
import com.cairone.bc.domain.StringBlockchain;

public class StringBlockchainService extends AbstractBlockchainService<String> {

    public StringBlockchainService(StringBlockchain blockchain) {
        super(blockchain);
    }

    @Override
    public StringBlockchain getChain() {
        return (StringBlockchain) super.getChain();
    }

    @Override
    public StringBlock newBlock(String data) {
        
        Block<String> prevBlock = blockchain.lastBlock();
        
        Payload<String> payload = new Payload<>(data);
        String prevId = prevBlock == null ? "0" : prevBlock.getId();
        
        StringBlock block = StringBlock.builder()
            .prevId(prevId)
            .payload(payload)
            .build();
        
        return block;
    }
    
}
