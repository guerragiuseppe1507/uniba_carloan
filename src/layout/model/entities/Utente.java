package layout.model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utente implements CarloanEntity, Comparable<Utente>{
	
	private int id;
	private StringProperty username;
	private StringProperty email;
	private StringProperty nome;
	private StringProperty cognome;
	private StringProperty telefono;
	private StringProperty residenza;
	private Filiale filiale;
	public static String[] properties = {"username", "email", "nome", "cognome", "telefono", "residenza"};
	
	public Utente(int id, String username, String email, String nome, 
			String cognome, String telefono, String residenza){
		
		this.id = id;
		this.username = new SimpleStringProperty(username);
		this.email = new SimpleStringProperty(email);
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		this.telefono = new SimpleStringProperty(telefono);
		this.residenza = new SimpleStringProperty(residenza);
		
	}
	
	public Utente(int id_manager, String usernameManager){
		this.id = id_manager;
		this.username = new SimpleStringProperty(usernameManager);
	}
	
	public void setFiliale(Filiale filiale){
		this.filiale = filiale;
	}
	
	
	public int getId(){return id;}
	
	public String getUsername(){return username.get();}
	public String getEmail()throws NullPointerException{return email.get();}
	public String getNome()throws NullPointerException{return nome.get();}
	public String getCognome()throws NullPointerException{return cognome.get();}
	public String getTelefono()throws NullPointerException{return telefono.get();}
	public String getResidenza()throws NullPointerException{return residenza.get();}
	
	public StringProperty getProperty(String propertyName) throws NullPointerException{
		
		StringProperty wanted;
		
		switch (propertyName){
			case("username"): 	
				wanted = username;
				break;
			case("email"): 	
				wanted = email;
				break;
			case("nome"): 	
				wanted = nome;
				break;
			case("cognome"): 	
				wanted = cognome;
				break;
			case("telefono"): 	
				wanted = telefono;
				break;
			case("residenza"): 	
				wanted = residenza;
				break;
			default:
				wanted = null;
				break;
		}
		
		return wanted;
		
	}
	
	public Filiale getFiliale()throws NullPointerException{return filiale;}

	@Override
	public int compareTo(Utente other) {
		return this.getUsername().compareTo(other.getUsername());
	}
	
}
