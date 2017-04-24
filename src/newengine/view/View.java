package newengine.view;
import java.util.ArrayList;
import java.util.List;
import bus.EventBus;
import commons.point.GamePoint;
import commons.point.ViewPoint;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import newengine.events.input.KeyEvent;
import newengine.events.input.MouseEvent;
import newengine.model.Models;
import newengine.model.PlayerStatsModel;
import newengine.model.SelectionModel;
import newengine.model.SpriteModel;
import newengine.skill.Skill;
import newengine.sprite.Sprite;
import newengine.sprite.components.Images;
import newengine.sprite.components.Position;
import newengine.sprite.components.SkillSet;
import newengine.utils.image.LtubImage;
import newengine.view.camera.Camera;
import newengine.view.subview.SkillBox;
public class View {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;
	public static final int CANVAS_HEIGHT = 300;
	public static final int STATS_HEIGHT = 100;
	public static final Paint BACKGROUND = Color.BISQUE;
	private EventBus bus;
	private Camera camera;
	private Scene scene;
	private Canvas gameWorldCanvas;
	private GraphicsContext gc;
	private HBox bottomPane;
	private HBox statsPanel;
	private GraphicsContext gcSelected;
	private SkillBox skillBox;
	
	// TODO: mouse location should belong to player input state
	private ViewPoint mousePos;
	public View(EventBus bus, Camera camera) {
		this.bus = bus;
		this.camera = camera;
		VBox root = new VBox();
		scene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
		statsPanel = new HBox();
		gameWorldCanvas = new Canvas(WIDTH, CANVAS_HEIGHT);
		bottomPane = new HBox();
		root.getChildren().addAll(statsPanel, gameWorldCanvas, bottomPane);
		// selected sprite
		Canvas selectionCanvas = new Canvas(WIDTH / 2, HEIGHT - CANVAS_HEIGHT - STATS_HEIGHT);
		bottomPane.getChildren().add(selectionCanvas);
		gc = gameWorldCanvas.getGraphicsContext2D();
		gcSelected = selectionCanvas.getGraphicsContext2D();
		// skill box
		skillBox = new SkillBox(bus);
		bottomPane.getChildren().add(skillBox.getBox());
		initHandlers();
	}
	private void initHandlers() {
		gameWorldCanvas.setOnMouseClicked(e -> {
			ViewPoint viewPos = new ViewPoint(e.getX(), e.getY());
			if (e.getButton() == MouseButton.PRIMARY) {
				bus.emit(new MouseEvent(MouseEvent.LEFT, camera.viewToGame(viewPos)));
			} else if (e.getButton() == MouseButton.SECONDARY) {
				bus.emit(new MouseEvent(MouseEvent.RIGHT, camera.viewToGame(viewPos)));
			}
		});
		gameWorldCanvas.setOnMouseMoved(e -> {
			mousePos = new ViewPoint(e.getX(), e.getY());
		});
		scene.setOnKeyPressed(e -> {
			bus.emit(new KeyEvent(KeyEvent.PRESS, e.getCode()));
		});
		scene.setOnKeyReleased(e -> {
			bus.emit(new KeyEvent(KeyEvent.RELEASE, e.getCode()));
		});
		scene.setOnKeyTyped(e -> {
			bus.emit(new KeyEvent(KeyEvent.TYPE, e.getCode()));
		});
	}
	public Scene getScene() {
		return scene;
	}
	public void clear(){
		gc.clearRect(0, 0, WIDTH, CANVAS_HEIGHT);
	}
	
	public void render(Models models) {
		render(models.spriteModel());
		render(models.playerStatsModel());
		render(models.selectionModel());
	}
	
	public void render(SpriteModel model) {
		// render game cast
		for (Sprite sprite : model.getSprites()) {
			if (!sprite.getComponent(Position.TYPE).isPresent() ||
					!sprite.getComponent(Images.TYPE).isPresent()) {
				continue;
			}
			GamePoint spritePos = sprite.getComponent(Position.TYPE).get().pos();
			LtubImage image = sprite.getComponent(Images.TYPE).get().image();
			
			GamePoint gamePos = new GamePoint(spritePos.x() - image.getImagePivot().x(), 
					spritePos.y() - image.getImagePivot().y());
			ViewPoint viewPos = camera.gameToView(gamePos);
			gc.drawImage(new Image(image.getInputStream()), viewPos.x(),
					viewPos.y());
		}
	}
	
	public void render(PlayerStatsModel playerStatsModel) {
		this.statsPanel.getChildren().clear();
		statsPanel.setSpacing(10);
		statsPanel.maxHeight(100);
		createText(playerStatsModel).stream().forEach(e -> statsPanel.getChildren().add(e));
		
		//TODO if done with testing playerstatsmodel, delete
		//bus.emit(new ChangeLivesEvent(ChangeLivesEvent.CHANGE,Player.MAIN_PLAYER, -1));
		//bus.emit(new ChangeScoreEvent(ChangeScoreEvent.CHANGE, Player.MAIN_PLAYER, 1));
		//bus.emit(new ChangeWealthEvent(ChangeWealthEvent.CHANGE,Player.MAIN_PLAYER,"gold", 1));
	}
	
	private List<Text> createText(PlayerStatsModel playerStatsModel) {
		List<Text> statsLabels = new ArrayList<Text>();
		for(String wealthName : playerStatsModel.getWealth().keySet()){
			statsLabels.add(new Text(wealthName + ":" + playerStatsModel.getWealth().get(wealthName)));
		}
		//TODO map to resource file
		statsLabels.add(new Text("Lives:" + playerStatsModel.getLives()));
		statsLabels.add(new Text("Score:" + playerStatsModel.getScore()));
		return statsLabels;
	}
	public void render(SelectionModel selectionModel) {
		// render the selected sprite and its skill box
		if (selectionModel.getSelectedSprite().isPresent()) {
			Sprite sprite = selectionModel.getSelectedSprite().get();
			if (sprite.getComponent(Images.TYPE).isPresent()) {
				gcSelected.clearRect(0, 0, WIDTH, CANVAS_HEIGHT);
				gcSelected.drawImage(sprite.getComponent(Images.TYPE).get().image().getFXImage(), 20, 0);
			}
			if (sprite.getComponent(SkillSet.TYPE).isPresent()) {
				skillBox.render(sprite.getComponent(SkillSet.TYPE).get().skills());
			}
			else {
				skillBox.clear();
			}
		}
		else {
			gcSelected.clearRect(0, 0, WIDTH, CANVAS_HEIGHT);
			skillBox.clear();
		}
		// render the selected skill
		if (selectionModel.getSelectedSkill().isPresent()) {
			Skill skill = selectionModel.getSelectedSkill().get();
			if (skill.getIcon().isPresent()) {
				Image skillImage = skill.getIcon().get().getFXImage();
				scene.setCursor(new ImageCursor(skillImage));
			}
		}
		else {
			scene.setCursor(Cursor.DEFAULT);
		}
	}
}