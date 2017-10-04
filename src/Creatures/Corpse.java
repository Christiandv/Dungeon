package Creatures;
import java.awt.image.BufferedImage;

import Game.Thing;
import Items.Item;


public class Corpse extends Thing{
	Item loot;
	public Corpse(int x, int y,BufferedImage icon, BufferedImage fog, Item loot){
		super(x,y,false,icon,fog);
		this.loot = loot;
	}
	public Item getLoot() {
		return loot;
	}
	public void setLoot(Item loot) {
		this.loot = loot;
	}
	
}
