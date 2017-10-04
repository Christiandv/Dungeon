package Creatures;

import java.awt.image.BufferedImage;

import Game.Damage;
import Game.Thing;
import Items.Armor;
import Items.Elements;
import Items.Weapon;

public class Player extends Thing implements Elements {
	protected int health;
	protected int healthMax;
	protected Damage attack;
	protected int exp;
	protected int level;
	private boolean leveled = false;
	private Weapon weapon;
	private Armor armor;

	public Player(int x, int y, BufferedImage icon, BufferedImage fog) {
		super(x, y, true, icon, fog);
		this.health = 50;
		this.healthMax = 50;
		attack = new Damage(0,true,NONE);
		exp = 0;
		level = 1;
		weapon = null;
	}
	public int getRange(){
		if(weapon != null){
			return weapon.getRange();
		}
		return 1;
	}
	public int getHealth() {
		return health;
	}

	public void setWeapon(Weapon w) {
		weapon = w;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	

	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public void setHealth(Damage damage) {
		health = health - damage.getAmount();
	}

	public Damage getAttack() {
		attack = new Damage(level*3,true,NONE);
		if(weapon != null){
			attack = new Damage(level*3+ weapon.getDamage(),true,NONE);
		}
	return attack;
	}

	public void setDamage(Damage attack) {
		this.attack = attack;
	}

	public int getHealthMax() {
		return healthMax;
	}

	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}
	

	public int getExp() {
		return exp;
	}

	public void addExp(int exp) {
		this.exp += exp;

	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLeveled() {
		return leveled;
	}

	public void update() {// saving throws and such
		
		if (Math.random() < .3) {// health regen
			health++;
		}
		if (health > healthMax)
			health = healthMax;
		leveled = false;
		while (exp >= level * 20) {
			leveled = true;
			exp -= level * 20;
			level++;
			
			int healthAdd = (int) (Math.random() * 10) + 4;
			health += healthAdd;
			healthMax += healthAdd;
		}
	}

}
