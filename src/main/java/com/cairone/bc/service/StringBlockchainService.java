package com.cairone.bc.service;

import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Payload;
import com.cairone.bc.domain.StringBlock;
import com.cairone.bc.domain.StringBlockchain;

public class StringBlockchainService extends AbstractBlockchainService<String> {

    public StringBlockchainService(StringBlockchain blockchain, int miningDifficulty) {
        super(blockchain, miningDifficulty);
    }

    @Override
    public StringBlock newBlock(String data) {
        
        Block<String> prevBlock = lastBlock();
        
        Payload<String> payload = new Payload<>(data);
        String prevId = prevBlock == null ? "0" : prevBlock.getId();
        
        StringBlock block = StringBlock.builder()
            .prevId(prevId)
            .payload(payload)
            .build();

        String id = calculateHash(block);
        block.setId(id);
        
        addBlock(block);
        
        return block;
    }

}
