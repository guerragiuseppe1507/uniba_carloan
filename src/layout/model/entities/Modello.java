package layout.model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Modello implements CarloanEntity{
	
	private int id;
	private StringProperty nome;
	private Fascia fascia;
	
	public Modello(int id, String nome){
		
		this.id = id;
		this.nome = new SimpleStringProperty(nome);
		
	}
	
	public void setFascia(Fascia fascia){
		
		this.fascia = fascia;
		
	}
	
	public Fascia getFascia(){
		
		return this.fascia;
		
	}
	
	public int getId(){return id;}
	
	public String getNome(){return nome.get();}
	
	public StringProperty getProperty(String propertyName) throws NullPointerException{
		StringProperty wanted;
		
		switch (propertyName){
			case("nome"): 	
				wanted = nome;
				break;
			default:
				wanted = null;
				break;
		}
		
		return wanted;
	}
	
	@Override
	public String toString(){
		return this.getNome();
	}

}
