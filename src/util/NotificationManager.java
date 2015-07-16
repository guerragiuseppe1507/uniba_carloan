package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class NotificationManager {
	
	private static Alert notifica;
	
	public static void setError(String e){
		notifica = new Alert(AlertType.ERROR);
		showNotification(e);
	};
	
	public static void setInfo(String i){
		notifica = new Alert(AlertType.INFORMATION);
		showNotification(i);
	}

	public static void setWarning(String w){
		notifica = new Alert(AlertType.WARNING);
		showNotification(w);
	}
	
	public static Optional<ButtonType> setConfirm(String c){
		notifica = new Alert(AlertType.CONFIRMATION);
		notifica.setTitle("");	
		notifica.setContentText(c);
		Optional<ButtonType> result = notifica.showAndWait();
		return result;
	}
	
	private static void showNotification(String n){
		notifica.setTitle("");	
		notifica.setContentText(n);
		notifica.showAndWait();
	}
}
