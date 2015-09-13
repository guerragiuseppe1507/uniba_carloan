package layout.view;

import java.io.IOException;
import java.util.HashMap;

import util.NotificationManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import layout.model.entities.CarloanEntity;


public class ScreensController extends StackPane {
	

	private Node currentScreen;
	private Stage winScreen;
	private ControlledScreen controller;
	public HashMap<String,CarloanEntity> params = new HashMap<String,CarloanEntity>();
	public String opzioniAvvio;
	
	public ScreensController(){
		super();
	}

	public void setScreen(Node screen){
		currentScreen = screen;
	}

	public Node getScreen(String name){
		return currentScreen;
	}

	public boolean loadScreen(final String resource){
		try{
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			controller = ((ControlledScreen) myLoader.getController());
			controller.setScreenParent(this);
			setScreen(loadScreen);
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	public boolean showScreen(final String resource){
		
		boolean isLoad;
		
		loadScreen(resource);
		
		if (currentScreen != null){
			
			if (!getChildren().isEmpty()){                     		
				
				getChildren().remove(0);              		
				getChildren().add(0, currentScreen);
			
			} else {
				
				getChildren().add(currentScreen);				
				
			}
			
			Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	controller.riempiCampi();
		        }
		    });
			
			isLoad = true;
		} else {
			
			isLoad = false;
		}
		
		return isLoad;
		
	}

	//GESTIONE SCHERMATE SOVRAPPOSTE
	
	//Questo Metodo Visualizza la  schermata scelta in una nuova finestra
	public boolean showScreenNewWindow(final String res, final String title){
		
		Parent root;
		try {
			
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(res));
            root = myLoader.load();
            ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			winScreen = new Stage();
			winScreen.setTitle(ScreensFramework.APP_NAME+" - "+title);
			winScreen.initModality(Modality.APPLICATION_MODAL);
			winScreen.setResizable(false);
			winScreen.setScene(new Scene(root));
			winScreen.initOwner(ScreensFramework.PRIMARY_STAGE.getScene().getWindow());
			winScreen.show();
			
			Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	myScreenController.riempiCampi();
		        }
		    });
			
        } catch (IOException e) {
            e.printStackTrace();
        }
		return true;
		
	}
	
	public void closeWinStage(){
		winScreen.close();
	}
	
	public void disconnetti(String msg){
		showScreen(ScreensFramework.loginFile);
		NotificationManager.setInfo(msg);
	}

}
