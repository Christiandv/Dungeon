package Map;

import java.awt.image.BufferedImage;

import Game.Thing;


public class Stairs extends Thing {
	boolean up;
	int refX;
	int refY;
	int level;
	public Stairs(int x, int y,
			BufferedImage icon, BufferedImage fog, boolean up,int level){
		super(x,y,false,icon,fog);
		this.up = up;
		
		this.level = level;
	}
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public int getRefX() {
		return refX;
	}
	public void setRefX(int refX) {
		this.refX = refX;
	}
	public int getRefY() {
		return refY;
	}
	public void setRefY(int refY) {
		this.refY = refY;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
