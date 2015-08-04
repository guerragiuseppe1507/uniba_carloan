package layout.model.entities;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Auto implements Comparable<Auto>{
	
	private int id;
	private StringProperty modello;
	private StringProperty nomeFiliale;
	private StringProperty status;
	private StringProperty chilometraggio;
	private StringProperty fasce;
	private StringProperty targa;
	private StringProperty provenienza;
	public static final String[] POSSIBILE_STATUS = {"DISPONIBILE", "MANUTENZIONE", "NOLEGGIATA", "ROTTAMAZIONE", "VENDUTA"};
	public static String[] properties = {"modello", "nomeFiliale", "chilometraggio", "status", "fasce", "targa", "provenienza"};
	
	public Auto(int id, String modello, String nomeFiliale, String status,String targa, String chilometraggio, String fasce, String provenienza){
		
		this.id = id;
		this.modello=new SimpleStringProperty(modello);
		this.status=new SimpleStringProperty(status);
		this.targa= new SimpleStringProperty(targa);
		this.nomeFiliale = new SimpleStringProperty(nomeFiliale);
		this.chilometraggio = new SimpleStringProperty(chilometraggio);
		this.fasce=new SimpleStringProperty(fasce);
		this.provenienza=new SimpleStringProperty(provenienza);
		
	}
	
	public int getId(){return id;}
	
	public String getModello(){return modello.get();}
	public String getnomeFiliale(){return nomeFiliale.get();}
	public String getChilometraggio(){return chilometraggio.get();}
	public String getStatus(){return status.get();}
	public String getFasce(){return fasce.get();}
	public String getTarga(){return targa.get();}
	public String getProvenienza(){return provenienza.get();}
	
	public StringProperty getProperty(String propertyName) throws NullPointerException{
		
		StringProperty wanted;
		
		switch (propertyName){
			case("modello"): 	
				wanted = modello;
				break;
			case("nomeFiliale"): 	
				wanted = nomeFiliale;
				break;
			case("chilometraggio"): 	
				wanted = chilometraggio;
				break;
			case("status"): 	
				wanted = status;
				break;
			case("fasce"): 	
				wanted = fasce;
				break;
			case("targa"): 	
				wanted = targa;
				break;
			case("provenienza"): 	
				wanted = provenienza;
				break;
			default:
				wanted = null;
				break;
		}
		
		return wanted;
		
	}

	@Override
	public int compareTo(Auto other) {
		return this.getTarga().compareTo(other.getTarga());
	}


}


