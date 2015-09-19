package layout.view;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
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
import layout.model.entities.Fascia;
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
	private Text limiteText;
	@FXML
	private Label tariffaBaseLabel;
	@FXML
	private Label costoLabel;
	@FXML
	private Label penaleLabel;
	@FXML
	private Label limiteLabel;

	
	@FXML
	private TextField inserisciAcconto;
	@FXML
	private ComboBox<Filiale> scegliFiliale;
	@FXML
	private ComboBox<String> scegliChilometraggio;
	@FXML
	private ComboBox<String> scegliTariffa;
	
	@FXML
	private Button stipulaContratto;
	@FXML
	private Button inserisciClienteBtn;
	
	private ObservableList<Filiale> filialiData = FXCollections.observableArrayList();
	HashMap<String, String> prezzi = new HashMap<String, String>();
	
	private Auto autoDispScelta;
	private Cliente clienteScelto;
	private Contratto contrattoDaModificare;
	
	private boolean isEditMode = false;
	 
	@Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		autoTable.setPlaceholder(new Label(util.ResultKeys.LOADING_TABLE));
		clientiTable.setPlaceholder(new Label(util.ResultKeys.LOADING_TABLE));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		dataInizioLabel.setText(dateFormat.format(date).toString());
		
		filialeDiPartenzaLabel.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
		popolaSpinnerChilometraggio();
		popolaSpinnerTariffa();
		
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
		
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	@Override
	public void riempiCampi(){
		
		riempiTabellaClienti();
		riempiTabellaAuto();
		
		popolaSpinnerFiliali();
		
		clientiTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliCliente(newValue));
		autoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliAuto(newValue));
		
		if(myController.params.get("modContratto_contratto") != null
			&& myController.params.get("modContratto_auto") != null
			&& myController.params.get("modContratto_cliente") != null){
			inizializzaModificaContratto();
		}
		
	}
	
	private void inizializzaModificaContratto(){
		
		contrattoDaModificare = (Contratto) myController.params.get("modContratto_contratto");
		autoDispScelta = (Auto) myController.params.get("modContratto_auto");
		clienteScelto = (Cliente) myController.params.get("modContratto_cliente");
		clientiTable.getSelectionModel().select(clienteScelto);
		autoTable.getSelectionModel().select(autoDispScelta);
		autoTable.getColumns().clear();
		clientiTable.getColumns().clear();
		autoTable.setPlaceholder(new Label("Non è possibilie modificare l'auto associata al contratto"));
		clientiTable.setPlaceholder(new Label("Non è possibilie modificare il cliente associato al contratto"));
		
		inserisciClienteBtn.setDisable(true);
		
		Contratto c = (Contratto) myController.params.get("modContratto_contratto");
		
		scegliTariffa.setValue(c.getTariffa());
		scegliChilometraggio.setValue(c.getTipoKm());
		Filiale filialeDiArrivo = null;
		for(Filiale item : scegliFiliale.getItems()) {
            if(item.toString().equals(c.getFilialeDiArrivo())) {
            	filialeDiArrivo = item;
            }
        }
		if(filialeDiArrivo != null){
			scegliFiliale.setValue(filialeDiArrivo);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(c.getDataLimite(), formatter);
		dataLimitePicker.setValue(date);
		
		inserisciAcconto.setText(c.getAcconto());
		accontoLabel.setText(c.getAcconto()+" €");
		
		stipulaContratto.setText("Modifica Contratto");
		
		myController.params.remove("modContratto_contratto");
		myController.params.remove("modContratto_auto");
		myController.params.remove("modContratto_cliente");
		isEditMode = true;
		
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
		myController.showScreenNewWindow(ScreensFramework.InserimentoClienteFile, 
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
	 @FXML
	 public void onActionTariffa(){
		 
		 tariffaLabel.setText(scegliTariffa.getValue());
		 impostaPrezzi();
		 
	 }
	 
	 @FXML
	 public void onActionChilometraggio(){
		 chilometraggioLabel.setText(scegliChilometraggio.getValue());
		 impostaPrezzi();
		 
	 }
	 
	 private void impostaPrezzi(){
		 try{ 
			 
			 if(scegliTariffa.getValue().equals("GIORNALIERA")){
				 tariffaBaseText.setText("Tariffa base giornaliera :");
				 tariffaBaseLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("tariffa_base_g")));
				 if (scegliChilometraggio.getValue().equals("LIMITATO")){
					 limiteText.setText("Limite chilometri giornalieri :");
					 limiteLabel.setText(Integer.toString(Fascia.LIM_KM_G)+" km");
				 } 
			 } else {
				 tariffaBaseText.setText("Tariffa Base Settimanale :");
				 tariffaBaseLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("tariffa_base_s")));
				 if (scegliChilometraggio.getValue().equals("LIMITATO")){
					 limiteText.setText("Limite chilometri settimanali");
					 limiteLabel.setText(Integer.toString(Fascia.LIM_KM_S)+" km");
				 }
			 }
			 
			 if(scegliChilometraggio.getValue().equals("LIMITATO")){
				 costoText.setText("Costo chilometraggio limitato :");
				 penaleText.setText("Penale chilometraggio limitato :");
				 costoLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("costo_chilometrico")));
				 penaleLabel.setText(
						 PriceValidator.validatePrice("€", prezzi.get("penale_chilometri")));
			 } else {
				 costoText.setText("Costo chilometraggio Illimitato :");
				 penaleText.setText("");
				 penaleLabel.setText("");
				 limiteText.setText("");
				 limiteLabel.setText("");
				 if(scegliTariffa.getValue().equals("GIORNALIERA")){
					 costoLabel.setText(
							 PriceValidator.validatePrice("€", prezzi.get("tariffa_illim_g")));
				 } else {
					 costoLabel.setText(PriceValidator.validatePrice("€", prezzi.get("tariffa_illim_s")));
				 }
			 }
		 } catch (NullPointerException e){} 
		 
	 }
	 
	 @FXML
	 public void onActionFiliale(){
		 filialeDiArrivoLabel.setText(scegliFiliale.getValue().toString());
		 
	 }
	 
	 @FXML
	 public void onActionAcconto(){
		 String resultString = inserisciAcconto.getText();
		 resultString = PriceValidator.validatePrice("€", resultString);
		 accontoLabel.setText(resultString); 
	 }
	 
	 public void handleScegliAuto(Auto auto){
			autoDispScelta = auto;
			fasciaLabel.setText(auto.getFascia());
			modelloLabel.setText(auto.getModello().getNome());
			targaLabel.setText(auto.getTarga());
			caricaPrezzi(auto.getFascia());
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
			 if(isEditMode){
				 queryEditContratto();
			 } else {
				 queryApriContratto();
			 }
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
	 
	 private void queryEditContratto(){
			String[] comando = new String[]{"businessTier.GestioneContratti", "modificaContratto"};
			
			HashMap<String, String> inputParam = new HashMap<>();
			
			setParams(inputParam);
			inputParam.put("id_contratto", Integer.toString(contrattoDaModificare.getId()));
			inputParam.put("id_dipendente", Integer.toString(contrattoDaModificare.getIdDipendente()));
			
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			
			if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
				myController.showScreen(ScreensFramework.gestioneContrattoFile);
				if(Context.getInstance().getUtente().getId() == contrattoDaModificare.getIdDipendente()){
					ScreensFramework.PRIMARY_STAGE.setTitle(ScreensFramework.APP_NAME+" - "+ScreensFramework.gestioneContrattoTitle);
				} else {
					ScreensFramework.PRIMARY_STAGE.setTitle(ScreensFramework.APP_NAME+" - "+ScreensFramework.gestioneContrattoFilialeTitle);
					myController.opzioniAvvio = "gestioneFiliale";
				}
				NotificationManager.setInfo("Contratto modificato con successo.");
			} else {
				NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
			}
			
	 }
	 
	 private void queryApriContratto(){
			String[] comando = new String[]{"businessTier.GestioneContratti", "stipulaContratto"};
			
			HashMap<String, String> inputParam = new HashMap<>();
			
			setParams(inputParam);
			inputParam.put("id_dipendente", Integer.toString(Context.getInstance().getUtente().getId()));
			
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			
			if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
				myController.showScreen(ScreensFramework.gestioneContrattoFile);
				ScreensFramework.PRIMARY_STAGE.setTitle(ScreensFramework.APP_NAME+" - "+ScreensFramework.gestioneContrattoTitle);
				NotificationManager.setInfo("Contratto stipulato con successo.");
			} else {
				NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
			}	
	 }
	 
	 private void setParams(HashMap<String, String> inputParam){
		 
		 	inputParam.put("tipo_km", chilometraggioLabel.getText());
			inputParam.put("tariffa", tariffaLabel.getText());
			inputParam.put("data_inizio", dataInizioLabel.getText());
			inputParam.put("data_limite", dataLimiteLabel.getText());
			inputParam.put("acconto", accontoLabel.getText().substring(0,accontoLabel.getText().length() -2));
			inputParam.put("status_contratto", Contratto.STATUS_APERTO);
			inputParam.put("id_cliente", Integer.toString(clienteScelto.getId()));
			inputParam.put("id_auto", Integer.toString(autoDispScelta.getId()));
			inputParam.put("id_start_filiale", Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
			inputParam.put("id_end_filiale", Integer.toString(scegliFiliale.getSelectionModel().getSelectedItem().getId()));
			inputParam.put("status_auto", Auto.STATUS_NOLEGGIATA);
			
	 }
	 
}
	
	
	
	
	
	
	
	
	



