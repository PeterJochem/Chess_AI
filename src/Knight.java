import java.awt.Image;
import java.util.ArrayList;

/* This class describes the knight 
 * All the information relevant to the
 *  knight piece lives here
 */
public class Knight extends Piece{

	boolean dead;
	
	// The current position
	int x;
	int y;

	// This records who owns the piece
	public String name;

	public Knight(String name) {
		this.name = name.trim();
	}


	/* This method will generate all the moves for 
	 * a knight in the given position
	 */
	public ArrayList<Move> generateMoves(GameState currentGame) {

		ArrayList<Move> moves = new ArrayList<Move>();

		if (dead) {
			return moves;
		}

		int newX = x + 2; 
		int newY = y + 1;

		// Move 1
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		// Move 2
		newX = x + 1;
		newY = y + 2;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		// Move 3
		newX = x - 2;
		newY = y + 1;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		// Move 4
		newX = x - 1;
		newY = y + 2;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		// Move 5
		newX = x + 2;
		newY = y - 1;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		// Move 6
		newX = x + 1;
		newY = y - 2;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		
		// Move 7
		newX = x - 1;
		newY = y - 2;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
		}

		// Move 8
		newX = x - 2;
		newY = y - 1;
		if ( onBoard(newX, newY) ) {
			if ( isEmpty(currentGame, newX, newY) ){
				moves.add(new Move(x, y, newX, newY) );
			}
			else if ( isEnemy(currentGame, newX, newY) ) {
				moves.add(new Move(x, y, newX, newY) );
				moves.get(moves.size() - 1).capturedPieces.add(new capturedPiece(newX, newY) );
			}
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
	 * square is empty, ie if any piece is at
	 * this location on the board
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

			if ( currentGame.board[y][x] instanceof Queen  ) {
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

	// Create a deep copy of this knight
	public Knight duplicate()  {
		
		Knight newKnight = new Knight(this.name);
		newKnight.x = this.x;
		newKnight.y = this.y;
		newKnight.dead = this.dead;

		return newKnight;
	}
}
