package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Statement;

public class DAO {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String NOME_DATABASE = "carloan";
	
	//private static long dataSistema = System.currentTimeMillis();
	//private static final int DUE_GIORNI_MILLIS = 172800000;
	private Connection connessione = null;
	
	private HashMap<String, String> connetti() {
		
		HashMap<String, String> risultato = new HashMap<String, String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connessione = DriverManager.getConnection(
					"jdbc:mysql://localhost/"	
					+ NOME_DATABASE, USERNAME, PASSWORD);	
			risultato.put("esito", "true");
			
		} catch (ClassNotFoundException | SQLException e) {
			
			risultato.put("esito", "false");
			risultato.put("msgErr", "Connessione al DataBase fallita");
			
		}
		
		return risultato;
	}

	
	public HashMap<String, String> accesso(HashMap<String,String> inputParam){
		
		HashMap<String, String> risultato = new HashMap<String, String>();

		risultato = connetti();
		
		//check manager
		String queryUtente = "SELECT * FROM "+ SchemaDb.TAB_UTENTI +" where (username = ? OR email = ?) AND password = ?";
		//String queryManagerDiSistema = "SELECT * FROM "+ SchemaDb.TAB_MANAGER_DI_SISTEMA +" where username = ? AND password = ?";
		
		try {
			PreparedStatement istruzione = connessione.prepareStatement(queryUtente);
			istruzione.setString(1, inputParam.get("username"));
			istruzione.setString(2, inputParam.get("username"));
			istruzione.setString(3, inputParam.get("password"));
			ResultSet res = istruzione.executeQuery();
			Boolean isUtente = res.first();
			
			if (isUtente){
				
				risultato.put("esito", "true");
				risultato.put("tipoUtente", "Loggato!!!");
				
			}else {
				risultato.put("esito", "false");
				risultato.put("msgErr", "Email o password errata");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			risultato.put("esito", "false");
			risultato.put("msgErr", "Errore Query");
		}
		
		return risultato;

	}
	
	// Registrazione Manager
	private int registraUtente(String username,String email,String password,String nome,String cognome, String telefono,String residenza){
		String query1= "INSERT INTO  "+ SchemaDb.TAB_UTENTI
				+" ( `username` ,  `email` ,  `password` ,  `nome` ,  `cognome` ,  `telefono` ,  `residenza` ) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement istruzione1 = connessione.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
			istruzione1.setString(1, username);
			istruzione1.setString(2, email);
			istruzione1.setString(3, password);
			istruzione1.setString(4, nome);
			istruzione1.setString(5, cognome);
			istruzione1.setString(6, telefono);
			istruzione1.setString(7, residenza);

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
		
		String query = "INSERT INTO " + SchemaDb.TAB_MANAGER_DI_FILIALE + " (id_utente, id_filiale) VALUES (?, ?)";
		
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
	
	public boolean registraManagerDiFiliale(String username,String email,String password,int id_filiale, String nome,String cognome, String telefono,String residenza){
		
		int id_utente = registraUtente(username, email, password, nome, cognome, telefono, residenza);
		boolean isSetted = setManagerDiFiliale(id_utente, id_filiale);
		return isSetted;
		
	}
	
}
