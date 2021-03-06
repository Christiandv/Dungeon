package Map;

import java.util.ArrayList;

public class Rect {
	int x;
	int y;
	int height;
	int length;
	int area;
	int midX;
	int midY;

	public Rect() {

		x = (int) (Math.random() * 58)+1 ;
		y = (int) (Math.random() * 28)+1 ;

		height = (int) (Math.random() * (29-y));
		length = (int) (Math.random() * (59-x));
		area = height * length;
		midX = (int) (x + length / 2);
		midY = (int) (y + height / 2);

	}

	public boolean intersect(Rect r) {
		int xbot = x + length;
		int ybot = y + height;

		if (x > r.x + r.length)
			return false;
		if (y > r.y + r.height)
			return false;
		if (xbot < r.x)
			return false;
		if (ybot < r.y)
			return false;
		return true;

	}

	public void random() {
		x = (int) (Math.random() * 58)+1 ;
		y = (int) (Math.random() * 28) +1;

		height = (int) (Math.random() * (29-y));
		length = (int) (Math.random() * (59-x));
		area = height * length;
		midX = (int) (x + length / 2);
		midY = (int) (y + height / 2);
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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getMidX() {
		return midX;
	}

	public void setMidX(int midX) {
		this.midX = midX;
	}

	public int getMidY() {
		return midY;
	}

	public void setMidY(int midY) {
		this.midY = midY;
	}
}
