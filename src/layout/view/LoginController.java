package layout.view;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.NotificationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import layout.model.Context;

public class LoginController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	Context c = Context.getInstance();
	
	@FXML
	StackPane container;
	
	@FXML
	private TextField usernameLabel;
	
	@FXML
	private TextField passwordLabel;
	
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
	private void handleLogin() {
		
		String[] comando = new String[]{"businessTier.Autenticazione", "login"};
		
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("username", usernameLabel.getText());
		inputParam.put("password", passwordLabel.getText());
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			if (risultato.get(util.ResultKeys.TIPO_UTENTE).equals(Context.managerSistema)){
				c.setUserType(Context.managerSistema);
				loadManagerSistemaScreens();		
				myController.setScreen(ScreensFramework.homeManagerDiSistemaID);
			}
			
			if(risultato.get(util.ResultKeys.TIPO_UTENTE).equals(Context.managerFiliale)){
				c.setUserType(Context.managerFiliale);
				loadManagerFilialeScreens();
				myController.setScreen(ScreensFramework.homeManagerDiFilialeID);
			}
			
			if(risultato.get(util.ResultKeys.TIPO_UTENTE).equals(Context.dipendenteFiliale)){
				c.setUserType(Context.dipendenteFiliale);
				loadDipendenteFilialeScreens();
				myController.setScreen(ScreensFramework.homeDipendenteDiFilialeID);
			}
			
			if(risultato.get(util.ResultKeys.TIPO_UTENTE).equals("utenteNonAssegnato")){
				
				NotificationManager.setInfo("Utente non ancora associato ad alcuna filiale\n"+
											"Contatta il manager della tua filiale di riferimento");
			}
			
			

		}else if(risultato.get(util.ResultKeys.MSG_ERR).equalsIgnoreCase("Connessione al DataBase fallita")){
						
			NotificationManager.setError("Connessione al server fallita");
			
		}else{

			NotificationManager.setError("Ricontrolla i dati inseriti o contatta un manager della tua filiale di riferimento");

		}
		
		
	}
	
	private void loadManagerSistemaScreens(){
		//Il controller carica tutte le schermate date come parametro al metodo loadScreen.
		myController.loadScreen(ScreensFramework.homeManagerDiSistemaID,ScreensFramework.homeManagerDiSistemaFile);
		myController.loadScreen(ScreensFramework.insertManagerID,ScreensFramework.insertManagerFile);

	}
	private void loadManagerFilialeScreens(){
		//Il controller carica tutte le schermate date come parametro al metodo loadScreen.
		myController.loadScreen(ScreensFramework.homeManagerDiFilialeID,ScreensFramework.homeManagerDiFilialeFile);
		myController.loadScreen(ScreensFramework.insertDipendenteDiFilialeID,ScreensFramework.insertDipendenteDiFilialeFile);
		myController.loadScreen(ScreensFramework.manageAutoID,ScreensFramework.manageAutoFile);
		myController.loadScreen(ScreensFramework.gestioneDipendentiID,ScreensFramework.gestioneDipendenti);
	}
	private void loadDipendenteFilialeScreens(){
		//Il controller carica tutte le schermate date come parametro al metodo loadScreen.
		myController.loadScreen(ScreensFramework.homeDipendenteDiFilialeID,ScreensFramework.homeDipendenteDiFilialeFile);
	}

}
