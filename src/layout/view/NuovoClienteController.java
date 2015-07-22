package layout.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class NuovoClienteController  implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	 @Override
	public void initialize(URL url, ResourceBundle rb){

	}

	public void setScreenParent(ScreensController screenParent){
		myController = screenParent;
	}
		
}
