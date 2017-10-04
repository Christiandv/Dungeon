package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Items.Item;

public class Rigid {
	Item[][] field;
	int posX;
	int posY;
	BufferedImage inventSlot;

	public Rigid(int posX, int posY, int length, int height,BufferedImage inventSlot) {
		
		field = new Item[length][height];

		this.posX = posX;
		this.posY = posY;
		this.inventSlot = inventSlot;
	}

	public void addItem(Item c, int x, int y) {
		if (isOpen(x, y)) {
			c.setX(posX + 4 + x * 60);
			c.setY(posY + 4 + y * 60);

			field[x][y] = c;
		}
	}

	public void addItem(Item c, Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		if (isOpen(x, y)) {
			c.setX(posX + 4 + x * 60);
			c.setY(posY + 4 + y * 60);
			field[x][y] = c;
		}
	}

	public Item getItem(int x, int y) {
		return field[x][y];
	}

	public Item getItem(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		return field[x][y];
	}

	public void clearSpot(int x, int y) {
		field[x][y] = null;
	}

	public void clearSpot(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		field[x][y] = null;
	}

	public Item removeItem(int x, int y) {
		Item c = getItem(x, y);
		clearSpot(x, y);
		return c;
	}

	public Item removeItem(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		Item c = getItem(x, y);
		clearSpot(x, y);
		return c;
	}

	public boolean isOpen(int x, int y) {

		return (field[x][y] == null);
	}

	public boolean isOpen(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		return (field[x][y] == null);
	}

	public void draw(Graphics gi) {
		gi.setColor(Color.yellow);
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[0].length; y++) {

				gi.drawImage(inventSlot, (posX - 0) + (x * 60), (posY + 0)
						+ (y * 60), 60, 60, null);
				if (field[x][y] != null) {
					field[x][y].draw(gi, 0, 0);
				}
			}
		}
	}

	public boolean withinBounds(int x, int y) {
		Rectangle bounding = new Rectangle(posX, posY, 60 * field.length,
				60 * field[0].length);
		if (bounding.contains(x, y))
			return true;
		else
			return false;
	}

	public boolean withinBounds(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();
		Rectangle bounding = new Rectangle(posX, posY, 60 * field.length,
				60 * field[0].length);
		if (bounding.contains(x, y))
			return true;
		else
			return false;
	}

	public Point posInGrid(int x, int y) {// returns grid pos for given point in
											// form of a point
		// ONLY USE IF YOU CHECKED FOR BOUNDS
		ArrayList<Rectangle> boundings = new ArrayList<Rectangle>();
		for (int X = 0; X < field.length; X++) {
			for (int Y = 0; Y < field[0].length; Y++) {
				boundings.add(new Rectangle(posX + (X * 60), posY + (Y * 60),
						60, 60));
			}
		}
		for (Rectangle r : boundings) {
			if (r.contains(x, y)) {
				return new Point((int) ((r.getX() - posX) / 60),
						(int) ((r.getY() - posY) / 60));
			}
		}
		return null;
	}

	public Point posInGrid(Point p) {// returns grid pos for given point in
		// form of a point
		// ONLY USE IF YOU CHECKED FOR BOUNDS
		int x = (int) p.getX();
		int y = (int) p.getY();
		ArrayList<Rectangle> boundings = new ArrayList<Rectangle>();
		for (int X = 0; X < field.length; X++) {
			for (int Y = 0; Y < field[0].length; Y++) {
				boundings.add(new Rectangle(posX + (X * 60), posY + (Y * 60),
						60, 60));
			}
		}
		for (Rectangle r : boundings) {
			if (r.contains(x, y)) {
				return new Point((int) ((r.getX() - posX) / 60),
						(int) ((r.getY() - posY) / 60));
			}
		}
		return null;
	}

	public boolean placeItem(Item c, int x, int y) {// atempts o place the given
													// card
		if (isOpen(x, y)) {

			c.setX(posX + 4 + (x * 60));
			c.setY(posY + 4 + (y * 60));// change placement to center

			field[x][y] = c;
			return true;
		}
		return false;
	}

	public boolean placeItem(Item c, Point p) {// atempts o place the given card
		int x = (int) p.getX();
		int y = (int) p.getY();

		if (isOpen(x, y)) {

			c.setX(posX + 4 + (x * 60));
			c.setY(posY + 4 + (y * 60));// change placement to center

			field[x][y] = c;
			return true;
		}
		return false;

	}

	public Point getFirstOpen() {
		for (int y = 0; y < field[0].length; y++) {
			for (int x = 0; x < field.length; x++) {

				if (isOpen(x, y)) {
					return new Point(x, y);
				}
			}

		}
		return null;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

}
