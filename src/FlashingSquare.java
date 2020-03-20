

/* This class supports the graphics class. It records a 
 * location on which we want to put a bounding box to convey some 
 * meaning to the user
 */
public class FlashingSquare {

	public int x = -100;
	public int y = -100;
	public boolean on;

	
	public FlashingSquare(int x, int y, boolean on) {
		this.x = x;
		this.y = y;
		this.on = on;
	}
}
