package com.cairone.bc.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class CryptoUtil {
    
    /**
     * Applies Sha256 to a string and returns the result. 
     * 
     * @param input
     * @return
     */
	public static String sha256Hash(String input){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
	        return StringUtil.toHexString(hash);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encodeToString(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static byte[] applyECDSASig(PrivateKey privateKey, String data) {
		return applyECDSASig(privateKey, data.getBytes());
	}

	public static byte[] applyECDSASig(PrivateKey privateKey, byte[] data) {
		
		Signature dsa;
		byte[] output = new byte[0];

		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			dsa.update(data);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		return verifyECDSASig(publicKey, data.getBytes(), signature);
	}
	
	public static boolean verifyECDSASig(PublicKey publicKey, byte[] data, byte[] signature) {
		
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data);
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
