package Map;

public class Hallway {
	int startX;
	int startY;
	int endX;
	int endY;

	public Hallway(Rect r, Rect e) {
		startX = r.midX;
		startY = r.midY;
		endX = e.midX;
		endY = e.midY;
	}
}
