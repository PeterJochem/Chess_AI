import java.awt.Image;
import java.util.ArrayList;

// This class describes the information about a rook

public class Rook extends Piece {

	// The given image for the graphics to display
	Image KingImg;

	// Is the Rook alive or dead?
	boolean dead;
	
	// The current position
	int x;
	int y;

	// This records who owns the piece
	public String name;

	// Constructor
	public Rook(String name) {
		this.name = name;
	}

	/* This method will generate all the moves 
	 * that the rook can make from a given
	 * position. It returns a list of moves
	 */
	public ArrayList<Move> generateMoves(GameState currentGame) {

		ArrayList<Move> moves = new ArrayList<Move>();

		if (dead) {
			return moves;
		}
		
		int newX;
		int newY;

		// Move 1
		newX = x;
		newY = y + 1;
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if  ( isEnemy(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) ); 
				break;
			}
			else {
				break;
			}

			newY = newY + 1;
		}

		// Move 2
		newX = x;
		newY = y - 1;
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if  ( isEnemy(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) ); 
				break;
			}
			else {
				break;
			}

			newY = newY - 1;
		}

		// Move 3
		newX = x + 1;
		newY = y;
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if  ( isEnemy(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) ); 
				break;
			}
			else {
				break;
			}

			newX = newX + 1;
		}

		// Move 4
		newX = x  - 1;
		newY = y;
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if  ( isEnemy(currentGame, newX, newY)  ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) ); 
				break;
			}
			else {
				break;
			}

			newX = newX - 1;
		}

		return moves;
	}

	/* This method checks whether a location is actually
	 * on the board or not
	 */	
	public boolean onBoard(int x, int y) {

		if (x < 8 && y < 8 && x >= 0 && y >= 0) {
			return true;
		}

		return false;	
	}

	/* This method checks if a given
	 * square is empty, ie can I move there w/o a collision
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
			else if ( currentGame.board[y][x] instanceof Bishop ) {
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
	
	/* This method updates the piece's fields 
	 *  when a move is executed
	 */
	public void executeMove(Move move) {
		
		x = move.newX;
		y = move.newY;
	}
	
	// Create a DEEP copy of this item
	public Rook duplicate()  {
		Rook newRook = new Rook(this.name);
		newRook.x = this.x;
		newRook.y = this.y;
		newRook.dead = this.dead;

		return newRook;
	}
}
