package layout.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Auto {
	
	private int id;
	private StringProperty modello;
	private StringProperty nomeFiliale;
	private StringProperty status;
	private StringProperty chilometraggio;
	private StringProperty fasce;
	private StringProperty targa;
	private StringProperty provenienza;
	public static final String[] POSSIBILE_STATUS = {"DISPONIBILE", "MANUTENZIONE", "NOLEGGIATA", "ROTTAMAZIONE", "VENDUTA"};
	
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
	
	public String modello(){return modello.get();}
	public String getnomeFiliale(){return nomeFiliale.get();}
	public String getChilometraggio(){return chilometraggio.get();}
	public String getStatus(){return status.get();}
	public String getFasce(){return fasce.get();}
	public String getTarga(){return targa.get();}
	public String getProvenienza(){return provenienza.get();}
	
	public StringProperty modelloProperty(){return modello;}
	public StringProperty nomeFilialeProperty(){return nomeFiliale;}
	public StringProperty chilometraggioProperty(){return chilometraggio;}
	public StringProperty statusProperty(){return status;}
	public StringProperty fasceProperty(){return fasce;}
	public StringProperty targaProperty(){return targa;}
	public StringProperty provenienzaProperty(){return provenienza;}

}


