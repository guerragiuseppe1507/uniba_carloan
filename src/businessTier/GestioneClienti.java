	package businessTier;

	import java.util.HashMap;

import dataAccess.DAO;

	public class GestioneClienti {

		
		public HashMap<String, String> recuperoDatiClienti(HashMap<String, String> inputParam){
			
			HashMap<String, String> risultato = new HashMap<>();
			
			/* Accede al database per controllare che l'auto sia presente nel sistema
			 * e che le credenziali di accesso al sistema siano corrette.
			 */
			DAO dao = new DAO();
			risultato = dao.getClienti();	
			return risultato;
		
		
		}
		
		public HashMap<String, String> inserisciDatiCliente(HashMap<String, String> inputParam){
			
			HashMap<String, String> risultato = new HashMap<>();
			
			DAO dao = new DAO();
			risultato = dao.inserisciCliente(inputParam);
			
			return risultato;
		}
		
		
		
		
	}


