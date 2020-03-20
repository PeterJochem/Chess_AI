import java.awt.Image;
import java.util.ArrayList;

/* The king object represents the king on the board
 * All the piece's associated information will live here
 */
public class King extends Piece {

	boolean dead;
	
	// The current position
	public int x;
	public int y;

	// This records who owns the piece
	public String name;

	public King(String name) {
		x = 0;
		y = 0;
		dead = false;
		this.name = name;
	}

	/* This will generate a list of moves possible from the given spot
	 * Returns a list of moves
	 */
	public ArrayList<Move> generateMoves(GameState currentGame) {

		// This is the list of legal moves to add to
		ArrayList<Move> moves = new ArrayList<Move>();
				
		if (dead) {
			return moves;
		}

		// Check for moves without any captures
		if (onBoard(x + 1, y) && isEmpty(currentGame, x + 1, y) ) {
			moves.add(new Move(x, y, x + 1, y) );
		}
		if (onBoard(x - 1, y) && isEmpty(currentGame, x - 1, y) ) {
			moves.add(new Move(x, y, x - 1, y) );
		}
		if (onBoard(x, y + 1) && isEmpty(currentGame, x, y + 1) ) {
			moves.add(new Move(x, y, x, y + 1) );
		}
		if (onBoard(x, y - 1) && isEmpty(currentGame, x, y - 1) ) {
			moves.add(new Move(x, y, x, y - 1) ); 
		}
		if (onBoard(x - 1, y - 1) && isEmpty(currentGame, x - 1, y - 1) ) {
			moves.add(new Move(x, y, x - 1, y - 1) );
		}
		if (onBoard(x + 1, y + 1) && isEmpty(currentGame, x + 1, y + 1) ) {
			moves.add(new Move(x, y, x + 1, y + 1) );
		}
		if (onBoard(x - 1, y + 1) && isEmpty(currentGame, x - 1, y + 1) ) {
			moves.add(new Move(x, y, x - 1, y + 1) );
		}
		if (onBoard(x + 1, y - 1) && isEmpty(currentGame, x + 1, y - 1) ) {
			moves.add(new Move(x, y, x + 1, y - 1) );
		}

		// Check for moves with captures
		if (onBoard(x + 1, y) && isEnemy(currentGame, x + 1, y) ) {
			moves.add(new Move(x, y, x + 1, y) );
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x + 1, y) );
		}
		if (onBoard(x - 1, y) && isEnemy(currentGame, x - 1, y) ) {
			moves.add(new Move(x, y, x - 1, y) ); 
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x - 1, y) );
		}
		if (onBoard(x, y + 1) && isEnemy(currentGame, x, y + 1) ) {
			moves.add(new Move(x, y, x, y + 1) ); 
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x, y + 1) );
		}
		if (onBoard(x, y - 1) && isEnemy(currentGame, x, y - 1) ) {
			moves.add(new Move(x, y, x, y - 1) );
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x, y - 1) );
		}
		if (onBoard(x - 1, y - 1) && isEnemy(currentGame, x - 1, y - 1) ) {
			moves.add(new Move(x, y, x - 1, y - 1) ); 
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x - 1, y - 1) );
		}
		if (onBoard(x + 1, y + 1) && isEnemy(currentGame, x + 1, y + 1) ) {
			moves.add(new Move(x, y, x + 1, y + 1) ); 
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x + 1, y + 1) );
		}
		if (onBoard(x - 1, y + 1) && isEnemy(currentGame, x - 1, y + 1) ) {
			moves.add(new Move(x, y, x - 1, y + 1) ); 
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x - 1, y + 1) );
		}
		if (onBoard(x + 1, y - 1) && isEnemy(currentGame, x + 1, y - 1) ) {
			moves.add(new Move(x, y, x + 1, y - 1) ); 
			moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(x + 1, y - 1) );
		}


		// TO DO - TO DO - TO DO - TO DO
		// remove any moves that would put the king in check
		// remove any moves that would put the king in check
		// for (int i = 0; i < moves.size(); ++i) {	
		// }
		// moves.remove(index)
		// remove any moves that would put the king in check
		// remove any moves that would put the king in check
		
		// FIX ME
		// CASTLING MOVE!
		// FIX ME

		return moves;
	}

	/* This method checks whether a location is 
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
	 * a capture
	 */
	public boolean isEmpty(GameState currentGame, int x, int y) {

		if (currentGame.board[y][x] == null) {
			return true;
		}

		return false;
	}

	/* This method checks whether a location is 
	 * an enemy or not 
	 *  It returns true if an enemy is at the location
	 *  Returns false if there is not an enemy at this location
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
	
	// Create a deep copy of this item
	public King duplicate()  {
		King newKing = new King(this.name);
		newKing.x = this.x;
		newKing.y = this.y;
		newKing.dead = this.dead;

		return newKing;
	}


}



