package layout.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
	private StringProperty id;
	private StringProperty nome;
	private StringProperty cognome;
	private StringProperty email;
	private StringProperty dataDiNascita;
	private StringProperty residenza;
	private StringProperty codiceFiscale;
	private StringProperty codicePatente;
	
	
	
	public Cliente(String id, String Nome, String Cognome, String Email,String data_di_nascita, String Residenza, String codice_fiscale, String codice_patente){
		this.id = new SimpleStringProperty(id);
		this.nome=new SimpleStringProperty(Nome);
		this.cognome=new SimpleStringProperty(Cognome);
		this.email= new SimpleStringProperty(Email);
		this.dataDiNascita = new SimpleStringProperty(data_di_nascita);
		this.residenza = new SimpleStringProperty(Residenza);
		this.codiceFiscale=new SimpleStringProperty(codice_fiscale);
		this.codicePatente=new SimpleStringProperty(codice_patente);
		
	}
	
	public String getId(){return id.get();};
	public String getnome(){return nome.get();}
	public String getcognome(){return cognome.get();}
	public String getemail(){return email.get();}
	public String getResidenza(){return residenza.get();}
	public String getdataDiNascita(){return dataDiNascita.get();}
	public String getcodiceFiscale(){return codiceFiscale.get();}
	public String getcodicePatente(){return codicePatente.get();}
	
	public StringProperty idProperty(){ return id;}
	public StringProperty nomeProperty(){return nome;}
	public StringProperty cognomeProperty(){return cognome;}
	public StringProperty emailProperty(){return email;}
	public StringProperty residenzaProperty(){return residenza;}
	public StringProperty dataDiNascitaProperty(){return dataDiNascita;}
	public StringProperty codiceFiscaleProperty(){return codiceFiscale;}
	public StringProperty codicePatenteProperty(){return codicePatente;}

}




