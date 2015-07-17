package layout.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Contratto {
	
	private StringProperty id;
	private StringProperty tipoKm;
	private StringProperty tariffa;
	private StringProperty dataInizio;
	private StringProperty dataLimite;
	private StringProperty dataRientro;
	private StringProperty acconto;
	private StringProperty stato;
	private StringProperty idCliente;
	private StringProperty idDipendente;
	private StringProperty idAuto;
	private StringProperty totPrezzo;
	private StringProperty filialeDiPartenza;
	private StringProperty filialeDiArrivo;

	
	public Contratto(String Id, String TipoKm, String Tariffa,String DataInizio, String DataFine, String DataLimite, String DataRientro, String Acconto, String Stato,String IdCliente,  String IdDipendente, String IdAuto, String TotPrezzo, String FilialeDiPartenza, String FilialeDiArrivo){
		
		this.id=new SimpleStringProperty(Id);
		this.tipoKm=new SimpleStringProperty(TipoKm);
		this.tariffa= new SimpleStringProperty(Tariffa);
		this.dataInizio = new SimpleStringProperty(DataInizio);
		this.dataLimite = new SimpleStringProperty(DataLimite);
		this.dataRientro=new SimpleStringProperty(DataRientro);
		this.acconto=new SimpleStringProperty(Acconto);
		this.stato=new SimpleStringProperty(Stato);
		this.idCliente=new SimpleStringProperty(IdCliente);
		this.idDipendente=new SimpleStringProperty(IdDipendente);
		this.idAuto=new SimpleStringProperty(IdAuto);
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
	public String idCliente(){return idCliente.get();}
	public String idDipendente(){ return idDipendente.get();}
	public String idAuto(){ return idAuto.get();}
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
	public StringProperty idClienteProperty(){return idCliente;}
	public StringProperty idDipendenteProperty(){return idDipendente;}
	public StringProperty idAutoProperty(){return idAuto;}
	public StringProperty totPrezzoProperty(){return totPrezzo;}
	public StringProperty filialeDiPatenzaProperty(){return filialeDiPartenza;}
	public StringProperty filialeDiArrivoProperty(){return filialeDiArrivo;}
	
	
	
	
	
	
}	
	
	















