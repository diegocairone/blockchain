package com.cairone.bc.cfg;

import com.cairone.bc.domain.TxBlockchain;
import com.cairone.bc.service.TxBlockchainService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockchainCfg {
    
    @Value("${app.miningDifficulty:2}")
    private int miningDifficulty;

    @Bean
    public TxBlockchain getBlockchain() {
        TxBlockchain blockchain = new TxBlockchain();
        return blockchain;
    }

    @Bean
    public TxBlockchainService getBlockchainService() {
        return new TxBlockchainService(getBlockchain(), miningDifficulty);
    }
}
