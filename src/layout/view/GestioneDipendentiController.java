package layout.view;


import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
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
import layout.model.ContextMenu;
import layout.model.Utente;



public class GestioneDipendentiController implements Initializable, ControlledScreen {
	
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
    private TableView<Utente> usersTable;
    @FXML
    private TableColumn<Utente, String>username;
    @FXML
    private TableColumn<Utente, String>email;
    @FXML
    private TableColumn<Utente, String>nome;
    @FXML
    private TableColumn<Utente, String>cognome;
    @FXML
    private TableColumn<Utente, String>telefono;
    @FXML
    private TableColumn<Utente, String>residenza;
    
    private ObservableList<Utente> usersData;
    private Utente selectedUtente;



    @Override
	public void initialize(URL url, ResourceBundle rb){
		
    	container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		usersData = FXCollections.observableArrayList();

		
		usersData = FXCollections.observableArrayList();
		
		riempiTabellaUtenti();
		
		usersTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getUser(newValue));

	}
    
	private void getUser(Utente u){
		selectedUtente = u;
	}
    
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	
	
	
	
private void riempiTabellaUtenti(){
		
		
		
		
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiUtentiLiberi"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put(ResultKeys.RESTRICT, "liberi");
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		usersData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			
		
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				usersData.add(new Utente(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)), 
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i)), 
						risultato.get("residenza" + Integer.toString(i))));
				
			}
			
			username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
			email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
			nome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
			cognome.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());
			telefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
			residenza.setCellValueFactory(cellData -> cellData.getValue().residenzaProperty());
			
			usersTable.setItems(usersData);
			
		} else {
			usersTable.getItems().clear();
			usersTable.setPlaceholder(new Label("No Users Found"));
		}
    
}
}
