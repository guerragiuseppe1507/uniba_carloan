package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.beans.property.StringProperty;

import com.mysql.jdbc.Statement;

public class DAO {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String NOME_DATABASE = "carloan";
	
	//private static long dataSistema = System.currentTimeMillis();
	//private static final int DUE_GIORNI_MILLIS = 172800000;
	private Connection connessione = null;
	
	public HashMap<String, String> connetti() {
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connessione = DriverManager.getConnection(
					"jdbc:mysql://localhost/"	
					+ NOME_DATABASE, USERNAME, PASSWORD);	
			risultato.put(util.ResultKeys.esito, "true");
			
		} catch (ClassNotFoundException | SQLException e) {
			
			risultato.put(util.ResultKeys.esito, "false");
			risultato.put(util.ResultKeys.msgErr, "Connessione al DataBase fallita");
			
		}
		
		return risultato;
	}

	
    public HashMap<String, String> accesso(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.esito).equalsIgnoreCase("false")) return risultato;
		
		//check manager
		String queryUtente = "SELECT * FROM "+ SchemaDb.TAB_UTENTI +" WHERE (username = ? OR email = ?) AND password = ?";
		String queryManagerSis = "SELECT * FROM "+ SchemaDb.TAB_MANAGER_DI_SISTEMA +" WHERE username = ? AND password = ?";
		
		//String queryManagerDiSistema = "SELECT * FROM "+ SchemaDb.TAB_MANAGER_DI_SISTEMA +" where username = ? AND password = ?";
		
		int id ;
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("username"));
			istruzione.setString(2, inputParam.get("username"));
			istruzione.setString(3, inputParam.get("password"));
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.esito, "true");
				
				id = res.getInt("id");
				
				if(checkUserType(id,"managerdifiliale")){					
					risultato.put(util.ResultKeys.tipoUtente, "managerFiliale");					
				} else if (checkUserType(id,"dipendentedifiliale")){					
					risultato.put(util.ResultKeys.tipoUtente, "dipendenteFiliale");					
				}
				
			} else {
				
				istruzione = connessione.prepareStatement(queryManagerSis);
				istruzione.setString(1, inputParam.get("username"));
				istruzione.setString(2, inputParam.get("password"));
				res = istruzione.executeQuery();
				isUtente = res.first();
				
				if(isUtente){
					risultato.put(util.ResultKeys.tipoUtente, "managerSistema");
				} else {
					risultato.put(util.ResultKeys.esito, "false");
					risultato.put(util.ResultKeys.msgErr, "Email o password errata");
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.esito, "false");
			risultato.put(util.ResultKeys.msgErr, "Errore Query");
		}
		
		return risultato;

	}
	
	private boolean checkUserType(int id, String type) throws SQLException{
		
		String query;
		if(type.equalsIgnoreCase("managerdifiliale")){
			query = "SELECT * FROM " + SchemaDb.TAB_MANAGER_DI_FILIALE + " WHERE id = ?";
		} else if(type.equalsIgnoreCase("dipendentedifiliale")) {
			query = "SELECT * FROM " + SchemaDb.TAB_DIPENDENTI_DI_FILIALE + " WHERE id = ?";
		} else {
			return false;
		}
		
		PreparedStatement istruzione = connessione.prepareStatement(query);
		istruzione.setInt(1, id);
		ResultSet res = istruzione.executeQuery();
		return res.first();
	}
	
	public HashMap<String, String> getUtenti(HashMap<String, String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.esito).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "SELECT * FROM "+ SchemaDb.TAB_UTENTI + " , "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE +
				" WHERE "+ SchemaDb.TAB_UTENTI+".id = "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE+".id_utente"+
				" AND " + SchemaDb.TAB_DIPENDENTI_DI_FILIALE+".id_filiale = ?";
		
		String id;
		String username;
		String email;
		String nome;
		String cognome;
		String telefono;
		String residenza;
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("idFiliale"));
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.esito, "true");

				int pos = 0;
				do{
					
					id = Integer.toString(res.getInt("id_utente"));
					username = res.getString("username");
					email = res.getString("email");
					nome = res.getString("nome");
					cognome = res.getString("cognome");
					telefono = res.getString("telefono");
					residenza = res.getString("residenza");
					
					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("username" + Integer.toString(pos), username);
					risultato.put("email" + Integer.toString(pos), email);
					risultato.put("nome" + Integer.toString(pos), nome);
					risultato.put("cognome" + Integer.toString(pos), cognome);
					risultato.put("telefono" + Integer.toString(pos), telefono);
					risultato.put("residenza" + Integer.toString(pos), residenza);
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.resLength, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.esito, "false");
				risultato.put(util.ResultKeys.resLength, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.esito, "false");
			risultato.put(util.ResultKeys.msgErr, "Errore Query");
		}
		
		return risultato;
		
	}
	
	public HashMap<String, String> getFiliali(){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.esito).equalsIgnoreCase("false")) return risultato;
		
		String queryFiliale = "SELECT * FROM "+ SchemaDb.TAB_FILIALI;
		
		String id;
		String nome;
		String luogo;
		String telefono;
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryFiliale);
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.esito, "true");

				int pos = 0;
				do{
					
					id = Integer.toString(res.getInt("id"));
					nome = res.getString("nome");
					luogo = res.getString("luogo");
					telefono = res.getString("telefono");
					
					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("nome" + Integer.toString(pos), nome);
					risultato.put("luogo" + Integer.toString(pos), luogo);
					risultato.put("telefono" + Integer.toString(pos), telefono);
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.resLength, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.esito, "false");
				risultato.put(util.ResultKeys.resLength, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.esito, "false");
			risultato.put(util.ResultKeys.msgErr, "Errore Query");
		}
		
		return risultato;
		
	}
	
	
	public HashMap<String, String> getManagers(){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.esito).equalsIgnoreCase("false")) return risultato;
		
		String queryManagerDiFiliale = "SELECT * FROM "+ SchemaDb.TAB_UTENTI +
				" INNER JOIN "+ SchemaDb.TAB_MANAGER_DI_FILIALE +
				" ON " +SchemaDb.TAB_UTENTI+".id = " + SchemaDb.TAB_MANAGER_DI_FILIALE+".id_utente"+
				" INNER JOIN "+ SchemaDb.TAB_FILIALI+
				" ON " + SchemaDb.TAB_MANAGER_DI_FILIALE+".id_filiale = "+SchemaDb.TAB_FILIALI+".id";
		
		String id_utente;
		String id_filiale;
		String nome_filiale;
		String username_manager;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryManagerDiFiliale);
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.esito, "true");

				int pos = 0;
				do{
					
					id_utente = Integer.toString(res.getInt(SchemaDb.TAB_MANAGER_DI_FILIALE+".id_utente"));
					id_filiale = Integer.toString(res.getInt(SchemaDb.TAB_MANAGER_DI_FILIALE+".id_filiale"));
					nome_filiale = res.getString(SchemaDb.TAB_FILIALI+".nome");
					username_manager = res.getString(SchemaDb.TAB_UTENTI+".username");		
					
					risultato.put("id_utente" + Integer.toString(pos), id_utente);
					risultato.put("id_filiale" + Integer.toString(pos), id_filiale);
					risultato.put("nome" + Integer.toString(pos), nome_filiale);
					risultato.put("username" + Integer.toString(pos), username_manager);
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.resLength, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.esito, "false");
				risultato.put(util.ResultKeys.resLength, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.esito, "false");
			risultato.put(util.ResultKeys.msgErr, "Errore Query");
		}
		
		return risultato;
		
	}
	
	
	
	
	public HashMap<String, String> getAuto(){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.esito).equalsIgnoreCase("false")) return risultato;
		
		String queryAuto = "SELECT * FROM "+ SchemaDb.TAB_AUTO +
				" INNER JOIN "+ SchemaDb.TAB_FILIALI +
				" ON " +SchemaDb.TAB_AUTO+".id_filiale = " + SchemaDb.TAB_FILIALI+".id"+
				" INNER JOIN "+ SchemaDb.TAB_MODELLI+
				" ON " + SchemaDb.TAB_AUTO+".id_modello = "+SchemaDb.TAB_MODELLI+".id"+
				" INNER JOIN "+ SchemaDb.TAB_FASCE+
				" ON " + SchemaDb.TAB_MODELLI+".id_fascia = "+SchemaDb.TAB_FASCE+".id";
		String id_Auto;
		String nome_filiale;
		String id_Modello;
		String Targa;
		String Status;
		String chilometraggio;
		String Fasce;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			ResultSet res = istruzione.executeQuery();
			Boolean isAuto = res.first();
			
			if (isAuto){
				
				risultato.put(util.ResultKeys.esito, "true");

				int pos = 0;
				do{
					
					id_Auto = Integer.toString(res.getInt(SchemaDb.TAB_AUTO+".id"));
					nome_filiale = Integer.toString(res.getInt(SchemaDb.TAB_AUTO+".id_filiale"));
					id_Modello = res.getString(SchemaDb.TAB_AUTO+".id_modello");
					Targa = res.getString(SchemaDb.TAB_AUTO+".targa");
					Status=res.getString(SchemaDb.TAB_AUTO+".status");
					chilometraggio=res.getString(SchemaDb.TAB_AUTO+".chilometraggio");
					Fasce=res.getString(SchemaDb.TAB_FILIALI+".nome");
					
					risultato.put("id_auto" + Integer.toString(pos), id_Auto);
					risultato.put("nome_filiale" + Integer.toString(pos), nome_filiale);
					risultato.put("id_modello" + Integer.toString(pos), id_Modello);
					risultato.put("targa" + Integer.toString(pos), Targa);
					risultato.put("status" + Integer.toString(pos),Status);
					risultato.put("chilometraggio" + Integer.toString(pos),chilometraggio);
					risultato.put("fasce" + Integer.toString(pos),Fasce);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.resLength, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.esito, "false");
				risultato.put(util.ResultKeys.resLength, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.esito, "false");
			risultato.put(util.ResultKeys.msgErr, "Errore Query");
		}
		
		return risultato;
		
	}
	
	// Registrazione Manager
	private int registraManager(int id, int id_utente, int id_filiale){
		String query1= "INSERT INTO manager_di_filiale( `id` ,  `id_utente` ,  `id_filiale` ) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement istruzione1 = connessione.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
			istruzione1.setInt(1, id);
			istruzione1.setInt(2, id_utente);
			istruzione1.setInt(3, id_filiale);

			istruzione1.execute();
			ResultSet keys = istruzione1.getGeneratedKeys();
			
			return 1;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}
// Registrazione Dipendente
private int registraDipendente(String username,String email,String password,String nome,String cognome,int filiale, String telefono,String residenza){
	String query1= "INSERT INTO  "+ SchemaDb.TAB_UTENTI
			+" ( `username` ,  `email` ,  `password` ,  `nome` ,  `cognome` , 'filiale, `telefono` ,  `residenza` ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	try {
		PreparedStatement istruzione1 = connessione.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
		istruzione1.setString(1, username);
		istruzione1.setString(2, email);
		istruzione1.setString(3, password);
		istruzione1.setString(4, nome);
		istruzione1.setString(5, cognome);
		istruzione1.setInt(6, filiale);
		istruzione1.setString(7, telefono);
		istruzione1.setString(8, residenza);

		istruzione1.execute();
		ResultSet keys = istruzione1.getGeneratedKeys();
		
		int id_utente = 0;
		
		if(keys.next()){
			
			id_utente = keys.getInt(1);
			
		}
		
		return id_utente;
	}
	catch (SQLException e) {
		e.printStackTrace();
		return 0;
	}

	//todo 
	//Inserire qui sotto le query al db
	
	}
	
	private boolean setManagerDiFiliale(int id_utente, int id_filiale){
		
		String query = "INSERT INTO manager_di_filiale(id_utente, id_filiale) VALUES (?, ?)";
		
		try {
			PreparedStatement istruzione1 = connessione.prepareStatement(query);
			istruzione1.setInt(1, id_utente);
			istruzione1.setInt(2, id_filiale);

			istruzione1.execute();
			return true;	
		} 	
		catch (SQLException e) {
				
			e.printStackTrace();
			return false;
		}
		
	}
	
    private boolean setDipendente(int id_utente){
		
		String query = "INSERT INTO " + SchemaDb.TAB_MANAGER_DI_FILIALE + " (id_utente, id_filiale) VALUES (?, ?)";
		
		try {
			PreparedStatement istruzione1 = connessione.prepareStatement(query);
			istruzione1.setInt(1, id_utente);

			istruzione1.execute();
			return true;	
		} 	
		catch (SQLException e) {
				
			e.printStackTrace();
			return false;
		}
		
	}


	public boolean registraManagerDiFiliale(int id, int id_utente, int id_filiale){
		
		int utente = registraManager(id, id_utente, id_filiale);
		boolean isSetted = setManagerDiFiliale(id_utente, id_filiale);
		return isSetted;
		
	}


	public boolean dipendent(String username, String email, String password,String nome, String cognome, int filiale, String telefono, String residenza) {
		// TODO Auto-generated method stub
		int id_utente = registraDipendente(username, email, password, nome, cognome, filiale, telefono, residenza);	                               
		boolean isSetted = setDipendente(id_utente);
		return isSetted;

	}
	
}
