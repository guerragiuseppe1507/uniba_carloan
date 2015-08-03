package layout.view;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import layout.model.ContextMenu;
import layout.model.TableManager;

import layout.model.entities.Contratto;

public class GestioneContrattiController  implements Initializable, ControlledScreen {
		
ScreensController myController;
	
	@FXML
	StackPane container;
	
	@FXML
	AnchorPane menu;
	
	@FXML
	private TableView<Contratto> contrattoTable;

	 
	@Override
	public void initialize(URL url, ResourceBundle rb){
			
		container.setPrefHeight(ScreensFramework.DEFAULT_HEIGHT);
		container.setPrefWidth(ScreensFramework.DEFAULT_WIDTH);
		menu.setPrefHeight(ScreensFramework.DEFAULT_MENU_HEIGHT);
		menu.setPrefWidth(ScreensFramework.DEFAULT_MENU_WIDTH);
			
		TableManager.riempiTabellaContratto(contrattoTable);

	}

	public void setScreenParent(ScreensController screenParent){
			myController = screenParent;
			ContextMenu.showContextMenu(menu,myController);
	}
		
}
