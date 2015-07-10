package layout.view;

import java.util.HashMap;

import presentationTier.FrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import layout.model.Filiale;
import layout.model.ManagerDiFiliale;
import layout.model.Utente;
import dataAccess.DAO;

public class InsertManagerController {
	
	@FXML
    private TableView<Utente> usersTable;
    @FXML
    private TableColumn<Utente, String> usersUsernameColumn;
    @FXML
    private TableColumn<Utente, String> usersEmailColumn;
    @FXML
    private TableColumn<Utente, String> usersNomeColumn;
    @FXML
    private TableColumn<Utente, String> usersCognomeColumn;
    @FXML
    private TableColumn<Utente, String> usersTelefonoColumn;
    @FXML
    private TableColumn<Utente, String> usersResidenzaColumn;
    
    @FXML
    private TableView<Filiale> filialiTable;
    @FXML
    private TableColumn<Filiale, String> filialiNomeColumn;
    @FXML
    private TableColumn<Filiale, String> filialiLuogoColumn;
    @FXML
    private TableColumn<Filiale, String> filialiTelefonoColumn;
    
    @FXML
    private TableView<ManagerDiFiliale> managerDiFilialeTable;
    @FXML
    private TableColumn<ManagerDiFiliale, String> managerDiFilialeNomeMColumn;
    @FXML
    private TableColumn<ManagerDiFiliale, String> managerDiFilialeNomeFColumn;
	
    private ObservableList<ManagerDiFiliale> managerDiFilialeData = FXCollections.observableArrayList();
	private ObservableList<Utente> usersData = FXCollections.observableArrayList();
	private ObservableList<Filiale> filialiData = FXCollections.observableArrayList();
	
	private Filiale selectedFiliale;
	private Utente selectedUtente;

	@FXML
	private void initialize(){
		
		riempiTabellaManager();
		
		riempiTabellaFiliali();
		
		filialiTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> riempiTabellaUtenti(newValue));
		
		usersTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getUser(newValue));

	}
	
	@FXML
	private void setManager() {
		int id_utente = selectedUtente.getId();
		int id_filiale = selectedUtente.getId();
		
		//TODO 
		//1 controllare se la filiale scelta ha un manager
		//se si bisogna chiedere se licenziare il manager vecchio o settarlo come dipendente della filiale
		
	}
	
	private void getUser(Utente u){
		selectedUtente = u;
	}
	
	private void riempiTabellaManager(){
		
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiManagerDiFiliale"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.esito).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.resLength)) ; i++){
				
				managerDiFilialeData.add(new ManagerDiFiliale(Integer.parseInt(risultato.get("id_utente" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_filiale" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)), 
						risultato.get("nome" + Integer.toString(i)))
				); 
				
			}
			
			managerDiFilialeNomeMColumn.setCellValueFactory(cellData -> cellData.getValue().usernameManagerProperty());
			managerDiFilialeNomeFColumn.setCellValueFactory(cellData -> cellData.getValue().nomeFilialeProperty());
			
			managerDiFilialeTable.setItems(managerDiFilialeData);
			managerDiFilialeTable.setSelectionModel(null);
			
		} else {
			
			managerDiFilialeTable.setPlaceholder(new Label("No Managers Found"));
			
		}
		
	}
	
	private void riempiTabellaFiliali(){
		
		String[] comando = new String[]{"businessTier.GestioneFiliali", "recuperoDatiFiliali"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.esito).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.resLength)) ; i++){
				
				filialiData.add(new Filiale(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("luogo" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i))) 
				); 
				
			}
			
			filialiNomeColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
			filialiLuogoColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
			filialiTelefonoColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
			
			filialiTable.setItems(filialiData);
			
		} else {
			
			filialiTable.setPlaceholder(new Label("No Filiali Found"));
			
		}
		
	}
	
	private void riempiTabellaUtenti(Filiale filiale){
		
		selectedFiliale = filiale;
		
		String idFiliale = Integer.toString(filiale.getId());
		
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiUtenti"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("idFiliale", idFiliale);
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		usersData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.esito).equals("true")){
			
			
		
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.resLength)) ; i++){
				
				usersData.add(new Utente(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)), 
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i)), 
						risultato.get("residenza" + Integer.toString(i))));
				
			}
			
			usersUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
			usersEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
			usersNomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
			usersCognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());
			usersTelefonoColumn.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
			usersResidenzaColumn.setCellValueFactory(cellData -> cellData.getValue().residenzaProperty());
			
			usersTable.setItems(usersData);
			
		} else {
			usersTable.getItems().clear();
			usersTable.setPlaceholder(new Label("No Users Found"));
			
		}
		
	}




















}