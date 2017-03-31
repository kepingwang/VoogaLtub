package api.GameDevelopment;
import api.Data.InterfaceData;
import javafx.*;
import javafx.scene.Node;

//Creates a popup box based for each kind of interface, which allows the user to set the data used 
//for an implementation of that data.
public interface InterfaceDataPopupCreator{//Extends Node
	//Creates the popup box for some kind of interface data.
	public Node createInterfaceDataPopup(InterfaceData data);
}
