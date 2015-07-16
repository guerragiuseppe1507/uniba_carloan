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
import layout.model.DipendenteDiFiliale;
import layout.model.Filiale;
import layout.model.ManagerDiFiliale;
import layout.model.Utente;



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
    
    
    @FXML
    private TableView<DipendenteDiFiliale> dipendentTable;
    @FXML
    private TableColumn<DipendenteDiFiliale, String>usernameDipendent;
    @FXML
    private TableColumn<DipendenteDiFiliale, String>emailDipendent;
    @FXML
    private TableColumn<DipendenteDiFiliale, String>nomeDipendent;
    @FXML
    private TableColumn<DipendenteDiFiliale, String>cognomeDipendent;
    @FXML
    private TableColumn<DipendenteDiFiliale, String>telefonoDipendent;
    @FXML
    private TableColumn<DipendenteDiFiliale, String>residenzaDipendent;
    
    private ObservableList<Utente> usersData;
    private ObservableList<DipendenteDiFiliale> employeeData;
    private Utente selectedUtente;
    private DipendenteDiFiliale selectedDipendent;



    @Override
	public void initialize(URL url, ResourceBundle rb){
		
    	container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		filiale.setText(Context.getInstance().getUtente().getFiliale().getNome());
		
		usersData = FXCollections.observableArrayList();
		employeeData = FXCollections.observableArrayList();
		
		riempiTabellaUtenti();
		riempiTabellaDipendenti(Context.getInstance().getUtente().getFiliale());
		
		usersTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getUser(newValue));
		
		dipendentTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getDipendent(newValue));

	}
    
	private void getUser(Utente u){
		selectedUtente = u;
	}
	
	private void getDipendent(DipendenteDiFiliale u){
		selectedDipendent = u;
	}
    
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	private void riempiTabellaUtenti(){
			
			
			
			
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiUtentiLiberi"};
		HashMap<String, String> inputParam = new HashMap<>();
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
	
	private void riempiTabellaDipendenti(Filiale filiale){
		
		String idFiliale = Integer.toString(filiale.getId());
		
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiUtenti"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("idFiliale", idFiliale);
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		employeeData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			
		
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				employeeData.add(new DipendenteDiFiliale(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)), 
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i)), 
						risultato.get("residenza" + Integer.toString(i))));
				
			}
			
			usernameDipendent.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
			emailDipendent.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
			nomeDipendent.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
			cognomeDipendent.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());
			telefonoDipendent.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
			residenzaDipendent.setCellValueFactory(cellData -> cellData.getValue().residenzaProperty());
			
			dipendentTable.setItems(employeeData);
			
		} else {
			dipendentTable.getItems().clear();
			dipendentTable.setPlaceholder(new Label("No Employees Found"));
		}
		
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
			riempiTabellaUtenti();
			riempiTabellaDipendenti(Context.getInstance().getUtente().getFiliale());
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
			riempiTabellaUtenti();
			riempiTabellaDipendenti(Context.getInstance().getUtente().getFiliale());
		}else{
			NotificationManager.setError(risultato.get(ResultKeys.MSG_ERR));
		}
		
	}
}
