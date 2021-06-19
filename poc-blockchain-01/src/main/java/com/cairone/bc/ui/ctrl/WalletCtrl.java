package com.cairone.bc.ui.ctrl;

import java.util.Set;

import com.cairone.bc.domain.Tx;
import com.cairone.bc.service.KeyStoreService;
import com.cairone.bc.service.TxService;
import com.cairone.bc.ui.ctrl.form.NewTxFrm;
import com.cairone.bc.ui.response.BasicResponse;
import com.cairone.bc.ui.response.StringResponse;
import com.cairone.bc.ui.response.TxResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletCtrl {
    
    @Autowired private KeyStoreService keyStoreService;
    @Autowired private TxService txService;

    @GetMapping("/keys")
    public ResponseEntity<BasicResponse<Set<String>>> listPubKeys() {
        Set<String> keys = keyStoreService.listKeys();
        BasicResponse<Set<String>> response = new BasicResponse<Set<String>>(keys);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/create")
    public ResponseEntity<StringResponse> generateKeyPair() {
        String pubKey = keyStoreService.createKeyPair();
        return ResponseEntity.ok(new StringResponse(pubKey));
    }

    @PostMapping("/transactions")
    public ResponseEntity<TxResponse> newTx(@RequestBody NewTxFrm newTxFrm) {
        Tx tx = txService.create(newTxFrm);
        TxResponse response = new TxResponse(tx);
        return ResponseEntity.ok(response);
    }
}
