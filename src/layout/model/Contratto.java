package layout.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.net.URL;



public class Contratto {
	
	private StringProperty id;
	private StringProperty tipoKm;
	private StringProperty tariffa;
	private StringProperty dataInizio;
	private StringProperty dataLimite;
	private StringProperty dataRientro;
	private StringProperty acconto;
	private StringProperty stato;
	private StringProperty nomeCliente;
	private StringProperty nomeDipendente;
	private StringProperty modello;
	private StringProperty totPrezzo;
	private StringProperty filialeDiPartenza;
	private StringProperty filialeDiArrivo;

	
	public Contratto(String Id, String TipoKm, String Tariffa,String DataInizio, String DataLimite, String DataRientro, String Acconto, String Stato,String NomeCliente,  String NomeDipendente, String Modello, String TotPrezzo, String FilialeDiPartenza, String FilialeDiArrivo){
		
		this.id=new SimpleStringProperty(Id);
		this.tipoKm=new SimpleStringProperty(TipoKm);
		this.tariffa= new SimpleStringProperty(Tariffa);
		this.dataInizio = new SimpleStringProperty(DataInizio);
		this.dataLimite = new SimpleStringProperty(DataLimite);
		this.dataRientro=new SimpleStringProperty(DataRientro);
		this.acconto=new SimpleStringProperty(Acconto);
		this.stato=new SimpleStringProperty(Stato);
		this.nomeCliente=new SimpleStringProperty(NomeCliente);
		this.nomeDipendente=new SimpleStringProperty(NomeDipendente);
		this.modello=new SimpleStringProperty(Modello);
		this.totPrezzo=new SimpleStringProperty(TotPrezzo);
		this.filialeDiPartenza=new SimpleStringProperty(FilialeDiPartenza);
		this.filialeDiArrivo=new SimpleStringProperty(FilialeDiArrivo);
		
	}
	
	
	public String Id(){return id.get();}
	public String tipoKm(){return tipoKm.get();}
	public String tariffa(){return tariffa.get();}
	public String dataInizio(){return dataInizio.get();}
	public String dataLimite(){return dataLimite.get();}
	public String dataRientro(){return dataRientro.get();} 
	public String acconto(){ return acconto.get();}
	public String stato(){return stato.get();}
	public String nomeCliente(){return nomeCliente.get();}
	public String nomeDipendente(){ return nomeDipendente.get();}
	public String modello(){ return modello.get();}
	public String totPrezzo(){return totPrezzo.get();};
	public String filialeDiPartenza(){return filialeDiPartenza.get();}
	public String filialeDiArrivo(){return filialeDiArrivo.get();}
	
	
	public StringProperty idProperty(){return id;}
	public StringProperty tipoKmProperty(){return tipoKm;}
	public StringProperty tariffaProperty(){return tariffa;}
	public StringProperty dataInizioProperty(){return dataInizio;}
	public StringProperty dataLimiteProperty(){return dataLimite;}
	public StringProperty dataRientroProperty(){return dataRientro;}
	public StringProperty accontoProperty(){return acconto;}
	public StringProperty statoProperty(){return stato;}
	public StringProperty nomeClienteProperty(){return nomeCliente;}
	public StringProperty nomeDipendenteProperty(){return nomeDipendente;}
	public StringProperty modelloProperty(){return modello;}
	public StringProperty totPrezzoProperty(){return totPrezzo;}
	public StringProperty filialeDiPartenzaProperty(){return filialeDiPartenza;}
	public StringProperty filialeDiArrivoProperty(){return filialeDiArrivo;}
	
	
	
	
	
	
}	
	
	















