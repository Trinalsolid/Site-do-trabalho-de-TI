package Service;

import java.security.*;
import java.math.*;

public class MD5 {
	
	public static String toMD5(String s) {
		String result="";
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			//System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16)); 
			result = (new BigInteger(1,m.digest()).toString(16));
		} catch (NoSuchAlgorithmException n) {
			System.out.println("ERRO : "+n.getMessage());
		}
	   
	   return result;
	}
	
}
