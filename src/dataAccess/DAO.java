package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.mysql.jdbc.exceptions.jdbc4.*;

public class DAO {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String NOME_DATABASE = "carloan";
	
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
			risultato.put(util.ResultKeys.MSG_ERR, util.ResultKeys.DB_CONN_ERR);
			
		}
		
		return risultato;
	}
	
	public boolean disconnetti() {
		try {
			connessione.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
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
			query = "SELECT * FROM " + SchemaDb.TAB_MANAGER_DI_FILIALE + " WHERE id_utente = ?";
		} else if(type.equalsIgnoreCase("dipendentedifiliale")) {
			query = "SELECT * FROM " + SchemaDb.TAB_DIPENDENTI_DI_FILIALE + " WHERE id_utente = ?";
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
		
		String queryUtente;
		
		if(inputParam.get("filter").equals("filiale")){
			queryUtente = "SELECT * FROM "+ SchemaDb.TAB_UTENTI + " , "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE +
					" WHERE "+ SchemaDb.TAB_UTENTI+".id = "+ SchemaDb.TAB_DIPENDENTI_DI_FILIALE+".id_utente"+
					" AND " + SchemaDb.TAB_DIPENDENTI_DI_FILIALE+".id_filiale = ?";
		} else {
			queryUtente = "SELECT * FROM "+SchemaDb.TAB_UTENTI+" WHERE id NOT IN(SELECT id_utente FROM "
					+ SchemaDb.TAB_MANAGER_DI_FILIALE+") AND id NOT IN(SELECT id_utente FROM "+SchemaDb.TAB_DIPENDENTI_DI_FILIALE+")";
		}
		
		String id;
		String username;
		String email;
		String nome;
		String cognome;
		String telefono;
		String residenza;
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			if(inputParam.get("filter").equals("filiale")){
				istruzione.setString(1, inputParam.get("idFiliale"));
			}
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					if(inputParam.get("filter").equals("filiale")){
						id = Integer.toString(res.getInt("id_utente"));
					} else {
						id = Integer.toString(res.getInt("id"));
					}
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
				risultato.put(util.ResultKeys.MSG_ERR, "Nessun utente trovato");
				
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
				risultato.put(util.ResultKeys.MSG_ERR, "Nessuna filiale trovata");
				
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
				risultato.put(util.ResultKeys.MSG_ERR, "Nessun manager trovato");
				
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
		} else if(inputParam.get("disponibilita").equals("singola")){
			queryAuto += " WHERE "+ SchemaDb.TAB_AUTO +".id = ?";
		}
	
		String id;
		String nome_filiale;
		String id_modello;
		String modello;
		String targa;
		String status;
		String chilometraggio;
		String fasce;
		String provenienza;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			if(inputParam.get("disponibilita").equals("singola")){
				istruzione.setString(1, inputParam.get("id_auto"));
			} else {
				istruzione.setString(1, "DISPONIBILE");
				istruzione.setString(2, inputParam.get("id_filiale"));
			}
			ResultSet res = istruzione.executeQuery();
			Boolean isAuto = res.first();
			
			if (isAuto){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					id=res.getString(SchemaDb.TAB_AUTO+".id");
					nome_filiale = res.getString(SchemaDb.TAB_FILIALI+".nome");
					id_modello = res.getString(SchemaDb.TAB_MODELLI+".id");
					modello = res.getString(SchemaDb.TAB_MODELLI+".nome");
					targa = res.getString(SchemaDb.TAB_AUTO+".targa");
					status=res.getString(SchemaDb.TAB_AUTO+".status");
					provenienza=res.getString(SchemaDb.TAB_AUTO+".provenienza");
					chilometraggio=res.getString(SchemaDb.TAB_AUTO+".chilometraggio");
					fasce=res.getString(SchemaDb.TAB_FASCE+".nome");

					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("nome_filiale" + Integer.toString(pos), nome_filiale);
					risultato.put("id_modello" + Integer.toString(pos), id_modello);
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
				risultato.put(util.ResultKeys.MSG_ERR, "Nessuna auto trovata");
				
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

		} catch (MySQLIntegrityConstraintViolationException e){
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Username o Email gi� utilizzati");
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

		} catch (MySQLIntegrityConstraintViolationException e){
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Username o Email gi� utilizzati");
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
	
		String id;
		String nome_fascia;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryAuto);
			ResultSet res = istruzione.executeQuery();
			Boolean isFascia = res.first();
			
			if (isFascia){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					id = res.getString("id");
					nome_fascia = res.getString("nome");
					
					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("nome_fascia" + Integer.toString(pos), nome_fascia);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				risultato.put(util.ResultKeys.MSG_ERR, "Nessuna fascia trovata");
				
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
	
		String id;
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
					
					id = res.getString("id");
					nome_modello = res.getString("nome");
					
					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("nome_modello" + Integer.toString(pos), nome_modello);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				risultato.put(util.ResultKeys.MSG_ERR, "Nessun modello trovato per la fascia "+inputParam.get("nome_fascia"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
    public HashMap<String, String> getContratto(HashMap<String,String> inputParam){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryContratto = "SELECT * FROM "+ SchemaDb.TAB_CONTRATTI +			
				" INNER JOIN "+ SchemaDb.TAB_AUTO+
				" ON " + SchemaDb.TAB_CONTRATTI+".id_auto = "+SchemaDb.TAB_AUTO+".id"+
				" INNER JOIN "+SchemaDb.TAB_MODELLI+
				" ON " + SchemaDb.TAB_AUTO +".id_modello = "+SchemaDb.TAB_MODELLI+".id"+
				" INNER JOIN "+ SchemaDb.TAB_CLIENTI+
				" ON " + SchemaDb.TAB_CONTRATTI+".id_cliente = "+SchemaDb.TAB_CLIENTI+".id"+
				" INNER JOIN "+SchemaDb.TAB_UTENTI+
				" ON " + SchemaDb.TAB_CONTRATTI+".id_dipendente = "+SchemaDb.TAB_UTENTI+".id"+
				" INNER JOIN "+SchemaDb.TAB_FILIALI+
				" ON " + SchemaDb.TAB_CONTRATTI+".filiale_di_partenza = "+SchemaDb.TAB_FILIALI+".id"+
				" INNER JOIN "+SchemaDb.TAB_FILIALI+ " AS filiali1" +
				" ON " + SchemaDb.TAB_CONTRATTI+".filiale_di_arrivo = filiali1.id";
		
		if(inputParam.get("filter").equals("GESTIONE_FILIALE")){
			
			queryContratto += " WHERE "+ SchemaDb.TAB_CONTRATTI+".id_dipendente != ? "+
							"AND ("+ SchemaDb.TAB_CONTRATTI +".filiale_di_partenza = ? " +
							"OR "+ SchemaDb.TAB_CONTRATTI +".filiale_di_arrivo = ?)";
			
			if(!inputParam.get("status").equals("TUTTI")){
				queryContratto += " AND "+ SchemaDb.TAB_CONTRATTI+".stato = '"+ inputParam.get("status")+"'";
			}
			
		} else if(inputParam.get("filter").equals("CONTRATTI_FILIALE")){
			
			queryContratto += " WHERE "+ SchemaDb.TAB_CONTRATTI+".id_dipendente != ? "+
							" AND (" + SchemaDb.TAB_CONTRATTI +".filiale_di_arrivo = ? "+
									" AND " + SchemaDb.TAB_CONTRATTI +".filiale_di_partenza != ?)";
			
			if(!inputParam.get("status").equals("TUTTI")){
				queryContratto += " AND "+ SchemaDb.TAB_CONTRATTI+".stato = '"+ inputParam.get("status")+"'";
			}
			
		} else if(inputParam.get("filter").equals("MIEI_CONTRATTI")){
			
			queryContratto += " WHERE "+ SchemaDb.TAB_CONTRATTI+".id_dipendente = ?"+
					"AND ("+ SchemaDb.TAB_CONTRATTI +".filiale_di_partenza = ?" +
					"OR "+ SchemaDb.TAB_CONTRATTI +".filiale_di_arrivo = ?)";
	
			if(!inputParam.get("status").equals("TUTTI")){
				queryContratto += " AND "+ SchemaDb.TAB_CONTRATTI+".stato = '"+ inputParam.get("status")+"'";
			}
			
		}
	
		String id;
		String id_cliente;
		String id_auto;
		String id_dipendente;
		String id_filiale_di_partenza;
		String id_filiale_di_arrivo;
		String tipoKm;
		String tariffa;
		String dataInizio;
		String dataLimite;
		String dataRientro;
		String acconto;
		String stato;
		String nomeDipendente;
		String totPrezzo;
		String filialeDiPartenza;
		String filialeDiArrivo;

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryContratto);
			istruzione.setString(1, inputParam.get("id_dipendente"));
			istruzione.setString(2, inputParam.get("id_filiale"));
			istruzione.setString(3, inputParam.get("id_filiale"));
			
			ResultSet res = istruzione.executeQuery();
			Boolean isAuto = res.first();
			
			if (isAuto){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					id = res.getString(SchemaDb.TAB_CONTRATTI+".id");
					id_cliente = res.getString(SchemaDb.TAB_CONTRATTI+".id_cliente");
					id_auto = res.getString(SchemaDb.TAB_CONTRATTI+".id_auto");
					id_dipendente = res.getString(SchemaDb.TAB_UTENTI+".id");
					id_filiale_di_partenza = res.getString(SchemaDb.TAB_FILIALI+".id");
					id_filiale_di_arrivo = res.getString("filiali1.id");
					tipoKm = res.getString(SchemaDb.TAB_CONTRATTI+".tipo_km");
					tariffa = res.getString(SchemaDb.TAB_CONTRATTI+".tariffa");
					dataInizio = res.getString(SchemaDb.TAB_CONTRATTI+".data_inizio");
					dataLimite = res.getString(SchemaDb.TAB_CONTRATTI+".data_limite");
					dataRientro = res.getString(SchemaDb.TAB_CONTRATTI+".data_rientro");
					acconto = res.getString(SchemaDb.TAB_CONTRATTI+".acconto");
					stato = res.getString(SchemaDb.TAB_CONTRATTI+".stato");
					nomeDipendente = res.getString(SchemaDb.TAB_UTENTI+".username");
					totPrezzo = res.getString(SchemaDb.TAB_CONTRATTI+".tot_prezzo");
					filialeDiPartenza = res.getString(SchemaDb.TAB_FILIALI+".nome");
					filialeDiArrivo = res.getString("filiali1.nome");
					
					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("id_cliente" + Integer.toString(pos), id_cliente);
					risultato.put("id_auto" + Integer.toString(pos), id_auto);
					risultato.put("id_dipendente" + Integer.toString(pos), id_dipendente);
					risultato.put("id_filiale_di_partenza" + Integer.toString(pos), id_filiale_di_partenza);
					risultato.put("id_filiale_di_arrivo" + Integer.toString(pos), id_filiale_di_arrivo);
					risultato.put("tipoKm" + Integer.toString(pos), tipoKm);
					risultato.put("tariffa" + Integer.toString(pos), tariffa);
					risultato.put("dataInizio" + Integer.toString(pos),dataInizio);
					risultato.put("dataLimite" + Integer.toString(pos),dataLimite);
					risultato.put("dataRientro" + Integer.toString(pos),dataRientro);
					risultato.put("dipendente" + Integer.toString(pos),nomeDipendente);
					risultato.put("acconto" + Integer.toString(pos),acconto);
					risultato.put("stato" + Integer.toString(pos),stato);
					risultato.put("totPrezzo" + Integer.toString(pos),totPrezzo);
					risultato.put("filialeDiPartenza" + Integer.toString(pos),filialeDiPartenza);
					risultato.put("filialeDiArrivo" + Integer.toString(pos),filialeDiArrivo);
					
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				risultato.put(util.ResultKeys.MSG_ERR, "Nessun contratto trovato");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
		
	}
	
	
	
    public HashMap<String, String> getClienti(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		String queryUtente = "SELECT * FROM "+ SchemaDb.TAB_CLIENTI;
		
		if(inputParam.get("filtro") != null && inputParam.get("filtro").equals("singolo")){
			queryUtente += " WHERE "+ SchemaDb.TAB_CLIENTI+".id = ?";
		}
								
		String id;
		String nome;
		String cognome;
		String email;
		String residenza;
		String dataDiNascita;
		String codiceFiscale;
		String codicePatente;
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			if(inputParam.get("filtro") != null && inputParam.get("filtro").equals("singolo")){
				istruzione.setString(1, inputParam.get("id_cliente"));
			} 
			ResultSet res = istruzione.executeQuery();
			Boolean isCliente = res.first();
			
			if (isCliente){
				
				risultato.put(util.ResultKeys.ESITO, "true");

				int pos = 0;
				do{
					
					id=res.getString(SchemaDb.TAB_CLIENTI+".id");
					nome = res.getString(SchemaDb.TAB_CLIENTI+".nome");
					cognome = res.getString(SchemaDb.TAB_CLIENTI+".cognome");
					email = res.getString(SchemaDb.TAB_CLIENTI+".mail");
					residenza = res.getString(SchemaDb.TAB_CLIENTI+".residenza");
					dataDiNascita = res.getString(SchemaDb.TAB_CLIENTI+".data_di_nascita");
					codiceFiscale=res.getString(SchemaDb.TAB_CLIENTI+".cod_fiscale");
					codicePatente=res.getString(SchemaDb.TAB_CLIENTI+".cod_patente");
					
					risultato.put("id" + Integer.toString(pos), id);
					risultato.put("nome" + Integer.toString(pos), nome);
					risultato.put("cognome" + Integer.toString(pos), cognome);
					risultato.put("email" + Integer.toString(pos), email);
					risultato.put("residenza" + Integer.toString(pos), residenza);
					risultato.put("dataDiNascita"+ Integer.toString(pos), dataDiNascita);
					risultato.put("codiceFiscale" + Integer.toString(pos), codiceFiscale);
					risultato.put("codicePatente" + Integer.toString(pos), codicePatente);
					pos++;
					
				}while(res.next());
				
				risultato.put(util.ResultKeys.RES_LENGTH, Integer.toString(pos));
				
			}else{
				
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.RES_LENGTH, "0");
				risultato.put(util.ResultKeys.MSG_ERR, "Nessun cliente trovato");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		return risultato;
	
		}

	
	public HashMap<String, String> inserisciCliente(HashMap<String,String> inputParam){
	
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "INSERT INTO "+ SchemaDb.TAB_CLIENTI +" (nome, cognome, mail, residenza, data_di_nascita, cod_fiscale , cod_patente) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("nome"));
			istruzione.setString(2, inputParam.get("cognome"));
			istruzione.setString(3, inputParam.get("mail"));
			istruzione.setString(4, inputParam.get("residenza"));
			istruzione.setString(5, inputParam.get("data_di_nascita"));
			istruzione.setString(6, inputParam.get("cod_fiscale"));
			istruzione.setString(7, inputParam.get("cod_patente"));
				
			istruzione.execute();
			
			risultato.put(util.ResultKeys.ESITO, "true");

		}catch (MySQLIntegrityConstraintViolationException e){
			String[] error = e.toString().split("'");
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Esiste gi� un cliente con \n" + error[3] + " : " + error[1]);
		}catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;

	}




	public HashMap<String, String> deleteAuto(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String deleteAuto= "DELETE FROM " +SchemaDb.TAB_AUTO + " WHERE id = ?";
	
		try {
			if(!checkAuto(inputParam.get("autoCanc"),"nofilter")){
				PreparedStatement istruzione = connessione.prepareStatement(deleteAuto);
				istruzione.setString(1, inputParam.get("autoCanc"));		
				istruzione.execute();
				
				risultato.put(util.ResultKeys.ESITO, "true");
			}else {
				risultato.put(util.ResultKeys.ESITO, "true");
				risultato.put(util.ResultKeys.MSG_ERR, "Auto utilizzata in un contratto");
			}
	
		} catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
	
	}
		
	private boolean checkAuto(String id_auto, String filter){
		
		boolean isAuto;
		String checkAuto = "";
		
		if (filter.equals("nofilter")){
			checkAuto = "SELECT * FROM " + SchemaDb.TAB_CONTRATTI + " WHERE id_auto = ?";
		} else if (filter.equals("noleggiata")){
			checkAuto = "SELECT * FROM " + SchemaDb.TAB_CONTRATTI + " WHERE id_auto = ? AND stato = 'APERTO'";
		}
		
		try{	
		
			PreparedStatement istruzione  = connessione.prepareStatement(checkAuto);
			istruzione.setString(1, id_auto);	
			ResultSet res = istruzione.executeQuery();
			isAuto = res.first();
			
		} catch (SQLException e){
				isAuto = false;
		}
		
		return isAuto;
		
	}
	
	public HashMap<String, String> changeAutoStatus(HashMap<String,String> inputParam){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String updateStatus="UPDATE "+ SchemaDb.TAB_AUTO
				+" SET status = ? WHERE id = ?";
	
		try {
			boolean runnable;
			if(inputParam.get("force").equals("true")){
				runnable = true;
			} else {
				runnable = !checkAuto(inputParam.get("id_auto"),"noleggiata");
			}
			if(runnable){
				PreparedStatement istruzione = connessione.prepareStatement(updateStatus);
				istruzione.setString(1, inputParam.get("new_status"));
				istruzione.setString(2, inputParam.get("id_auto"));
				istruzione.execute();
				
				risultato.put(util.ResultKeys.ESITO, "true");
			} else {
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.MSG_ERR, 
						"Lo stato di un auto in noleggio non puo essere cambiato.");
			}
	
		} catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
	}
		
	public HashMap<String, String> insertAuto(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String newAuto="INSERT INTO " + SchemaDb.TAB_AUTO
				+ " (id_modello, targa, id_filiale, status, chilometraggio, provenienza) VALUES (? ,?, ?, ?, ?, ?)";

		try {
			PreparedStatement istruzione = connessione.prepareStatement(newAuto);
			
			istruzione.setString(1, inputParam.get("id_modello"));
			istruzione.setString(2, inputParam.get("targa"));
			istruzione.setString(3, inputParam.get("id_filiale"));
			istruzione.setString(4, "DISPONIBILE");
			istruzione.setInt(5, 0);
			istruzione.setString(6, inputParam.get("nazione"));
			istruzione.execute();
			
			risultato.put(util.ResultKeys.ESITO, "true");

		}catch (MySQLIntegrityConstraintViolationException e){
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Auto gi� presente");
		}catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
	
	}	
		
		
	
	public HashMap<String, String> changeAutoTarga(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String updateStatus="UPDATE "+ SchemaDb.TAB_AUTO
				+" SET targa = ? WHERE id = ?";

		try {
			if(!checkAuto(inputParam.get("id_auto"),"noleggiata")){
				PreparedStatement istruzione = connessione.prepareStatement(updateStatus);
				istruzione.setString(1, inputParam.get("new_targa"));
				istruzione.setString(2, inputParam.get("id_auto"));
				istruzione.execute();
				
				risultato.put(util.ResultKeys.ESITO, "true");
			} else {
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.MSG_ERR, 
						"La targa di un auto in noleggio non puo essere cambiata.");
			}

		} catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
	}
	
	public HashMap<String, String> cambiaKmAuto(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String updateStatus="UPDATE "+ SchemaDb.TAB_AUTO
				+" SET chilometraggio = ? WHERE id = ?";

		try {
			if(!checkAuto(inputParam.get("id_auto"),"noleggiata")){
				PreparedStatement istruzione = connessione.prepareStatement(updateStatus);
				istruzione.setString(1, inputParam.get("nuovi_km"));
				istruzione.setString(2, inputParam.get("id_auto"));
				istruzione.execute();
				
				risultato.put(util.ResultKeys.ESITO, "true");
			} else {
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.MSG_ERR, 
						"I chilometri di un auto in noleggio non possono essere cambiati.");
			}

		} catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
	}
	

	public HashMap<String, String> getPrezzi(HashMap<String, String> inputParam) {
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryPrezzi =  "SELECT * FROM "+ SchemaDb.TAB_FASCE +
				" INNER JOIN "+ SchemaDb.TAB_PREZZI +
				" ON " + SchemaDb.TAB_FASCE+".id_prezzi = "+SchemaDb.TAB_PREZZI+".id"+
				" WHERE " + SchemaDb.TAB_FASCE+".nome = ?";
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryPrezzi);
			istruzione.setString(1, inputParam.get("nomeFascia"));
			ResultSet res = istruzione.executeQuery();
			Boolean isFascia = res.first();
			if(isFascia){
				String id_prezzi= res.getString(SchemaDb.TAB_PREZZI +".id");
				String tariffa_base_g= res.getString("tariffa_base_g");
				String tariffa_base_s= res.getString("tariffa_base_s");
				String costo_chilometrico= res.getString("costo_chilometrico");
				String penale_chilometri= res.getString("penale_chilometri");
				String tariffa_illim_g= res.getString("tariffa_illim_g");
				String tariffa_illim_s= res.getString("tariffa_illim_s");
				
				risultato.put("id_prezzi", id_prezzi);
				risultato.put("tariffa_base_g", tariffa_base_g);
				risultato.put("tariffa_base_s",tariffa_base_s);
				risultato.put("costo_chilometrico",costo_chilometrico);
				risultato.put("penale_chilometri",penale_chilometri);
				risultato.put("tariffa_illim_g",tariffa_illim_g);
				risultato.put("tariffa_illim_s",tariffa_illim_s);
				
				risultato.put(util.ResultKeys.ESITO, "true");
			} else {
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.MSG_ERR, 
						"Prezzi non trovati per la fascia " + inputParam.get("nomeFascia"));
			}

		} catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		
		return risultato;
	}
	
	public HashMap<String, String> inserisciContratto(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		HashMap<String, String> datiQueryAuto = new HashMap<String, String>();
		datiQueryAuto.put("new_status", inputParam.get("status_auto"));
		datiQueryAuto.put("id_auto", inputParam.get("id_auto"));
		datiQueryAuto.put("force", "false");
		
		String queryUtente = "INSERT INTO "+ SchemaDb.TAB_CONTRATTI 
								+" (tipo_km, tariffa, data_inizio, data_limite, acconto, stato, id_cliente,"
									+" id_dipendente, id_auto, filiale_di_partenza, filiale_di_arrivo)"
								+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("tipo_km"));
			istruzione.setString(2, inputParam.get("tariffa"));
			istruzione.setString(3, inputParam.get("data_inizio"));
			istruzione.setString(4, inputParam.get("data_limite"));
			istruzione.setString(5, inputParam.get("acconto"));
			istruzione.setString(6, inputParam.get("status_contratto"));
			istruzione.setString(7, inputParam.get("id_cliente"));
			istruzione.setString(8, inputParam.get("id_dipendente"));
			istruzione.setString(9, inputParam.get("id_auto"));
			istruzione.setString(10, inputParam.get("id_start_filiale"));
			istruzione.setString(11, inputParam.get("id_end_filiale"));	
			datiQueryAuto = changeAutoStatus(datiQueryAuto);
			if (datiQueryAuto.get(util.ResultKeys.ESITO).equals("true")){
				istruzione.execute();
				risultato.put(util.ResultKeys.ESITO, "true");
			} else {
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.MSG_ERR, "Auto gi� noleggiata");
			}	
		}catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		return risultato;
	}
	
	public HashMap<String, String> modificaContratto(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "UPDATE "+ SchemaDb.TAB_CONTRATTI 
								+" SET tipo_km = ?, tariffa = ?, data_inizio = ?, data_limite = ?, acconto = ?, stato = ?, id_cliente = ?,"
									+" id_dipendente = ?, id_auto = ?, filiale_di_partenza = ?, filiale_di_arrivo = ?"
								+" WHERE id = ?";
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("tipo_km"));
			istruzione.setString(2, inputParam.get("tariffa"));
			istruzione.setString(3, inputParam.get("data_inizio"));
			istruzione.setString(4, inputParam.get("data_limite"));
			istruzione.setString(5, inputParam.get("acconto"));
			istruzione.setString(6, inputParam.get("status_contratto"));
			istruzione.setString(7, inputParam.get("id_cliente"));
			istruzione.setString(8, inputParam.get("id_dipendente"));
			istruzione.setString(9, inputParam.get("id_auto"));
			istruzione.setString(10, inputParam.get("id_start_filiale"));
			istruzione.setString(11, inputParam.get("id_end_filiale"));	
			istruzione.setString(12, inputParam.get("id_contratto"));	
			
			istruzione.execute();
			risultato.put(util.ResultKeys.ESITO, "true");

		}catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		return risultato;
	}
	
	public HashMap<String, String> rimuoviContratto(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		HashMap<String, String> datiQueryAuto = new HashMap<String, String>();
		datiQueryAuto.put("new_status", inputParam.get("status_auto"));
		datiQueryAuto.put("id_auto", inputParam.get("id_auto"));
		datiQueryAuto.put("force", "true");
		
		String queryUtente = "DELETE FROM "+ SchemaDb.TAB_CONTRATTI 
								+" WHERE id = ?";
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("id_contratto"));
			
			datiQueryAuto = changeAutoStatus(datiQueryAuto);
			if (datiQueryAuto.get(util.ResultKeys.ESITO).equals("true")){
				istruzione.execute();
				risultato.put(util.ResultKeys.ESITO, "true");
			} else {
				risultato.put(util.ResultKeys.ESITO, "false");
				risultato.put(util.ResultKeys.MSG_ERR, "Errore auto");
			}	

		}catch (SQLException e){
			e.printStackTrace();
			risultato.put(util.ResultKeys.ESITO, "false");
			risultato.put(util.ResultKeys.MSG_ERR, "Errore Query");
		}
		return risultato;
	}
	
	public HashMap<String, String> chiudiContratto(HashMap<String,String> inputParam){
		HashMap<String, String> risultato = new HashMap<String, String>();
		risultato = connetti();
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("false")) return risultato;
		
		String queryUtente = "UPDATE "+ SchemaDb.TAB_CONTRATTI 
				+" SET data_rientro = ? , stato = ? , tot_prezzo = ? WHERE id = ?";
		
		String queryAuto = "UPDATE "+ SchemaDb.TAB_AUTO 
				+" SET id_filiale = ? , status = ? , chilometraggio = ? WHERE id = ?";
		
		startConnectionTransaction();

		try {
			
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("data_rientro"));
			istruzione.setString(2, inputParam.get("new_status_contratto"));
			istruzione.setString(3, inputParam.get("tot"));
			istruzione.setString(4, inputParam.get("id_contratto"));
			istruzione.execute();
			
			istruzione = connessione.prepareStatement(queryAuto);
			istruzione.setString(1, inputParam.get("new_filiale"));
			istruzione.setString(2, inputParam.get("new_status_auto"));
			istruzione.setString(3, inputParam.get("new_chilometraggio_auto"));
			istruzione.setString(4, inputParam.get("id_auto"));
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
	
}
