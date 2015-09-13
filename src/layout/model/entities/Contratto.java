package layout.model.entities;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Contratto implements CarloanEntity, Comparable<Contratto>{
	
	private int id;
	private int id_cliente;
	private int id_auto;
	private int id_dipendente;
	private int id_filiale_di_partenza;
	private int id_filiale_di_arrivo;
	private StringProperty tipoKm;
	private StringProperty tariffa;
	private StringProperty dataInizio;
	private StringProperty dataLimite;
	private StringProperty dataRientro;
	private StringProperty acconto;
	private StringProperty stato;
	private StringProperty nomeDipendente;
	private StringProperty totPrezzo;
	private StringProperty filialeDiPartenza;
	private StringProperty filialeDiArrivo;
	public static final String STATUS_APERTO = "APERTO";
	public static final String STATUS_CHIUSO = "CHIUSO";
	public static final String[] POSSIBILE_STATUS = {STATUS_APERTO, STATUS_CHIUSO};
	public static final String[] POSSIBILE_CHILOMETRAGGIO = {"LIMITATO", "ILLIMITATO"};
	public static final String[] POSSIBILE_TARIFFA = {"GIORNALIERA", "SETTIMANALE"};
	public static String[] properties = {"dipendente", "tipoKm", "tariffa", "dataInizio", "dataLimite", "dataRientro", "filialeDiPartenza", "filialeDiArrivo" , "acconto"
		, "stato", "totPrezzo"};

	
	public Contratto(int id, int id_cliente, int id_auto, int id_dipendente, int id_filiale_di_partenza, int id_filiale_di_arrivo, String tipoKm, String tariffa, String dataInizio, String dataLimite, 
			String dataRientro, String filialeDiPartenza, String filialeDiArrivo, String acconto, String stato,
			String nomeDipendente, String totPrezzo){
		
		this.id = id;
		this.id_cliente = id_cliente;
		this.id_auto = id_auto;
		this.id_dipendente = id_dipendente;
		this.id_filiale_di_partenza = id_filiale_di_partenza;
		this.id_filiale_di_arrivo = id_filiale_di_arrivo;
		this.tipoKm = new SimpleStringProperty(tipoKm);
		this.tariffa = new SimpleStringProperty(tariffa);
		this.dataInizio = new SimpleStringProperty(dataInizio);
		this.dataLimite = new SimpleStringProperty(dataLimite);
		this.dataRientro = new SimpleStringProperty(dataRientro);
		this.acconto = new SimpleStringProperty(acconto);
		this.stato = new SimpleStringProperty(stato);
		this.nomeDipendente = new SimpleStringProperty(nomeDipendente);
		this.totPrezzo = new SimpleStringProperty(totPrezzo);
		this.filialeDiPartenza = new SimpleStringProperty(filialeDiPartenza);
		this.filialeDiArrivo = new SimpleStringProperty(filialeDiArrivo);
		
	}
	
	
	public int getId(){return id;}
	public int getIdCliente(){return id_cliente;}
	public int getIdAuto(){return id_auto;}
	public int getIdDipendente(){return id_dipendente;}
	public int getIdFilialeDiPartenza(){return id_filiale_di_partenza;}
	public int getIdFilialeDiArrivo(){return id_filiale_di_arrivo;}
	public String getTipoKm(){return tipoKm.get();}
	public String getTariffa(){return tariffa.get();}
	public String getDataInizio(){return dataInizio.get();}
	public String getDataLimite(){return dataLimite.get();}
	public String getDataRientro(){return dataRientro.get();} 
	public String getAcconto(){ return acconto.get();}
	public String getStato(){return stato.get();}
	public String getNomeDipendente(){ return nomeDipendente.get();}
	public String getTotPrezzo(){return totPrezzo.get();};
	public String getFilialeDiPartenza(){return filialeDiPartenza.get();}
	public String getFilialeDiArrivo(){return filialeDiArrivo.get();}
	
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
			case("dipendente"): 	
				wanted = nomeDipendente;
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
	
	@Override
	public int compareTo(Contratto other) {
		return this.getDataInizio().compareTo(other.getDataInizio());
	}
	
}	
	
	















