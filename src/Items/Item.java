package Items;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Game.Thing;

public class Item extends Thing {
	private boolean ground;
	private boolean selected;
	
	public Item(int x, int y, BufferedImage icon, BufferedImage fog,
			boolean ground) {
		super(x, y, false, icon, fog);
		
		this.ground = ground;
	}
public Item(){
	
}
public void draw(Graphics g,int offX,int offY) {
		if (ground) {
			if (revealed) {
				g.drawImage(icon, (x-offX) * 32, (y-offY) * 32, 32, 32, null);
			} else if (wasRevealed) {
				// only draws the fog on it if it isn't currently revealed
				// but was once
				g.drawImage(icon,(x-offX) * 32, (y-offY) * 32, 32, 32, null);
				g.drawImage(fog, (x-offX) * 32, (y-offY) * 32, 32, 32, null);
			}
		} else {
			g.drawImage(icon, x+2,y, 48, 48, null);
		}
	}

	public boolean isGround() {
		return ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	

}
