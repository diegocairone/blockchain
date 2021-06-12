package com.cairone.bc.cfg;

import com.cairone.bc.domain.StringBlockchain;
import com.cairone.bc.service.StringBlockchainService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockchainCfg {
    
    @Bean
    public StringBlockchain getBlockchain() {
        StringBlockchain blockchain = new StringBlockchain();
        return blockchain;
    }

    @Bean
    public StringBlockchainService getBlockchainService() {
        return new StringBlockchainService(getBlockchain());
    }
}