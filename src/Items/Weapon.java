package Items;
import java.awt.image.BufferedImage;


public class Weapon extends Item implements Elements {
	protected int dieSize;
	protected int numDie;
	protected int damageBoost;
	protected String name;
	protected int range;
	protected int accuracyBoost;
	protected int rarity;
	
	

	public Weapon(int x, int y,int dieSize,int numDie,int damageBoost,int accuracy, int range, BufferedImage icon, BufferedImage fog,
			int rarity,String name)  {
		super(x, y, icon, fog,false);
		this.dieSize = dieSize;
		this.numDie = numDie;
		this.damageBoost = damageBoost;
		this.accuracyBoost = accuracy;
		this.range = range;
		

	}
	public Weapon(){
		
	}

	public int getDamage() {
		System.out.println(numDie*(((int)(Math.random()*dieSize))+1)+damageBoost);
		
		return (numDie*((int)(Math.random()*dieSize)+1))+damageBoost ;
	}

	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getDieSize() {
		return dieSize;
	}
	public void setDieSize(int dieSize) {
		this.dieSize = dieSize;
	}
	public int getNumDie() {
		return numDie;
	}
	public void setNumDie(int numDie) {
		this.numDie = numDie;
	}
	public int getDamageBoost() {
		return damageBoost;
	}
	public void setDamageBoost(int damageBoost) {
		this.damageBoost = damageBoost;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getAccuracy() {
		return accuracyBoost;
	}
	public void setAccuracy(int accuracy) {
		this.accuracyBoost = accuracy;
	}
	public int getRarity() {
		return rarity;
	}
	public void setRarity(int rarity) {
		this.rarity = rarity;
	}


	
	
	
	}
