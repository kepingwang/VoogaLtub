package gameDevelopmentInterface;

import data.AttributesForScreenUse;
import data.ScreenModelData;
import javafx.collections.ObservableMap;
import javafx.scene.layout.BorderPane;
/**
 * 
 * The job of the ScreenModelCreator is to provide an interface for the developer to set all the data for a 
 * given screen. It has no public methods aside from it's instantiator. Things it does: binding between the 
 * DummyScreenView and the ScreenModel, allowing the user to place objects on the screen. Adding an object 
 * to the screen should modify the ScreenModel and display something on the DummyScreen if the object implements
 * Sprite.
 * 
 * @author Jake
 *
 */

public class ScreenModelCreator extends BorderPane {
	private ObservableMap<String,String> myData;
	private ScreenModelData myScreenModel = new ScreenModelData();
	private ScreenObjectHolder myObjectsToPlace;
	private ScreenMap myScreen = new ScreenMap();
	private ButtonsPanel myButtonsPanel = new ButtonsPanel(this);
	private AttributesForScreenUse myAttributesModel;
	
	public ScreenModelCreator(AttributesForScreenUse attributesModel, GeneralDataCreator gdc) {
		myAttributesModel = attributesModel;
		myObjectsToPlace = new ScreenObjectHolder(this, myScreenModel, myAttributesModel);
		myData = gdc.getAllData();
		this.setTop(new GeneralGameDataBar(myData));
		this.setBottom(myObjectsToPlace);
		this.setCenter(myScreen);
		this.setRight(myButtonsPanel);
	}
	/**
	 * 
	 * @return the screen this border pane instantiates for use in ScreenObjectHolder
	 */
	public ScreenMap getScreen() {
		return myScreen;
	}
}