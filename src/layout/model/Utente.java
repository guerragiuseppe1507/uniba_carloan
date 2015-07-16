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
	private Filiale filiale;
	
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
	
	public void setFiliale(int id_filiale, String nome_filiale){
		this.filiale = new Filiale(id_filiale,nome_filiale);
	}
	
	public int getId(){return id;}
	
	public String getUsername(){return username.get();}
	public String getEmail()throws NullPointerException{return email.get();}
	public String getNome()throws NullPointerException{return nome.get();}
	public String getCognome()throws NullPointerException{return cognome.get();}
	public String getTelefono()throws NullPointerException{return telefono.get();}
	public String getResidenza()throws NullPointerException{return residenza.get();}
	
	public StringProperty usernameProperty(){return username;}
	public StringProperty emailProperty()throws NullPointerException{return email;}
	public StringProperty nomeProperty()throws NullPointerException{return nome;}
	public StringProperty cognomeProperty()throws NullPointerException{return cognome;}
	public StringProperty telefonoProperty()throws NullPointerException{return telefono;}
	public StringProperty residenzaProperty()throws NullPointerException{return residenza;}
	
	public int getIdFiliale()throws NullPointerException{return filiale.getId();}
	public String getNomeFiliale()throws NullPointerException{return filiale.getNome();}
	public StringProperty nomeFilialeProperty()throws NullPointerException{return filiale.nomeProperty();}
}
