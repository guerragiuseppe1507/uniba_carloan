package layout.model.entities;

public class ManagerDiFiliale extends Utente{

	public ManagerDiFiliale(int id, String username, String email, String nome,
			String cognome, String telefono, String residenza) {
		super(id, username, email, nome, cognome, telefono, residenza);
		
	}
	
	public ManagerDiFiliale(int id, String username) {
		super(id, username);
		
	}
	
}
