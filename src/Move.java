import java.util.ArrayList;

// This class will store a given piece's Move so that it can be executed
public class Move {
	
	// Coordinates
	int oldX;
	int oldY;
	int newX;
	int newY;
	
	// Captured pieces from executing this move
	ArrayList<capturedPiece> capturedPieces = new ArrayList<capturedPiece>();
	
	// Constructor
	public Move(int oldX, int oldY, int newX, int newY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}
	
	// Add a captured piece object to the list
	public void addCapture(int x, int y) {
		capturedPiece newCap = new capturedPiece(x, y);
		capturedPieces.add(newCap);
	}
	
	/* This method is for debugging. It just prints the 
	* coordinates in an aesthetic/readable format
	*/
	public void print( ) {
		System.out.print("(" + this.oldX + ", " + this.oldY + ") --> ");
		System.out.println("(" + this.newX + ", " + this.newY + ")");
	}
}
