package layout.view;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		
		if (risultato.get(util.ResultKeys.esito).equalsIgnoreCase("true")){
			if (risultato.get(util.ResultKeys.tipoUtente).equals(Context.managerSistema)){
				c.setUserType(Context.managerSistema);
				loadManagerSistemaScreens();		
				myController.setScreen(ScreensFramework.homeManagerDiSistemaID);
			}
			
			if(risultato.get(util.ResultKeys.tipoUtente).equals(Context.managerFiliale)){
				c.setUserType(Context.managerFiliale);
				loadManagerFilialeScreens();
				myController.setScreen(ScreensFramework.homeManagerDiFilialeID);
			}
			
			if(risultato.get(util.ResultKeys.tipoUtente).equals(Context.dipendenteFiliale)){
				c.setUserType(Context.dipendenteFiliale);
				loadDipendenteFilialeScreens();
				myController.setScreen(ScreensFramework.homeDipendenteDiFilialeID);
			}
			
			if(risultato.get(util.ResultKeys.tipoUtente).equals("utenteNonAssegnato")){
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Accesso Rifiutato");
				alert.setHeaderText("Utente non ancora associato ad alcuna filiale");
				alert.setContentText("Contatta il manager della tua filiale di riferimento");

				alert.showAndWait();
			}
			
			

		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Accesso Rifiutato");
			alert.setHeaderText("Utente non trovato");
			alert.setContentText("Ricontrolla i dati inseriti o contatta un manager della tua filiale di riferimento");

			alert.showAndWait();
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
