package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneContratti {

	
	public HashMap<String, String> recuperoDatiContratto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getContratto(inputParam);	
		return risultato;
	
	
	}
	
	public HashMap<String, String> stipulaContratto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.inserisciContratto(inputParam);	
		return risultato;
	
	}
	
	public HashMap<String, String> modificaContratto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.modificaContratto(inputParam);	
		return risultato;
	
	}
	
	public HashMap<String, String> annullaContratto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.rimuoviContratto(inputParam);	
		return risultato;
	
	}
	
	public HashMap<String, String> chiudiContratto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.chiudiContratto(inputParam);	
		return risultato;
	
	}
	
}
