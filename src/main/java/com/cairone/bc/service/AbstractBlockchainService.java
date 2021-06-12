package com.cairone.bc.service;

import java.util.List;

import com.cairone.bc.domain.AbstractBlock;
import com.cairone.bc.domain.Block;
import com.cairone.bc.domain.Blockchain;
import com.cairone.bc.util.CryptoUtil;
import com.cairone.bc.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBlockchainService<T> {

    protected Blockchain<T> blockchain;
    protected int miningDifficulty;

    public AbstractBlockchainService(Blockchain<T> blockchain, int miningDifficulty) {
        this.blockchain = blockchain;
        this.miningDifficulty = miningDifficulty;
    }

    public abstract Block<T> newBlock(T data);

    public List<Block<T>> getBlocks() {
        return blockchain.getBlocks();
    }

    public Block<T> lastBlock() {
        if (blockchain.getBlocks() == null || blockchain.getBlocks().size() == 0) {
            return null;
        }
        return blockchain.getBlocks().get(blockchain.getBlocks().size() - 1);
    }

    public boolean validateBlockchain() {

        List<Block<T>> blocks = this.blockchain.getBlocks();

        Block<T> currentBlock; 
		Block<T> previousBlock;
		String hashTarget = StringUtil.getDificultyString(miningDifficulty);

        for(int i=1; i < blocks.size(); i++) {

            currentBlock = blocks.get(i);
			previousBlock = blocks.get(i-1);

            //compare registered hash and calculated hash:
			if(!currentBlock.getId().equals(calculateHash(currentBlock)) ){
				return false;
			}

            //compare previous hash and registered previous hash
            if(!previousBlock.getId().equals(currentBlock.getPrevId()) ){
				return false;
			}

            //check if hash is solved
            if(!currentBlock.getId().substring( 0, miningDifficulty).equals(hashTarget)) {
				return false;
			}
        }

        return true;
    }

    protected AbstractBlockchainService<T> addBlock(AbstractBlock<T> block) {
        mineBlock(block);
        blockchain.getBlocks().add(block);
        return this;
    }

    protected String calculateHash(Block<T> block) {
        String data = String.format("%s:%s%s:%s", 
            block.getPrevId(),
            Long.toString(block.getCreatedAt()),
            Integer.toString(block.getNonce()),
            block.getPayload().getData().toString());
        String hash = CryptoUtil.sha256Hash(data);
        return hash;
    }

    private void mineBlock(AbstractBlock<T> block) {

        //Create a string with difficulty * "0" 
        String target = StringUtil.getDificultyString(miningDifficulty);
        
		while(!block.getId().substring( 0, miningDifficulty).equals(target)) {
			block.setNonce( block.getNonce() + 1);
			block.setId(calculateHash(block));
		}
        log.debug("Block Mined!!! : " + block.getId());
    }
}