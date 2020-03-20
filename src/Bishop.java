import java.awt.Image;
import java.util.ArrayList;

public class Bishop extends Piece {

	boolean dead;
	// The current position
	public int x;
	public int y;

	// This records who owns the piece
	public String name;

	public Bishop(String name) {
		this.name = name;
	}

	/* This method will generate all the moves from a given position
	 * for a bishop. It returns a list of type Move
	 */
	public ArrayList<Move> generateMoves(GameState currentGame) {

		ArrayList<Move> moves = new ArrayList<Move>();

		if ( dead ) {
			return moves;
		}
		int newX = x + 1;
		int newY = y + 1;

		// Diagonal 1
		while ( onBoard(newX, newY)  ) {

			if (  isEmpty(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
				break;
			}
			else {
				break;
			}

			newX = newX + 1;
			newY = newY + 1;
		}

		// re-set newX, newY
		newX = x + 1;
		newY = y - 1;
		// Diagonal 2
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY)  ) { 
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
				break;
			}
			else {
				break;
			}

			newX = newX + 1;
			newY = newY - 1;
		}

		// re-set newX, newY
		newX = x - 1;
		newY = y + 1;

		// Diagonal 3
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY)  ) { 
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
				break;
			}
			else {
				break;
			}

			newX = newX - 1;
			newY = newY + 1;
		}

		// re-set newx, and newY
		newX = x - 1;
		newY = y - 1;

		// Diagonal 4
		while ( onBoard(newX, newY) ) {

			if ( isEmpty(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY)  ) { 
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
				break;
			}
			else {
				break;
			}

			newX = newX - 1;
			newY = newY - 1;
		}

 		return moves;	
	}

	/* This method checks whether a location is 
	 * on the board or not
	 */
	public boolean onBoard(int x, int y) {

		if (x < 8 && y < 8 && x >= 0 && y >= 0) {
			return true;
		}

		return false;	
	}

	/* This method checks if a given
	 * square is empty, ie can I move there w/o
	 * a collision/capture
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

			if (  currentGame.board[y][x] instanceof Queen  ) {
				if ( !(((Queen)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if (  currentGame.board[y][x] instanceof King ) {
				if ( !(((King)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if (  currentGame.board[y][x] instanceof Knight ) {
				if ( !(((Knight)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Rook  ) {
				if ( !(((Rook)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Bishop  ) {
				if ( !(((Bishop)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
			else if ( currentGame.board[y][x] instanceof Pawn  ) {
				if ( !(((Pawn)currentGame.board[y][x]).name).equals(this.name) ) {
					return true;
				}
			}
		}

		return false;
	}


	// create a deep copy of this item
	public Bishop duplicate()  {
		Bishop newBishop = new Bishop(this.name);
		newBishop.x = this.x;
		newBishop.y = this.y;
		newBishop.dead = this.dead;

		return newBishop;
	}



}



