package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneAuto {
	
	public GestioneAuto(){};
	
	
	public HashMap<String, String> recuperoDatiAuto(HashMap<String, String> inputParam){
			
			HashMap<String, String> risultato = new HashMap<>();
			
			/* Accede al database per controllare che l'auto sia presente nel sistema
			 * e che le credenziali di accesso al sistema siano corrette.
			 */
			DAO dao = new DAO();
			risultato = dao.getAuto(inputParam);	
			return risultato;
	}
	
	public HashMap<String, String> recuperoDatiFasce(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'auto sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.getFasce();	
		return risultato;
	}
	
	public HashMap<String, String> recuperoDatiModelli(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'auto sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.getModelli(inputParam);	
		return risultato;
	}
		
	public HashMap<String, String> cancellaAuto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		/* Accede al database per controllare che l'auto sia presente nel sistema
		 * e che le credenziali di accesso al sistema siano corrette.
		 */
		DAO dao = new DAO();
		risultato = dao.deleteAuto(inputParam);	
		return risultato;
	}
	
	
}

