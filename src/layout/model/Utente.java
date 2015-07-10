package layout.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utente {
	
	private int id;
	private StringProperty username;
	private StringProperty email;
	private StringProperty nome;
	private StringProperty cognome;
	private StringProperty telefono;
	private StringProperty residenza;
	
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

	public int getId(){return id;}
	
	public String getUsername(){return username.get();}
	public String getEmail(){return email.get();}
	public String getNome(){return nome.get();}
	public String getCognome(){return cognome.get();}
	public String getTelefono(){return telefono.get();}
	public String getResidenza(){return residenza.get();}
	
	public StringProperty usernameProperty(){return username;}
	public StringProperty emailProperty(){return email;}
	public StringProperty nomeProperty(){return nome;}
	public StringProperty cognomeProperty(){return cognome;}
	public StringProperty telefonoProperty(){return telefono;}
	public StringProperty residenzaProperty(){return residenza;}

}
