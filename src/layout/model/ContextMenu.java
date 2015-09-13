package layout.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import layout.view.ScreensController;
import layout.view.ScreensFramework;

public class ContextMenu {
	
	public ContextMenu(){}
    
    public static void showContextMenu(Pane c, ScreensController ctrl){
    	final String APP_NAME = ScreensFramework.APP_NAME;
        
    	MenuBar mainMenu = new MenuBar();
    	c.getChildren().add(mainMenu);
    	mainMenu.prefWidthProperty().bind(c.widthProperty());
		mainMenu.prefHeightProperty().bind(c.heightProperty());
		Boolean notManager = true;
		
    	
    	if(Context.getInstance().getUserType().equals(Context.MANAGER_SISTEMA)){
    		notManager = false;
			Menu gestisci = new Menu("Gestisci Filiali");
			mainMenu.getMenus().addAll(gestisci);
			
			//TODO voci del menu
			MenuItem gestisciManager = new MenuItem("Gestisci Manager");
			gestisci.getItems().addAll(gestisciManager);
			
			gestisciManager.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.insertManagerFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.insertManagerTitle);
	            }
	        });
			
    	}else if(Context.getInstance().getUserType().equals(Context.MANAGER_FILIALE)){
    		
    		
    		Menu contratti = new Menu("Contratti");
    		Menu gestisci = new Menu("Gestisci Filiale");
    		mainMenu.getMenus().addAll(contratti, gestisci);
    		
    		//TODO voci del menu
    		
    		MenuItem gestisciAuto = new MenuItem("Gestisci Auto");
    		MenuItem gestisciDipendente = new MenuItem("Gestisci Dipendenti");
    		MenuItem gestisciContratto = new MenuItem("Miei Contratti");
    		MenuItem inserimentoContratto = new MenuItem("Stipula Contratto");
    		MenuItem contrattiFiliale = new MenuItem("Gestisci Contratti");
    		
    		contratti.getItems().addAll(inserimentoContratto, gestisciContratto);
    		gestisci.getItems().addAll(gestisciAuto, gestisciDipendente, contrattiFiliale);
    		
    	
    		gestisciAuto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.manageAutoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.manageAutoTitle);
	            }
	        });
    		
    		
    		gestisciContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.gestioneContrattoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneContrattoTitle);
	            	ctrl.opzioniAvvio = "mieiContratti";
	            }
	        });
    		
    		contrattiFiliale.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.gestioneContrattoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneContrattoFilialeTitle);
	            	ctrl.opzioniAvvio = "gestioneFiliale";
	            }
	        });
    		
    		gestisciDipendente.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.gestioneDipendentiFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneDipendentiTitle);
	            }
	            
	            
	        });
    		

    		
    		inserimentoContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.inserimentoContrattoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.inserimentoContrattoTitle);
	            }
	        });
    		
    	} else if (Context.getInstance().getUserType().equals(Context.DIPENDENTE_FILIALE)){
    		
    		Menu gestisci = new Menu("Contratti");
    		Menu filiale = new Menu("Filiale");
    		mainMenu.getMenus().addAll(gestisci, filiale);
    		MenuItem inserimentoContratto = new MenuItem("Stipula Contratto");
    		MenuItem gestisciContratto = new MenuItem("Miei Contratti");
    		MenuItem contrattiInArrivo = new MenuItem("Contratti in arrivo");
    		//TODO voci del menu
    		gestisci.getItems().addAll(inserimentoContratto, gestisciContratto);
    		filiale.getItems().addAll(contrattiInArrivo);
    		
    		inserimentoContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.inserimentoContrattoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.inserimentoContrattoTitle);
	            }
	        });
    		
    		gestisciContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.gestioneContrattoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneContrattoTitle);
	            	ctrl.opzioniAvvio = "mieiContratti";
	            }
	        });
    		
    		contrattiInArrivo.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.gestioneContrattoFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneContrattoFilialeTitle);
	            	ctrl.opzioniAvvio = "contrattiInArrivo";
	            }
	        });
    		
    	}
    	
    	Menu account = new Menu("Account");
		mainMenu.getMenus().addAll(account);
		MenuItem disconnetti = new MenuItem("Disconnetti");
		
		disconnetti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ctrl.showScreen(ScreensFramework.loginFile);
            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME);
            }
        });
		
		if (notManager){
			MenuItem editProfile = new MenuItem("Dati Personali");
			account.getItems().addAll(editProfile,disconnetti);
			editProfile.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.showScreen(ScreensFramework.gestioneProfiloFile);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneProfiloTitle);
	            }
			 });
		} else {
			account.getItems().addAll(disconnetti);
		}
    	
    }
    
}
