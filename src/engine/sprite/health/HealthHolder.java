package engine.sprite.health;

import data.AttributeData;
import engine.sprite.Attribute;

public class HealthHolder implements Attribute {
	
	protected double currHealth;
	private Boolean hasHealth;
	
	public HealthHolder(AttributeData data){
		 this.currHealth = Double.parseDouble(data.getVariable("health"));
	}
	
	public HealthHolder(double initial){
		currHealth = initial;
		hasHealth = true;
	}

	@Override
	public void switchOn() {
		hasHealth = true;
	}

	@Override
	public void switchOff() {
		hasHealth = false;
	}

	@Override
	public Boolean isAttribute() {
		return hasHealth;
	}

	@Override
	public double update(double dt) {
		//not gonna do anything here because only changes when event is fired
		return 0;
	}
	
	public double getHealth(){
		return currHealth;
	}
	
	public void decrementHealth(double amt){
		currHealth = currHealth - amt;
	}

}
