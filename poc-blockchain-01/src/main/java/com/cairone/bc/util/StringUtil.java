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

    public static String toHexString(byte[] data) {

        if (data == null) {
            return null;
        }

        StringBuffer hexString = new StringBuffer(); // hash as hexidecimal

        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] toBytesArray(String data) {
        
        byte[] val = new byte[data.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(data.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }
}
