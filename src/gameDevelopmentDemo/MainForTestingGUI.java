package gameDevelopmentDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commons.point.GamePoint;
import data.EventHandleData;
import data.SpriteMakerModel;
import gameDevelopmentInterface.MasterDeveloperInterface;
import gamedata.DataTranslator;
import javafx.application.Application;
import javafx.stage.Stage;
import newengine.events.sprite.MoveEvent;
import newengine.skill.Skill;
import newengine.skill.SkillType;
import newengine.skill.skills.MoveSkill;
import newengine.sprite.Sprite;
import newengine.sprite.components.Collidable;
import newengine.sprite.components.EventQueue;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Images;
import newengine.sprite.components.Owner;
import newengine.sprite.components.Position;
import newengine.sprite.components.Range;
import newengine.sprite.components.Selectable;
import newengine.sprite.components.SkillSet;
import newengine.sprite.components.SoundEffect;
import newengine.sprite.components.Speed;
import newengine.sprite.components.TestPosition;
import newengine.sprite.components.Collidable.CollisionBoundType;
import newengine.sprite.components.Selectable.SelectionBoundType;
import newengine.sprite.player.Player;
import newengine.utils.Target;
import newengine.utils.image.ImageSet;
import newengine.utils.image.LtubImage;
import utilities.XStreamHandler;

public class MainForTestingGUI extends Application {
	private static final String DEVELOPER_GUI = "DeveloperGUI";

	public void start(Stage primaryStage) {
		XStreamHandler xStream = new XStreamHandler();
//		//xStream.saveToFile(new EventHandleData());
//		EventHandleData data = (EventHandleData) xStream.getObjectFromFile();
//		Sprite sprite = data.getSprite();
//		sprite.emit(new MoveEvent(MoveEvent.SPECIFIC, sprite, new Target(new GamePoint(0,0))));
		Player player1 = new Player(Player.MAIN_PLAYER);
		SpriteMakerModel sprite1 = new SpriteMakerModel();
		LtubImage image1 = new LtubImage("images/characters/bahamut_left.png");
		ImageSet imageSet1 = new ImageSet(image1);
		sprite1.addComponent(new TestPosition());
		
//		Map<SkillType<? extends Skill>, Skill> skillMap = new HashMap<>();
//		skillMap.put(MoveSkill.TYPE, new MoveSkill());
//		sprite1.addComponent(new GameBus());
//		sprite1.addComponent(new SkillSet(skillMap));
//		sprite1.addComponent(new Owner(player1));
//		sprite1.addComponent(new Position(new GamePoint(200, 100), 0));
//		sprite1.addComponent(new SoundEffect("data/sounds/Psyessr4.wav"));
//		sprite1.addComponent(new Images(imageSet1));
//		sprite1.addComponent(new Speed(200));
//		sprite1.addComponent(new Collidable(CollisionBoundType.IMAGE));
//		sprite1.addComponent(new Selectable(SelectionBoundType.IMAGE));
//		sprite1.addComponent(new Range(128));
//		sprite1.addComponent(new EventQueue());
		
		xStream.saveToFile(sprite1);
		
		SpriteMakerModel isItTheSame = (SpriteMakerModel) xStream.getObjectFromFile();
		
		//isItTheSame.printComponents();
		
		List<SpriteMakerModel> myList = new ArrayList<SpriteMakerModel>();
		myList.add(isItTheSame);
		DataTranslator dt = new DataTranslator();
		dt.toSprites(myList);
		Sprite sprite = dt.getGameSprites().getSprites().get(0);
		sprite.emit(new MoveEvent(MoveEvent.SPECIFIC, sprite, new Target(new GamePoint(100,0))));
		
		
		
		
		
//		primaryStage.setTitle(DEVELOPER_GUI);
//		primaryStage.show();
//		MasterDeveloperInterface developerView = new MasterDeveloperInterface();
//		primaryStage.setScene(developerView.getScene());
	}

	public static void main(String[] args) {
		launch(args);
	}
}