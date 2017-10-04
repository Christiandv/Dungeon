package Items.Weapons.Swords;

import java.awt.image.BufferedImage;


import Items.Weapons.Sword;

public class BronzeSword extends Sword {
	public BronzeSword(int x, int y, BufferedImage icon, BufferedImage fog,
			int rarity, String name) {// generates random
	super(x,y,icon,fog,rarity,name);
		dieSize = 6;
		if(rarity <90)
			numDie = 1;
		else
			numDie = 2;//SUPA RARE 
		damageBoost = 1;
		accuracyBoost = 2;
		
range = 2;
	}

	public BronzeSword(int x, int y, int dieSize, int numDie, int damageBoost,
			int accuracy, int range, BufferedImage icon, BufferedImage fog,
			int rarity, String name) {// explicit
		super(x, y, dieSize, numDie, damageBoost, accuracy, range, icon, fog,
				rarity, name);

	}
}
