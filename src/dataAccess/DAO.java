package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String NOME_DATABASE = "carloan";
	
	//private static long dataSistema = System.currentTimeMillis();
	//private static final int DUE_GIORNI_MILLIS = 172800000;
	private Connection connessione;
	
	public void connetti() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connessione = DriverManager.getConnection(
					"jdbc:mysql://localhost/"	
					+ NOME_DATABASE, USERNAME, PASSWORD);	
		} catch (ClassNotFoundException | SQLException e) {
			throw new EccezioneConnessioneDatabase("non connesso");
		}
	}
	
	public boolean disconnetti(){
		try{
			connessione.close();
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	
	//todo 
	//Inserire qui sotto le query al db
	

}
