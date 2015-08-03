package layout.model;

import java.util.HashMap;

import presentationTier.FrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import layout.model.entities.Contratto;
import layout.model.entities.Utente;

public class TableManager {
	
	public static void riempiTabellaUtenti(TableView<Utente> usersTable , String... filter){
		
		ObservableList<Utente> usersData = FXCollections.observableArrayList();
		String[] comando = new String[2];
		comando[0] = "businessTier.GestioneUtenti";
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		
		if (filter[0].equals("liberi")){
			comando[1] = "recuperoDatiUtentiLiberi";
		}else if (filter[0].equals("filiale")){
			comando[1] = "recuperoDatiUtenti";
			inputParam.put("idFiliale", filter[1]);
		}
		
		risultato =	FrontController.request(comando, inputParam);
		
		usersTable.getSelectionModel().clearSelection();
		usersTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				usersData.add(new Utente(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)), 
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i)), 
						risultato.get("residenza" + Integer.toString(i))));
				
			}
			
			String[] columnNames = {"Username", "Email", "Nome" , "Cognome" , "Telefono", "Residenza"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Utente, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Utente, String>(columnNames[i]);
				usersTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Utente.properties[wantedProperty]));
			}
			
			FXCollections.sort(usersData);
			usersTable.setItems(usersData);
			
		} else {
			usersTable.setPlaceholder(new Label("No Users Found"));
		}
		
	}
	
	public static void riempiTabellaContratto(TableView<Contratto> contrattoTable){
		
		ObservableList<Contratto> contrattoData = FXCollections.observableArrayList();
		
		String[] comando = new String[]{"businessTier.GestioneContratti", "recuperoDatiContratto"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		contrattoTable.getSelectionModel().clearSelection();
		contrattoTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				contrattoData.add(new Contratto(
						Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("tipoKm" + Integer.toString(i)), 
						risultato.get("tariffa" + Integer.toString(i)),
						risultato.get("dataInizio"+Integer.toString(i)),
						risultato.get("dataLimite" + Integer.toString(i)),
						risultato.get("dataRientro" + Integer.toString(i)),
						risultato.get("acconto" + Integer.toString(i)),
						risultato.get("stato" + Integer.toString(i)),
						risultato.get("nomeCliente" + Integer.toString(i)),
						risultato.get("nomeDipendente" + Integer.toString(i)),
						risultato.get("modello" + Integer.toString(i)),
						risultato.get("totPrezzo" + Integer.toString(i)),
						risultato.get("filialeDiPartenza" + Integer.toString(i)),
						risultato.get("filialeDiArrivo" + Integer.toString(i)))
				); 
			}
			
			String[] columnNames = {"tipoKm", "tariffa", "dataInizio", "dataLimite", "dataRientro", "acconto"
					, "stato", "nomeCliente", "nomeDipendente", "modello", "totPrezzo", "filialeDiPartenza", "filialeDiArrivo"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Contratto, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Contratto, String>(columnNames[i]);
				contrattoTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Contratto.properties[wantedProperty]));
			}
				
			contrattoTable.setItems(contrattoData);
			
		} else {
			
			contrattoTable.setPlaceholder(new Label("No Cars Found"));
			
		}
		
	}

}
