package gameDevelopmentInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import data.AttributeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AttributeDisplay extends VBox{
	private final double prefWidth=200;
	private AttributeDataFactory fileConverter;
	private AttributeData targetData;
	
	public AttributeDisplay(String listTitle, List<AttributeData> displayedData, AttributeData targetData) {
		this.targetData=targetData;
		ObservableList<AttributeData> observableAttributeNames = FXCollections.observableList(displayedData);
		ListView<AttributeData> attributesBox = new ListView<AttributeData>();
		attributesBox.setItems(observableAttributeNames);

		attributesBox.setCellFactory(new Callback<ListView<AttributeData>, ListCell<AttributeData>>() {
			@Override
			public ListCell<AttributeData> call(ListView<AttributeData> list) {
				return new AttributeCustomizerOption();
			}
		});

		attributesBox.setPrefWidth(prefWidth);
		Label title = new Label(listTitle);
		this.getChildren().addAll(title, attributesBox);
	}

	class AttributeCustomizerOption extends ListCell<AttributeData> {
		@Override
		public void updateItem(AttributeData item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null) {
				return;
			}
			Button attributeCustomizer = new Button(item.getName());
			attributeCustomizer.setOnAction((c) -> {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File(System.getProperty("user.dir")));
				new AttributeCustomizerPopup(item,
						targetData);
			});
			attributeCustomizer.setPrefWidth(prefWidth);
			setGraphic(attributeCustomizer);
		}
	}

}