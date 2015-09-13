package layout.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.NotificationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.Auto;
import layout.model.entities.Cliente;
import layout.model.entities.Contratto;
import layout.model.entities.Modello;

public class GestioneContrattiController  implements Initializable, ControlledScreen, InterStageCallBackListener {
		
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	private TableView<Contratto> contrattoTable;
	
	@FXML
	private Text tableTitle;
	
	@FXML
	private ComboBox<String> statoMenuButton;
	
	//dati auto
	@FXML
	private Text targaAuto;
	
	@FXML
	private Text modelloAuto;
	
	@FXML
	private Text fasciaAuto;
	
	@FXML
	private Text kmAuto;
	
	@FXML
	private Text provenienzaAuto;
	
	//dati cliente
	@FXML
	private Text nomeCliente;
	
	@FXML
	private Text cognomeCliente;
	
	@FXML
	private Text emailCliente;
	
	@FXML
	private Text residenzaCliente;
	
	@FXML
	private Text dataNascitaCliente;
	
	@FXML
	private Text codFiscaleCliente;
	
	@FXML
	private Text codPatenteCliente;
	
	@FXML
	private Button modificaContrattoBtn;
	
	@FXML
	private Button annullaContrattoBtn;
	
	@FXML
	private Button chiudiContrattoBtn;
	
	private Auto autoScelta;
	private Cliente clienteScelto;
	private Contratto contrattoScelto;
	private String opzioniAvvio;
	 
	@Override
	public void initialize(URL url, ResourceBundle rb){
			
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
			
		contrattoTable.setPlaceholder(new Label(util.ResultKeys.LOADING_TABLE));
		
		popolaSpinnerStatus();
	

	}

	public void setScreenParent(ScreensController screenParent){
			myController = screenParent;
			ContextMenu.showContextMenu(menu,myController);
	}
	
	@Override
	public void riempiCampi(){
		
		opzioniAvvio = myController.opzioniAvvio;
		
		riempiTabellaContratti("APERTO");
		if (opzioniAvvio != null && opzioniAvvio.equals("contrattiInArrivo")){
			annullaContrattoBtn.setDisable(true);
			modificaContrattoBtn.setDisable(true);
			chiudiContrattoBtn.setDisable(true);
		}
		
		contrattoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> handleScegliContratto(newValue));
		
		myController.opzioniAvvio = null;
		
	}
	
	private void riempiTabellaContratti(String status){
		
		if (opzioniAvvio != null && opzioniAvvio.equals("gestioneFiliale")){
			TableManager.riempiTabellaContratto(contrattoTable,
					Integer.toString(Context.getInstance().getUtente().getId()),
					Integer.toString(Context.getInstance().getUtente().getFiliale().getId()),
					"GESTIONE_FILIALE",
					Context.getInstance().getUserType(),
					status);
		} else if (opzioniAvvio != null && opzioniAvvio.equals("contrattiInArrivo")){
			TableManager.riempiTabellaContratto(contrattoTable,
					Integer.toString(Context.getInstance().getUtente().getId()),
					Integer.toString(Context.getInstance().getUtente().getFiliale().getId()),
					"CONTRATTI_FILIALE",
					Context.getInstance().getUserType(),
					status);
		} else {
			TableManager.riempiTabellaContratto(contrattoTable,
					Integer.toString(Context.getInstance().getUtente().getId()),
					Integer.toString(Context.getInstance().getUtente().getFiliale().getId()),
					"MIEI_CONTRATTI",
					"TUTTI",
					status);
		}
		
	}
	
	private void popolaSpinnerStatus(){
		
		ObservableList<String> statusPossibili = FXCollections.observableArrayList();
		
		for(int i = 0 ; i < Contratto.POSSIBILE_STATUS.length ; i++){
			statusPossibili.add(Contratto.POSSIBILE_STATUS[i]);
			
		}
		statoMenuButton.setItems(statusPossibili);
		statoMenuButton.getSelectionModel().select(Contratto.STATUS_APERTO);
		
	}
	
	@FXML
	private void onActionStatus(){
		
		riempiTabellaContratti(statoMenuButton.getValue());
		
		if (statoMenuButton.getValue().equals(Contratto.STATUS_APERTO) 
				&& (opzioniAvvio != null && !opzioniAvvio.equals("contrattiInArrivo"))){
			tableTitle.setText("Contratti aperti");
			annullaContrattoBtn.setDisable(false);
			modificaContrattoBtn.setDisable(false);
			chiudiContrattoBtn.setDisable(false);
		} else if (statoMenuButton.getValue().equals(Contratto.STATUS_CHIUSO) 
				&& (opzioniAvvio != null && !opzioniAvvio.equals("contrattiInArrivo"))){
			tableTitle.setText("Contratti chiusi");
			annullaContrattoBtn.setDisable(true);
			modificaContrattoBtn.setDisable(true);
			chiudiContrattoBtn.setDisable(true);
		}
		
		resetInfoContratto();
		
	}
	
	private void resetInfoContratto(){
		targaAuto.setText("");
		modelloAuto.setText("");
		fasciaAuto.setText("");
		kmAuto.setText("");
		provenienzaAuto.setText("");
		nomeCliente.setText("");
		cognomeCliente.setText("");
		emailCliente.setText("");
		residenzaCliente.setText("");
		dataNascitaCliente.setText("");
		codFiscaleCliente.setText("");
		codPatenteCliente.setText("");
	}
	
	private void handleScegliContratto(Contratto c){
		
		if(c != null){
			this.contrattoScelto = c;
			this.clienteScelto = getCliente(c.getIdCliente());
			this.autoScelta = getAuto(c.getIdAuto());
		}
		
		if (clienteScelto != null && autoScelta != null){
		
			//riempiment dati auto
			targaAuto.setText(autoScelta.getTarga());		
			modelloAuto.setText(autoScelta.getModello().getNome());		
			fasciaAuto.setText(autoScelta.getFascia());		
			kmAuto.setText(autoScelta.getChilometraggio() + " km");		
			provenienzaAuto.setText(autoScelta.getProvenienza());
					
			//riempimento dati cliente
			nomeCliente.setText(clienteScelto.getNome());		
			cognomeCliente.setText(clienteScelto.getCognome());		
			emailCliente.setText(clienteScelto.getEmail());		
			residenzaCliente.setText(clienteScelto.getResidenza());		
			dataNascitaCliente.setText(clienteScelto.getDataDiNascita());			
			codFiscaleCliente.setText(clienteScelto.getCodiceFiscale());		
			codPatenteCliente.setText(clienteScelto.getCodicePatente());
		
		}
		
	}
	
	private Auto getAuto(int id){
		Auto a = null;
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("disponibilita", "singola");
		inputParam.put("id_auto", Integer.toString(id));
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);

		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			
			a = new Auto(
						Integer.parseInt(risultato.get("id0")),
						risultato.get("nome_filiale0"), 
						risultato.get("status0"),
						risultato.get("targa0"),
						risultato.get("chilometraggio0"),
						risultato.get("fasce0"),
						risultato.get("provenienza0")
					);
			a.setModello(new Modello(Integer.parseInt(risultato.get("id_modello0")),
					risultato.get("modello0"))
			);

		} else {
			System.out.println("Auto "+risultato.get(util.ResultKeys.MSG_ERR));
		}
			
		return a;
	}
		
	private Cliente getCliente(int id){
		Cliente c = null;
		
		String[] comando = new String[]{"businessTier.GestioneClienti", "recuperoDatiClienti"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("filtro", "singolo");
		inputParam.put("id_cliente", Integer.toString(id));
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);

		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			
			c = new Cliente(
						Integer.parseInt(risultato.get("id0")),
						risultato.get("nome0"),
						risultato.get("cognome0"), 
						risultato.get("email0"),
						risultato.get("dataDiNascita0"),
						risultato.get("residenza0"),
						risultato.get("codiceFiscale0"),
						risultato.get("codicePatente0")
					); 

		} else {
			System.out.println("Cliente "+risultato.get(util.ResultKeys.MSG_ERR));
		}
		
		return c;
	}
	
	
	@FXML
	private void handleModificaContratto(){
		if(contrattoScelto != null){
			myController.showScreen(ScreensFramework.inserimentoContrattoFile);
			ScreensFramework.PRIMARY_STAGE.setTitle(ScreensFramework.APP_NAME+" - "+ScreensFramework.modificaContrattoTitle);
			myController.params.put("modContratto_contratto", contrattoScelto);
			myController.params.put("modContratto_auto", autoScelta);
			myController.params.put("modContratto_cliente", clienteScelto);
		} else {
			NotificationManager.setWarning("Scegliere un contratto prima.");
		}
		
	}
	
	@FXML
	private void handleAnnullaContratto(){
		
		if(contrattoScelto != null){			
			Optional<ButtonType> result = NotificationManager.setConfirm("L'operazione è irreversibile.\nContinuare?");
			if (result.get() == ButtonType.OK){
				
				String[] comando = new String[]{"businessTier.GestioneContratti", "annullaContratto"};
				HashMap<String, String> inputParam = new HashMap<>();
				
				inputParam.put("id_contratto", Integer.toString(contrattoScelto.getId()));
				inputParam.put("id_auto", Integer.toString(autoScelta.getId()));
				inputParam.put("status_auto", Auto.STATUS_DISPONIBILE);
				
				HashMap<String, String> risultato = new HashMap<>();
				risultato =	FrontController.request(comando, inputParam);
				
				if(risultato.get(util.ResultKeys.ESITO).equals("true")){
					riempiTabellaContratti("APERTO");
					resetInfoContratto();
					NotificationManager.setInfo("Contratto annullato con successo.");
				} else {
					NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
				}
			}	
		} else {
			NotificationManager.setWarning("Scegliere un contratto prima.");
		}
		
	}
	
	@FXML
	private void handleChiudiContratto(){
		if(contrattoScelto != null){
			NotificationManager.setInput("Chilometri attuali auto :").ifPresent(km -> {
				if (isKmAttualiCorretto(km)){
					myController.showScreenNewWindow(ScreensFramework.chiusuraContrattoFile, 
			    			ScreensFramework.chiusuraContrattoTitle);
					InterStageEventsHandler.getInstance().setCaller(this);
					InterStageEventsHandler.getInstance().params.put("ChiudiContratto_contratto", contrattoScelto);
					InterStageEventsHandler.getInstance().params.put("ChiudiContratto_auto", autoScelta);
					InterStageEventsHandler.getInstance().params.put("ChiudiContratto_cliente", clienteScelto);
					InterStageEventsHandler.getInstance().options.put("ChiudiContratto_newKm", km);
				} else {
					NotificationManager.setError("Valore chilometri invalido "
							+ "o inferiore ai chilometri precontrattuali dell'auto.");
				}
			});
		} else {
			NotificationManager.setWarning("Scegliere un contratto prima.");
		}
	}

	@Override
	public void callBack() {
		riempiTabellaContratti("APERTO");
		resetInfoContratto();
		NotificationManager.setInfo("Contratto chiuso con successo.");
	}
	
	private boolean isKmAttualiCorretto(String km){
		
		boolean correct = false;
		
		if (km.matches("[0-9]{1,6}$") 
				&& Integer.parseInt(km) > Integer.parseInt(autoScelta.getChilometraggio())){
			correct = true;
		}
		
		return correct;
		
	}
	
}
