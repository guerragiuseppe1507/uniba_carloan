package layout.model;

import java.util.ArrayList;
import java.util.HashMap;

import presentationTier.FrontController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import layout.model.entities.Auto;
import layout.model.entities.Cliente;
import layout.model.entities.Contratto;
import layout.model.entities.Filiale;
import layout.model.entities.ManagerDiFiliale;
import layout.model.entities.Modello;
import layout.model.entities.Utente;

public class TableManager {
	
	public static void riempiTabellaUtenti(TableView<Utente> usersTable , String... filter){
		
		ObservableList<Utente> usersData = FXCollections.observableArrayList();
		String[] comando = new String[2];
		comando[0] = "businessTier.GestioneUtenti";
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		
		if (filter[0].equals("liberi")){
			comando[1] = "recuperoDatiUtentiLiberi";
		}else if (filter[0].equals("filiale")){
			comando[1] = "recuperoDatiUtenti";
			inputParam.put("idFiliale", filter[1]);
		}
		
		risultato =	FrontController.request(comando, inputParam);
		
		usersTable.getSelectionModel().clearSelection();
		usersTable.getColumns().clear();
		
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
			
			String[] columnNames = {"Username", "Email", "Nome" , "Cognome" , "Telefono", "Residenza"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Utente, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Utente, String>(columnNames[i]);
				usersTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Utente.properties[wantedProperty]));
			}
			
			FXCollections.sort(usersData);
			usersTable.setItems(usersData);
			
		} else {
			usersTable.setPlaceholder(new Label(risultato.get(util.ResultKeys.MSG_ERR)));
		}
		
	}
	
	public static void riempiTabellaContratto(TableView<Contratto> contrattoTable,String id_dipendente, String id_filiale,
													String filtro, String tipoAccesso, String status){
		
		ObservableList<Contratto> contrattoData = FXCollections.observableArrayList();
		
		String[] comando = new String[]{"businessTier.GestioneContratti", "recuperoDatiContratto"};
		HashMap<String, String> inputParam = new HashMap<>();
		

		inputParam.put("status", status);
		inputParam.put("id_dipendente", id_dipendente);
		inputParam.put("id_filiale", id_filiale);
		inputParam.put("filter", filtro);
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		contrattoTable.getSelectionModel().clearSelection();
		contrattoTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				contrattoData.add(new Contratto(
						Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_cliente" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_auto" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_dipendente" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_filiale_di_partenza" + Integer.toString(i))),
						Integer.parseInt(risultato.get("id_filiale_di_arrivo" + Integer.toString(i))),
						risultato.get("tipoKm" + Integer.toString(i)), 
						risultato.get("tariffa" + Integer.toString(i)),
						risultato.get("dataInizio"+Integer.toString(i)),
						risultato.get("dataLimite" + Integer.toString(i)),
						risultato.get("dataRientro" + Integer.toString(i)),
						risultato.get("filialeDiPartenza" + Integer.toString(i)),
						risultato.get("filialeDiArrivo" + Integer.toString(i)),
						risultato.get("acconto" + Integer.toString(i)),
						risultato.get("stato" + Integer.toString(i)),
						risultato.get("dipendente" + Integer.toString(i)),
						risultato.get("totPrezzo" + Integer.toString(i)))
				); 
			}
			
			String[] columnNames = {"Dipendente", "TipoKm", "Tariffa", "DataInizio", "DataLimite", "DataRientro", "FilialeDiPartenza", "FilialeDiArrivo" , "Acconto"
					, "Stato", "TotPrezzo"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Contratto, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Contratto, String>(columnNames[i]);
				contrattoTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Contratto.properties[wantedProperty]));
			}	
			
			FXCollections.sort(contrattoData);	
			contrattoTable.setItems(contrattoData);
			
			if(tipoAccesso.equals(Context.MANAGER_FILIALE)
					 || tipoAccesso.equals(Context.DIPENDENTE_FILIALE)){
				removeColumns(contrattoTable,9);
			} else if(tipoAccesso.equals("TUTTI")){
				removeColumns(contrattoTable,0,9);
			}
			
		} else {
			
			contrattoTable.setPlaceholder(new Label(risultato.get(util.ResultKeys.MSG_ERR)));
			
		}
		
	}
	
	public static void riempiTabellaClienti(TableView<Cliente> clientiTable){
		
		ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
		
		String[] comando = new String[]{"businessTier.GestioneClienti", "recuperoDatiClienti"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		clientiTable.getSelectionModel().clearSelection();
		clientiTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				clienteData.add(new Cliente(
						Integer.parseInt(risultato.get("id"+Integer.toString(i))),
						risultato.get("nome" + Integer.toString(i)),
						risultato.get("cognome" + Integer.toString(i)), 
						risultato.get("email" + Integer.toString(i)),
						risultato.get("dataDiNascita" + Integer.toString(i)),
						risultato.get("residenza"+Integer.toString(i)),
						risultato.get("codiceFiscale" + Integer.toString(i)),
						risultato.get("codicePatente" + Integer.toString(i)))); 
			}
				
			String[] columnNames = {"Nome", "Cognome", "Email", "Residenza", "DataDiNascita", "CodiceFiscale", "CodicePatente"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Cliente, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Cliente, String>(columnNames[i]);
				clientiTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Cliente.properties[wantedProperty]));
			}

			FXCollections.sort(clienteData);
			clientiTable.setItems(clienteData);
			
		} else {
			
			clientiTable.setPlaceholder(new Label(risultato.get(util.ResultKeys.MSG_ERR)));
			
		}
		
	}
	
	public static void riempiTabellaAuto(TableView<Auto> autoTable,String filiale, String... filter){
		
		ObservableList<Auto> autoData = FXCollections.observableArrayList();
		
		String[] comando = new String[]{"businessTier.GestioneAuto", "recuperoDatiAuto"};
		HashMap<String, String> inputParam = new HashMap<>();	
		
		if (filter[0].equals("disponibile")){
			inputParam.put("disponibilita", "disponibile");
		}else if (filter[0].equals("non_disponibile")){
			inputParam.put("disponibilita", "non_disponibile");
		}
		
		inputParam.put("id_filiale", filiale);
		
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		autoTable.getSelectionModel().clearSelection();
		autoTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				Auto auto = new Auto(
						Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("nome_filiale" + Integer.toString(i)), 
						risultato.get("status" + Integer.toString(i)),
						risultato.get("targa"+Integer.toString(i)),
						risultato.get("chilometraggio" + Integer.toString(i)),
						risultato.get("fasce" + Integer.toString(i)),
						risultato.get("provenienza" + Integer.toString(i))
						); 
				
				
				auto.setModello(new Modello(Integer.parseInt(risultato.get("id_modello" + Integer.toString(i))),
						risultato.get("modello" + Integer.toString(i)))
				);
				
				autoData.add(auto);
				
			}
			
			String[] columnNames = {"Modello", "NomeFiliale", "km", "Status", "Fascia", "Targa", "Provenienza"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Auto, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Auto, String>(columnNames[i]);
				autoTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Auto.properties[wantedProperty]));
			}
			
			autoTable.getColumns().remove(columns[1]);
			
			FXCollections.sort(autoData);
			autoTable.setItems(autoData);
			
		} else {
			
			autoTable.setPlaceholder(new Label(risultato.get(util.ResultKeys.MSG_ERR)));
			
		}
		
	}
	
	public static ArrayList<ManagerDiFiliale> riempiTabellaManager(TableView<ManagerDiFiliale> managerDiFilialeTable){
		
		ObservableList<ManagerDiFiliale> managerDiFilialeData = FXCollections.observableArrayList();
		ArrayList<ManagerDiFiliale> managersDiFiliale = new ArrayList<ManagerDiFiliale>();
		
		String[] comando = new String[]{"businessTier.GestioneUtenti", "recuperoDatiManagerDiFiliale"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		managerDiFilialeTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				ManagerDiFiliale tmp = new ManagerDiFiliale(Integer.parseInt(risultato.get("id_utente" + Integer.toString(i))),
						risultato.get("username" + Integer.toString(i)));
				tmp.setFiliale(new Filiale(Integer.parseInt(risultato.get("id_filiale" + Integer.toString(i))),
						risultato.get("nome" + Integer.toString(i))));
				managerDiFilialeData.add(tmp); 
				managersDiFiliale.add(tmp);				
			}
			
			String[] columnNames = {"Username", "Filiaale"};
			
			@SuppressWarnings("unchecked")
			TableColumn<ManagerDiFiliale, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<ManagerDiFiliale, String>(columnNames[i]);
				managerDiFilialeTable.getColumns().add(columns[i]);
			}
			
			columns[0].setCellValueFactory(cellData -> cellData.getValue().getProperty("username"));
			columns[1].setCellValueFactory(cellData -> cellData.getValue().getFiliale().getProperty("nome"));
			
			managerDiFilialeTable.setItems(managerDiFilialeData);
			managerDiFilialeTable.setSelectionModel(null);
			
			
		} else {
			
			managerDiFilialeTable.setPlaceholder(new Label(risultato.get(util.ResultKeys.MSG_ERR)));
			
		}
		
		return managersDiFiliale;
		
	}
	
	public static void riempiTabellaFiliali(TableView<Filiale> filialiTable){
		
		ObservableList<Filiale> filialiData = FXCollections.observableArrayList();
		
		String[] comando = new String[]{"businessTier.GestioneFiliali", "recuperoDatiFiliali"};
		HashMap<String, String> inputParam = new HashMap<>();
		HashMap<String, String> risultato = new HashMap<>();
		risultato =	FrontController.request(comando, inputParam);
		
		filialiTable.getSelectionModel().clearSelection();
		filialiTable.getColumns().clear();
		
		if(risultato.get(util.ResultKeys.ESITO).equals("true")){
			
			for(int i = 0; i < Integer.parseInt(risultato.get(util.ResultKeys.RES_LENGTH)) ; i++){
				
				filialiData.add(new Filiale(Integer.parseInt(risultato.get("id" + Integer.toString(i))),
						risultato.get("nome" + Integer.toString(i)), 
						risultato.get("luogo" + Integer.toString(i)), 
						risultato.get("telefono" + Integer.toString(i))) 
				); 
				
			}
			
			String[] columnNames = {"Nome", "Luogo", "Telefono"};
			
			@SuppressWarnings("unchecked")
			TableColumn<Filiale, String>[] columns = new TableColumn[columnNames.length];
			
			for (int i = 0 ; i < columnNames.length ; i++){
				columns[i] = new TableColumn<Filiale, String>(columnNames[i]);
				filialiTable.getColumns().add(columns[i]);
				final int wantedProperty = i;
				columns[i].setCellValueFactory(cellData -> cellData.getValue().getProperty(Filiale.properties[wantedProperty]));
			}
			
			filialiTable.setItems(filialiData);
			
		} else {
			
			filialiTable.setPlaceholder(new Label(risultato.get(util.ResultKeys.MSG_ERR)));
			
		}
		
	}
	
	private static void removeColumns(TableView<?> table, int... indexes){
		
		for(int i = 0 ; i < indexes.length ; i++){
			table.getColumns().remove(indexes[i] - i);
		}
		
	}

}
