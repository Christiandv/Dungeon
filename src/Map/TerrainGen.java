package Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import Game.Thing;
import Items.Item;

public class TerrainGen {
	boolean intersect;
	private ArrayList<Thing[][]> map = new ArrayList<Thing[][]>();
	int[][] level = new int[60][30];
	ArrayList<Rect> rooms = new ArrayList();
	ArrayList<Hallway> halls = new ArrayList();
	ArrayList<Stairs> upStairs = new ArrayList();
	ArrayList<Stairs> downStairs = new ArrayList();
	BufferedImage fog;
	BufferedImage tile;
	BufferedImage chest;
	ArrayList<BufferedImage> openChests;
	ArrayList<BufferedImage> walls;
	BufferedImage upStair;
	BufferedImage downStair;
	int currentLevel = 0;

	public TerrainGen() {
		walls = new ArrayList<BufferedImage>();
		openChests = new ArrayList<BufferedImage>();
		try {
			fog = ImageIO.read(new File("fog1.png"));
			walls.add(ImageIO.read(new File("wall1.png")));
			walls.add(ImageIO.read(new File("wall2.png")));
			walls.add(ImageIO.read(new File("wall3.png")));
			walls.add(ImageIO.read(new File("wall4.png")));
			openChests.add(ImageIO.read(new File("OpenWoodenChest1.png")));
			openChests.add(ImageIO.read(new File("OpenWoodenChest2.png")));
			openChests.add(ImageIO.read(new File("OpenWoodenChest3.png")));
			openChests.add(ImageIO.read(new File("OpenWoodenChest4.png")));
			tile = ImageIO.read(new File("tile.png"));
			upStair = ImageIO.read(new File("upStair.png"));
			downStair = ImageIO.read(new File("downStair.png"));
			chest = ImageIO.read(new File("ClosedWoodenChest.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int f = 0; f < 20; f++) {
			currentLevel = f;
			map.add(genLevel());
		}
		for (int f = 0; f < 19; f++) {
			upStairs.get(f).setRefX(downStairs.get(f).getX());
			upStairs.get(f).setRefY(downStairs.get(f).getY());
			downStairs.get(f).setRefX(upStairs.get(f).getX());
			downStairs.get(f).setRefY(upStairs.get(f).getY());
			map.get(upStairs.get(f).getLevel())[upStairs.get(f).getX()][upStairs
					.get(f).getY()] = upStairs.get(f);
			map.get(downStairs.get(f).getLevel())[downStairs.get(f).getX()][downStairs
					.get(f).getY()] = downStairs.get(f);
		}
		int holdLevel;
		int holdX;
		int holdY;
		for (int i = 0; i < 100; i++) {
			holdLevel = (int) (Math.random() * 20);
			holdX = (int) (Math.random() * 60);
			holdY = (int) (Math.random() * 30);
			if (map.get(holdLevel)[holdX][holdY] instanceof Tile) {
				// map.get(holdLevel)[holdX][holdY] = new Chest(holdX, holdY,
				// chest, fog, openChests.get((int) (Math.random() * 4)),
				// holdLevel);
				map.get(holdLevel)[holdX][holdY] = new Item(holdX, holdY,
						chest, fog, true);
				System.out.println("level: " + holdLevel + ", X: " + holdX
						+ ", Y: " + holdY);
			}
		}
	}

	// returns closest Rect
	public Rect closest(Rect r) {
		Rect close = rooms.get(0);
		double dclose = 1000;
		double d = 0;

		for (Rect q : rooms) {
			if (q.equals(r)) {

			} else {

				d = Math.abs((r.midX - q.midX) + (r.midY - q.midY));
				if (d < dclose) {
					close = q;
					dclose = d;
				}
			}

		}
		return close;

	}

	// returns furthest
	public Rect furthest(Rect r) {
		Rect close = rooms.get(0);
		double dclose = 0;
		double d = 0;

		for (Rect q : rooms) {
			if (q.equals(r)) {

			} else {

				d = Math.abs((r.midX - q.midX) + (r.midY - q.midY));
				if (d > dclose) {
					close = q;
					dclose = d;
				}
			}

		}
		return close;

	}

	public Thing[][] genLevel() {

		level = new int[60][30];
		rooms = new ArrayList();
		halls = new ArrayList();
		rooms.add(new Rect());
		boolean possible = true;
		boolean restart = true;
		Rect hold = new Rect();
		// makes a number of rooms = num
		int numInt = 0;
		outerloop: for (int q = 0; q < (int) (Math.random() * 8) + 7; q++) {
			restart = true;
			// finds a Rect that doesn't overlap any others
			while (restart) {
				numInt++;
				restart = false;
				hold = new Rect();
				if (hold.area < 15 || hold.area > 110 || hold.getHeight() < 3
						|| hold.getLength() < 3)
					restart = true;
				for (Rect r : rooms) {
					if (r.intersect(hold)) {
						restart = true;
					}
				}
			}
			// adds it to the list
			rooms.add(hold);
			// does that whole thing 'num' times
			if (numInt > 1000) {
				System.out.println("break");
				break outerloop;

			}
		}

		for (Rect r : rooms) {

			for (int q = r.x + 1; q < r.x + r.length - 1; q++) {
				for (int w = r.y + 1; w < r.y + r.height - 1; w++) {
					level[q][w] = 0;
				}
			}

			for (int q = r.x; q < r.x + r.length; q++) {
				for (int w = r.y; w < r.y + r.height; w++) {
					level[q][w] = 1;
				}
			}

			Hallway n = new Hallway(r, closest(r));
			halls.add(n);
		}
		for (int i = 0; i < 3 && i < rooms.size(); i++) {
			halls.add(new Hallway(rooms.get(i), furthest(rooms.get(i))));
		}

		for (Hallway h : halls) {

			if (h.startX < h.endX)
				for (int x = h.startX; x < h.endX; x++) {
					level[x][h.startY] = 2;
				}
			if (h.startX > h.endX)
				for (int x = h.startX; x > h.endX; x--) {
					level[x][h.startY] = 2;
				}
			if (h.startY < h.endY)
				for (int y = h.startY; y < h.endY; y++) {
					level[h.endX][y] = 2;
				}
			if (h.startY > h.endY)
				for (int y = h.startY; y > h.endY; y--) {
					level[h.endX][y] = 2;
				}

		}

		Thing[][] holdLevel = new Thing[60][30];
		for (int z = 0; z < 60; z++) {
			for (int x = 0; x < 30; x++) {
				if (level[z][x] == 0) {
					holdLevel[z][x] = new Wall(z, x, walls.get((int) (Math
							.random() * 4)), fog);
				} else {
					holdLevel[z][x] = new Tile(z, x, tile, fog);
				}
			}

		}
		if (currentLevel != 19) {
			restart = true;
			while (restart) {// make downstair
				restart = false;
				int holdX = (int) (Math.random() * 60);
				int holdY = (int) (Math.random() * 30);
				if (holdLevel[holdX][holdY] instanceof Tile) {
					holdLevel[holdX][holdY] = new Stairs(holdX, holdY,
							downStair, fog, false, currentLevel);
					downStairs.add(new Stairs(holdX, holdY, downStair, fog,
							false, currentLevel));
				} else {
					restart = true;
				}
			}
		}
		if (currentLevel != 0) {
			restart = true;
			while (restart) {// make upstair
				restart = false;
				int holdX = (int) (Math.random() * 60);
				int holdY = (int) (Math.random() * 30);
				if (holdLevel[holdX][holdY] instanceof Tile) {
					holdLevel[holdX][holdY] = new Stairs(holdX, holdY, upStair,
							fog, true, currentLevel);
					upStairs.add(new Stairs(holdX, holdY, upStair, fog, true,
							currentLevel));
				} else {
					restart = true;
				}
			}
		}
		System.out.println("Loading Floor #" + (1 + map.size()) + " ... With: "
				+ rooms.size() + " Rooms.");
		return holdLevel;
	}

	public ArrayList getMap() {
		return map;
	}

}
