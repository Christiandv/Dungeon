package Map;

import java.awt.image.BufferedImage;

import Game.Thing;

public class Wall extends Thing {
	public Wall(int x, int y, BufferedImage icon, BufferedImage fog) {
		super(x, y, true, icon, fog);
	}
}
