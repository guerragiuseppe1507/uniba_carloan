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
		
    	
    	if(Context.getInstance().getUserType().equals(Context.managerSistema)){
    		notManager = false;
			Menu gestisci = new Menu("Gestisci Filiali");
			mainMenu.getMenus().addAll(gestisci);
			
			//TODO voci del menu
			MenuItem gestisciManager = new MenuItem("Gestisci Manager");
			gestisci.getItems().addAll(gestisciManager);
			
			gestisciManager.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.insertManagerID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.insertManagerTitle);
	            }
	        });
			
    	}else if(Context.getInstance().getUserType().equals(Context.managerFiliale)){
    		
    		Menu gestisci = new Menu("Gestisci Filiale");
    		mainMenu.getMenus().addAll(gestisci);
    		
    		//TODO voci del menu
    		
    		MenuItem gestisciAuto = new MenuItem("Gestisci Auto");
    		MenuItem gestisciDipendente = new MenuItem("Gestisci Dipendenti");
    		MenuItem gestisciContratto = new MenuItem("Gestisci Contratti");
    		MenuItem inserimentoContratto= new MenuItem("Inseimento Contratto");
    		gestisci.getItems().addAll(gestisciAuto, gestisciDipendente, gestisciContratto,inserimentoContratto);
    		
    	
    		gestisciAuto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.manageAutoID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.manageAutoTitle);
	            }
	        });
    		
    		
    		gestisciContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.gestioneContrattoID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneContrattoTitle);
	            }
	        });
    		
    		gestisciDipendente.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.gestioneDipendentiID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneDipendentiTitle);
	            }
	            
	            
	        });
    		

    		
    		inserimentoContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.InserimentoContrattoID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.InserimentoContrattoTitle);
	            }
	        });
    		
    	}else if(Context.getInstance().getUserType().equals(Context.dipendenteFiliale)){
    		
    		Menu gestisci = new Menu("Gestisci");
    		mainMenu.getMenus().addAll(gestisci);
    		MenuItem inserimentoContratto = new MenuItem("Inserimento Contratto");
    		//TODO voci del menu
    		gestisci.getItems().addAll(inserimentoContratto);
    		
    		inserimentoContratto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.InserimentoContrattoID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.InserimentoContrattoTitle);
	            }
	        });
    		
    	}
    	
    	
    	
    	Menu account = new Menu("Account");
		mainMenu.getMenus().addAll(account);
		MenuItem disconnetti = new MenuItem("Disconnetti");
		
		
		
		
		disconnetti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ctrl.setScreen(ScreensFramework.loginID);
            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME);
            	ctrl.resetScreens();
            }
        });
		
		if (notManager){
			MenuItem editProfile = new MenuItem("Dati Personali");
			account.getItems().addAll(editProfile,disconnetti);
			editProfile.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.gestioneProfiloID);
	            	ScreensFramework.PRIMARY_STAGE.setTitle(APP_NAME+" - "+ScreensFramework.gestioneProfiloTitle);
	            }
			 });
		}else{
			account.getItems().addAll(disconnetti);
		};
    	
    }
}

