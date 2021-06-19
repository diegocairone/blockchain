package com.cairone.bc.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.cairone.bc.util.CryptoUtil;

public class KeyStoreService {
    
    private Map<String, KeyPair> keys;

    public KeyStoreService() {
        this.keys = new HashMap<>();
    }

    public Optional<KeyPair> findByPubKey(String pubKey) {
        return Optional.ofNullable(keys.get(pubKey));
    }

    public Set<String> listKeys() {
        return keys.keySet();
    }

    public String createKeyPair() {
        
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

			// Initialize the key generator and generate a KeyPair
            //256 bytes provides an acceptable security level
			keyGen.initialize(ecSpec, random);   

            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            String pubKeyAsString = CryptoUtil.encodeToString(publicKey);

            this.keys.put(pubKeyAsString, keyPair);

            return pubKeyAsString;

		}catch(Exception e) {
			throw new RuntimeException(e);
		}
    }
}
