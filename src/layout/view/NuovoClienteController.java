package layout.view;


import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.DateValidator;
import util.EmailValidator;
import util.NotificationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;


public class NuovoClienteController  implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	
	//Sezione Coontratti Stipulati
	@FXML
	Button Inserisci;
	
	
 
	@FXML
	private TextField nomeText;
	@FXML
	private TextField cognomeText;
	@FXML
	private TextField mailText;
	@FXML
	private TextField residenzaText;
	@FXML
	private DatePicker dataDiNascitaPicker;
	@FXML
	private TextField codiceFiscaleText;
	@FXML
	private TextField codicePatenteText;
	 
	 
	@Override
	public void initialize(URL url, ResourceBundle rb){
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
		   public DateCell call(final DatePicker datePicker) {
		       return new DateCell() {
		           @Override 
		           public void updateItem(LocalDate item, boolean empty) {
		               super.updateItem(item, empty);

		               if (DateValidator.isBornDateValid(item)) {
		                       setDisable(true);
		                       setStyle("-fx-background-color: #ffc0cb;");
		               }
		                
		           }
		       };
		   }
		};
		dataDiNascitaPicker.setDayCellFactory(dayCellFactory);
		dataDiNascitaPicker.setShowWeekNumbers(false);
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}
	
	@Override
	public void riempiCampi(){}
	
	@FXML
	public void onActionInsersciCliente(){
		try{
			String nome = nomeText.getText();
			String cognome = cognomeText.getText();
			String mail = mailText.getText();
			String residenza = residenzaText.getText();
			String patente = codicePatenteText.getText();
			String cf = codiceFiscaleText.getText();
			if(nome.isEmpty() || cognome.isEmpty() ||mail.isEmpty() || 
					residenza.isEmpty() || patente.isEmpty() || cf.isEmpty()){
				NotificationManager.setWarning("Compilare tutti i campi");
			} else if(!EmailValidator.isValidEmailAddress(mail)){
				NotificationManager.setError("Mail non valida");
			} else {
				registraCliente();
			}
		}catch (RuntimeException e){
			e.printStackTrace();
			NotificationManager.setWarning("Compilare tutti i campi");
		}
	}
	
	private void registraCliente(){
		String[] comando = new String[]{"businessTier.GestioneClienti", "inserisciDatiCliente"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("nome",nomeText.getText());
		inputParam.put("cognome",cognomeText.getText());
		inputParam.put("mail",mailText.getText());
		inputParam.put("residenza",residenzaText.getText());
		LocalDate date = dataDiNascitaPicker.getValue();
		inputParam.put("data_di_nascita",date.toString());
		inputParam.put("cod_patente",codicePatenteText.getText());
		inputParam.put("cod_fiscale",codiceFiscaleText.getText());
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			InterStageEventsHandler.getInstance().getCaller().callBack();
			myController.closeWinStage();
		} else {
			NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
		}
		
	}
	
	@FXML
	public void onActionDatepicker(){
		dataDiNascitaPicker.setValue(LocalDate.now().minusYears(50));
	}
	
}
