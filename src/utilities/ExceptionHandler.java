package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {
	public static void errorPopUp(String message){
		Alert errorAlert=new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(message);
	}

}
