package layout.view;

import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.NumberPlateValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.Auto;
import layout.model.entities.Cliente;
import layout.model.entities.Contratto;

public class InserimentoContrattoController implements Initializable, ControlledScreen,InterStageCallBackListener {
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	//Sezione Coontratti Stipulati
	@FXML
	Button newClient;
	
	@FXML
	private TableView<Cliente> clientiTable; 
	 
	@FXML
	private TableView<Auto> autoTable;
	
	
	@FXML
	private Label chilometraggioLabel;
	@FXML
	private Label modelloLabel;
	@FXML
	private Label fasciaLabel;
	@FXML
	private Label tariffaLabel;
	@FXML
	private Label targaLabel;
	@FXML
	private Label dataInizioLabel;
	@FXML
	private Label dataRientroLabel;
	@FXML
	private Label filialeDiPartenzaLabel;
	@FXML
	private Label filialeDiArrivoLabel;
	@FXML
	private Label accontoLabel;
	@FXML
	private Label prezzoTariffaLabel;
	@FXML
	private Label prezzoFasciaLabel;
	
	@FXML
	private TextField inserisciAcconto;
	@FXML
	private ComboBox<String> scegliFiliale;
	@FXML
	private ComboBox<String> scegliChilometraggio;
	@FXML
	private ComboBox<String> scegliTariffa;
	

	
	private ObservableList<String> filialiData = FXCollections.observableArrayList();
	
	private Auto autoDispScelta;
	 
	 
	 @Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		riempiTabellaClienti();
		riempiTabellaAuto();
		
		popolaSpinnerChilometraggio();
		popolaSpinnerTariffa();
		popolaSpinnerFiliali();
		
		
		autoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliAuto(newValue));
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	private void riempiTabellaAuto(){
		
		TableManager.riempiTabellaAuto(autoTable,
				Integer.toString(Context.getInstance().getUtente().getFiliale().getId()),"disponibile");
		
	}
    private void riempiTabellaClienti(){
		
		TableManager.riempiTabellaClienti(clientiTable);
		
	}
	
	@FXML
	public void handleNewClient(){
		myController.setScreenNewWindow(ScreensFramework.InserimentoClienteID,
				ScreensFramework.InserimentoClienteFile, 
				ScreensFramework.InserimentoClienteTitle);
		InterStageEventsHandler.getInstance().setCaller(this);
	}
	
	public void callBack(){
		riempiTabellaClienti();
	}
	
	
 

	private void popolaSpinnerChilometraggio(){
		ObservableList<String> tipiChilometraggi = FXCollections.observableArrayList();
		
		for(int i = 0 ; i < Contratto.POSSIBILE_CHILOMETRAGGIO.length ; i++){
			tipiChilometraggi.add(Contratto.POSSIBILE_CHILOMETRAGGIO[i]);
		}
		scegliChilometraggio.setItems(tipiChilometraggi);
		
	}



	private void popolaSpinnerTariffa(){
		ObservableList<String> tipiTariffa = FXCollections.observableArrayList();
		
		for(int i = 0 ; i < Contratto.POSSIBILE_TARIFFA.length ; i++){
			tipiTariffa.add(Contratto.POSSIBILE_TARIFFA[i]);
		}
		scegliTariffa.setItems(tipiTariffa);
		
	}

	
	
	 private void popolaSpinnerFiliali(){
		String[] comando = new String[]{"businessTier.GestioneFiliali", "recuperoDatiFiliali"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				filialiData.add(
						risultato.get("id" + Integer.toString(i))
				); 
				
			}
		}
		
		scegliFiliale.setItems(filialiData);
	}


	 
	 //Riepiloghi
	 public void onActionTariffa(){

		 tariffaLabel.setText(scegliTariffa.getValue());
		 
	 }
	 
	 public void onActionChilometraggio(){
		 chilometraggioLabel.setText(scegliChilometraggio.getValue());
		 
	 }
	 
	 public void onActionFiliale(){
		 filialeDiArrivoLabel.setText(scegliFiliale.getValue());
		 
	 }
	 
	 public void onActionAcconto(){
		 accontoLabel.setText(inserisciAcconto.getText());
	 }
	 
	 public void handleScegliAuto(Auto auto){
			autoDispScelta = auto;
			fasciaLabel.setText(auto.getFasce());
			modelloLabel.setText(auto.getModello());
			targaLabel.setText(auto.getTarga());
		}
	 
}
	
	
	
	
	
	
	
	
	



