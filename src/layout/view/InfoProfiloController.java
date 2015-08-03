package layout.view;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;












import presentationTier.FrontController;
import util.NotificationManager;
import util.ResultKeys;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.entities.Utente;


public class InfoProfiloController implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	Text tvUtente;
	
	@FXML
	Text tvTipo;
	
	@FXML
	Text tvFiliale;
	@FXML
	TextField tfUsername;
	@FXML
	TextField tfEmail;
	@FXML
	TextField tfNome;
	@FXML
	TextField tfCognome;
	@FXML
	TextField tfTelefono;
	@FXML
	TextField  tfResidenza;
	@FXML
	TextField  tfPassword;

    @Override
	public void initialize(URL url, ResourceBundle rb){
		
    	container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		riempiCampi();
		tvFiliale.setText(Context.getInstance().getUtente().getFiliale().getNome());
		tvUtente.setText(Context.getInstance().getUtente().getUsername());
		tvTipo.setText(Context.getInstance().getUserType());

	}
    
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	private void riempiCampi(){
		tfUsername.setText(Context.getInstance().getUtente().getUsername());
		tfEmail.setText(Context.getInstance().getUtente().getEmail());
		tfNome.setText(Context.getInstance().getUtente().getNome());
		tfCognome.setText(Context.getInstance().getUtente().getCognome());
		tfTelefono.setText(Context.getInstance().getUtente().getTelefono());
		tfResidenza.setText(Context.getInstance().getUtente().getResidenza());
		//tfPassword.setText(Context.getInstance().getPassword());
	}
	
	@FXML
	private void  handleSave(){
		String[] comando = new String[]{"businessTier.GestioneUtenti", "updateProfile"};
		int id = Context.getInstance().getUtente().getId();
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id", Integer.toString(id));
		inputParam.put("username", tfUsername.getText());
		inputParam.put("email", tfEmail.getText());
		inputParam.put("nome", tfNome.getText());
		inputParam.put("cognome", tfCognome.getText());
		inputParam.put("telefono", tfTelefono.getText());
		inputParam.put("residenza", tfResidenza.getText());
		inputParam.put("password", tfPassword.getText());
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		if (risultato.get(util.ResultKeys.ESITO).equalsIgnoreCase("true")){
			NotificationManager.setInfo("Modifiche effettuate");
			Context.getInstance().setUtente(new Utente(
					id,
					tfUsername.getText(),
					tfEmail.getText(),
					tfNome.getText(),
					tfCognome.getText(),
					tfTelefono.getText(),
					tfResidenza.getText()
					));
			Context.getInstance().setPassword(tfPassword.getText());
			riempiCampi();
		} else {
			NotificationManager.setError(risultato.get(ResultKeys.MSG_ERR));
			riempiCampi();
		}
	}
	
	

}
