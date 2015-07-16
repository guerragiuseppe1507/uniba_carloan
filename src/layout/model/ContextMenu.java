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
	            }
	        });
			
    	}else if(Context.getInstance().getUserType().equals(Context.managerFiliale)){
    		
    		Menu gestisci = new Menu("Gestisci Filiale");
    		mainMenu.getMenus().addAll(gestisci);
    		
    		//TODO voci del menu
    		MenuItem gestisciAuto = new MenuItem("Gestisci Auto");
    		MenuItem gestisciDipendente = new MenuItem("Gestisci Dipendenti");
    		gestisci.getItems().addAll(gestisciAuto, gestisciDipendente);
    		
    	
    		gestisciAuto.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.manageAutoID);
	            }
	        });
    		
    		
    		gestisciDipendente.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	ctrl.setScreen(ScreensFramework.gestioneDipendentiID);
	            }
	        });
    		
    		
    	}else if(Context.getInstance().getUserType().equals(Context.dipendenteFiliale)){
    		
    		Menu gestisci = new Menu("Gestisci");
    		mainMenu.getMenus().addAll(gestisci);
    		
    		//TODO voci del menu
    	} 
    	
    	Menu account = new Menu("Account");
		mainMenu.getMenus().addAll(account);
		MenuItem disconnetti = new MenuItem("Disconnetti");
		
		
		
		
		disconnetti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ctrl.setScreen(ScreensFramework.loginID);
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
	            	ctrl.resetScreens();
	            }
			 });
		}else{
			account.getItems().addAll(disconnetti);
		};
    	
    }
}

