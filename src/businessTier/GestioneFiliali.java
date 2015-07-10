package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneFiliali {
	
public GestioneFiliali(){}
	
	public HashMap<String, String> recuperoDatiFiliali(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'utente sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.getFiliali();	
		return risultato;
		
	}

}
