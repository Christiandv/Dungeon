package Items.Weapons;

import java.awt.image.BufferedImage;

import Items.Weapon;

public class Spear extends Weapon {
	public Spear(int x, int y, BufferedImage icon, BufferedImage fog,
			int rarity, String name) {// generates sword
		this.x = x;
		this.y = y;
		this.icon = icon;
		this.fog = fog;
		this.rarity = rarity;
		this.name = name;
		range = 2;

	}

	public Spear(int x, int y, int dieSize, int numDie, int damageBoost,
			int accuracy, int range, BufferedImage icon, BufferedImage fog,
			int rarity, String name) {// use o make an explicit sword
		super(x, y, dieSize, numDie, damageBoost, accuracy, range, icon, fog,
				rarity, name);

	}
}
