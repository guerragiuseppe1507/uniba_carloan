package layout.view;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import util.NotificationManager;
import util.ResultKeys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import layout.model.Context;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.DipendenteDiFiliale;
import layout.model.entities.Filiale;
import layout.model.entities.ManagerDiFiliale;
import layout.model.entities.Utente;



public class GestioneDipendentiController implements Initializable, ControlledScreen {
	
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
    private Label filiale;
	
	@FXML
    private TableView<Utente> usersTable;  
    
    @FXML
    private TableView<Utente> employeeTable;

    private Utente selectedUtente;
    private Utente selectedDipendent;



    @Override
	public void initialize(URL url, ResourceBundle rb){
		
    	container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		filiale.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
		TableManager.riempiTabellaUtenti(usersTable, "liberi");
		TableManager.riempiTabellaUtenti(employeeTable, "filiale",Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
		
		usersTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getUser(newValue));
		
		employeeTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getEmployee(newValue));

	}
    
	private void getUser(Utente u){
		selectedUtente = u;
	}
	
	private void getEmployee(Utente u){
		selectedDipendent = u;
	}
    
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	@FXML
	public void handleAssunzione(){
		String[] comando = new String[]{"businessTier.GestioneFiliali", "assumi"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id_utente", Integer.toString(selectedUtente.getId()));
		inputParam.put("id_filiale", Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			TableManager.riempiTabellaUtenti(usersTable, "liberi");
			TableManager.riempiTabellaUtenti(employeeTable, "filiale",Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
		}else{
			NotificationManager.setError(risultato.get(ResultKeys.MSG_ERR));
		}
	}
	
	@FXML
	public void handleLicenziamento(){
		
		String[] comando = new String[]{"businessTier.GestioneFiliali", "licenzia"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("id_utente", Integer.toString(selectedDipendent.getId()));
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			TableManager.riempiTabellaUtenti(usersTable, "liberi");
			TableManager.riempiTabellaUtenti(employeeTable, "filiale",Integer.toString(Context.getInstance().getUtente().getFiliale().getId()));
		}else{
			NotificationManager.setError(risultato.get(ResultKeys.MSG_ERR));
		}
		
	}
}
