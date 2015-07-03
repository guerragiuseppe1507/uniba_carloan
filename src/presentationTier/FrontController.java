package presentationTier;

import java.util.HashMap;

/*Questa classe implementa il pattern del FrontController, necessario a fornire all' applicazione
 * un unico punto di ingresso per le richieste provenienti dalla gui, e dunque dagli utenti.
 * Il metodo request del Front Controller prende in input dalla schermata visualizzate il comando
 * relativo alla richiesta selezionata e i parametri necessari al suo corretto completamento.
 * Restituirà poi un HashMap contenente il risultato proveniente dai servizi richiamati. 
 */

public class FrontController {

	public static HashMap<String, String> request(String[] comando, HashMap<String,String> inputParam){
		
		HashMap<String,String> risultato = new HashMap<>();
		
		//Chiamata dell' Application Controller a cui viene delegata la richiesta coi relativi parametri
		businessTier.ApplicationController ac = new businessTier.ApplicationController();
		risultato = ac.execute(comando, inputParam);
		
		return risultato;
	}
}
