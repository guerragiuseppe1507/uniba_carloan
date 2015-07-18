package layout.view;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.Auto;
import layout.model.ContextMenu;
import layout.model.Contratto;
import layout.model.Utente;

public class GestioneContrattiController  implements Initializable, ControlledScreen {
		
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	 private TableView<Contratto> tableContratto;
	@FXML
	 private TableColumn<Contratto, String>id;
	@FXML
	private TableColumn<Contratto, String>tipoKm;
	 @FXML
	 private TableColumn<Contratto, String>tariffa;
	 @FXML
	 private TableColumn<Contratto, String>dataInizio;
	 @FXML
	 private TableColumn<Contratto, String>dataLimite;
	 @FXML
	 private TableColumn<Contratto, String>dataRientro;
	 @FXML
	 private TableColumn<Contratto,String>acconto;
	 @FXML
	 private TableColumn<Contratto, String>stato;
	 @FXML
	 private TableColumn<Contratto, String>nomeCliente;
	 @FXML
	 private TableColumn<Contratto, String>nomeDipendente;
	 @FXML
	 private TableColumn<Contratto, String>modello;
	 @FXML
	 private TableColumn<Contratto, String>totPrezzo;
	 @FXML
	 private TableColumn<Contratto, String>filialeDiPartenza;
	 @FXML
	 private TableColumn<Contratto, String>filialeDiArrivo;
	
	 private ObservableList<Contratto> contrattoData = FXCollections.observableArrayList();
	
	 @Override
		public void initialize(URL url, ResourceBundle rb){
			
			container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
			container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
			menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
			menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
			
			riempiTabellaContratto();
		
		}
	
	
		public void setScreenParent(ScreensController screenParent){
			myController = screenParent;
			ContextMenu.showContextMenu(menu,myController);
		}
	
	
		
		private void riempiTabellaContratto(){
			
			String[] comando = new String[]{"businessTier.GestioneContratti", "recuperoDatiContratto"};
			HashMap<String, String> inputParam = new HashMap<>();
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			
			if(risultato.get(util.ResultKeys.ESITO).equals("true")){
				
				for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
					
					contrattoData.add(new Contratto(
							risultato.get("id" + Integer.toString(i)),
							risultato.get("tipoKm" + Integer.toString(i)), 
							risultato.get("tariffa" + Integer.toString(i)),
							risultato.get("dataInizio"+Integer.toString(i)),
							risultato.get("dataLimite" + Integer.toString(i)),
							risultato.get("dataRientro" + Integer.toString(i)),
							risultato.get("acconto" + Integer.toString(i)),
							risultato.get("stato" + Integer.toString(i)),
							risultato.get("nomeCliente" + Integer.toString(i)),
							risultato.get("nomeDipendente" + Integer.toString(i)),
							risultato.get("nomeAuto" + Integer.toString(i)),
							risultato.get("totPrezzo" + Integer.toString(i)),
							risultato.get("filialeDiPartenza" + Integer.toString(i)),
							risultato.get("filialeDiArrivo" + Integer.toString(i)))); }
					
				
				tipoKm.setCellValueFactory(cellData->cellData.getValue().tipoKmProperty());
				tariffa.setCellValueFactory(cellData->cellData.getValue().tariffaProperty());
				dataInizio.setCellValueFactory(cellData->cellData.getValue().dataInizioProperty());
				dataLimite.setCellValueFactory(cellData->cellData.getValue().dataLimiteProperty());
				dataRientro.setCellValueFactory(cellData->cellData.getValue().dataRientroProperty());
				acconto.setCellValueFactory(cellData->cellData.getValue().accontoProperty());
				nomeCliente.setCellValueFactory(cellData->cellData.getValue().nomeClienteProperty());
				modello.setCellValueFactory(cellData->cellData.getValue().modelloProperty());
				nomeDipendente.setCellValueFactory(cellData->cellData.getValue().nomeDipendenteProperty());
				totPrezzo.setCellValueFactory(cellData->cellData.getValue().totPrezzoProperty());
				filialeDiPartenza.setCellValueFactory(cellData->cellData.getValue().filialeDiPartenzaProperty());
				filialeDiArrivo.setCellValueFactory(cellData->cellData.getValue().filialeDiArrivoProperty());
				tableContratto.setItems(contrattoData);
				
			} else {
				
				tableContratto.setPlaceholder(new Label("No Auto Found"));
				
			}
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
