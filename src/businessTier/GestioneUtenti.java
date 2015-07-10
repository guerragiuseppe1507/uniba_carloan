package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneUtenti {
	
	public GestioneUtenti(){}
	
	public HashMap<String, String> recuperoDatiUtenti(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'utente sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.getUtenti(inputParam);	
		return risultato;
		
	}
	
	public HashMap<String, String> recuperoDatiManagerDiFiliale(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'utente sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.getManagers();	
		return risultato;
		
	}

}
