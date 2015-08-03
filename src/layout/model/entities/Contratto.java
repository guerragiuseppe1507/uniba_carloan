package layout.model.entities;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Contratto {
	
	private int id;
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
	public static String[] properties = {"tipoKm", "tariffa", "dataInizio", "dataLimite", "dataRientro", "acconto"
		, "stato", "nomeCliente", "nomeDipendente", "modello", "totPrezzo", "filialeDiPartenza", "filialeDiArrivo"};

	
	public Contratto(int id, String tipoKm, String tariffa,String dataInizio, String dataLimite, 
			String dataRientro, String acconto, String stato,String nomeCliente,
			String nomeDipendente, String modello, String totPrezzo,
			String filialeDiPartenza, String filialeDiArrivo){
		
		this.id=id;
		this.tipoKm=new SimpleStringProperty(tipoKm);
		this.tariffa= new SimpleStringProperty(tariffa);
		this.dataInizio = new SimpleStringProperty(dataInizio);
		this.dataLimite = new SimpleStringProperty(dataLimite);
		this.dataRientro=new SimpleStringProperty(dataRientro);
		this.acconto=new SimpleStringProperty(acconto);
		this.stato=new SimpleStringProperty(stato);
		this.nomeCliente=new SimpleStringProperty(nomeCliente);
		this.nomeDipendente=new SimpleStringProperty(nomeDipendente);
		this.modello=new SimpleStringProperty(modello);
		this.totPrezzo=new SimpleStringProperty(totPrezzo);
		this.filialeDiPartenza=new SimpleStringProperty(filialeDiPartenza);
		this.filialeDiArrivo=new SimpleStringProperty(filialeDiArrivo);
		
	}
	
	
	public int Id(){return id;}
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
	
	public StringProperty getProperty(String propertyName){
		
		StringProperty wanted;
		
		switch (propertyName){
			case("tipoKm"): 	
				wanted = tipoKm;
				break;
			case("tariffa"): 	
				wanted = tariffa;
				break;
			case("dataInizio"): 	
				wanted = dataInizio;
				break;
			case("dataLimite"): 	
				wanted = dataLimite;
				break;
			case("dataRientro"): 	
				wanted = dataRientro;
				break;
			case("acconto"): 	
				wanted = acconto;
				break;
			case("stato"): 	
				wanted = stato;
				break;
			case("nomeCliente"): 	
				wanted = nomeCliente;
				break;
			case("nomeDipendente"): 	
				wanted = nomeDipendente;
				break;
			case("modello"): 	
				wanted = modello;
				break;
			case("totPrezzo"): 	
				wanted = totPrezzo;
				break;
			case("filialeDiPartenza"): 	
				wanted = filialeDiPartenza;
				break;
			case("filialeDiArrivo"): 	
				wanted = filialeDiArrivo;
				break;
			default:
				wanted = null;
				break;
		}
		
		return wanted;
		
	}
	
}	
	
	















