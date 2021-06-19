package com.cairone.bc.ui.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cairone.bc.domain.Tx;
import com.cairone.bc.util.CryptoUtil;
import com.cairone.bc.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxResponse {

    private String id;

    private String sender;

    private String recipient;

    private BigDecimal amount;

    private String signature;

    private LocalDateTime createdAt;

    public TxResponse(Tx tx) {
        this(
            tx.getId(),
            CryptoUtil.encodeToString(tx.getSender()),
            CryptoUtil.encodeToString(tx.getRecipient()),
            tx.getAmount(),
            StringUtil.toHexString(tx.getSignature()),
            tx.getCreatedAt()
        );
    }
}
