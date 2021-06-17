package com.cairone.bc.ui.ctrl.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class NewTxFrm {
    
    private String sender;

    private String recipient;

    private BigDecimal amount;

}
