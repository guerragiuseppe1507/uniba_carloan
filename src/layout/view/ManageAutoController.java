package layout.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.Auto;
import layout.model.entities.Fascia;
import layout.model.entities.Modello;

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
	private ComboBox<Fascia> scegliFascia;
	@FXML
	private ComboBox<String> scegliProvenienza;
	@FXML
	private ComboBox<Modello> scegliModello;
	@FXML
	private TextField nuovaTarga;
	@FXML
	private ComboBox<String> scegliFiliale;
	
	
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
	
	private ObservableList<Fascia> fasceData = FXCollections.observableArrayList();
	private ObservableList<Modello> modelliData = FXCollections.observableArrayList();
	
	//elementi scelti dagli spinner
	private Modello modelloScelto;
	private Fascia fasciaScelta;
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
		
		autoTable.setPlaceholder(new Label(util.ResultKeys.LOADING_TABLE));
		autoTableNonDisp.setPlaceholder(new Label(util.ResultKeys.LOADING_TABLE));
		
		filiale.setText(Context.getInstance().getUtente().getFiliale().getNome());
		filialeNonDisp.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
		scegliModello.setDisable(true);
		popolaSpinnerStatus();
		
		scegliStatusDaDispon.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliStatusDaDispon(new_value));
		scegliStatusDaNonDispon.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliStatusDaNonDispon(new_value));
		
		scegliProvenienza.setItems(CountryManager.getCountryNames());
		
		scegliProvenienza.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliProvenienza(new_value));
	
	}
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	@Override
	public void riempiCampi(){
		
		riempiTabellaAuto();
		riempiTabellaAutoNonDisp();
		
		autoTableNonDisp.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliAutoNonDispon(newValue));
		
		autoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliAutoDispon(newValue));
		
		//Popolamento Spinner
		popolaSpinnerFasce();
		
		scegliFascia.getSelectionModel().selectedIndexProperty().addListener(
				(ChangeListener<Number>) (ov, value, new_value) -> handleScegliFascia(new_value));	
		
	}
	
	
	//gestione scelta tabella
	
	private void handleScegliAutoDispon(Auto auto){
		autoDispScelta = auto;
		if(autoDispScelta != null){
			examplePlateAutoDisp.setText(NumberPlateValidator.getPlateExample(autoDispScelta.getProvenienza()));
			scegliStatusDaDispon.getSelectionModel().select(autoDispScelta.getStatus());
			nuovaTargaAutoDisp.setText(autoDispScelta.getTarga());
		}
	}
	
	private void handleScegliAutoNonDispon(Auto auto){
		autoNonDispScelta = auto;
		if(autoNonDispScelta != null){
			examplePlateAutoNonDisp.setText(NumberPlateValidator.getPlateExample(autoNonDispScelta.getProvenienza()));
			scegliStatusDaNonDispon.getSelectionModel().select(autoNonDispScelta.getStatus());
			nuovaTargaAutoNonDisp.setText(autoNonDispScelta.getTarga());
		}
	}
	
	//Gestione scelta spinner
	
	
	private void handleScegliStatusDaDispon(Number value){
		if(value.intValue() != -1){
			statusSceltoDaDisp = scegliStatusDaDispon.getItems().get(value.intValue());
		}
	}
	
	
	private void handleScegliStatusDaNonDispon(Number value){
		if(value.intValue() != -1){
			statusSceltoDaNonDisp = scegliStatusDaNonDispon.getItems().get(value.intValue());
		}
	}
	
	
	
	private void handleScegliProvenienza(Number value){
		nazioneScelta = scegliProvenienza.getItems().get(value.intValue());
		examplePlate.setText(NumberPlateValidator.getPlateExample(nazioneScelta));
		
	}
	
	private void handleScegliFascia(Number value){
		fasciaScelta = scegliFascia.getItems().get(value.intValue());
		
		popolaSpinnerModelli(fasciaScelta);
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
	
	private void popolaSpinnerModelli(Fascia fascia){
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiModelli"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("nome_fascia",fascia.getNome());
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		modelliData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				modelliData.add(new Modello(
						Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("nome_modello" + Integer.toString(i)))		
				); 
				
			}
		}
		
		scegliModello.setItems(modelliData);
		scegliModello.setDisable(false);

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
				
				fasceData.add(new Fascia(
						Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("nome_fascia" + Integer.toString(i)))
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
			NotificationManager.setWarning("Scegli il nuovo status");
		}else{
			queryModificaStatus(autoDispScelta.getId(),"disponibile");
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
			queryModificaStatus(autoNonDispScelta.getId(),"non_disponibile");
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
			queryInsertAuto(nuovaTarga.getText(), nazioneScelta, Integer.toString(modelloScelto.getId()));
		}

	}
	@FXML
	private void handleCambiaTargaAutoNonDisp() {
			
		if(nuovaTargaAutoNonDisp.getText().equals("")){
			NotificationManager.setWarning("Inserisci una targa.");
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
			
	
		String[] comando = new String[]{"businessTier.GestioneAuto", "modificaTarga"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id_auto",Integer.toString (idAuto));
		inputParam.put("new_targa", targa);
		HashMap<String, String> risultato = new HashMap<>();
		
		risultato =	FrontController.request(comando, inputParam);
		riempiTabellaAuto();
		riempiTabellaAutoNonDisp();
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			NotificationManager.setInfo("Targa modificata con successo.");
		} else {
			NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
		}
	
	}
	
	private void queryCancellaAuto(int idAuto){
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "cancellaAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id_auto", Integer.toString(idAuto));
		HashMap<String, String> risultato = new HashMap<>();
		
		risultato =	FrontController.request(comando, inputParam);
		riempiTabellaAuto();
		riempiTabellaAutoNonDisp();
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){	
			NotificationManager.setInfo("Auto eliminata con successo.");
		} else {
			NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
		}
	
	}
	
	private void queryInsertAuto(String targa, String nazione, String id_modello){
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "inserisciAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("nazione", nazione);
		inputParam.put("id_modello", id_modello);
		inputParam.put("targa", targa);
		inputParam.put("id_filiale", Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
		HashMap<String, String> risultato = new HashMap<>();
		
		risultato =	FrontController.request(comando, inputParam);
		riempiTabellaAuto();
		riempiTabellaAutoNonDisp();
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			NotificationManager.setInfo("Auto inserita con successo.");	
		} else {
			NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
		}
		
	}
	
	private void queryModificaStatus(int id_auto, String filter){
		String[] comando = new String[]{"businessTier.GestioneAuto", "modificaStatus"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id_auto", Integer.toString(id_auto));
		if (filter.equals("disponibile")){
			inputParam.put("new_status", statusSceltoDaDisp);
		}else if (filter.equals("non_disponibile")){
			inputParam.put("new_status", statusSceltoDaNonDisp);
		}
		inputParam.put("force", "false");
			
		HashMap<String, String> risultato = new HashMap<>();
		
		risultato =	FrontController.request(comando, inputParam);
		riempiTabellaAuto();
		riempiTabellaAutoNonDisp();
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			if (filter.equals("disponibile")){
				nuovaTargaAutoDisp.setText("");
				scegliStatusDaDispon.setValue(null);
			}else if (filter.equals("non_disponibile")){
				nuovaTargaAutoNonDisp.setText("");
				scegliStatusDaNonDispon.setValue(null);
			}
		} else {
			NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
		}
		
	}
	
}
