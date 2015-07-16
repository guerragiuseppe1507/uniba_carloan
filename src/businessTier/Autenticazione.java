package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class Autenticazione {
	public Autenticazione(){
	}
	
	
	public HashMap<String, String> login(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		DAO dao = new DAO();
		risultato = dao.accesso(inputParam);
		
		return risultato;
	}
	
	public HashMap<String, String> register(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		DAO dao = new DAO();
		risultato = dao.registrazione(inputParam);
		
		return risultato;
	}
	

}
