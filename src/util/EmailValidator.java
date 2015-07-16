package util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;



/*
 * Questa classe di utility permette di validare secondo gli standard comuni la correttezza
 * di un indirizzo email, fornito al metodo come parametro di tipo String.
 * Il metodo restituirà un valore booleano che conferma o meno la validità.
 */


public class EmailValidator {
	
	public static boolean isValidEmailAddress(String email) {
	   boolean result = true;
	   try {
		   
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();
	      
	   } catch (AddressException ex) {
		   
	      result = false;
	   }
	   return result;
	}

}