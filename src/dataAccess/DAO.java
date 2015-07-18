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
			risultato.put(util.ResultKeys.ESITO, "true");
			
		} catch (ClassNotFoundException | SQLException e) {
			
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Connessione al DataBase fallita");
			
		}
		
		return risultato;
	}
	
	private void startConnectionTransaction(){
		
		try {
			connessione.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
    private void endConnectionTransaction(){
    	
    	try {
			connessione.commit();
			connessione.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
    
    private void connectionRollBack(){
    	
    	try {
			connessione.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	
    public HashMap<String, String> accesso(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		int id ;
		String username;
		String email;
		String nome;
		String cognome;
		String telefono;
		String residenza;
		String password;
		
		
		//check manager
		String queryUtente = "SELECT * FROM "+ SchemaDb.TAB_UTENTI +" WHERE (username = ? OR email = ?) AND password = ?";
		String queryManagerSis = "SELECT * FROM "+ SchemaDb.TAB_MANAGER_DI_SISTEMA +" WHERE username = ? AND password = ?";
		
		//String queryManagerDiSistema = "SELECT * FROM "+ SchemaDb.TAB_MANAGER_DI_SISTEMA +" where username = ? AND password = ?";
		
		
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("username"));
			istruzione.setString(2, inputParam.get("username"));
			istruzione.setString(3, inputParam.get("password"));
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.ESITO, "true");
				id = res.getInt("id");
				
				ResultSet resFiliale = null;
				
				if(checkUserType(id,"managerdifiliale")){					
					risultato.put(util.ResultKeys.TIPO_UTENTE, "Manager Di Filiale");
					
					String query = "SELECT * FROM "+SchemaDb.TAB_MANAGER_DI_FILIALE+" INNER JOIN "+SchemaDb.TAB_FILIALI+
							" ON id_filiale = "+SchemaDb.TAB_FILIALI+
							".id WHERE id_utente = ?";
					PreparedStatement istruzioneFiliale = connessione.prepareStatement(query);
					istruzioneFiliale.setInt(1, id);
					resFiliale = istruzioneFiliale.executeQuery();
				} else if (checkUserType(id,"dipendentedifiliale")){					
					risultato.put(util.ResultKeys.TIPO_UTENTE, "Dipendente Di Filiale");	
					String query = "SELECT * FROM "+SchemaDb.TAB_DIPENDENTI_DI_FILIALE+" INNER JOIN "+SchemaDb.TAB_FILIALI+
							" ON id_filiale = "+SchemaDb.TAB_FILIALI+
							".id WHERE id_utente = ?";
					PreparedStatement istruzioneFiliale = connessione.prepareStatement(query);
					istruzioneFiliale.setInt(1, id);
					resFiliale = istruzioneFiliale.executeQuery();
				} else {
					risultato.put(util.ResultKeys.TIPO_UTENTE, "utenteNonAssegnato");
				}
				
				if(resFiliale != null){
					id = res.getInt("id");
					username = res.getString("username");
					email = res.getString("email");
					nome = res.getString("nome");
					cognome = res.getString("cognome");
					telefono = res.getString("telefono");
					residenza = res.getString("residenza");
					password = res.getString("password");
					resFiliale.first();
					String id_filiale = resFiliale.getString("id_filiale");
					String nome_filiale = resFiliale.getString("nome");
					String telefono_filiale = resFiliale.getString("luogo");
					String luogo_filiale = resFiliale.getString("telefono");
					
					risultato.put("id", Integer.toString(id));
					risultato.put("username", username);
					risultato.put("email", email);
					risultato.put("nome", nome);
					risultato.put("cognome", cognome);
					risultato.put("telefono", telefono);
					risultato.put("residenza", residenza);
					risultato.put("password", password);
					risultato.put("id_filiale", id_filiale);
					risultato.put("nome_filiale", nome_filiale);
					risultato.put("luogo_filiale", luogo_filiale);
					risultato.put("telefono_filiale", telefono_filiale);
				}
				
			} else {
				
				istruzione = connessione.prepareStatement(queryManagerSis);
				istruzione.setString(1, inputParam.get("username"));
				istruzione.setString(2, inputParam.get("password"));
				res = istruzione.executeQuery();
				isUtente = res.first();
				
				if(isUtente){
					risultato.put(util.ResultKeys.TIPO_UTENTE, "Manager Di Sistema");
				} else {
					risultato.put(util.ResultKeys.ESITO, "false");
					risultato.put(util.ResultKeys.MSG_ERR, "Email o password errata");
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
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
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
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
				
				risultato.put(util.ResultKeys.ESITO, "true");

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
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
	public HashMap<String, String> getFiliali(){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
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
				
				risultato.put(util.ResultKeys.ESITO, "true");

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
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
	
	public HashMap<String, String> getManagers(){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryManagerDiFiliale = "SELECT * FROM "+ SchemaDb.TAB_UTENTI +
				" INNER JOIN "+ SchemaDb.TAB_MANAGER_DI_FILIALE +
				" ON " +SchemaDb.TAB_UTENTI+".id = " + SchemaDb.TAB_MANAGER_DI_FILIALE+".id_utente"+
				" RIGHT JOIN "+ SchemaDb.TAB_FILIALI+
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
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					id_utente = Integer.toString(res.getInt(SchemaDb.TAB_MANAGER_DI_FILIALE+".id_utente"));
					id_filiale = Integer.toString(res.getInt(SchemaDb.TAB_FILIALI+".id"));
					nome_filiale = res.getString(SchemaDb.TAB_FILIALI+".nome");
					username_manager = res.getString(SchemaDb.TAB_UTENTI+".username");		
					
					risultato.put("id_utente" + Integer.toString(pos), id_utente==null?"0":id_utente);
					risultato.put("id_filiale" + Integer.toString(pos), id_filiale);
					risultato.put("nome" + Integer.toString(pos), nome_filiale);
					risultato.put("username" + Integer.toString(pos), username_manager==null?"Non Impostato":username_manager);
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
	
	
	
	public HashMap<String, String> getAuto(HashMap<String, String> inputParam){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryAuto = "SELECT * FROM "+ SchemaDb.TAB_AUTO +
				" INNER JOIN "+ SchemaDb.TAB_FILIALI +
				" ON " +SchemaDb.TAB_AUTO+".id_filiale = " + SchemaDb.TAB_FILIALI+".id"+
				" INNER JOIN "+ SchemaDb.TAB_MODELLI+
				" ON " + SchemaDb.TAB_AUTO+".id_modello = "+SchemaDb.TAB_MODELLI+".id"+
				" INNER JOIN "+ SchemaDb.TAB_FASCE+
				" ON " + SchemaDb.TAB_MODELLI+".id_fascia = "+SchemaDb.TAB_FASCE+".id";
		
		if(inputParam.get("disponibilita").equals("disponibile")){
			queryAuto += " WHERE status = ? AND id_filiale = ?";
		} else if(inputParam.get("disponibilita").equals("non_disponibile")){
			queryAuto += " WHERE (NOT (status = ?)) AND id_filiale = ?";
		} else {
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "disponibilita non impostata");
		}
	
		String id;
		String nome_filiale;
		String modello;
		String targa;
		String status;
		String chilometraggio;
		String fasce;
		String provenienza;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			istruzione.setString(1, "DISPONIBILE");
			istruzione.setString(2, inputParam.get("id_filiale"));
			ResultSet res = istruzione.executeQuery();
			Boolean isAuto = res.first();
			
			if (isAuto){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					id=res.getString(SchemaDb.TAB_AUTO+".id");
					nome_filiale = res.getString(SchemaDb.TAB_FILIALI+".nome");
					modello = res.getString(SchemaDb.TAB_MODELLI+".nome");
					targa = res.getString(SchemaDb.TAB_AUTO+".targa");
					status=res.getString(SchemaDb.TAB_AUTO+".status");
					provenienza=res.getString(SchemaDb.TAB_AUTO+".provenienza");
					chilometraggio=res.getString(SchemaDb.TAB_AUTO+".chilometraggio");
					fasce=res.getString(SchemaDb.TAB_FASCE+".nome");

					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("nome_filiale" + Integer.toString(pos), nome_filiale);
					risultato.put("modello" + Integer.toString(pos), modello);
					risultato.put("targa" + Integer.toString(pos), targa);
					risultato.put("status" + Integer.toString(pos),status);
					risultato.put("provenienza" + Integer.toString(pos),provenienza);
					risultato.put("chilometraggio" + Integer.toString(pos),chilometraggio);
					risultato.put("fasce" + Integer.toString(pos),fasce);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
    public HashMap<String, String> setManager(HashMap<String, String> inputParam){
		
    	HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		int newManager = Integer.parseInt(inputParam.get("idUtente"));
		int idFiliale = Integer.parseInt(inputParam.get("idFiliale"));
		
		String queryGetManager =  "SELECT * FROM "+ SchemaDb.TAB_MANAGER_DI_FILIALE +
				" INNER JOIN "+ SchemaDb.TAB_FILIALI +
				" ON " + SchemaDb.TAB_MANAGER_DI_FILIALE+".id_filiale = "+SchemaDb.TAB_FILIALI+".id"+
				" WHERE " + SchemaDb.TAB_MANAGER_DI_FILIALE+".id_filiale = ?";
		
		String setDipendente = "INSERT INTO "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE +" (id_utente, id_filiale) VALUES (?, ?)";
		
		String deleteDipendente = "DELETE FROM "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE +" WHERE id_utente = ?";
		
		String updateManager = "UPDATE "+ SchemaDb.TAB_MANAGER_DI_FILIALE 
				+" SET id_utente = ? WHERE id_utente = ?";
		
		String setManager = "INSERT INTO "+ SchemaDb.TAB_MANAGER_DI_FILIALE +" (id_utente, id_filiale) VALUES (?, ?)";
			
		startConnectionTransaction();

		try {
			
			PreparedStatement istruzione = connessione.prepareStatement(queryGetManager);
			istruzione.setInt(1, idFiliale);
			ResultSet res = istruzione.executeQuery();
			Boolean hasManager = res.first();
			
			if (hasManager){
				int oldManager = res.getInt(SchemaDb.TAB_MANAGER_DI_FILIALE+".id_utente");
				
				istruzione = connessione.prepareStatement(updateManager);
				istruzione.setInt(1, newManager);
				istruzione.setInt(2, oldManager);
				istruzione.execute();
				
				istruzione = connessione.prepareStatement(setDipendente);
				istruzione.setInt(1, oldManager);
				istruzione.setInt(2, idFiliale);
				istruzione.execute();
				
			}else{
				
				PreparedStatement istruzione1 = connessione.prepareStatement(setManager);
				istruzione1.setInt(1, newManager);
				istruzione1.setInt(2, idFiliale);
				istruzione1.execute();
			}
			
			istruzione = connessione.prepareStatement(deleteDipendente);
			istruzione.setInt(1, newManager);
			istruzione.execute();
			
			endConnectionTransaction();
			risultato.put(util.ResultKeys.ESITO, "true");
			
		} catch (SQLException e) {
			e.printStackTrace();
			connectionRollBack();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
    public HashMap<String, String> updateProfile(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "UPDATE "+ SchemaDb.TAB_UTENTI +" SET username=?,email=?,password=?,nome=?,cognome=?,telefono=?,residenza=? "+
							"WHERE id = ?";

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("username"));
			istruzione.setString(2, inputParam.get("email"));
			istruzione.setString(3, inputParam.get("password"));
			istruzione.setString(4, inputParam.get("nome"));
			istruzione.setString(5, inputParam.get("cognome"));
			istruzione.setString(6, inputParam.get("telefono"));
			istruzione.setString(7, inputParam.get("residenza"));
			istruzione.setString(8, inputParam.get("id"));
			istruzione.execute();
			
			risultato.put(util.ResultKeys.ESITO, "true");

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Username o Email già utilizzati");
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;

	}
    
    public HashMap<String, String> registrazione(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "INSERT INTO "+ SchemaDb.TAB_UTENTI +" (username, email, password) VALUES (?, ?, ?)";

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("username"));
			istruzione.setString(2, inputParam.get("email"));
			istruzione.setString(3, inputParam.get("password"));
			istruzione.execute();
			
			risultato.put(util.ResultKeys.ESITO, "true");

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Username o Email già utilizzati");
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;

	}
    
    public HashMap<String, String> assumiDipendente(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "INSERT INTO "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE +" (id_utente, id_filiale) VALUES (?, ?)";

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("id_utente"));
			istruzione.setString(2, inputParam.get("id_filiale"));
			istruzione.execute();
			
			risultato.put(util.ResultKeys.ESITO, "true");
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;

	}
    
    public HashMap<String, String> licenziaDipendente(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "DELETE FROM "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE +" WHERE id_utente = ?";

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("id_utente"));
			istruzione.execute();
			
			risultato.put(util.ResultKeys.ESITO, "true");
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;

	}

    public HashMap<String, String> getFasce(){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryAuto = "SELECT * FROM "+ SchemaDb.TAB_FASCE + " INNER JOIN "+SchemaDb.TAB_PREZZI+" ON id_prezzi = "+
				SchemaDb.TAB_PREZZI+".id";
	
		String nome_fascia;
		String id_prezzi;
		String tariffa_base_g;
		String tariffa_base_s;
		String costo_chilometrico;
		String penale_chilometri;
		String tariffa_illim_g;
		String tariffa_illim_s;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			ResultSet res = istruzione.executeQuery();
			Boolean isFascia = res.first();
			
			if (isFascia){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					nome_fascia = res.getString("nome");
					id_prezzi= res.getString("nome");
					tariffa_base_g= res.getString("nome");
					tariffa_base_s= res.getString("nome");
					costo_chilometrico= res.getString("nome");
					penale_chilometri= res.getString("nome");
					tariffa_illim_g= res.getString("nome");
					tariffa_illim_s= res.getString("nome");
					
					risultato.put("nome_fascia" + Integer.toString(pos), nome_fascia);
					risultato.put("id_prezzi" + Integer.toString(pos), id_prezzi);
					risultato.put("tariffa_base_g" + Integer.toString(pos), tariffa_base_g);
					risultato.put("tariffa_base_s" + Integer.toString(pos),tariffa_base_s);
					risultato.put("costo_chilometrico" + Integer.toString(pos),costo_chilometrico);
					risultato.put("penale_chilometri" + Integer.toString(pos),penale_chilometri);
					risultato.put("tariffa_illim_g" + Integer.toString(pos),tariffa_illim_g);
					risultato.put("tariffa_illim_s" + Integer.toString(pos),tariffa_illim_s);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
    
    public HashMap<String, String> getModelli(HashMap<String, String> inputParam){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryAuto = "SELECT * FROM "+ SchemaDb.TAB_MODELLI +" INNER JOIN "+SchemaDb.TAB_FASCE+" ON id_fascia = "+
				SchemaDb.TAB_FASCE+".id WHERE "+SchemaDb.TAB_FASCE+".nome = ?";
	
		String nome_modello;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			istruzione.setString(1, inputParam.get("nome_fascia"));
			ResultSet res = istruzione.executeQuery();
			Boolean isModello = res.first();
			
			if (isModello){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					nome_modello = res.getString("nome");
					
					risultato.put("nome_modello" + Integer.toString(pos), nome_modello);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}

	




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// METODI DANIELE
	
    public HashMap<String, String> getFreeUsers(){
			
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		String queryUtente = "SELECT * FROM "+SchemaDb.TAB_UTENTI+" WHERE id NOT IN(SELECT id_utente FROM "+SchemaDb.TAB_MANAGER_DI_FILIALE+") AND id NOT IN(SELECT id_utente FROM "+SchemaDb.TAB_DIPENDENTI_DI_FILIALE+")";
		
				
				//SELECT * FROM utenti where id NOT IN (select id_utente from manager_di_filiale) 
				//and id NOT IN ( select id_utente from dipendenti_di_filiale)
		String id;
		String username;
		String email;
		String nome;
		String cognome;
		String telefono;
		String residenza;
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					id = res.getString("id");
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
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
			
	}
		
		
	
    public HashMap<String, String> getContratto(){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryAuto = "SELECT * FROM "+ SchemaDb.TAB_CONTRATTI +			
				" INNER JOIN "+ SchemaDb.TAB_AUTO+
				" ON " + SchemaDb.TAB_CONTRATTI+".id_auto = "+SchemaDb.TAB_AUTO+".id"+
				" INNER JOIN "+SchemaDb.TAB_MODELLI+
				" ON " + SchemaDb.TAB_AUTO +".id_modello = "+SchemaDb.TAB_MODELLI+".id"+
				" INNER JOIN "+ SchemaDb.TAB_CLIENTI+
				" ON " + SchemaDb.TAB_CONTRATTI+".id_cliente = "+SchemaDb.TAB_CLIENTI+".id"+
				" INNER JOIN "+SchemaDb.TAB_UTENTI+
				" ON " + SchemaDb.TAB_CONTRATTI+".id_dipendente = "+SchemaDb.TAB_UTENTI+".id";
	
		 String id;
		 String tipoKm;
		 String tariffa;
		 String dataInizio;
		 String dataLimite;
		 String dataRientro;
		 String acconto;
		 String stato;
		 String idCliente;
		 String idDipendente;
		 String idAuto;
		 String totPrezzo;
		 String filialeDiPartenza;
		 String filialeDiArrivo;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			ResultSet res = istruzione.executeQuery();
			Boolean isAuto = res.first();
			
			if (isAuto){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					tipoKm = res.getString(SchemaDb.TAB_CONTRATTI+".tipo_km");
					tariffa = res.getString(SchemaDb.TAB_CONTRATTI+".tariffa");
					dataInizio=res.getString(SchemaDb.TAB_CONTRATTI+".data_inizio");
					dataLimite=res.getString(SchemaDb.TAB_CONTRATTI+".data_limite");
					dataRientro=res.getString(SchemaDb.TAB_CONTRATTI+".data_rientro");
					acconto=res.getString(SchemaDb.TAB_CONTRATTI+".acconto");
					stato=res.getString(SchemaDb.TAB_CONTRATTI+".stato");
					idCliente=res.getString(SchemaDb.TAB_CLIENTI+".nome");
					idDipendente=res.getString(SchemaDb.TAB_UTENTI+".nome");
					idAuto = res.getString(SchemaDb.TAB_MODELLI+".nome");
					totPrezzo=res.getString(SchemaDb.TAB_CONTRATTI+".tot_prezzo");
					filialeDiPartenza=res.getString(SchemaDb.TAB_CONTRATTI+".filiale_di_partenza");
					filialeDiArrivo=res.getString(SchemaDb.TAB_CONTRATTI+".filiale_di_arrivo");
					
					risultato.put("tipoKm" + Integer.toString(pos), tipoKm);
					risultato.put("idAuto" + Integer.toString(pos), idAuto);
					risultato.put("tariffa" + Integer.toString(pos), tariffa);
					risultato.put("dataInizio" + Integer.toString(pos),dataInizio);
					risultato.put("dataLimite" + Integer.toString(pos),dataLimite);
					risultato.put("dataRientro" + Integer.toString(pos),dataRientro);
					risultato.put("acconto" + Integer.toString(pos),acconto);
					risultato.put("stato" + Integer.toString(pos),stato);
					risultato.put("idCliente" + Integer.toString(pos),idCliente);
					risultato.put("totPrezzo" + Integer.toString(pos),totPrezzo);
					risultato.put("filialeDiPartenza" + Integer.toString(pos),filialeDiPartenza);
					risultato.put("filialeDiArrivo" + Integer.toString(pos),filialeDiArrivo);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
