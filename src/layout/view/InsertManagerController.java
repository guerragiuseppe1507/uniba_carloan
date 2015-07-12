package layout.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import presentationTier.FrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	
    private ArrayList<ManagerDiFiliale> managersDiFiliale;
    private ObservableList<ManagerDiFiliale> managerDiFilialeData;
	private ObservableList<Utente> usersData;
	private ObservableList<Filiale> filialiData;
	
	private Filiale selectedFiliale;
	private Utente selectedUtente;

	@FXML
	private void initialize(){
		
		managersDiFiliale = new ArrayList<ManagerDiFiliale>();
		managerDiFilialeData = FXCollections.observableArrayList();
		usersData = FXCollections.observableArrayList();
		filialiData = FXCollections.observableArrayList();
		
		riempiTabellaManager();
		
		riempiTabellaFiliali();
		
		filialiTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> riempiTabellaUtenti(newValue));
		
		usersTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getUser(newValue));

	}
	
	@FXML
	private void setManager() {
		int id_utente;
		int id_filiale;
		Boolean b = false;
		ManagerDiFiliale altroManager = null;
		
		try{
			id_filiale = selectedFiliale.getId();
			id_utente = selectedUtente.getId();
			for(int i = 0 ; i < managersDiFiliale.size(); i++){
				if(managersDiFiliale.get(i).getIdFiliale() == id_filiale &&
						managersDiFiliale.get(i).getIdManagr() != 0){
						b = true;
						altroManager = managersDiFiliale.get(i);
				}
			}
			
			if(b==true){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Conferma");
				alert.setHeaderText("Esiste gia il manager '"+altroManager.getUsernameManager()+"' per la filiale selezionata");
				alert.setContentText("Continuare ugualmente?\nNB: il manager precedente verrà impostato come dipendente della sua filiale.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    setTheNewManager(id_utente, id_filiale);
				}
				
			}else{
				setTheNewManager(id_utente, id_filiale);
			}
			
		}catch(NullPointerException e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Attenzione!");
			alert.setHeaderText("Dipendente non selezionato");
			alert.setContentText("Assicurati di scegliere una filiale e successivamente un dipendente prima di continuare");

			alert.showAndWait();
		}
		
	}
	
	private void setTheNewManager(int idUtente, int idFiliale){
		String[] comando = new String[]{"businessTier.GestioneUtenti", "setManagerDiFiliale"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("idUtente", Integer.toString(idUtente));
		inputParam.put("idFiliale", Integer.toString(idFiliale));
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.esito).equals("true")){
			riempiTabellaManager();
			riempiTabellaUtenti(selectedFiliale);
		}
		
	}
	
	private void getUser(Utente u){
		selectedUtente = u;
	}
	
	private void riempiTabellaManager(){
		
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiManagerDiFiliale"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		managerDiFilialeData = FXCollections.observableArrayList();
		
		if(risultato.get(util.ResultKeys.esito).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.resLength)) ; i++){
				
				ManagerDiFiliale tmp = new ManagerDiFiliale(Integer.parseInt(risultato.get("id_utente" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_filiale" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)), 
						risultato.get("nome" + Integer.toString(i)));
				managerDiFilialeData.add(tmp); 
				managersDiFiliale.add(tmp);				
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