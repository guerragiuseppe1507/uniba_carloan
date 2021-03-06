package layout.view;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.EmailValidator;
import util.Md5Encrypter;
import util.NotificationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import layout.model.Context;
import layout.model.entities.Filiale;
import layout.model.entities.Utente;

public class LoginController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	Context c = Context.getInstance();
	final String APP_NAME = ScreensFramework.APP_NAME;
	
	@FXML
	StackPane container;
	
	@FXML
	private TextField regUsername;
	
	@FXML
	private TextField regEmail;
	
	@FXML
	private TextField regPassword;
	
	@FXML
	private TextField usernameLabel;
	
	@FXML
	private PasswordField passwordLabel;
	
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
	
	@Override
	public void riempiCampi(){}
	
	@FXML
	private void handleLogin() {
		
		String[] comando = new String[]{"businessTier.Autenticazione", "login"};
		
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("username", usernameLabel.getText());
		inputParam.put("password", Md5Encrypter.encrypt(passwordLabel.getText()));
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			
			if (risultato.get(util.ResultKeys.TIPO_UTENTE).equals(Context.MANAGER_SISTEMA)){
				c.setUserType(Context.MANAGER_SISTEMA);		
				myController.showScreen(ScreensFramework.homeManagerDiSistemaFile);
				ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.homeManagerDiSistemaTitle);
			}
			
			if(risultato.get(util.ResultKeys.TIPO_UTENTE).equals(Context.MANAGER_FILIALE)){
				c.setUserType(Context.MANAGER_FILIALE);
				istanziaUtente(risultato);
				myController.showScreen(ScreensFramework.homeManagerDiFilialeFile);
				ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.homeManagerDiFilialeTitle);
			}
			
			if(risultato.get(util.ResultKeys.TIPO_UTENTE).equals(Context.DIPENDENTE_FILIALE)){
				c.setUserType(Context.DIPENDENTE_FILIALE);
				istanziaUtente(risultato);
				myController.showScreen(ScreensFramework.homeDipendenteDiFilialeFile);
				ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.homeDipendenteDiFilialeTitle);
			}
			
			if(risultato.get(util.ResultKeys.TIPO_UTENTE).equals("utenteNonAssegnato")){
				
				NotificationManager.setInfo("Utente non ancora associato ad alcuna filiale\n"+
											"Contatta il manager della tua filiale di riferimento");
				
			}

		}else if(usernameLabel.getText().equals(risultato.get("username").toLowerCase())){
						
			NotificationManager.setError("Connessione al server fallita");
			
		}else{

			NotificationManager.setError("Ricontrolla i dati inseriti o contatta un manager della tua filiale di riferimento");

		}
		
	}
	
	private void istanziaUtente(HashMap<String, String> risultato){
		Context.getInstance().setUtente(new Utente(
				Integer.parseInt(risultato.get("id")),
				risultato.get("username"),
				risultato.get("email"),
				risultato.get("nome"),
				risultato.get("cognome"),
				risultato.get("telefono"),
				risultato.get("residenza")));
		Context.getInstance().getUtente().setFiliale(new Filiale(
				Integer.parseInt(risultato.get("id_filiale")),
				risultato.get("nome_filiale"),
				risultato.get("luogo_filiale"),
				risultato.get("telefono_filiale")));
		Context.getInstance().setPassword(risultato.get("password"));
	}
	
	@FXML
	private void handleRegister() {
		
		Boolean registrable = true;
		
		if (regUsername.getText().equals("") ||
				regEmail.getText().equals("") ||
				regPassword.getText().equals("")){
			NotificationManager.setWarning("Tutti i campi sono obbligatori");
			registrable = false;
		}else if(!EmailValidator.isValidEmailAddress(regEmail.getText())){
			NotificationManager.setWarning("Formato E-mail non valido");
			registrable = false;
		}else if (!regUsername.getText().equals(regUsername.getText().toLowerCase())){
			NotificationManager.setWarning("L'username non deve avere lettere maiuscole");
			registrable = false;
		}
		
		if(registrable == true){
			String[] comando = new String[]{"businessTier.Autenticazione", "register"};
			
			HashMap<String, String> inputParam = new HashMap<>();
			inputParam.put("username", regUsername.getText());
			inputParam.put("email", regEmail.getText());
			inputParam.put("password", Md5Encrypter.encrypt(regPassword.getText()));
			
			HashMap<String, String> risultato = new HashMap<>();
			risultato =	FrontController.request(comando, inputParam);
			
			if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
				NotificationManager.setInfo("Registrazione Effettuata\nContatta un Manager Di Filiale per farti assegnare ad una filiale");
			} else {
				NotificationManager.setError(risultato.get(util.ResultKeys.MSG_ERR));
			}
			
		}
		
	}
	
}
