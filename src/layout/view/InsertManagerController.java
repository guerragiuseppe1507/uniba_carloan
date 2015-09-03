package layout.view;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import presentationTier.FrontController;
import util.NotificationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.ContextMenu;
import layout.model.TableManager;
import layout.model.entities.Filiale;
import layout.model.entities.ManagerDiFiliale;
import layout.model.entities.Utente;

public class InsertManagerController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
    private TableView<Utente> usersTable;
   
    @FXML
    private TableView<Filiale> filialiTable;
    
    @FXML
    private TableView<ManagerDiFiliale> managerDiFilialeTable;
	
    private ArrayList<ManagerDiFiliale> managersDiFiliale;
	
	private Filiale selectedFiliale;
	private Utente selectedUtente;

	@Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		managersDiFiliale = new ArrayList<ManagerDiFiliale>();
		
		riempiTabellaManager();
		
		riempiTabellaFiliali();
		
		filialiTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> {
	            	selectedFiliale = newValue;
	            	riempiTabellaUtenti(Integer.toString(newValue.getId()));
	            	});
		
		usersTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> getUser(newValue));

	}
	
	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	private void riempiTabellaManager(){
		
		managersDiFiliale = TableManager.riempiTabellaManager(managerDiFilialeTable);
		
	}
	
	private void riempiTabellaFiliali(){
		
		TableManager.riempiTabellaFiliali(filialiTable);
		
	}
	
	private void riempiTabellaUtenti(String idFiliale){
		
		TableManager.riempiTabellaUtenti(usersTable, "filiale",idFiliale);
		
	}
	
	private void getUser(Utente u){
		selectedUtente = u;
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
				if(managersDiFiliale.get(i).getFiliale().getId() == id_filiale &&
						managersDiFiliale.get(i).getId() != 0){
						b = true;
						altroManager = managersDiFiliale.get(i);
				}
			}
			
			if(b==true){
				
				Optional<ButtonType> result = NotificationManager.setConfirm("Esiste gia il manager '"+altroManager.getUsername()+"' per la filiale selezionata; "+
						"Continuare ugualmente?\n\nNB: il manager precedente verrà impostato come dipendente della sua filiale.");
				
				if (result.get() == ButtonType.OK){
				    setTheNewManager(id_utente, id_filiale);
				}
				
			}else{
				setTheNewManager(id_utente, id_filiale);
			}
			
		}catch(NullPointerException e){
			e.printStackTrace();
			NotificationManager.setWarning("Utente non selezionato");
		}
		
	}
	
	private void setTheNewManager(int idUtente, int idFiliale){
		String[] comando = new String[]{"businessTier.GestioneUtenti", "setManagerDiFiliale"};
		HashMap<String, String> inputParam = new HashMap<>();
		inputParam.put("idUtente", Integer.toString(idUtente));
		inputParam.put("idFiliale", Integer.toString(idFiliale));
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			riempiTabellaManager();
			riempiTabellaUtenti(Integer.toString(idFiliale));
		}
		
	}
	
	@FXML
	public void caricaUtentiLiberiHandler(){
		TableManager.riempiTabellaUtenti(usersTable, "liberi");
	}

}