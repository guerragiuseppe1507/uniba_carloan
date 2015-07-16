package businessTier;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/* Questa classe implementa il pattern Application Controller. Dopo aver ricevuto la richiesta
 * e i relativi parametri l'Application Controller decide dinamicamente (tramite reflection) a
 * quale servizio incaricare lo svolgimento del compito richiesto. Sarà poi lui a restituire ai
 * livelli superiori il risultato delle operazioni effettuate.
 * 
 */

public class ApplicationController {
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> execute(String[] comando, HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		try{
			
			Class<?> genericClass = Class.forName(comando[0]);
			Constructor<?> genericConstructor = genericClass.getConstructor();
			Object instance = genericConstructor.newInstance();
			Method method = genericClass.getMethod(comando[1],HashMap.class);
			Object risul = method.invoke(instance, (Object) inputParam);
			risultato = (HashMap<String,String>) risul;
			
		}
		catch(Exception e){
			
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, e.toString());
			
		}
		
		return risultato;
		
	}	
}
