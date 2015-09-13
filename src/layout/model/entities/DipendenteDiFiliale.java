package layout.model.entities;

public class DipendenteDiFiliale extends Utente implements CarloanEntity{

	public DipendenteDiFiliale(int id, String username, String email, String nome,
			String cognome, String telefono, String residenza) {
		super(id, username, email, nome, cognome, telefono, residenza);
		
	}
	
}