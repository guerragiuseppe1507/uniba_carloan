package layout.model;

import java.util.Comparator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente implements Comparable<Cliente> {
	private int id;
	private StringProperty nome;
	private StringProperty cognome;
	private StringProperty email;
	private StringProperty dataDiNascita;
	private StringProperty residenza;
	private StringProperty codiceFiscale;
	private StringProperty codicePatente;
	
	
	
	public Cliente(int id, String Nome, String Cognome, String Email,String Data_Di_Nascita, String Residenza, String codice_fiscale, String codice_patente){
		this.id =id;
		this.nome=new SimpleStringProperty(Nome);
		this.cognome=new SimpleStringProperty(Cognome);
		this.email= new SimpleStringProperty(Email);
		this.dataDiNascita = new SimpleStringProperty(Data_Di_Nascita);
		this.residenza = new SimpleStringProperty(Residenza);
		this.codiceFiscale=new SimpleStringProperty(codice_fiscale);
		this.codicePatente=new SimpleStringProperty(codice_patente);
		
	}
	
	public int getId(){return id;};
	public String getNome(){return nome.get();}
	public String getCognome(){return cognome.get();}
	public String getEmail(){return email.get();}
	public String getResidenza(){return residenza.get();}
	public String getDataDiNascita(){return dataDiNascita.get();}
	public String getCodiceFiscale(){return codiceFiscale.get();}
	public String getCodicePatente(){return codicePatente.get();}
	
	
	public StringProperty nomeProperty(){return nome;}
	public StringProperty cognomeProperty(){return cognome;}
	public StringProperty emailProperty(){return email;}
	public StringProperty residenzaProperty(){return residenza;}
	public StringProperty dataDiNascitaProperty(){return dataDiNascita;}
	public StringProperty codiceFiscaleProperty(){return codiceFiscale;}
	public StringProperty codicePatenteProperty(){return codicePatente;}


	@Override
	public int compareTo(Cliente other) {
		return this.getNome().compareTo(other.getNome());
	}

}




