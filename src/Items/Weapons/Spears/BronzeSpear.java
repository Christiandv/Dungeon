package Items.Weapons.Spears;

import java.awt.image.BufferedImage;

import Items.Weapons.Spear;

public class BronzeSpear extends Spear{
	public BronzeSpear(int x, int y, BufferedImage icon, BufferedImage fog,
			int rarity, String name) {// generates random
	super(x,y,icon,fog,rarity,name);
		dieSize = 8;
		if(rarity <90)
			numDie = 1;
		else
			numDie = 2;//SUPA RARE 
		damageBoost = 2;
		accuracyBoost = 3;
		

	}

	public BronzeSpear(int x, int y, int dieSize, int numDie, int damageBoost,
			int accuracy, int range, BufferedImage icon, BufferedImage fog,
			int rarity, String name) {// explicit
		super(x, y, dieSize, numDie, damageBoost, accuracy, range, icon, fog,
				rarity, name);

	}
}
