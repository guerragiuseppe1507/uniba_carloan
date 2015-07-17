package util;

import java.math.BigInteger;
import java.security.MessageDigest;

/*
 * Questa classe di utility svolge il compito di calcolare l'hash MD5 data una stringa.
 * Se l'operazione va a buon fine il metodo restituisce l'hash calcolato sotto forma di stringa.
 */

public class Md5Encrypter {
	
    public static String encrypt(String message) {
        
    	try{
    		
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(message.getBytes());
            return String.format("%032x",new BigInteger(1,m.digest()));
            
        }
        catch(Exception e){
        	
            return null;
        }
    }

}