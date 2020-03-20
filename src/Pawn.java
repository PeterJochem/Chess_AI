import java.awt.Image;
import java.util.ArrayList;

/* This class describes a pawn. All
 * the information relevant to a pawn will
 * live here
 */
public class Pawn extends Piece{

	// Is this pice still alive?
	boolean dead;
	
	// The current position
	int x;
	int y;

	/* The things a pawn can do on it's first move 
	* are slightly diffrent. This boolean records if it 
	* really is the pawn's first move
	*/
	boolean firstMove;

	// This records who owns this piece
	String name;

	public Pawn(String name) {
		firstMove = true;
		this.name = name.trim();
	}	

	/* This will generate a list of moves possible from the given spot
	 * Returns a list of moves
	 */
	public ArrayList<Move> generateMoves(GameState currentGame) {

		ArrayList<Move> moves = new ArrayList<Move>();
			
		if (dead) {
			return moves;
		}
		
		if (currentGame.player1Turn) {
			
			// Generate double moves
			if (firstMove && onBoard(x, y + 1) && isEmpty(currentGame, x, y + 1) ) {
				if ( onBoard(x, y + 2) && isEmpty(currentGame, x, y + 2) ) { 
					moves.add(new Move(x, y, x, y + 2) );
				}	
			}
			
			// Generate single moves
			if ( onBoard(x, y + 1) &&  isEmpty(currentGame, x, y + 1) ) {
				moves.add(new Move(x, y, x, y + 1) );
				// FIX ME
				// en passant
				// FIX ME
			}
			
			// check for capture moves!
			if ( onBoard(x + 1, y + 1) && isEnemy(currentGame, x + 1, y + 1) ) {
				moves.add(new Move(x, y, x + 1, y + 1) );
				moves.get(moves.size() - 1).addCapture(x + 1, y + 1);
			}
			if ( onBoard(x - 1, y + 1) && isEnemy(currentGame, x - 1, y + 1) ) {
				moves.add(new Move(x, y, x - 1, y + 1) );
				moves.get(moves.size() - 1).addCapture(x - 1, y + 1);
			}	
		}
		
		else {
			
			// Generate double moves since it's the pawn's first move
			if (firstMove && onBoard(x, y - 1) && isEmpty(currentGame, x, y - 1) ) {
				if ( onBoard(x, y - 2) && isEmpty(currentGame, x, y - 2) ) {
					// Move(int oldx, int oldY, int newX, int newY) 
					moves.add(new Move(x, y, x, y - 2) );
				}
			}

			// Generate single moves
			if ( onBoard(x, y - 1) && isEmpty(currentGame, x, y - 1) ) {
				moves.add(new Move(x, y, x, y - 1) );
			}
			
			// Check for capture moves!
			if ( onBoard(x + 1, y - 1) && isEnemy(currentGame, x + 1, y - 1) ) {
				moves.add(new Move(x, y, x + 1, y - 1) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x + 1, y - 1) );
			}
			if ( onBoard(x - 1, y - 1) && isEnemy(currentGame, x - 1, y - 1) ) {
				moves.add(new Move(x, y, x - 1, y - 1) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x - 1, y - 1) );
			}
		}

		return moves;
	}

	/* This method checks whether a loaction is 
	 * on the board
	 */
	public boolean onBoard(int x, int y) {

		if (x < 8 && y < 8 && x >= 0 && y >= 0) {
			return true;
		}

		return false;	
	}

	/* This method checks if a given
	 * square is empty, ie can I move there w/o
	 * a collision
	 */
	public boolean isEmpty(GameState currentGame, int x, int y) {

		if (currentGame.board[y][x] == null) {
			return true;
		}

		return false;
	}

	/* This method checks whether a location is 
	 * an enemy or not 
	 */
	public boolean isEnemy(GameState currentGame, int x, int y) {

		if ( currentGame.board[y][x] != null ) {

			if ( currentGame.board[y][x] instanceof Queen ) {
				if ( !(((Queen)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof King ) {
				if ( !(((King)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Knight ) {
				if ( !(((Knight)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Rook ) {
				if ( !(((Rook)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Bishop  ) {
				if ( !(((Bishop)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Pawn ) {
				if ( !(((Pawn)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
		}

		return false;
	}

	/*This method will execute a given move 
	 * and update the pieces information
	*/
	public void executeMove(Move move) {
		
		x = move.newX;
		y = move.newY;
	}

	// Create a deep copy of this item
	public Pawn duplicate()  {
		Pawn newPawn = new Pawn(this.name);
		newPawn.x = this.x;
		newPawn.y = this.y;
		newPawn.dead = this.dead;
		newPawn.firstMove = this.firstMove;
		newPawn.name = this.name;

		return newPawn;
	}
}
