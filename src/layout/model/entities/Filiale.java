package layout.model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Filiale implements CarloanEntity, Comparable<Filiale>{
	
	private int id;
	private StringProperty nome;
	private StringProperty luogo;
	private StringProperty telefono;
	public static String[] properties = {"nome", "luogo", "telefono"};
	
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
	
	public StringProperty getProperty(String propertyName) throws NullPointerException{
		
		StringProperty wanted;
		
		switch (propertyName){
			case("nome"): 	
				wanted = nome;
				break;
			case("luogo"): 	
				wanted = luogo;
				break;
			case("telefono"): 	
				wanted = telefono;
				break;
			default:
				wanted = null;
				break;
		}
		
		return wanted;
		
	}
	
	@Override
	public int compareTo(Filiale other) {
		return this.getNome().compareTo(other.getNome());
	}
	
	@Override
	public String toString(){
		return this.getNome();
	}

}
