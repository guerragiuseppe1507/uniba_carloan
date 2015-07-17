package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneContratti {

	
	public HashMap<String, String> recuperoDatiContratto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'auto sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.getContratto();	
		return risultato;
	
	
	}
	
	
	
	
}
