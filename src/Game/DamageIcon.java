package Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DamageIcon {
	private int x;
	private int y;
	private int randX;
	private int randY;
	private int amount;
	private ArrayList<BufferedImage> backing;
	private int countdown;

	public DamageIcon(int x, int y, int amount, ArrayList<BufferedImage> backing) {
		this.x = x;
		this.y = y;
		this.amount = amount;
		this.backing = backing;
		countdown = 10;
		if (amount < 10) {
			
		randX = (int) (Math.random() * 18)+6;
			randY = (int) (Math.random() * 16);

		
		} else if (amount < 100) {
			randX = (int) (Math.random() * 10)+6;
			randY= (int) (Math.random() * 16);
			
			
		} else {
		
			randX= (int) (Math.random() * 2)+6;
			randY= (int) (Math.random() * 16);
			
		
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getCountdown(){
		return countdown;
	}
	public void countdown(){
		countdown--;
	}

	public void draw(Graphics gi, int offX, int offY) {
		int X= 0;
		int Y = 0;
		String damage = "" + amount;
		gi.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
		if (amount < 10) {
		
			X = (x - offX) * 32 + randX;
			Y = ((y - offY) + 1) * 32 - randY;

			gi.drawImage(backing.get(0), X - 6, Y - 14, 20, 20, null);
			gi.drawString(damage, X+1, Y-1);
		} else if (amount < 100) {
			X= (x - offX) * 32 + randX;
			Y=((y - offY) + 1) * 32 - randY;
			
			gi.drawImage(backing.get(0), X - 6, Y - 14, 28, 20, null);
			gi.drawString(damage, X+3, Y-1);
		} else {
		
			X=  (x - offX) * 32 + randX;
			Y=((y - offY) + 1) * 32 -randY;
			
			gi.drawImage(backing.get(0), X - 6, Y - 14, 28, 20, null);
			gi.drawString(damage, X+1, Y-1);
		}
		
		
	}

}
