package layout.view;

import java.util.HashMap;

import presentationTier.FrontController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField usernameLabel;
	
	@FXML
	private TextField passwordLabel;
	
	@FXML
	private Label resultLabel;
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void handleLogin() {
		
		String[] comando = new String[]{"businessTier.Autenticazione", "login"};
		
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("username", usernameLabel.getText());
		inputParam.put("password", passwordLabel.getText());
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if (risultato.get("esito").equalsIgnoreCase("true")){
			resultLabel.setText(risultato.get("tipoUtente"));
		}else{
			resultLabel.setText(risultato.get("msgErr"));
		}
		
		
	}

}
