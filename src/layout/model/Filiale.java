package layout.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Filiale {
	
	private int id;
	private StringProperty nome;
	private StringProperty luogo;
	private StringProperty telefono;
	
	public Filiale(int id, String nome, String luogo, String telefono){
		
		this.id = id;
		this.nome = new SimpleStringProperty(nome);
		this.luogo = new SimpleStringProperty(luogo);
		this.telefono = new SimpleStringProperty(telefono);
		
	}

	public int getId(){return id;}
	
	public String getUsername(){return nome.get();}
	public String getEmail(){return luogo.get();}
	public String getNome(){return telefono.get();}
	
	public StringProperty usernameProperty(){return nome;}
	public StringProperty emailProperty(){return luogo;}
	public StringProperty nomeProperty(){return telefono;}

}
