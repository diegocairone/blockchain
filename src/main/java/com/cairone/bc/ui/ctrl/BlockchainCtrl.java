package com.cairone.bc.ui.ctrl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cairone.bc.service.TxBlockchainService;
import com.cairone.bc.ui.response.BlockTxResponse;
import com.cairone.bc.util.CryptoUtil;
import com.cairone.bc.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainCtrl {
    
    @Autowired private TxBlockchainService blockchainService;

    @GetMapping("/api/blockchain")
    public ResponseEntity<List<BlockTxResponse>> getBlockchain() {

        final List<BlockTxResponse> items = new ArrayList<>();

        blockchainService.getBlocks().forEach(block -> {

            final String blockId = block.getId();
            final String blockPrevId = block.getPrevId();
            final Long blockCreatedAt = block.getCreatedAt();
            final Integer nonce = block.getNonce();
            
            block.getPayload().getData().forEach(tx -> {

                final String txId = tx.getId();
                final String sender = CryptoUtil.encodeToString(tx.getSender());
                final String recipient = CryptoUtil.encodeToString(tx.getRecipient());
                final BigDecimal amount = tx.getAmount();
                final String signature = StringUtil.toHexString(tx.getSignature());
                final LocalDateTime createdAt = tx.getCreatedAt();

                BlockTxResponse response = new BlockTxResponse(
                    blockId, 
                    blockPrevId, 
                    blockCreatedAt, 
                    nonce, 
                    txId, 
                    sender, 
                    recipient, 
                    amount, 
                    signature, 
                    createdAt);

                items.add(response);
            });
        });

        return ResponseEntity.ok(items);
    }

    @GetMapping("/api/blockchain/verify")
    public ResponseEntity<?> verifyBlockchain() {
        boolean isValid = blockchainService.validateBlockchain();
        return ResponseEntity.ok(isValid);
    }

}
