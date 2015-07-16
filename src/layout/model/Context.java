package layout.model;

/*Questa classe implementa il pattern Singleton e permette l'istanziazione privata dei campi
 * Email e Competizione, con i relativi accessi al loro contenuto. E' stato necessario
 * implementare questa classe per permettere a più schermate della gui di condividere e 
 * scambiarsi informazioni sugli oggetti di Business necessarie per alcune operazioni.  
 * L' implementazione singleton permette di avere delle variabili sempre disponibili
 * e accessibili da qualsiasi parte dell' applicazione in ogni momento.
 */

public class Context {

	//Dichiarazione e instanziazione di un oggetto della classe Context
	private final static Context instance = new Context();
	public static final String dipendenteFiliale = "Dipendente Di Filiale";
	public static final String managerFiliale = "Manager Di Filiale";
	public static final String managerSistema = "Manager Di Sistema";

	private String userType;
	private Utente utente;
	
	private String password;
	
	//Metodo che restituisce l'oggetto statico instance di tipo Context
    public static Context getInstance() {
        return instance;
    }
    
    public Utente getUtente(){
    	return utente;
    }
    
    public void setUtente(Utente u){
    	utente = u;
    }
    
    public void setUserType(String t){
    	this.userType = t;	
    }
    
    public String getUserType(){
    	return userType;
    	
    }
    
    public void setPassword(String p){
    	this.password = p;
    }
    
    public String getPassword(){
    	return this.password;
	}

}

