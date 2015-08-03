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
import layout.model.entities.Auto;
import layout.model.entities.Cliente;

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
	 private TableView<Cliente> tableClienti;
	 @FXML
	 private TableColumn<Cliente, String>nomeCliente;
	 @FXML
	 private TableColumn<Cliente, String>cognomeCliente;
	 @FXML
	 private TableColumn<Cliente, String>emailCliente;
	 @FXML
	 private TableColumn<Cliente, String>dataDiNascitaCliente;
	 @FXML
	 private TableColumn<Cliente, String>residenzaCliente;
	 @FXML
	 private TableColumn<Cliente,String>codiceFiscaleCliente;
	 @FXML
	 private TableColumn<Cliente, String>codicePatenteCliente;
	 
	 
	 @FXML
	 private TableView<Auto> autoTable;
	 @FXML
	 private TableColumn<Auto, String> modello;
	@FXML
	private TableColumn<Auto, String> targa;
	@FXML
	private TableColumn<Auto, String> status;
	@FXML
	private TableColumn<Auto, String> chilometraggio;
	@FXML
	private TableColumn<Auto, String> fasce;	
	@FXML
	private TableColumn<Auto, String> provenienza;
	
	
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
	
	public static final String[] POSSIBILE_CHILOMETRAGGIO = {"LIMITATO", "ILLIMITATO"};
	public static final String[] POSSIBILE_TARIFFA = {"GIORNALIERA", "SETTIMANALE"};
	
	private ObservableList<String> filialiData = FXCollections.observableArrayList();
	private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
	private ObservableList<Auto> autoData = FXCollections.observableArrayList(); 
	
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
	
	
	
    private void riempiTabellaClienti(){
		
		String[] comando = new String[]{"businessTier.GestioneClienti", "recuperoDatiClienti"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		clienteData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				clienteData.add(new Cliente(
						Integer.parseInt(risultato.get("id"+Integer.toString(i))),
						risultato.get("nome" + Integer.toString(i)),
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)),
						risultato.get("dataDiNascita" + Integer.toString(i)),
						risultato.get("residenza"+Integer.toString(i)),
						risultato.get("codiceFiscale" + Integer.toString(i)),
						risultato.get("codicePatente" + Integer.toString(i)))); 
			}
				
			
			nomeCliente.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
			cognomeCliente.setCellValueFactory(cellData->cellData.getValue().cognomeProperty());
			emailCliente.setCellValueFactory(cellData->cellData.getValue().emailProperty());
			residenzaCliente.setCellValueFactory(cellData->cellData.getValue().residenzaProperty());
			dataDiNascitaCliente.setCellValueFactory(cellData->cellData.getValue().dataDiNascitaProperty());
			codiceFiscaleCliente.setCellValueFactory(cellData->cellData.getValue().codiceFiscaleProperty());
			codicePatenteCliente.setCellValueFactory(cellData->cellData.getValue().codicePatenteProperty());

			FXCollections.sort(clienteData);

			tableClienti.setItems(clienteData);
			
		}
		
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
	
	private void riempiTabellaAuto(){
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("disponibilita", "disponibile");
		inputParam.put("id_filiale", Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		autoData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				autoData.add(new Auto(
						Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("modello" + Integer.toString(i)),
						risultato.get("nome_filiale" + Integer.toString(i)), 
						risultato.get("status" + Integer.toString(i)),
						risultato.get("targa"+Integer.toString(i)),
						risultato.get("chilometraggio" + Integer.toString(i)),
						risultato.get("fasce" + Integer.toString(i)),
						risultato.get("provenienza" + Integer.toString(i)))
				); 
				
			}
			
			modello.setCellValueFactory(cellData->cellData.getValue().modelloProperty());
			status.setCellValueFactory(cellData->cellData.getValue().statusProperty());
			chilometraggio.setCellValueFactory(cellData->cellData.getValue().chilometraggioProperty());
			targa.setCellValueFactory(cellData->cellData.getValue().targaProperty());
			fasce.setCellValueFactory(cellData->cellData.getValue().fasceProperty());
			provenienza.setCellValueFactory(cellData->cellData.getValue().provenienzaProperty());
			
			autoTable.setItems(autoData);
			
		} else {
			
			autoTable.setPlaceholder(new Label("No Auto Found"));
			
		}
		
	}
 

	private void popolaSpinnerChilometraggio(){
		ObservableList<String> tipiChilometraggi = FXCollections.observableArrayList();
		
		for(int i = 0 ; i < POSSIBILE_CHILOMETRAGGIO.length ; i++){
			tipiChilometraggi.add(POSSIBILE_CHILOMETRAGGIO[i]);
		}
		scegliChilometraggio.setItems(tipiChilometraggi);
		
	}



	private void popolaSpinnerTariffa(){
		ObservableList<String> tipiTariffa = FXCollections.observableArrayList();
		
		for(int i = 0 ; i < POSSIBILE_TARIFFA.length ; i++){
			tipiTariffa.add(POSSIBILE_TARIFFA[i]);
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
						risultato.get("nome" + Integer.toString(i))
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
	
	
	
	
	
	
	
	
	



