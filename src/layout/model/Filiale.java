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
	
	public Filiale(int id, String nome){
		this.id = id;
		this.nome = new SimpleStringProperty(nome);
		this.luogo = null;
		this.telefono = null;
	}

	public int getId(){return id;}
	
	public String getNome(){return nome.get();}
	public String getLuogo()throws NullPointerException{return luogo.get();}
	public String getTelefono()throws NullPointerException{return telefono.get();}
	
	public StringProperty nomeProperty(){return nome;}
	public StringProperty luogoProperty()throws NullPointerException{return luogo;}
	public StringProperty telefonoProperty()throws NullPointerException{return telefono;}

}
