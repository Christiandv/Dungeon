package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Thing {
	protected int x;
	protected int y;

	// for collisions
	protected boolean solid;

	// remembers if it was ever revealed so fog works
	protected boolean wasRevealed = false;

	protected BufferedImage icon;
	protected BufferedImage fog;
	protected boolean revealed;

	// dep = dependent
	// coords of the tile that must be clear to be revealed
	protected int depX = 0;
	protected int depY = 0;
	


	// used because we don't have classes for everything
	// so I can't use "instanceof"

	public Thing() {
		try {
			this.fog = ImageIO.read(new File("fog1.png"));
			this.icon = ImageIO.read(new File("placeHolder.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = 0;
		this.y = 0;
		this.solid = false;
		this.revealed = false;

	}

	// targetImage is a String for the name of the file it should use
	public Thing(int x, int y, boolean solid, BufferedImage icon,
			BufferedImage fog) {
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.revealed = false;

		this.icon = icon;
		this.fog = fog;
	}

	public void draw(Graphics g, int offX, int offY) {
		if (revealed) {
			g.drawImage(icon, (x - offX) * 32, (y - offY) * 32, 32, 32, null);
			
		} else if (wasRevealed) {
			// only draws the fog on it if it isn't currently revealed
			// but was once
			g.drawImage(icon, (x - offX) * 32, (y - offY) * 32, 32, 32, null);
			g.drawImage(fog, (x - offX) * 32, (y - offY) * 32, 32, 32, null);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public void setIcon(String targetImage) {
		try {
			icon = ImageIO.read(new File(targetImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		// remembers if it was revealed
		if (revealed)
			wasRevealed = true;
		this.revealed = revealed;
	}

	// figures out which other tile it is dependent on
	public void setDep(int pX, int pY) {
		// difference in x and y
		int diffX = pX - x;
		int diffY = pY - y;

		// if horizontal
		if (diffX == 0) {
			depX = x;
			depY = y + (diffY / Math.abs(diffY));
			// if vertical
		} else if (diffY == 0) {
			depX = x + (diffX / Math.abs(diffX));
			depY = y;

		} else {
			// if the difference in the differences is less than 4
			if (Math.abs(Math.abs(diffX) - Math.abs(diffY)) <= 3) {
				// dependent of diagonal
				depX = x + (diffX / Math.abs(diffX));
				depY = y + (diffY / Math.abs(diffY));
			} else {
				if (Math.abs(diffX) > Math.abs(diffY)) {
					// dependent on horizontal tile towards player
					depX = x + (diffX / Math.abs(diffX));
					depY = y;
				} else {
					// " " but vertical
					depX = x;
					depY = y + (diffY / Math.abs(diffY));
				}
			}
		}

	}

	public int getDepX() {
		return depX;
	}

	public int getDepY() {
		return depY;
	}

	public void setDepX(int depX) {
		this.depX = depX;
	}

	public void setDepY(int depY) {
		this.depY = depY;
	}

	public boolean isWasRevealed() {
		return wasRevealed;
	}

	public int getDistance(Thing t) {
		return Math.abs(t.getX() - this.getX())
				+ Math.abs(t.getY() - this.getY());
	}

	public boolean inBounds(Point p, int offX, int offY) {
		Rectangle r = new Rectangle((x - offX) * 32, (y - offY) * 32, 32, 32);
		if (r.contains(p))
			return true;
		return false;
	}

	
}
