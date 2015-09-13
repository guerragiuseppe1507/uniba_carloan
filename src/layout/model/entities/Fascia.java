package layout.model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fascia implements CarloanEntity{
	
	private int id;
	private StringProperty nome;
	private StringProperty tariffa_base_g;
	private StringProperty tariffa_base_s;
	private StringProperty costo_chiometrico;
	private StringProperty penale_chilometri;
	private StringProperty tariffa_illim_g;
	private StringProperty tariffa_illim_s;
	public static final int LIM_KM_G = 400;
	public static final int LIM_KM_S = 3600;

	public Fascia(int id, String nome){
		
		this.id = id;
		this.nome = new SimpleStringProperty(nome);
		
	}
	
	public void setListaPrezzi(String tariffa_base_g, String tariffa_base_s, String costo_chiometrico
			, String penale_chilometri, String tariffa_illim_g, String tariffa_illim_s){
		
		this.tariffa_base_g = new SimpleStringProperty(tariffa_base_g);
		this.tariffa_base_s = new SimpleStringProperty(tariffa_base_s);
		this.costo_chiometrico = new SimpleStringProperty(costo_chiometrico);
		this.penale_chilometri = new SimpleStringProperty(penale_chilometri);
		this.tariffa_illim_g = new SimpleStringProperty(tariffa_illim_g);
		this.tariffa_illim_s = new SimpleStringProperty(tariffa_illim_s);
		
	}
	
	public int getId(){return id;}
	
	public String getNome(){return nome.get();}
	public String getTariffaBaseG(){return tariffa_base_g.get();}
	public String getTariffaBaseS(){return tariffa_base_s.get();}
	public String getCostoChilometrico(){return costo_chiometrico.get();}
	public String getPenaleChilometri(){return penale_chilometri.get();}
	public String getTariffaIllimG(){return tariffa_illim_g.get();}
	public String getTariffaIllimS(){return tariffa_illim_s.get();}
	
	public StringProperty getProperty(String propertyName) throws NullPointerException{
		
		StringProperty wanted;
		
		switch (propertyName){
			case("nome"): 	
				wanted = nome;
				break;
			case("tariffa_base_g"): 	
				wanted = tariffa_base_g;
				break;
			case("tariffa_base_s"): 	
				wanted = tariffa_base_s;
				break;
			case("costo_chiometrico"): 	
				wanted = costo_chiometrico;
				break;
			case("penale_chilometri"): 	
				wanted = penale_chilometri;
				break;
			case("tariffa_illim_g"): 	
				wanted = tariffa_illim_g;
				break;
			case("tariffa_illim_s"): 	
				wanted = tariffa_illim_s;
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
