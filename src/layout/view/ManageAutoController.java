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
import layout.model.Auto;
import dataAccess.DAO;

public class ManageAutoController {
	@FXML
	 private TableView<Auto> autoTable;
	@FXML
	private TableColumn<Auto, String> id_Auto;
	@FXML
	private TableColumn<Auto, String> id_Modello;
	@FXML
	private TableColumn<Auto, String> targa;
	@FXML
	private TableColumn<Auto, String> nomeFiliale;
	@FXML
	private TableColumn<Auto, String> status;
	@FXML
	private TableColumn<Auto, String> chilometraggio;
	@FXML
	private TableColumn<Auto, String> fasce;
	
	private ObservableList<Auto> AutoData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize(){
		
		riempiTabellaAuto();
		
		
		autoTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> riempiTabellaAuto());
		
	
	}
	
private void riempiTabellaAuto(){
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiAuto"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		if(risultato.get(util.ResultKeys.esito).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.resLength)) ; i++){
				
				AutoData.add(new Auto(Integer.parseInt(risultato.get("id_auto" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_modello" + Integer.toString(i))),
						
						risultato.get("nome_filiale" + Integer.toString(i)), 
						risultato.get("status" + Integer.toString(i)),
						risultato.get("targa"+Integer.toString(i)),
						risultato.get("chilometraggio" + Integer.toString(i)),
						risultato.get("fasce" + Integer.toString(i)))
				); 
				
			}
			
				
			nomeFiliale.setCellValueFactory(cellData->cellData.getValue().nomeFilialeProperty());
			status.setCellValueFactory(cellData->cellData.getValue().statusProperty());
			chilometraggio.setCellValueFactory(cellData->cellData.getValue().chilometraggioProperty());
			targa.setCellValueFactory(cellData->cellData.getValue().targaProperty());
			fasce.setCellValueFactory(cellData->cellData.getValue().fasceProperty());
			autoTable.setItems(AutoData);
			
		} else {
			
			autoTable.setPlaceholder(new Label("No Auto Found"));
			
		}
		
	}
	
	
}
