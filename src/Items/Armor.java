package Items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Armor extends Item {
	int defence;
	String name;
	int Level;

	public Armor(int x, int y, BufferedImage icon, BufferedImage fog,
			int rarity,String name)  {
		super(x, y, icon, fog,false);

		if (rarity <= 50) {
			
			defence =(int) (Math.random() * 7+1);
		} else if (rarity > 50 && rarity <= 80) {
			
			defence = (int)(Math.random() * 13 + 7);

		} else if (rarity > 80) {
			
			defence = (int)(Math.random() * 18 + 13);
		}
		

	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}
	
	
	}