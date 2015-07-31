package layout.view;


import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.EmailValidator;
import util.NotificationManager;
import util.ResultKeys;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import layout.model.Context;
import layout.model.Utente;


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
	
	 @FXML
	 private Label resultLabel;
	 
	 
	 @Override
	public void initialize(URL url, ResourceBundle rb){
		 container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		 container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH); 
		 
		 
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}
	
	@FXML
	public void onActionInsersciCliente(){
		try{
			LocalDate date = dataDiNascitaPicker.getValue();
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
			} else if (!isBornDateValid(date)){
				NotificationManager.setError("Data di nascita non valida");
			} else {
				registraCliente();
			}
		}catch (RuntimeException e){
			e.printStackTrace();
			NotificationManager.setWarning("Compilare tutti i campi");
		}
	}
	
	
	private boolean isBornDateValid(LocalDate d){
		int year = d.getYear();
		int day = d.getDayOfYear();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		
		boolean valid = false;
		if((((year < (currentYear-18)) && year > 1900) || 
				(year == (currentYear-18) && day <= currentDay))) valid = true;
		return valid;
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
			resultLabel.setText("QueryInserita");
			InterStageEventsHandler.getInstance().getCaller().callBack();
			myController.closeWinStage();
		} else {

		}
		
		//InserimentoContrattoController.riempiTabellaClienti();
	}
	
	
	
	
	
	
	
		
}
