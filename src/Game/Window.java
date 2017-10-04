package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

import Creatures.Corpse;
import Creatures.Enemy;
import Creatures.Player;
import Items.Armor;
import Items.Elements;
import Items.Item;
import Items.Weapon;
//import Items.Weapons.Swords.BronzeSword;
import Map.Chest;
import Map.Stairs;
import Map.TerrainGen;
import Map.Tile;
import Map.Wall;

//Made by Christian Vaughan

public class Window extends JFrame implements Elements {
	private int width = 1260;// Actual map goes to 960
	private int height = 480;
	// /////////////////////// key presses
	private boolean up = false;
	private boolean down = false;
	private boolean right = false;
	private boolean left = false;
	private boolean wait = false;
	private boolean enter = false;
	private boolean enemyInDaWay;
	private int offX;
	private int offY;

	// ///////////////////////
	private BufferedImage bi = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB); // buffered image for whole screen
	private BufferedImage background;
	Graphics gi = bi.createGraphics();
	// ///////////////////////
	private Player player;
	private boolean levelUp = false;
	private boolean lose = false;
	private boolean clicking = false;
	private Rigid inventory;
	private Rigid equipedWeapon;
	private Rigid equipedArmor;
	private Item currentItem = null;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Corpse> corpses = new ArrayList<Corpse>();

	private BufferedImage playerIcon;
	private BufferedImage fog;
	private BufferedImage lvUp;
	private BufferedImage placeHolder;
	private ArrayList<BufferedImage> enemyPics;
	private ArrayList<BufferedImage> weaponImages;
	private ArrayList<BufferedImage> armorImages;
	private BufferedImage loss;
	private ArrayList<BufferedImage> popupBacking;
	private ArrayList<BufferedImage> inventBacking;
	private boolean didMove = true;

	private ArrayList<Point> hittable = new ArrayList<Point>();
	private BufferedImage redHighlight;
	/*
	 * holds everything about the player. Make new class when stuff gets
	 * complicated
	 */
	// ///////////////////////

	private ArrayList<Thing[][]> map = new ArrayList<Thing[][]>();

	private int currentLevel = 0;

	/*
	 * holds map.
	 */
	private Thing checkMove = new Thing(); /*
											 * used for checking movement of
											 * player
											 */

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<DamageIcon> popups = new ArrayList<DamageIcon>();

	public static void main(String[] args) { // control+shift+f for format
		new Window();
		/*
		
		 */
	}

	@SuppressWarnings("unchecked")
	public Window() {

		Timer it = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateInventory();
			}

		});
		Timer gt = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				repaint();
			}

		});
		map = new TerrainGen().getMap();

		this.setSize(width + 18, height + 38);// yay borders
		this.setLocation(2, 6);// don't question me
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // no troll please
		this.setResizable(false);
		enemyPics = new ArrayList();
		popupBacking = new ArrayList<BufferedImage>();
		weaponImages = new ArrayList<BufferedImage>();
		armorImages = new ArrayList<BufferedImage>();
		inventBacking = new ArrayList<BufferedImage>();
		try { // import every pic needed in window here

			background = ImageIO.read(new File("Background.png"));
			playerIcon = ImageIO.read(new File("knight.PNG"));
			fog = ImageIO.read(new File("fog1.png"));
			placeHolder = ImageIO.read(new File("placeHolder.png"));
			enemyPics.add(ImageIO.read(new File("slime.png")));
			enemyPics.add(ImageIO.read(new File("kobold.png")));
			enemyPics.add(ImageIO.read(new File("imp.png")));
			enemyPics.add(ImageIO.read(new File("Dog.png")));
			loss = ImageIO.read(new File("lossScreen1.png"));
			lvUp = ImageIO.read(new File("LvUp.png"));
			popupBacking.add(ImageIO.read(new File("DamageMarker.png")));
			popupBacking
					.add(ImageIO.read(new File("DamageMarkerTwoDigit.png")));
			inventBacking.add(ImageIO.read(new File("invent.png")));
			// inventBacking.add(ImageIO.read(new File("inventWeap.png")));
			// inventBacking.add(ImageIO.read(new File("inventArmor.png")));
			armorImages.add(ImageIO.read(new File("Armor1.png")));
			weaponImages.add(ImageIO.read(new File("Sword1.png")));
			weaponImages.add(ImageIO.read(new File("Sword2.png")));

			redHighlight = ImageIO.read(new File("redHighlight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		inventory = new Rigid(960, 240, 5, 4, inventBacking.get(0));
		equipedWeapon = new Rigid(960, 120, 1, 1, inventBacking.get(0));
		equipedArmor = new Rigid(960, 180, 1, 1, inventBacking.get(0));

	//	inventory.addItem(new BronzeSword(0, 0, weaponImages.get(0), fog, 90,
	//			"bronzie the bear"), 0, 0);
		inventory.addItem(new Armor(1, 0, armorImages.get(0), fog, 80, "derp"),
				1, 0);
		inventory.addItem(new Weapon(1, 1, 10, 10, 2, 2, 1,
				weaponImages.get(1), fog, 80, "derp"), 1, 1);
		inventory.addItem(new Item(1, 3, enemyPics.get(1), fog, false), 1, 3);

		int holdX;
		int holdY;
		int holdLevel = 0;
		for (int i = 0; i < 30; i++) {
			// holdLevel = (int) (Math.random() * 20);
			holdX = (int) (Math.random() * 60);
			holdY = (int) (Math.random() * 30);
			if (map.get(holdLevel)[holdX][holdY] instanceof Tile) {
				if (Math.random() < .5) {
					enemies.add(new Enemy(holdX, holdY, enemyPics.get(0), fog,
							popupBacking, holdLevel, 22, new Damage(3, true,
									ACID), 1, 7, 2));// slime
				} else {
					enemies.add(new Enemy(holdX, holdY, enemyPics.get(1), fog,
							popupBacking, holdLevel, 14, new Damage(3, true,
									NONE), 1, 13, 5));// kobold
				}

			}
		}
		holdLevel = 1;
		for (int i = 0; i < 30; i++) {
			// holdLevel = (int) (Math.random() * 20);
			holdX = (int) (Math.random() * 60);
			holdY = (int) (Math.random() * 30);
			if (map.get(holdLevel)[holdX][holdY] instanceof Tile) {
				double spawn = Math.random();
				if (spawn < .3) {
					enemies.add(new Enemy(holdX, holdY, enemyPics.get(0), fog,
							popupBacking, holdLevel, 22, new Damage(3, true,
									ACID), 1, 7, 2));// slime
				} else if (spawn < .7) {
					enemies.add(new Enemy(holdX, holdY, enemyPics.get(1), fog,
							popupBacking, holdLevel, 14, new Damage(7, true,
									NONE), 1, 13, 5));// kobold
				} else {
					enemies.add(new Enemy(holdX, holdY, enemyPics.get(2), fog,
							popupBacking, holdLevel, 20, new Damage(11, false,
									FIRE), 1, 25, 10));// imp
				}

			}
		}
		holdLevel = 2;
		for (int i = 0; i < 20; i++) {
			// holdLevel = (int) (Math.random() * 20);
			holdX = (int) (Math.random() * 60);
			holdY = (int) (Math.random() * 30);
			if (map.get(holdLevel)[holdX][holdY] instanceof Tile) {
				enemies.add(new Enemy(holdX, holdY, enemyPics.get(2), fog,
						popupBacking, holdLevel, 20,
						new Damage(11, false, FIRE), 1, 25, 10));// imp
			}
		}
		boolean restart = true;
		while (restart) {
			restart = false;
			holdX = (int) (Math.random() * 60);
			holdY = (int) (Math.random() * 30);
			if (map.get(holdLevel)[holdX][holdY] instanceof Tile) {
				enemies.add(new Enemy(holdX, holdY, enemyPics.get(3), fog,
						popupBacking, holdLevel, 50,
						new Damage(11, false, DARK), 1, 100, 10));
			} else {
				restart = true;
			}
		}
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) { // what happens when
														// clicked
				// figures out which tile of map you clicked on:

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// has something to do with hovering?

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent mouse) {
				// TODO Auto-generated method stub
				Point m = new Point(mouse.getX() - 8, mouse.getY() - 29);
				if (mouse.getButton() == (MouseEvent.BUTTON3)) {

					for (Enemy e : enemies) {
						e.setExamined(false);
						if (e.inBounds(m, offX, offY)) {
							e.setExamined(true);
						}

					}
				} else {
					clicking = true;
					if (currentItem == null) {// if empty mouse
						if (inventory.withinBounds(m)) {
							Point p = inventory.posInGrid(m);
							if (!inventory.isOpen(p)) {// if occupied
								Item hold = inventory.removeItem(p);
								currentItem = hold;
								clicking = true;
							}
						}
						if (equipedWeapon.withinBounds(m)) {
							Point p = equipedWeapon.posInGrid(m);
							if (!equipedWeapon.isOpen(p)) {// if occupied
								Item hold = equipedWeapon.removeItem(p);
								player.setWeapon(null);
								currentItem = hold;
								clicking = true;
							}
						}
						if (equipedArmor.withinBounds(m)) {
							Point p = equipedArmor.posInGrid(m);
							if (!equipedArmor.isOpen(p)) {// if occupied
								Item hold = equipedArmor.removeItem(p);
								player.setArmor(null);
								currentItem = hold;
								clicking = true;
							}
						}
						for (Enemy e : enemies) {
							// click to attack
							if (e.inBounds(m, offX, offY))
								if (e.getDistance(player) <= player.getRange())
									if (e.isRevealed()) {
										System.out.println("HIT");
										e.setHealth(player.getAttack());
										wait = true;
										updatePhysics();
										break;
									}

						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent mouse) {// REMEMBER TO
															// ADD/REMOVE THE
															// CURRENT ITEM WHEN
															// IT ENTERS/EXITS
															// THE FIELD
				// TODO Auto-generated method stub
				clicking = false;
				Point m = new Point(mouse.getX() - 8, mouse.getY() - 29);
				if (currentItem != null) {
					if (inventory.withinBounds(m)) {
						if (inventory.isOpen(inventory.posInGrid(m))) {
							inventory.placeItem(currentItem,
									inventory.posInGrid(m));
							clicking = false;

							currentItem = null;
						} else {
							Item hold = inventory.getItem(inventory
									.posInGrid(m));
							inventory.removeItem(inventory.posInGrid(m));
							inventory.addItem(currentItem,
									inventory.posInGrid(m));
							currentItem = hold;
						}

					}
					if (equipedWeapon.withinBounds(m)
							&& currentItem instanceof Weapon) {
						if (equipedWeapon.isOpen(equipedWeapon.posInGrid(m))) {
							equipedWeapon.placeItem(currentItem,
									equipedWeapon.posInGrid(m));
							clicking = false;
							player.setWeapon((Weapon) currentItem);
							currentItem = null;

						} else {
							Item hold = equipedWeapon.getItem(equipedWeapon
									.posInGrid(m));
							equipedWeapon.removeItem(equipedWeapon.posInGrid(m));
							equipedWeapon.addItem(currentItem,
									equipedWeapon.posInGrid(m));
							player.setWeapon((Weapon) currentItem);
							currentItem = hold;
						}

					}
					if (equipedArmor.withinBounds(m)
							&& currentItem instanceof Armor) {
						if (equipedArmor.isOpen(equipedArmor.posInGrid(m))) {
							equipedArmor.placeItem(currentItem,
									equipedArmor.posInGrid(m));
							clicking = false;
							player.setArmor((Armor) currentItem);
							currentItem = null;

						} else {
							Item hold = equipedArmor.getItem(equipedArmor
									.posInGrid(m));
							equipedArmor.removeItem(equipedArmor.posInGrid(m));
							equipedArmor.addItem(currentItem,
									equipedArmor.posInGrid(m));
							player.setArmor((Armor) currentItem);
							currentItem = hold;
						}

					}
				}
			}

		});
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// what happens when a key is pressed
				if (e.getKeyCode() == KeyEvent.VK_W
						|| (e.getKeyCode() == KeyEvent.VK_UP)) {
					up = true;
					down = false;
					left = false;
					right = false;
					wait = false;
					// done so it is turn based
					updatePhysics();
					repaint();
					// is kinda weird, replace?
				} else if (e.getKeyCode() == KeyEvent.VK_S
						|| (e.getKeyCode() == KeyEvent.VK_DOWN)) {
					up = false;
					down = true;
					left = false;
					right = false;
					wait = false;
					updatePhysics();
					repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_D
						|| (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
					up = false;
					down = false;
					left = false;
					right = true;
					wait = false;
					updatePhysics();
					repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_A
						|| (e.getKeyCode() == KeyEvent.VK_LEFT)) {
					up = false;
					down = false;
					left = true;
					right = false;
					wait = false;
					updatePhysics();
					repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_E
						|| (e.getKeyCode() == KeyEvent.VK_SPACE)
						|| (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					enter = true;
					up = false;
					down = false;
					left = false;
					right = false;
					if (map.get(currentLevel)[player.getX()][player.getY()] instanceof Stairs) {
						Stairs hold = (Stairs) map.get(currentLevel)[player
								.getX()][player.getY()];
						if (hold.isUp()) {
							currentLevel--;
						} else {
							currentLevel++;
						}
						player.setX(hold.getRefX());
						player.setY(hold.getRefY());
						System.out.println("current Level: " + currentLevel);
					} else if (map.get(currentLevel)[player.getX()][player
							.getY()] instanceof Chest) {
						((Chest) map.get(currentLevel)[player.getX()][player
								.getY()]).setOpenned(true);

					} else if (map.get(currentLevel)[player.getX()][player
							.getY()] instanceof Item) {
						if (inventory.getFirstOpen() != null) {
							Item i = (Item) map.get(currentLevel)[player.getX()][player
									.getY()];
							i.setGround(false);
							inventory.addItem(i, inventory.getFirstOpen());
							map.get(currentLevel)[player.getX()][player.getY()] = new Tile(
									player.getX(), player.getY());
						}

					}

					updatePhysics();
					repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_W) {
					up = false;
					wait = false;
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					down = false;
					wait = false;
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					right = false;
					wait = false;
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					left = false;
					wait = false;
				} else if (e.getKeyCode() == KeyEvent.VK_E) {
					enter = false;
					wait = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		this.setVisible(true);

		// interprets TerrainGen

		outerloop: for (int z = 0; z < 60; z++) {
			for (int x = 0; x < 30; x++) {
				if (map.get(currentLevel)[z][x] instanceof Tile) {
					// finds first open spot on level and places player
					player = new Player(z, x, playerIcon, fog);
					player.setRevealed(true);
					offX = z - 15;
					offY = x - 7;
					break outerloop;

				}
			}
		}

		// initialize area for other things:

		// testing area:

		// code that reveals everything:

		// boolean lightItUp = false;
		// for (int z = 0; z <= 59; z++) {
		// for (int x = 0; x <= 29; x++) {
		//
		// if (map[z][x] != null) {
		// lightItUp = false;
		// for (int i = z - 1; i <= z + 1; i++) {
		// for (int j = x - 1; j <= x + 1; j++) {
		// if (j >= 0 && i >= 0 && i < 60 && j < 30) {
		//
		// if (map[i][j].isSolid() == false) {
		// lightItUp = true;
		// }
		// }
		// }
		// }
		// if (lightItUp == true) {
		// map[z][x].setRevealed(true);
		// }
		// }
		//
		// }
		// }

		// starts game, otherwise button press would be required to start
		it.start();
		gt.start();
		updatePhysics();
		didMove = true;

	}

	@Override
	public void paint(Graphics g) {

		// draw everything to gi so double buffered
		if (!lose) {

			gi.drawImage(background, 0, 0, 1260, 480, null); // draws background
			gi.setColor(Color.white);
			gi.fillRect(980, 20, 260, 15);
			if (((double) player.getHealth() / (double) player.getHealthMax()) > .5) { // more
																						// than
																						// 1/2
																						// health
				gi.setColor(new Color(255 - (int) ((255 * (((double) player
						.getHealth() / (double) player.getHealthMax()) - .5))),
						255, 0));
			} else {

				gi.setColor(new Color(255, (int) (255 * ((double) 2
						* player.getHealth() / player.getHealthMax())), 0));
			}

			gi.fillRect(980, 20,
					(int) (260 * ((double) player.getHealth() / (double) player
							.getHealthMax())), 15);
			offX = player.getX() - 15;
			offY = player.getY() - 7;
			for (int x = offX; x < offX + 30; x++) {
				for (int y = offY; y < offY + 30; y++) {
					if (x >= 0 && y >= 0 && x < 60 && y < 30)
						if (map.get(currentLevel)[x][y] != null)
							map.get(currentLevel)[x][y].draw(gi, offX, offY);
				}
			}
			for (Enemy e : enemies) {
				if (e.getLevel() == currentLevel) {
					if (e.getX() < offX + 30 && e.getX() > offX - 30
							&& e.getY() < offY + 30)
						e.draw(gi, offX, offY);
				}
			}

			if (player != null) {
				player.draw(gi, offX, offY);
				if (levelUp) {
					gi.drawImage(lvUp, (player.getX() - offX) * 32,
							(player.getY() - offY) * 32, 32, 32, null);
				}
			}
			for (int x = 0; x < 60; x++) {
				for (int y = 0; y < 30; y++) {
					if (map.get(currentLevel)[x][y].isWasRevealed()) {
						if (map.get(currentLevel)[x][y] instanceof Tile)
							gi.setColor(Color.gray);
						if (map.get(currentLevel)[x][y] instanceof Wall)
							gi.setColor(Color.darkGray);
						if (map.get(currentLevel)[x][y] instanceof Stairs)
							gi.setColor(Color.red);
						if (map.get(currentLevel)[x][y] instanceof Chest) {
							if (!((Chest) map.get(currentLevel)[x][y])
									.isOpened()) {
								gi.setColor(new Color(255, 255, 0));
							} else {
								gi.setColor(new Color(200, 200, 0));
							}
						}
						gi.fillRect((x * 2) + 840, (y * 2) + 10, 2, 2);
					}
				}
			}

			gi.setColor(Color.green);
			gi.fillRect((player.getX() * 2) + 840, (player.getY() * 2) + 10, 2,
					2);

			// move detection to somewhere else!!!
			hittable.clear();

			for (int x = player.getX() - player.getRange(); x <= player.getX()
					+ player.getRange(); x++) {
				// counts from left to right
				for (int y = (player.getY() - player.getRange())
						+ Math.abs(player.getX() - x); y <= (player.getY() + (player
						.getRange() - Math.abs(player.getX() - x))); y++) {
					if (x >= 0 && y >= 0 && x < 60 && y < 30)// CHANGE IF MAP IS
																// MADE BIGGER
						if (map.get(currentLevel)[x][y] != null)
							if (map.get(currentLevel)[x][y].isRevealed() == true
									&& !(map.get(currentLevel)[x][y] instanceof Wall))
								hittable.add(new Point(x, y));
				}

			}

			for (Point p : hittable) {
				gi.drawImage(redHighlight, ((int) p.getX() - offX) * 32,
						((int) p.getY() - offY) * 32, 32, 32, null);
			}
			for (Enemy e : enemies) {
				if (e.isExamined())
					e.examine(gi, offX, offY, player.getLevel());
				// e.setExamined(false);
			}
			// for(Corpse c: corpses){
			// c.revealed = true;
			// c.draw(gi,offX,offY);
			// System.out.println("dersasaassasasa");
			// }
			// for (DamageIcon d : popups) {
			// d.draw(gi, offX, offY);
			// }
			// for (int i = 0; i < popups.size(); i++) {
			//
			// popups.get(i).countdown();
			// if (popups.get(i).getCountdown() < 0) {
			// popups.remove(i);
			//
			// }
			// }

		} else {

			gi.drawImage(loss, 0, 0, 1260, 480, null);
			gi.setColor(Color.white);
			gi.fillRect(980, 20, 260, 15);
		}

		gi.setColor(Color.black);
		gi.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		gi.drawString("Lv: " + player.getLevel(), 980, 10);
		gi.drawString("Exp: " + player.getExp() + "/" + player.getLevel() * 20,
				980, 20);
		gi.drawString(
				"Hp: " + player.getHealth() + "/" + player.getHealthMax(),
				1200, 45);
		inventory.draw(gi);
		equipedWeapon.draw(gi);
		equipedArmor.draw(gi);
		if (currentItem != null)
			currentItem.draw(gi, offX, offY);

		g.drawImage(bi, 8, 29, width, height, null); /*
													 * draws buffered image to
													 * screen
													 */
	}

	public void updatePhysics() {

		enemyInDaWay = false;
		if (!lose) {
			if (up) {

				// tells it what it is moving to
				checkMove = map.get(currentLevel)[player.getX()][player.getY() - 1];
				// checks for null so not out of bounds
				if (checkMove != null) {
					// yay collisions
					if (checkMove.isSolid() == false) {
						for (Enemy e : enemies) {
							if (e.getLevel() == currentLevel) {
								if (e.getX() == player.getX()
										&& e.getY() == player.getY() - 1) {
									enemyInDaWay = true;
									// enemies take damage
									e.setHealth(player.getAttack());

									wait = true;
									up = false;
								}
							}
						}
						if (!enemyInDaWay) {
							// moves
							player.setY(player.getY() - 1);
							wait = true;
							up = false;
						}
					}
				}
				didMove = true;
			}

			if (down) {
				checkMove = map.get(currentLevel)[player.getX()][player.getY() + 1];
				if (checkMove != null) {
					if (checkMove.isSolid() == false) {
						for (Enemy e : enemies) {
							if (e.getLevel() == currentLevel) {
								if (e.getX() == player.getX()
										&& e.getY() == player.getY() + 1) {
									enemyInDaWay = true;
									// enemies take damage
									e.setHealth(player.getAttack());

									wait = true;
									down = false;
								}
							}
						}
						if (!enemyInDaWay) {
							player.setY(player.getY() + 1);
							wait = true;
							down = false;
						}
					}
				}
				didMove = true;

			}
			if (left) {

				checkMove = map.get(currentLevel)[player.getX() - 1][player
						.getY()];
				if (checkMove != null) {
					if (checkMove.isSolid() == false) {
						for (Enemy e : enemies) {
							if (e.getLevel() == currentLevel) {
								if (e.getX() == player.getX() - 1
										&& e.getY() == player.getY()) {
									enemyInDaWay = true;
									// enemies take damage
									e.setHealth(player.getAttack());

									wait = true;
									left = false;
								}
							}
						}
						if (!enemyInDaWay) {

							player.setX(player.getX() - 1);
							wait = true;
							left = false;
						}
					}
				}
				didMove = true;

			}
			if (right) {
				checkMove = map.get(currentLevel)[player.getX() + 1][player
						.getY()];
				if (checkMove != null) {
					if (checkMove.isSolid() == false) {
						for (Enemy e : enemies) {
							if (e.getLevel() == currentLevel) {
								if (e.getX() == player.getX() + 1
										&& e.getY() == player.getY()) {
									enemyInDaWay = true;
									// enemies take damage
									e.setHealth(player.getAttack());

									wait = true;
									right = false;
								}
							}
						}
						if (!enemyInDaWay) {
							player.setX(player.getX() + 1);
							wait = true;
							right = false;
						}
					}
				}
				didMove = true;
			}
			if (enter) {// when you press e
				wait = true;
				enter = false;
				didMove = true;
			}

			// yay not walking off the edge
			if (player.getX() > 59) {
				player.setX(59);
			}
			if (player.getX() < 0) {
				player.setX(0);
			}
			if (player.getY() > 29) {
				player.setY(29);
			}
			if (player.getY() < 0) {
				player.setY(0);
			}

			if (wait) {// enemy turn

				Enemy e;
				int diffX;
				int diffY;
				int nextX;
				int nextY;
				boolean blocked = false;
				for (int i = 0; i < enemies.size(); i++) {// checks for death,
															// doesn't work.
															// skips some.
															// Replace with
															// Iterator
					e = enemies.get(i);
					e.setExamined(false);
					if (e.getHealth() <= 0) {
						player.addExp(e.getExp());
						// corpses.add(new Corpse(e.getX(), e.getY(),
						// e.getIcon(),
						// fog, new Weapon(1, 2, 6, 1, 2, 2, 1,
						// weaponImages.get(0), fog, 10, "derp")));
						enemies.remove(i);
					}
				}
				player.update();
				if (player.isLeveled()) {
					levelUp = true;
				} else {
					levelUp = false;
				}
				for (int i = 0; i < enemies.size(); i++) {// moves
					e = enemies.get(i);
					if (e.getLevel() == currentLevel) {

						diffX = player.getX() - e.getX();
						diffY = player.getY() - e.getY();
						if (Math.abs(diffX) <= 15 && Math.abs(diffY) <= 15) {
							if (Math.abs(diffX) > Math.abs(diffY)) {
								nextX = e.getX() + (diffX / Math.abs(diffX));
								nextY = e.getY();
								if (!map.get(currentLevel)[nextX][nextY]
										.isSolid()) {
									if (nextX == player.getX()
											&& nextY == player.getY()) {
										player.setHealth(e.getAttack());
										popups.add(new DamageIcon(
												player.getX(), player.getY(), e
														.getAttack()
														.getAmount(),
												popupBacking));
										// deal damage
									} else {
										blocked = false;
										for (Enemy E : enemies) {
											if (E.getLevel() == currentLevel) {
												if (E.getX() == nextX
														&& E.getY() == nextY)
													blocked = true;
											}
										}
										if (!blocked)
											e.setX(nextX);
									}

								} else {
									if (diffY != 0) {
										nextX = e.getX();
										nextY = e.getY()
												+ (diffY / Math.abs(diffY));
										if (!map.get(currentLevel)[nextX][nextY]
												.isSolid()) {
											if (nextX == player.getX()
													&& nextY == player.getY()) {
												player.setHealth(e.getAttack());
												popups.add(new DamageIcon(
														player.getX(), player
																.getY(), e
																.getAttack()
																.getAmount(),
														popupBacking));
											} else {
												blocked = false;
												for (Enemy E : enemies) {
													if (E.getLevel() == currentLevel) {
														if (E.getX() == nextX
																&& E.getY() == nextY)
															blocked = true;
													}
												}
												if (!blocked)
													e.setY(nextY);
											}
										}
									}
								}
							} else if (Math.abs(diffX) < Math.abs(diffY)) {
								nextX = e.getX();
								nextY = e.getY() + (diffY / Math.abs(diffY));
								if (!map.get(currentLevel)[nextX][nextY]
										.isSolid()) {

									if (nextX == player.getX()
											&& nextY == player.getY()) {
										player.setHealth(e.getAttack());
										popups.add(new DamageIcon(
												player.getX(), player.getY(), e
														.getAttack()
														.getAmount(),
												popupBacking));
									} else {
										blocked = false;
										for (Enemy E : enemies) {
											if (E.getLevel() == currentLevel) {
												if (E.getX() == nextX
														&& E.getY() == nextY)
													blocked = true;
											}
										}
										if (!blocked)
											e.setY(nextY);
									}
								} else {
									if (diffX != 0) {
										nextX = e.getX()
												+ (diffX / Math.abs(diffX));
										nextY = e.getY();
										if (!map.get(currentLevel)[nextX][nextY]
												.isSolid()) {
											if (nextX == player.getX()
													&& nextY == player.getY()) {
												player.setHealth(e.getAttack());
												popups.add(new DamageIcon(
														player.getX(), player
																.getY(), e
																.getAttack()
																.getAmount(),
														popupBacking));
											} else {
												blocked = false;
												for (Enemy E : enemies) {
													if (E.getLevel() == currentLevel) {
														if (E.getX() == nextX
																&& E.getY() == nextY)
															blocked = true;
													}
												}
												if (!blocked)
													e.setX(nextX);
											}
										}
									}
								}
							} else {// equal
								if (Math.random() < .5) {
									nextX = e.getX();
									nextY = e.getY()
											+ (diffY / Math.abs(diffY));
									if (!map.get(currentLevel)[nextX][nextY]
											.isSolid()) {
										if (nextX == player.getX()
												&& nextY == player.getY()) {

										} else {
											blocked = false;
											for (Enemy E : enemies) {
												if (E.getLevel() == currentLevel) {
													if (E.getX() == nextX
															&& E.getY() == nextY)
														blocked = true;
												}
											}
											if (!blocked)
												e.setY(nextY);
										}
									}
								} else {
									nextX = e.getX()
											+ (diffX / Math.abs(diffX));
									nextY = e.getY();
									if (!map.get(currentLevel)[nextX][nextY]
											.isSolid()) {
										if (nextX == player.getX()
												&& nextY == player.getY()) {

										} else {
											blocked = false;
											for (Enemy E : enemies) {
												if (E.getLevel() == currentLevel) {
													if (E.getX() == nextX
															&& E.getY() == nextY)
														blocked = true;
												}
											}
											if (!blocked)
												e.setX(nextX);
										}
									}
								}
							}
						} else {// wander
							nextX = e.getX() + (int) (Math.random() * 3) - 1;
							nextY = e.getY() + (int) (Math.random() * 3) - 1;
							if (!map.get(currentLevel)[nextX][nextY].isSolid()) {
								blocked = false;
								for (Enemy E : enemies) {
									if (E.getLevel() == currentLevel) {
										if (E.getX() == nextX
												&& E.getY() == nextY)
											blocked = true;
									}
								}
								if (!blocked) {
									e.setX(nextX);
									e.setY(nextY);
								}
							}
						}
					} else {
						// wander
						nextX = e.getX() + (int) (Math.random() * 3) - 1;
						nextY = e.getY() + (int) (Math.random() * 3) - 1;
						if (!map.get(currentLevel)[nextX][nextY].isSolid()) {
							blocked = false;
							for (Enemy E : enemies) {
								if (E.getLevel() == currentLevel) {
									if (E.getX() == nextX && E.getY() == nextY)
										blocked = true;
								}
							}
							if (!blocked) {
								e.setX(nextX);
								e.setY(nextY);
							}
						}
					}
				}
			}

			// real vision code
			for (int i = 0; i < 60; i++) {
				for (int j = 0; j < 30; j++) {
					// covers everything
					map.get(currentLevel)[i][j].setRevealed(false);
				}
			}

			// reveals things near that you can see

			// goes through extra times to not leave any behind
			for (int i = 0; i < 10; i++)
				// 7 is vision range
				for (int z = player.getX() - 7; z <= player.getX() + 7; z++) {
					for (int x = player.getY() - 7; x <= player.getY() + 7; x++) {
						if (Math.abs(z - player.getX())
								+ Math.abs(x - player.getY()) <= 7)

							/*
							 * checks for out of bounds ... could also just
							 * check for null ... but, meh? does the same thing.
							 */
							if (z >= 0 && x >= 0 && z < 60 && x < 30) {

								if (z == player.getX() && x == player.getY()) {
									// always reveals tile below player
									map.get(currentLevel)[z][x]
											.setRevealed(true);
									map.get(currentLevel)[z][x].setDepX(z);
									map.get(currentLevel)[z][x].setDepY(x);

								} else {
									// finds tile that current one is dependent
									// on
									map.get(currentLevel)[z][x].setDep(
											player.getX(), player.getY());
									/*
									 * if said dependent tile is not a wall and
									 * is revealed, reveal it.
									 */
									if (!(map.get(currentLevel)[map
											.get(currentLevel)[z][x].getDepX()][map
											.get(currentLevel)[z][x].getDepY()] instanceof Wall)
											&& map.get(currentLevel)[map
													.get(currentLevel)[z][x]
													.getDepX()][map
													.get(currentLevel)[z][x]
													.getDepY()].isRevealed()) {
										// YAY FORMATTING IS DUMB ^^^
										map.get(currentLevel)[z][x]
												.setRevealed(true);
									}
								}
							}
					}
				}

			// deals with walls you wouldn't normally see
			boolean lightItUp = false;
			// same as above
			for (int z = player.getX() - 7; z <= player.getX() + 7; z++) {
				for (int x = player.getY() - 7; x <= player.getY() + 7; x++) {
					if (Math.abs(z - player.getX())
							+ Math.abs(x - player.getY()) <= 7)

						if (z >= 0 && x >= 0 && z < 60 && x < 30) {
							// aw snap now it is different!
							lightItUp = false;
							/*
							 * if selected pos of grid is a wall and is adj to a
							 * tile that is revealed, reveal it also. Yay
							 * hallways don't look as dumb!
							 */
							if (map.get(currentLevel)[z][x] instanceof Wall) {
								for (int i = z - 1; i <= z + 1; i++) {
									for (int j = x - 1; j <= x + 1; j++) {
										// checks for bounds
										if (j >= 0 && i >= 0 && i < 60
												&& j < 30) {

											if (!(map.get(currentLevel)[i][j] instanceof Wall)
													&& map.get(currentLevel)[i][j]
															.isRevealed())
												lightItUp = true;
										}
									}
								}
								if (lightItUp) {
									map.get(currentLevel)[z][x]
											.setRevealed(true);
								}
							}
						}
				}

			}
			for (Enemy e : enemies) {// vision for enemies
				if (map.get(currentLevel)[e.getX()][e.getY()].isRevealed()
						&& currentLevel == e.getLevel()) {
					e.setRevealed(true);
				} else {
					if (e.isRevealed()) {
						e.setOldX(e.getX());
						e.setOldY(e.getY());
					}
					e.setRevealed(false);
				}
				if (e.getLevel() != currentLevel) {
					e.setRevealed(false);
					e.setWasRevealed(false);
				}
			}
			// end of real vision code

			if (player.getHealth() <= 0) {
				lose = true;
			}
		}
	}

	public void updateInventory() {
		// if (!clicking && currentItem == null) {
		// if ((int) MouseInfo.getPointerInfo().getLocation().getX() < 960
		// && (int) MouseInfo.getPointerInfo().getLocation().getY() < 480) {//
		// stops
		// // out
		// // of
		// // bounds
		// // errors
		// // map.get(currentLevel)[(int)
		// // ((MouseInfo.getPointerInfo()
		//
		// Point m = new Point((int) (MouseInfo.getPointerInfo()
		// .getLocation().getX() - 8), (int) (MouseInfo
		// .getPointerInfo().getLocation().getY() - 29));
		// for (Enemy e : enemies) {
		// if (e.inBounds(m, offX, offY)) {
		// e.setExamined(true);
		// }
		//
		// }
		// }
		// }
		if (clicking && currentItem != null) {

			currentItem.setX((int) MouseInfo.getPointerInfo().getLocation()
					.getX() - 35);
			currentItem.setY((int) MouseInfo.getPointerInfo().getLocation()
					.getY() - 56);

		}

	}

}
