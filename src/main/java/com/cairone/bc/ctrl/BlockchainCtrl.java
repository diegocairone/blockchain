package com.cairone.bc.ctrl;

import com.cairone.bc.domain.StringBlock;
import com.cairone.bc.domain.StringBlockchain;
import com.cairone.bc.service.StringBlockchainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainCtrl {
    
    @Autowired private StringBlockchainService blockchainService;

    @GetMapping("/api/blockchain")
    public ResponseEntity<StringBlockchain> getBlockchain() {
        return ResponseEntity.ok(blockchainService.getChain());
    }

    @PostMapping("/api/blockchain")
    public ResponseEntity<StringBlock> newBlock(String data) {
        
        StringBlock block = blockchainService.newBlock(data);
        return ResponseEntity.ok(block);
    }
}
