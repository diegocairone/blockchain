package com.cairone.bc.ui.ctrl;

import java.util.Set;

import com.cairone.bc.service.KeyStoreService;
import com.cairone.bc.ui.response.BasicResponse;
import com.cairone.bc.ui.response.StringResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletCtrl {
    
    @Autowired private KeyStoreService keyStoreService;

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
}
