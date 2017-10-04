package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.Thing;


public class Chest extends Thing{
	protected BufferedImage open;
	protected boolean opened;
	int level;// needs ArrayList of items
	public Chest(int x, int y,
			BufferedImage icon, BufferedImage fog,BufferedImage open, int level){
		super(x,y,false,icon,fog);
		this.level = level;
		this.open = open;
		opened = false;
		
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isOpened() {
		return opened;
	}
	public void setOpenned(boolean opened) {
		if(opened&&this.opened==false){
			icon = open;
		}
		this.opened = opened;
	}
	
	
}
