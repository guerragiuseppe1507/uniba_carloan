package businessTier;

import java.util.HashMap;

import dataAccess.DAO;

public class GestioneFiliali {
	
public GestioneFiliali(){}
	
	public HashMap<String, String> recuperoDatiFiliali(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();
		
		DAO dao = new DAO();
		risultato = dao.getFiliali();	
		return risultato;
		
	}
	
	public HashMap<String, String> assumi(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<>();

		DAO dao = new DAO();
		risultato = dao.assumiDipendente(inputParam);	
		return risultato;
		
	}

	public HashMap<String, String> licenzia(HashMap<String, String> inputParam){
	
	HashMap<String, String> risultato = new HashMap<>();

	DAO dao = new DAO();
	risultato = dao.licenziaDipendente(inputParam);	
	return risultato;
	
}

}
