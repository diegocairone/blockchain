package com.cairone.bc.util;

public class StringUtil {
    
    /**
     * Returns difficulty string target, to compare to hash. 
     * eg difficulty of 5 will return "00000".
     * 
     * @param difficulty
     * @return
     */
	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}
}
