package com.cairone.bc.cfg;

import com.cairone.bc.service.KeyStoreService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletCfg {
    
    @Bean
    public KeyStoreService getKeyStoreService() {
        return new KeyStoreService();
    }
}
