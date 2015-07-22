package layout.view;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import presentationTier.FrontController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.Cliente;
import layout.model.ContextMenu;
import layout.model.Contratto;









public class InserimentoContrattoController implements Initializable, ControlledScreen {
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	//Sezione Coontratti Stipulati
	
	 @FXML
	 private TableView<Cliente> tableClienti;
	 @FXML
	 private TableColumn<Cliente, String>nomeCliente;
	 @FXML
	 private TableColumn<Cliente, String>cognomeCliente;
	 @FXML
	 private TableColumn<Cliente, String>emailCliente;
	 @FXML
	 private TableColumn<Cliente, String>dataDiNascitaCliente;
	 @FXML
	 private TableColumn<Cliente, String>residenzaCliente;
	 @FXML
	 private TableColumn<Cliente,String>codiceFiscaleCliente;
	 @FXML
	 private TableColumn<Cliente, String>codicePatenteCliente;
	
	
	 
	 private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
	
	 
	 
	 
	 @Override
	public void initialize(URL url, ResourceBundle rb){
		
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
		
		riempiTabellaClienti();
		
	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
		ContextMenu.showContextMenu(menu,myController);
	}
	
	
	
	private void riempiTabellaClienti(){
		
		String[] comando = new String[]{"businessTier.GestioneClienti", "recuperoDatiCliente"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				clienteData.add(new Cliente(
						risultato.get("id"+Integer.toString(i)),
						risultato.get("nome" + Integer.toString(i)),
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)),
						risultato.get("residenza"+Integer.toString(i)),
						risultato.get("dataDiNascita" + Integer.toString(i)),
						risultato.get("codiceFiscale" + Integer.toString(i)),
						risultato.get("codicePatente" + Integer.toString(i)))); }
				
			
			nomeCliente.setCellValueFactory(cellData->cellData.getValue().nomeProperty());
			cognomeCliente.setCellValueFactory(cellData->cellData.getValue().cognomeProperty());
			emailCliente.setCellValueFactory(cellData->cellData.getValue().emailProperty());
			residenzaCliente.setCellValueFactory(cellData->cellData.getValue().residenzaProperty());
			dataDiNascitaCliente.setCellValueFactory(cellData->cellData.getValue().dataDiNascitaProperty());
			codiceFiscaleCliente.setCellValueFactory(cellData->cellData.getValue().codiceFiscaleProperty());
			codicePatenteCliente.setCellValueFactory(cellData->cellData.getValue().codicePatenteProperty());

			tableClienti.setItems(clienteData);
			
		}}}
	
	
	
	
	
	
	
	
	



