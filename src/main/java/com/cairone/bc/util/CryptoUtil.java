package com.cairone.bc.util;

import java.security.MessageDigest;

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
	        StringBuffer hexString = new StringBuffer(); // hash as hexidecimal

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
