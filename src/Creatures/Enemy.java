package Creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Game.Damage;
import Game.DamageIcon;
import Game.Thing;

public class Enemy extends Thing {
	protected int level;
	protected int health;
	protected int range;
	protected int oldX;
	protected int oldY;
	protected int exp;
	protected int difficulty;
	protected Damage attack;
	protected int healthMax;
	private ArrayList<DamageIcon> popups = new ArrayList<DamageIcon>();
	ArrayList<BufferedImage> popupBacking;
	Corpse corpse;

	protected boolean examined;

	public Enemy(int x, int y, BufferedImage icon, BufferedImage fog,
			ArrayList<BufferedImage> popupBacking, int level, int health,
			Damage attack, int range, int exp, int difficulty) {
		super(x, y, true, icon, fog);
		this.level = level;
		this.health = health;
		this.healthMax = health;
		this.attack = attack;
		this.range = range;
		this.exp = exp;
		this.popupBacking = popupBacking;
		this.difficulty = difficulty;

	}

	public boolean isExamined() {
		return examined;
	}

	public void setExamined(boolean examined) {
		this.examined = examined;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setHealth(Damage damage) {
		health = health - damage.getAmount();
		popups.add(new DamageIcon(getX(), getY(), damage.getAmount(),
				popupBacking));
	}

	public Damage getAttack() {
		return attack;
	}

	public void setAttack(Damage attack) {
		this.attack = attack;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getOldX() {
		return oldX;
	}

	public void setOldX(int oldX) {
		this.oldX = oldX;
	}

	public int getOldY() {
		return oldY;
	}

	public void setOldY(int oldY) {
		this.oldY = oldY;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		exp = exp;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public ArrayList<DamageIcon> getPopups() {
		return popups;
	}

	public void setPopups(ArrayList<DamageIcon> popups) {
		this.popups = popups;
	}

	public int getHealthMax() {
		return healthMax;
	}

	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}

	public void setWasRevealed(boolean wasRevealed) {
		this.wasRevealed = wasRevealed;

	}

	public void draw(Graphics gi, int offX, int offY) {
		if (revealed) {
			gi.drawImage(icon, (x - offX) * 32, (y - offY) * 32, 32, 32, null);
			for (DamageIcon d : popups) {
				d.draw(gi, offX, offY);
			}
			for (int i = 0; i < popups.size(); i++) {

				popups.get(i).countdown();
				if (popups.get(i).getCountdown() < 0) {
					popups.remove(i);

				}
			}
		} else if (wasRevealed) {
			// only draws the fog on it if it isn't currently revealed
			// but was once
			gi.drawImage(icon, (oldX - offX) * 32, (oldY - offY) * 32, 32, 32,
					null);
			gi.drawImage(fog, (oldX - offX) * 32, (oldY - offY) * 32, 32, 32,
					null);

		}
	}

	public void examine(Graphics gi, int offX, int offY, int playerLevel) {
		/*
		 * needed things: type, relative difficulty, relative health
		 */
		if (examined && revealed) {
			gi.setColor(Color.gray);
			gi.fillRect((x - offX) * 32 + 16, (y - offY) * 32 + 8, 64, 48);
			gi.setColor(Color.black);
			gi.drawString(this.getClass().getSimpleName(),
					(x - offX) * 32 + 20, (y - offY) * 32 + 20);
			if (playerLevel - difficulty < -4) {
				gi.setColor(Color.red);
				gi.drawString("RUN", (x - offX) * 32 + 20, (y - offY) * 32 + 34);
			} else if (playerLevel - difficulty >= (-4)
					&& playerLevel - difficulty < (-2)) {
				gi.setColor(Color.orange);
				gi.drawString("Dangerous", (x - offX) * 32 + 20,
						(y - offY) * 32 + 34);
			} else if (playerLevel - difficulty >= (-2)
					&& playerLevel - difficulty <= (2)) {
				gi.setColor(Color.yellow);
				gi.drawString("Fair", (x - offX) * 32 + 20,
						(y - offY) * 32 + 34);
			} else if (playerLevel - difficulty > (2)) {
				gi.setColor(Color.green);
				gi.drawString("Easy", (x - offX) * 32 + 20,
						(y - offY) * 32 + 34);
			}
			
			gi.setColor(Color.black);
			if(health/healthMax>.5){
				gi.setColor(Color.green);
				gi.drawString("Healthy", (x - offX) * 32 + 20,
						(y - offY) * 32 + 48);
			}else if(health/healthMax>=.2){
				gi.setColor(Color.orange);
				gi.drawString("Injured", (x - offX) * 32 + 20,
						(y - offY) * 32 + 48);
			}else{
				gi.setColor(Color.red);
				gi.drawString("Severely injured", (x - offX) * 32 + 20,
						(y - offY) * 32 + 48);
			}

		}
	}
}
