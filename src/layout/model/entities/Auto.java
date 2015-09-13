package layout.model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Auto implements CarloanEntity, Comparable<Auto>{
	
	private int id;
	private Modello modello;
	private StringProperty nomeFiliale;
	private StringProperty status;
	private StringProperty chilometraggio;
	private StringProperty fascia;
	private StringProperty targa;
	private StringProperty provenienza;
	public static final String STATUS_NOLEGGIATA = "NOLEGGIATA";
	public static final String STATUS_DISPONIBILE = "DISPONIBILE";
	public static final String STATUS_MANUTENZIONE = "MANUTENZIONE";
	public static final String STATUS_ROTTAMATA = "ROTTAMATA";
	public static final String[] POSSIBILE_STATUS = {STATUS_DISPONIBILE, STATUS_MANUTENZIONE, STATUS_ROTTAMATA};
	public static String[] properties = {"modello", "nomeFiliale", "chilometraggio", "status", "fascia", "targa", "provenienza"};
	
	public Auto(int id, String nomeFiliale, String status,String targa, String chilometraggio, String fasce, String provenienza){
		
		this.id = id;
		this.status=new SimpleStringProperty(status);
		this.targa= new SimpleStringProperty(targa);
		this.nomeFiliale = new SimpleStringProperty(nomeFiliale);
		this.chilometraggio = new SimpleStringProperty(chilometraggio);
		this.fascia=new SimpleStringProperty(fasce);
		this.provenienza=new SimpleStringProperty(provenienza);
		
	}
	
	public void setModello(Modello modello){
		
		this.modello = modello;
		
	}
	
	public Modello getModello() throws NullPointerException{
		return modello;
	}
	
	public int getId(){return id;}
	
	public String getnomeFiliale(){return nomeFiliale.get();}
	public String getChilometraggio(){return chilometraggio.get();}
	public String getStatus(){return status.get();}
	public String getFascia(){return fascia.get();}
	public String getTarga(){return targa.get();}
	public String getProvenienza(){return provenienza.get();}
	
	public StringProperty getProperty(String propertyName) throws NullPointerException{
		
		StringProperty wanted;
		
		switch (propertyName){
			case("modello"): 	
				wanted = modello.getProperty("nome");
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
			case("fascia"): 	
				wanted = fascia;
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


