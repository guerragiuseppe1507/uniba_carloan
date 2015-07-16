package layout.view;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;



public class ScreensController extends StackPane {
	

	private HashMap<String, Node> screens = new HashMap<>();
	
	public ScreensController(){
		super();
	}
	
	//Aggiunge la schermata alla collezione.
	public void addScreen(String name, Node screen){
		screens.put(name, screen);
	}
	
	//Restituisce il nodo con il nome appropriato.
	public Node getScreen(String name){
		return screens.get(name);
	}
	
	//Carica il file fxml, aggiunge la schermata alla collezione e infine
	//introduce lo ScreenPane nel controller
	public boolean loadScreen(String name, String resource){
		try{
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		}
		catch (Exception e){
			
			System.out.println(e.getMessage());
			return false;
		}
	}
	

	
	//Questo metodo prova a visualizzare la schermata col nome dato.
	//Prima di tutto controlla se la schermata è stata caricata. Poi, se c'è più di una schermata,
	//la nuova schermata viene aggiunta come seconda e la schermata corrente viene rimossa.
	//Se non c'è nessuna schermata già visualizzata, la nuova schermata viene direttamente aggiunta alla radice.
	public boolean setScreen(final String name){
		if (screens.get(name) != null){
			final DoubleProperty opacity = opacityProperty();
			
			if (!getChildren().isEmpty()){                     		//Se c'è più di una schermata.
				Timeline fade = new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue (opacity, 1.0)),
						new KeyFrame(new Duration(50), new EventHandler<ActionEvent>(){  //Duration = durata dissolvenza tra switch schermate
					@Override
					public void handle(ActionEvent t){
						getChildren().remove(0);              		//Rimuove la schermata visualizzata
						getChildren().add(0, screens.get(name));	//Aggiunge la nuova schermata
						Timeline fadeIn = new Timeline(
								new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
								new KeyFrame(new Duration(50), new KeyValue(opacity, 1.0)));
						fadeIn.play();
						
					}
				}, new KeyValue(opacity, 0.0)));
				fade.play();
			
			} else {
				setOpacity(0.0);
				getChildren().add(screens.get(name));				//Non c'è nient'altro visualizzato, mostra direttamente
				Timeline fadeIn = new Timeline(
						new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));  //Durata dissolvenza apertura schermata principale
				fadeIn.play();
			}
			return true;
		} else {
			System.out.println("La schermata non è stata caricata! \n");
			return false;
		}
	}
	
	//Rimuove la schermata con il nome dato dalla collezione
	public boolean unloadScreen(String name){
		if (screens.remove(name) == null){
			System.out.println("La schermata non esiste");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean resetScreens(){
		screens = new HashMap<>();
		return loadScreen(ScreensFramework.loginID,ScreensFramework.loginFile);
	}

}
