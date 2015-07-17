package layout.view;

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
import layout.model.ContextMenu;
import layout.model.Auto;


public class ManageAutoController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	 private TableView<Auto> autoTable;
	@FXML
	private TableColumn<Auto, String> modello;
	@FXML
	private TableColumn<Auto, String> targa;
	@FXML
	private TableColumn<Auto, String> nomeFiliale;
	@FXML
	private TableColumn<Auto, String> status;
	@FXML
	private TableColumn<Auto, String> chilometraggio;
	@FXML
	private TableColumn<Auto, String> fasce;
	
	@FXML
	private ChoiceBox<String> scegliFascia;
	
	private ObservableList<Auto> autoData = FXCollections.observableArrayList();
	private ObservableList<String> fasceData = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		riempiTabellaAuto();
		popolaSpinnerFasce();
		
		
		autoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> riempiTabellaAuto());
		
	
	}
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	private void riempiTabellaAuto(){
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				autoData.add(new Auto(
						risultato.get("modello" + Integer.toString(i)),
						risultato.get("nome_filiale" + Integer.toString(i)), 
						risultato.get("status" + Integer.toString(i)),
						risultato.get("targa"+Integer.toString(i)),
						risultato.get("chilometraggio" + Integer.toString(i)),
						risultato.get("fasce" + Integer.toString(i)))
				); 
				
			}
			
			modello.setCellValueFactory(cellData->cellData.getValue().modelloProperty());
			nomeFiliale.setCellValueFactory(cellData->cellData.getValue().nomeFilialeProperty());
			status.setCellValueFactory(cellData->cellData.getValue().statusProperty());
			chilometraggio.setCellValueFactory(cellData->cellData.getValue().chilometraggioProperty());
			targa.setCellValueFactory(cellData->cellData.getValue().targaProperty());
			fasce.setCellValueFactory(cellData->cellData.getValue().fasceProperty());
			autoTable.setItems(autoData);
			
		} else {
			
			autoTable.setPlaceholder(new Label("No Auto Found"));
			
		}
		
	}
	
	private void popolaSpinnerFasce(){
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiFasce"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				fasceData.add(
						risultato.get("nome_fascia" + Integer.toString(i))
				); 
				
			}
		}
		
		scegliFascia.setItems(fasceData);
	}
	
	
}
