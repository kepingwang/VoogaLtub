package engine.sprite.range;

import java.util.List;

import bus.EventBus;
import engine.camera.GamePoint;
import engine.sprite.Sprite;

/**
 * Check if one sprite is in the detection range of another sprite.
 * @author Yilin Gao
 *
 */
public class InRangeChecker {
	
	private EventBus bus;
	
	public InRangeChecker(EventBus bus) {
		this.bus = bus;
	}
	
	/**
	 * Loop over all sprites in the game and detect any in-range between any two sprites.
	 * @param sprites A list of sprites in the model
	 */
	public void checkInRange(List<Sprite> sprites) {
		for (Sprite detector: sprites) {
			for (Sprite detectee: sprites) {
				if (detector == detectee) { continue; }
				if (inRange(detector, detectee)) {
					bus.emit(new InRangeEvent(InRangeEvent.ANY, detector, detectee));
				}
			}
		}
	}
	
	public void checkMonstersInRange(List<Sprite> tower, List<Sprite> monsters){
		for (Sprite detector: tower) {
			for (Sprite detectee: monsters) {
				if (detector == detectee) { continue; }
				if (inRange(detector, detectee)) {
					bus.emit(new MonsterInRangeEvent(MonsterInRangeEvent.ANY, detector, detectee));
				}
			}
		}
	}

	private boolean inRange(Sprite detector, Sprite detectee) {
		GamePoint detectorPos = detector.getPos();
		GamePoint detecteePos = detectee.getPos();
		double distance = detectorPos.distFrom(detecteePos);
		if (distance <= detector.getDetectionRange()) {
			return true;
		}
		return false;
	}

}
