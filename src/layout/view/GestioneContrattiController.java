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

public class GestioneContrattiController  implements Initializable, ControlledScreen {
		
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	 private TableView<Contratto> tableContratto;
	@FXML
	 private TableView<Contratto>id;
	@FXML
	 private TableView<Contratto>tipoKm;
	 @FXML
	 private TableView<Contratto>tariffa;
	 @FXML
	 private TableView<Contratto>data_inizio;
	 @FXML
	 private TableView<Contratto>data_limite;
	 @FXML
	 private TableView<Contratto>data_rientro;
	 @FXML
	 private TableView<Contratto>acconto;
	 @FXML
	 private TableView<Contratto>stato;
	 @FXML
	 private TableView<Contratto>id_cliente;
	 @FXML
	 private TableView<Contratto>id_dipendente;
	 @FXML
	 private TableView<Contratto>id_auto;
	 @FXML
	 private TableView<Contratto>tot_prezzo;
	 @FXML
	 private TableView<Contratto>filiale_di_partenza;
	 @FXML
	 private TableView<Contratto>filiale_di_arrivo;
	
	 private ObservableList<Contratto> contrattoData = FXCollections.observableArrayList();
	
	 @Override
		public void initialize(URL url, ResourceBundle rb){
			
			container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
			container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
			menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
			menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
			
			riempiTabellaAuto();
		
		}
	
	
		public void setScreenParent(ScreensController screenParent){
			myController = screenParent;
			ContextMenu.showContextMenu(menu,myController);
		}
	
	
		
		private void riempiTabellaContatto(){
			
			String[] comando = new String[]{"businessTier.GestioneContratti", "recuperoDatiContratto"};
			HashMap<String, String> inputParam = new HashMap<>();
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			
			if(risultato.get(util.ResultKeys.ESITO).equals("true")){
				
				for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
					
					contrattoData.add(new Contratto(
							risultato.get("" + Integer.toString(i)),
							risultato.get("" + Integer.toString(i)), 
							risultato.get("" + Integer.toString(i)),
							risultato.get(""+Integer.toString(i)),
							risultato.get("" + Integer.toString(i)),
							risultato.get("" + Integer.toString(i)))
					); 
					
				}
				
				.setCellValueFactory(cellData->cellData.getValue().modelloProperty());
				.setCellValueFactory(cellData->cellData.getValue().nomeFilialeProperty());
				.setCellValueFactory(cellData->cellData.getValue().statusProperty());
				.setCellValueFactory(cellData->cellData.getValue().chilometraggioProperty());
				.setCellValueFactory(cellData->cellData.getValue().targaProperty());
				.setCellValueFactory(cellData->cellData.getValue().fasceProperty());
				.setItems(autoData);
				
			} else {
				
				autoTable.setPlaceholder(new Label("No Auto Found"));
				
			}
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
