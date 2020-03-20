
/* This describes the parent class for all the diffrent 
 * types of pieces - Knights, Queens, Bishops, ...
 * These classes all extend this parent class
 */
public class Piece {
	
	// This records who owns the piece
	public boolean player1;
	
	// Coordinates of the piece
	int x;
	int y;
	
	// Is this piece alive or dead?
	boolean dead;
	
	/* This records who owns the piece - I could not 
	* leave it null so I put a dummy value
	*/
	public String name = "1234567";
	
	// Constructor
	public Piece() {

	}
	
}
