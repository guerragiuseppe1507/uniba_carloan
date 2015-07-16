package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotificationManager {
	
	public static void setError(String e){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("");
		alert.setContentText(e);
		alert.showAndWait();
	};
	
	public static void setInfo(String i){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setContentText(i);
		alert.showAndWait();
	}

}
