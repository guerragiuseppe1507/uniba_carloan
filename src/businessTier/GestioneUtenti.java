package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneUtenti {
	
	public GestioneUtenti(){}
	
	public HashMap<String, String> recuperoDatiUtenti(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getUtenti(inputParam);	
		return risultato;
		
	}
	
	public HashMap<String, String> recuperoDatiUtentiLiberi(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getFreeUsers();	
		return risultato;
		
	}
		
	public HashMap<String, String> recuperoDatiManagerDiFiliale(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getManagers();	
		return risultato;
		
	}
	
	public HashMap<String, String> setManagerDiFiliale(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.setManager(inputParam);	
		return risultato;
		
	}
}
