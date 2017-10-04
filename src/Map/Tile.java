package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.Thing;

public class Tile extends Thing {
	public Tile(int x, int y, BufferedImage icon, BufferedImage fog) {
		super(x, y, false, icon, fog);
	}
	public Tile(int x, int y){
		BufferedImage icon = null;
		BufferedImage fog = null;
		try { // import every pic needed in window here
			icon = ImageIO.read(new File("tile.png"));
			fog = ImageIO.read(new File("fog1.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.solid = false;
		this.revealed = false;
		
		this.icon = icon;
		this.fog = fog;
	}
}
