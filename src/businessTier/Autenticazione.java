package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class Autenticazione {
	public Autenticazione(){
	}
	
	//--------------------------------Metodo Login------------------------------------//
	
	public HashMap<String, String> login(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'utente sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.accesso(inputParam);
		
		return risultato;
	}
	

}
