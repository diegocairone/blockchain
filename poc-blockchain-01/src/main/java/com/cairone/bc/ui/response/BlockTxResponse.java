package com.cairone.bc.ui.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockTxResponse {
    
    private String blockId;
    
    private String blockPrevId;
    
    private long blockCreatedAt;

    private int nonce;
    
    private String txId;

    private String sender;

    private String recipient;

    private BigDecimal amount;

    private String signature;

    private LocalDateTime createdAt;

}
