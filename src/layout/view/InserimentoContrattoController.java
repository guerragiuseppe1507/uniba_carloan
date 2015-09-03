package layout.view;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import presentationTier.FrontController;
import util.DateValidator;
import util.NotificationManager;
import util.PriceValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.Auto;
import layout.model.entities.Cliente;
import layout.model.entities.Contratto;
import layout.model.entities.Filiale;

public class InserimentoContrattoController implements Initializable, ControlledScreen,InterStageCallBackListener {
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	//Sezione Coontratti Stipulati
	@FXML
	private TableView<Cliente> clientiTable; 
	 
	@FXML
	private TableView<Auto> autoTable;
	
	
	@FXML
	private Text nomeCliente;
	@FXML
	private Text cognomeCliente;
	
	
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
	private DatePicker dataLimitePicker;
	@FXML
	private Label dataLimiteLabel;
	@FXML
	private Label filialeDiPartenzaLabel;
	@FXML
	private Label filialeDiArrivoLabel;
	@FXML
	private Label accontoLabel;
	
	//prezzi
	@FXML
	private Text tariffaBaseText;
	@FXML
	private Text costoText;
	@FXML
	private Text penaleText;
	@FXML
	private Label tariffaBaseLabel;
	@FXML
	private Label costoLabel;
	@FXML
	private Label penaleLabel;

	
	@FXML
	private TextField inserisciAcconto;
	@FXML
	private ComboBox<Filiale> scegliFiliale;
	@FXML
	private ComboBox<String> scegliChilometraggio;
	@FXML
	private ComboBox<String> scegliTariffa;
	

	
	private ObservableList<Filiale> filialiData = FXCollections.observableArrayList();
	HashMap<String, String> prezzi = new HashMap<>();
	
	private Auto autoDispScelta;
	private Cliente clienteScelto; 
	 
	@Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		dataInizioLabel.setText(dateFormat.format(date).toString());
		
		filialeDiPartenzaLabel.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
		riempiTabellaClienti();
		riempiTabellaAuto();
		
		popolaSpinnerChilometraggio();
		popolaSpinnerTariffa();
		popolaSpinnerFiliali();
		
		dataLimitePicker.valueProperty().addListener(new ChangeListener<LocalDate>() { 

			@Override
			public void changed(ObservableValue<? extends LocalDate> arg0,
					LocalDate arg1, LocalDate arg2) {
				dataLimiteLabel.setText(arg2.toString());
				
			}
	    });
		
		
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		    public DateCell call(final DatePicker datePicker) {
		        return new DateCell() {
		            @Override 
		            public void updateItem(LocalDate item, boolean empty) {
		                super.updateItem(item, empty);

		                if (DateValidator.isContractLimitValid(item)) {
		                        setDisable(true);
		                        setStyle("-fx-background-color: #ffc0cb;");
		                }
		                
		            }
		        };
		    }
		};
		dataLimitePicker.setDayCellFactory(dayCellFactory);
		dataLimitePicker.setShowWeekNumbers(false);
		
		clientiTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliCliente(newValue));
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
				
				filialiData.add(new Filiale(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("luogo" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i))) 
				); 
				
				
			}
		}
		
		scegliFiliale.setItems(filialiData);
		scegliFiliale.setValue(Context.getInstance().getUtente().getFiliale());
		filialeDiArrivoLabel.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
	}

	 
	 //Riepiloghi
	 public void onActionTariffa(){
		 
		 tariffaLabel.setText(scegliTariffa.getValue());
		 impostaPrezzi();
		 
	 }
	 
	 public void onActionChilometraggio(){
		 chilometraggioLabel.setText(scegliChilometraggio.getValue());
		 impostaPrezzi();
		 
	 }
	 
	 public void impostaPrezzi(){
		 try{ 
			 
			 if(scegliTariffa.getValue().equals("GIORNALIERA")){
				 tariffaBaseText.setText("Tariffa Base Giornaliera");
				 tariffaBaseLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("tariffa_base_g")));
			 } else {
				 tariffaBaseText.setText("Tariffa Base Settimanale");
				 tariffaBaseLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("tariffa_base_s")));
			 }
			 
			 if(scegliChilometraggio.getValue().equals("LIMITATO")){
				 costoText.setText("Costo Chilometraggio Limitato");
				 penaleText.setText("Penale Chilometraggio Limitato");
				 costoLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("costo_chilometrico")));
				 penaleLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("penale_chilometri")));
			 } else {
				 costoText.setText("Costo Chilometraggio Illimitato");
				 penaleText.setText("");
				 penaleLabel.setText("");
				 if(scegliTariffa.getValue().equals("GIORNALIERA")){
					 costoLabel.setText(
							 PriceValidator.validatePrice("€", prezzi.get("tariffa_illim_g")));
				 } else {
					 costoLabel.setText(PriceValidator.validatePrice("€", prezzi.get("tariffa_illim_s")));
				 }
			 }
		 } catch (NullPointerException e){
			 
		 } 
	 }
	 
	 public void onActionFiliale(){
		 filialeDiArrivoLabel.setText(scegliFiliale.getValue().toString());
		 
	 }
	 
	 public void onActionAcconto(){
		 String resultString = inserisciAcconto.getText();
		 resultString = PriceValidator.validatePrice("€", resultString);
		 accontoLabel.setText(resultString); 
	 }
	 
	 public void handleScegliAuto(Auto auto){
			autoDispScelta = auto;
			fasciaLabel.setText(auto.getFasce());
			modelloLabel.setText(auto.getModello().getNome());
			targaLabel.setText(auto.getTarga());
			caricaPrezzi(auto.getFasce());
			impostaPrezzi();
	 }
	 
	 public void handleScegliCliente(Cliente cliente){
			clienteScelto = cliente;
			nomeCliente.setText(cliente.getNome());
			cognomeCliente.setText(cliente.getCognome());
	 }
	
	 private void caricaPrezzi(String fascia){
			String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiPrezzi"};
			HashMap<String, String> inputParam = new HashMap<>();
			inputParam.put("nomeFascia", fascia);
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			if(risultato.get(util.ResultKeys.ESITO).equals("true")){
				prezzi.put("tariffa_base_g",risultato.get("tariffa_base_g"));
				prezzi.put("tariffa_base_s",risultato.get("tariffa_base_s"));
				prezzi.put("costo_chilometrico",risultato.get("costo_chilometrico"));
				prezzi.put("penale_chilometri",risultato.get("penale_chilometri"));
				prezzi.put("tariffa_illim_g",risultato.get("tariffa_illim_g"));
				prezzi.put("tariffa_illim_s",risultato.get("tariffa_illim_s"));
			}
	 }	 
	 
	 @FXML
	 public void handleOpenContract(){
		 
		 String statoContratto = checkContratto();
		 
		 if(statoContratto.equals("stipulabile")){
			 queryApriContratto();
		 } else {
			 NotificationManager.setWarning(statoContratto);
		 } 
	 }
	 
	 private String checkContratto(){
		 String warning = "Mancano nel contratto:\n";
		 boolean cStipulabile = true;
		 
		 if (nomeCliente.getText().equals("")){
			 warning += "- cliente;\n";
			 cStipulabile = false;
		 }
		 if (chilometraggioLabel.getText().equals("")){
			 warning += "- chilometraggio;\n";
			 cStipulabile = false;
		 }
		 if (tariffaLabel.getText().equals("")){
			 warning += "- tariffa;\n";
			 cStipulabile = false;
		 }
		 if (targaLabel.getText().equals("")) {
			 warning += "- auto;\n";
			 cStipulabile = false;
		 }
		 if (dataLimiteLabel.getText().equals("")) {
			 warning += "- data limite;\n";
			 cStipulabile = false;
		 }
		 if (accontoLabel.getText().equals("0.00 €")||
				 accontoLabel.getText().equals(PriceValidator.PRICE_ERR)){
			 warning += "- acconto;\n";
			 cStipulabile = false;
		 }
		 
		 if(!cStipulabile){
			 return warning;
		 } else {
			 return "stipulabile";
		 }
		 
	 }
	 
	 private void queryApriContratto(){
			String[] comando = new String[]{"businessTier.GestioneContratti", "stipulaContratto"};
			
			HashMap<String, String> inputParam = new HashMap<>();
			
			inputParam.put("tipo_km", chilometraggioLabel.getText());
			inputParam.put("tariffa", tariffaLabel.getText());
			inputParam.put("data_inizio", dataInizioLabel.getText());
			inputParam.put("data_limite", dataLimiteLabel.getText());
			inputParam.put("acconto", accontoLabel.getText().substring(0,accontoLabel.getText().length() -2));
			inputParam.put("status_contratto", Contratto.STATUS_APERTO);
			inputParam.put("id_cliente", Integer.toString(clienteScelto.getId()));
			inputParam.put("id_dipendente", Integer.toString(Context.getInstance().getUtente().getId()));
			inputParam.put("id_auto", Integer.toString(autoDispScelta.getId()));
			inputParam.put("id_start_filiale", Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
			inputParam.put("id_end_filiale", Integer.toString(scegliFiliale.getSelectionModel().getSelectedItem().getId()));
	
			inputParam.put("status_auto", Auto.STATUS_NOLEGGIATA);
			
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			
			if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
				NotificationManager.setInfo("Contratto stipulato con successo.");
			} else {
				NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
			}
			
	 }
	 
}
	
	
	
	
	
	
	
	
	



