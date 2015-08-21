package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneAuto {
	
	public GestioneAuto(){};
	
	
	public HashMap<String, String> recuperoDatiAuto(HashMap<String, String> inputParam){
			
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getAuto(inputParam);	
		return risultato;
	}
	
	public HashMap<String, String> recuperoDatiFasce(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getFasce();	
		return risultato;
	}
	
	public HashMap<String, String> recuperoDatiModelli(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.getModelli(inputParam);	
		return risultato;
	}
	
	public HashMap<String, String> recuperoDatiPrezzi(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		DAO dao = new DAO();
		risultato = dao.getPrezzi(inputParam);	
		return risultato;
	}
		
	public HashMap<String, String> cancellaAuto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.deleteAuto(inputParam);	
		return risultato;
	}
	
	public HashMap<String, String> modificaStatus(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.changeStatus(inputParam);	
		return risultato;
	}
	
	
	public HashMap<String, String> modificaTarga(HashMap<String, String> inputParam){
			
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.changeTarga(inputParam);	
		return risultato;
	}
		
	public HashMap<String, String> inserisciAuto(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.insertAuto(inputParam);	
		return risultato;
	}
	
	
	
}

