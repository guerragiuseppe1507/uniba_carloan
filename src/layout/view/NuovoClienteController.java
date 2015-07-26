package layout.view;


import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.Cliente;
import layout.view.InserimentoContrattoController;

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
	
	
	public void OnActionInsersciCliente(){
		String[] comando = new String[]{"businessTier.GestioneClienti", "inserisciDatiCliente"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		resultLabel.setText("QueryInserita");
		//InserimentoContrattoController.riempiTabellaClienti();
	}
	
	
	
	
	
	
	
	
		
}
