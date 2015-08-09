package layout.view;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import dataAccess.SchemaDb;
import presentationTier.FrontController;
import util.CountryManager;
import util.NotificationManager;
import util.NumberPlateValidator;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.Auto;
import layout.model.entities.ManagerDiFiliale;


public class ManageAutoController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	Label filiale;
	@FXML
	Label filialeNonDisp;
	@FXML
	Label examplePlate;
	
	
	//Sezione inserimento auto
	@FXML
	private ComboBox<String> scegliFascia;
	@FXML
	private ComboBox<String> scegliProvenienza;
	@FXML
	private ComboBox<String> scegliModello;
	@FXML
	private TextField nuovaTarga;
	
	
	//Sezione auto non disponibili
	@FXML
	 private TableView<Auto> autoTableNonDisp;
	@FXML
	private ComboBox<String> scegliStatusDaNonDispon;
	@FXML
	private TextField nuovaTargaAutoNonDisp;
	@FXML
	Label examplePlateAutoNonDisp;
	
	//Sezione auto disponibili
	@FXML
	 private TableView<Auto> autoTable;
	@FXML
	private ComboBox<String> scegliStatusDaDispon;
	@FXML
	private TextField nuovaTargaAutoDisp;
	@FXML
	Label examplePlateAutoDisp;
	
	
	
	private ObservableList<Auto> autoData = FXCollections.observableArrayList();
	private ObservableList<String> fasceData = FXCollections.observableArrayList();
	private ObservableList<String> modelliData = FXCollections.observableArrayList();
	
	
	//elementi scelti dagli spinner
	private String modelloScelto;
	private String nazioneScelta;
	private String statusSceltoDaDisp;
	private String statusSceltoDaNonDisp;
	
	//elementi scelti dalle tabelle
	private Auto autoDispScelta;
	private Auto autoNonDispScelta;
	
	@Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		filiale.setText(Context.getInstance().getUtente().getFiliale().getNome());
		filialeNonDisp.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
		riempiTabellaAuto();
		riempiTabellaAutoNonDisp();
		
		autoTableNonDisp.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliAutoNonDispon(newValue));
		
		autoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliAutoDispon(newValue));
		
		//Popolamento Spinner
		popolaSpinnerFasce();
		popolaSpinnerStatus();
		
		scegliStatusDaDispon.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliStatusDaDispon(new_value));
		scegliStatusDaNonDispon.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliStatusDaNonDispon(new_value));
		
		scegliProvenienza.setItems(CountryManager.getCountryNames());
		
		scegliProvenienza.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliProvenienza(new_value));
		scegliFascia.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliFascia(new_value));	
	
	}
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	//gestione scelta tabella
	
	private void handleScegliAutoDispon(Auto auto){
		autoDispScelta = auto;
		examplePlateAutoDisp.setText(NumberPlateValidator.getPlateExample(auto.getProvenienza()));
		scegliStatusDaDispon.getSelectionModel().select(auto.getStatus());
		nuovaTargaAutoDisp.setText(auto.getTarga());
	}
	
	private void handleScegliAutoNonDispon(Auto auto){
		autoNonDispScelta = auto;
		examplePlateAutoNonDisp.setText(NumberPlateValidator.getPlateExample(auto.getProvenienza()));
		scegliStatusDaNonDispon.getSelectionModel().select(auto.getStatus());
		nuovaTargaAutoNonDisp.setText(auto.getTarga());
	}
	
	//Gestione scelta spinner
	
	
	private void handleScegliStatusDaDispon(Number value){
		statusSceltoDaDisp = scegliStatusDaDispon.getItems().get(value.intValue());
	}
	
	
	private void handleScegliStatusDaNonDispon(Number value){
		statusSceltoDaNonDisp = scegliStatusDaNonDispon.getItems().get(value.intValue());
	}
	
	
	
	private void handleScegliProvenienza(Number value){
		nazioneScelta = scegliProvenienza.getItems().get(value.intValue());
		examplePlate.setText(NumberPlateValidator.getPlateExample(nazioneScelta));
		
	}
	
	private void handleScegliFascia(Number value){
		String selectedFascia = scegliFascia.getItems().get(value.intValue());
		
		popolaSpinnerModelli(selectedFascia);
		scegliModello.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ovModello, valueModello, new_valueModello) -> handleScegliModello(new_valueModello));
		
	}
	
	private void handleScegliModello(Number value){
		try{
			modelloScelto = scegliModello.getItems().get(value.intValue());
		}catch(ArrayIndexOutOfBoundsException e){
			scegliModello.getSelectionModel().select(null);
		}	
	}
	
	
	
	//Popolazione spinner
	
	private void popolaSpinnerModelli(String fascia){
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiModelli"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("nome_fascia",fascia);
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		modelliData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				modelliData.add(
						risultato.get("nome_modello" + Integer.toString(i))
				); 
				
			}
		}
		
		scegliModello.setItems(modelliData);

	}
	
	private void popolaSpinnerStatus(){
		ObservableList<String> statiAuto = FXCollections.observableArrayList();
		
		for(int i = 0 ; i < Auto.POSSIBILE_STATUS.length ; i++){
			statiAuto.add(Auto.POSSIBILE_STATUS[i]);
		}
		
		scegliStatusDaDispon.setItems(statiAuto);
		scegliStatusDaNonDispon.setItems(statiAuto);
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
	
	
	
	//Popolazione tabelle
	
	
	private void riempiTabellaAuto(){
		TableManager.riempiTabellaAuto(autoTable,
				Integer.toString(Context.getInstance().getUtente().getFiliale().getId()),"disponibile");
		
	}
	
	private void riempiTabellaAutoNonDisp(){
		
		TableManager.riempiTabellaAuto(autoTableNonDisp,
				Integer.toString(Context.getInstance().getUtente().getFiliale().getId()),"non_disponibile");
		
	}
	
	
	//gestione pulsanti
	
	@FXML
	private void handleCancellaAutoDisp() {
		if(autoDispScelta != null){
			Optional<ButtonType> result = NotificationManager.setConfirm("L'auto non sarà più disponbile nel sistema.\n\nContinuare?");
			if (result.get() == ButtonType.OK){
				queryCancellaAuto(autoDispScelta.getId());
			}
		}else{
			NotificationManager.setWarning("Scegli un auto tra quelle disponibili prima.");
		}
		
	}
	@FXML
	private void handleCambiaStatusAutoDisp() {
		if (autoDispScelta == null){
			NotificationManager.setWarning("Scegli un auto tra quelle disponibili prima.");
		}else if(statusSceltoDaDisp == null){
			NotificationManager.setWarning("Scegli il Nuovo status");
		}else{
			queryModificaStatus(autoDispScelta.getId());
		}
	}
	
	@FXML
	private void handleCancellaAutoNonDisp() {
		if(autoNonDispScelta != null){
			Optional<ButtonType> result = NotificationManager.setConfirm("L'auto non sarà più disponbile nel sistema.\n\nContinuare?");
			if (result.get() == ButtonType.OK){
			    queryCancellaAuto(autoNonDispScelta.getId());
			}
		}else{
			NotificationManager.setWarning("Scegli un auto tra quelle non disponibili prima.");
		}
	}
	@FXML
	private void handleCambiaStatusAutoNonDisp() {
		if (autoNonDispScelta == null){
			NotificationManager.setWarning("Scegli un auto tra quelle disponibili prima.");
		}else if(statusSceltoDaNonDisp == null){
			NotificationManager.setWarning("Scegli il Nuovo status");
		}else{
			queryModificaStatus(autoNonDispScelta.getId());
		}
	}
	@FXML
	private void handleInserisciAuto() {
		
		if (nuovaTarga.getText().equals("") 
				|| nazioneScelta == null
				|| modelloScelto == null){
			NotificationManager.setError("Tutti i campi sono obbligatori");
		} else if (!NumberPlateValidator.validate(nuovaTarga.getText(), nazioneScelta)){
			NotificationManager.setError("Targa non conforme agli standard della nazione scelta.\n"+
										"Nazione Scelta : "+nazioneScelta);
		} else {
			queryInsertAuto(nuovaTarga.getText(), nazioneScelta, modelloScelto);
		}

	}
	@FXML
	private void handleCambiaTargaAutoNonDisp() {
			
		if(nuovaTargaAutoNonDisp.getText().equals("")){
			NotificationManager.setWarning("Inserisci Una Targa.");
		}else if(autoNonDispScelta == null){
			NotificationManager.setWarning("Scegli un auto tra quelle non disponibili prima.");
		}else if(!NumberPlateValidator.validate(nuovaTargaAutoNonDisp.getText(),
				autoNonDispScelta.getProvenienza())){
			NotificationManager.setWarning("Targa non nel formato standard della sua nazione.");
		}else{
			queryCambiaTarga(nuovaTargaAutoNonDisp.getText(),autoNonDispScelta.getId());
		}

	}
	
	@FXML
	private void handleCambiaTargaAutoDisp() {
		if(nuovaTargaAutoDisp.getText().equals("")){
			NotificationManager.setWarning("Inserisci Una Targa.");
		}else if(autoDispScelta == null){
			NotificationManager.setWarning("Scegli un auto tra quelle disponibili prima.");
		}else if(!NumberPlateValidator.validate(nuovaTargaAutoDisp.getText(),
				autoDispScelta.getProvenienza())){
			NotificationManager.setWarning("Targa non nel formato standard della sua nazione.");
		}else{
			queryCambiaTarga(nuovaTargaAutoDisp.getText(),autoDispScelta.getId());
		}
	}
	
	private void queryCambiaTarga(String targa,int idAuto){
		System.out.println("OK");
	}
	
	private void queryCancellaAuto(int idAuto){
		String[] comando = new String[]{"businessTier.GestioneAuto", "cancellaAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("ida", Integer.toString(idAuto));
		HashMap<String, String> risultato = new HashMap<>();
		
		risultato =	FrontController.request(comando, inputParam);
		
		}
	
	private void queryInsertAuto(String targa, String nazione, String modello){
		System.out.println("OK");
	}
	
	private void queryModificaStatus(int id){
		System.out.println("OK");
	}
}
