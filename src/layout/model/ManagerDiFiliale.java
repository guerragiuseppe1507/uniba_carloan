package layout.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ManagerDiFiliale {
	
	private int idManager;
	private int idFiliale;
	private StringProperty usernameManager;
	private StringProperty nomeFiliale;
	
	public ManagerDiFiliale(int id_manager, int id_filiale, String usernameManager, String nomeFiliale){
		
		this.idManager = id_manager;
		this.idFiliale = id_filiale;
		this.usernameManager = new SimpleStringProperty(usernameManager);
		this.nomeFiliale = new SimpleStringProperty(nomeFiliale);
		
	}

	public int getIdManagr(){return idManager;}
	public int getIdFiliale(){return idFiliale;}
	
	public String getUsernameManager(){return usernameManager.get();}
	public String getNomeFiliale(){return nomeFiliale.get();}
	
	public StringProperty usernameManagerProperty(){return usernameManager;}
	public StringProperty nomeFilialeProperty(){return nomeFiliale;}

}
