import java.awt.Graphics;
import java.util.ArrayList;

/* This class will hold all the information relevant to  
 * a given player in the chess game. AI player extends this class
 */
public class Player {
		
		String name;
		
		int gamesWon;
		
		int gamesLost;
		
		// A unique id so the same player can play itself. 
		// The (name, version number) pair will always be unique
		int version = 0;
		
		// This is the direction the player is oriented
		// 0 is at the top of the screen, 1 is at the bottom
		int startingDirection;
		
		// A Player's Pieces
		ArrayList<Queen> queens;
		King king;
		ArrayList<Bishop> bishops;
		ArrayList<Knight> knights;
		ArrayList<Rook> rooks;
		ArrayList<Pawn> pawns;
		
		/* This is a list of squares to light up to show that
		/* a given piece can move somehwere
		*/
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		public Player(String name) {
			this.name = name;
			this.gamesWon = 0;
			this.gamesLost = 0;
			this.queens = new ArrayList<Queen>();
			this.bishops = new ArrayList<Bishop>();
			this.knights = new ArrayList<Knight>();
			this.rooks = new ArrayList<Rook>();
			this.pawns = new ArrayList<Pawn>();
		}
		
		/* This is a dummy constructor to allow 
		 * us to duplicate/clone a given player
		 */
		public Player() {
			this.bishops = new ArrayList<Bishop>();
			this.knights = new ArrayList<Knight>();
			this.rooks = new ArrayList<Rook>();
			this.pawns = new ArrayList<Pawn>();
		}
		
		/* This executes a human move
		 */
		public void executeHumanMove(GameState currentGame, Move chosenMove)  {
			
			// Player1 is ALWAYS the human
			Player currentPlayer = currentGame.player1;
			
			UserInterface.waitingHumanMove = false;
			
			// King
			if ( currentPlayer.king != null && currentPlayer.king.dead != true && currentPlayer.king.x == chosenMove.oldX
					&&  currentPlayer.king.y == chosenMove.oldY ) {
				
				currentPlayer.king.x = chosenMove.newX;
				currentPlayer.king.y = chosenMove.newY;
				
				updateGameState(currentGame, chosenMove);
				for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
					removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
				}
				
				return;
			}
			
			// Queen
			for (int i = 0; i < currentPlayer.queens.size(); ++i) {
				if ( currentPlayer.queens.get(i) != null && currentPlayer.queens.get(i).dead != true 
						&& currentPlayer.queens.get(i).x == chosenMove.oldX
						&&  currentPlayer.queens.get(i).y == chosenMove.oldY ) {

					currentPlayer.queens.get(i).x = chosenMove.newX;
					currentPlayer.queens.get(i).y = chosenMove.newY;

					updateGameState(currentGame, chosenMove);
					for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
						removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
					}
					
					return;
				}
			}
			
			// Bishops 
			for (int i = 0; i < currentPlayer.bishops.size(); ++i) {
				
				if ( currentPlayer.bishops.get(i) != null && currentPlayer.bishops.get(i).dead != true && currentPlayer.bishops.get(i).x == chosenMove.oldX
						&&  currentPlayer.bishops.get(i).y == chosenMove.oldY ) {
					
					currentPlayer.bishops.get(i).x = chosenMove.newX;
					currentPlayer.bishops.get(i).y = chosenMove.newY;
					currentPlayer.possibleMoves = new ArrayList<Move>();
					
					updateGameState(currentGame, chosenMove);
					for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
						removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
					}
				
					return;
				}	
			}
			
			// Knights
			for (int i = 0; i < currentPlayer.knights.size(); ++i) {	
				if ( currentPlayer.knights.get(i) != null && currentPlayer.knights.get(i).dead != true && currentPlayer.knights.get(i).x == chosenMove.oldX
						&&  currentPlayer.knights.get(i).y == chosenMove.oldY ) {
					
					currentPlayer.knights.get(i).x = chosenMove.newX;
					currentPlayer.knights.get(i).y = chosenMove.newY;
				
					currentPlayer.possibleMoves = new ArrayList<Move>();
					
					updateGameState(currentGame, chosenMove);
					for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
						removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
					}
			
					return;
				}	
			}
			
			// Rooks
			for (int i = 0; i < currentPlayer.rooks.size(); ++i) {
				if ( currentPlayer.rooks.get(i) != null && currentPlayer.rooks.get(i).dead != true && currentPlayer.rooks.get(i).x == chosenMove.oldX
						&&  currentPlayer.rooks.get(i).y == chosenMove.oldY ) {
					
					currentPlayer.rooks.get(i).x = chosenMove.newX;
					currentPlayer.rooks.get(i).y = chosenMove.newY;
							
					updateGameState(currentGame, chosenMove);
					for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
						removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
					}
					
					return;
				}	
			}
			
			// Pawns
			for (int i = 0; i < currentPlayer.pawns.size(); ++i) {
				if ( currentPlayer.pawns.get(i) != null && currentPlayer.pawns.get(i).dead != true && currentPlayer.pawns.get(i).x == chosenMove.oldX
						&&  currentPlayer.pawns.get(i).y == chosenMove.oldY ) {
					
					currentPlayer.pawns.get(i).x = chosenMove.newX;
					currentPlayer.pawns.get(i).y = chosenMove.newY;
					currentPlayer.pawns.get(i).firstMove = false;
					
					currentPlayer.possibleMoves = new ArrayList<Move>();
					
					updateGameState(currentGame, chosenMove);
					
					for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
						removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
					}
			
					// Piece upgrade code
					boolean player1Upgrading = chosenMove.newY == 7 && currentPlayer.startingDirection == 0;
					boolean player2Upgrading = chosenMove.newY == 0 && currentPlayer.startingDirection == 1;
					if ( player1Upgrading  || player2Upgrading ) {
						currentPlayer.pawns.get(i).dead = true;
						currentPlayer.queens.add(new Queen(currentPlayer.name) );
						currentPlayer.queens.get(currentPlayer.queens.size() - 1).x = currentPlayer.pawns.get(i).x;
						currentPlayer.queens.get(currentPlayer.queens.size() - 1).y = currentPlayer.pawns.get(i).y;
					}
					
					return;
				}	
			}
		}
		
		/* This method will update the gameState array called Board 
		 * when a move is to be executed
		 */
		public void updateGameState(GameState currentGame, Move chosenMove)  {
			
			currentGame.board[chosenMove.oldY][chosenMove.oldX].x = chosenMove.newX;
			currentGame.board[chosenMove.oldY][chosenMove.oldX].y = chosenMove.newY;
		
			currentGame.board[chosenMove.newY][chosenMove.newX] = currentGame.board[chosenMove.oldY][chosenMove.oldX];
			currentGame.board[chosenMove.oldY][chosenMove.oldX] = null; 
		}
		
		/* This method is called when a piece is captured and 
		 *  we want to remove he piece from the lists of pieces 
		 *  and from the board
		 */
		public void removePiece(GameState currentGame, int x, int y) {

			Player currentPlayer;

			// The player whose pieces we are removing 
			if ( currentGame.player1Turn ) {
				currentPlayer = currentGame.player2;
			}
			else {
				currentPlayer = currentGame.player1;
			}
			
			// King
			if ( currentPlayer.king != null && currentPlayer.king.dead != true && currentPlayer.king.x == x
					&&  currentPlayer.king.y == y ) {

				currentPlayer.king.dead = true;
				return;
			}

			// Queen
			for (int i = 0; i < currentPlayer.queens.size(); ++i) {
				if ( currentPlayer.queens.get(i) != null && currentPlayer.queens.get(i).dead != true && currentPlayer.queens.get(i).x == x
						&&  currentPlayer.queens.get(i).y == y ) {

					currentPlayer.queens.get(i).dead = true;
					return;
				}
			}
			// Bishops 
			for (int i = 0; i < currentPlayer.bishops.size(); ++i) {

				if ( currentPlayer.bishops.get(i) != null && currentPlayer.bishops.get(i).dead != true && currentPlayer.bishops.get(i).x == x
						&&  currentPlayer.bishops.get(i).y == y ) {

					currentPlayer.bishops.get(i).dead = true;
					return;
				}	
			}

			// Knights
			for (int i = 0; i < currentPlayer.knights.size(); ++i) {

				if ( currentPlayer.knights.get(i) != null && currentPlayer.knights.get(i).dead != true && currentPlayer.knights.get(i).x == x
						&&  currentPlayer.knights.get(i).y == y ) {

					currentPlayer.knights.get(i).dead = true;
					return;
				}	
			}

			// Rooks
			for (int i = 0; i < currentPlayer.rooks.size(); ++i) {
				if ( currentPlayer.rooks.get(i) != null && currentPlayer.rooks.get(i).dead != true && currentPlayer.rooks.get(i).x == x
						&&  currentPlayer.rooks.get(i).y == y) {

					currentPlayer.rooks.get(i).dead = true;
					return;
				}	
			}

			// Pawns
			for (int i = 0; i < currentPlayer.pawns.size(); ++i) {
				if ( currentPlayer.pawns.get(i) != null && currentPlayer.pawns.get(i).dead != true && currentPlayer.pawns.get(i).x == x
						&&  currentPlayer.pawns.get(i).y == y ) {

					currentPlayer.pawns.get(i).dead = true;
					return;
				}	
			}
		}	
		
		/* This method creates a deep copy 
		 * of the given Player and returns it
		 */
		public Player duplicate() {
			
			Player newPlayer = new Player();
			newPlayer.name = this.name;
			newPlayer.gamesWon = this.gamesWon;
			newPlayer.gamesLost = this.gamesLost;
			newPlayer.version = this.version;
			
			if ( this.king != null ) {
				newPlayer.king = this.king.duplicate();
			}
			
			newPlayer.queens = new ArrayList<Queen>();
			for (int i = 0; i < queens.size(); ++i) {
				if ( this.queens.get(i) != null ) {
					newPlayer.queens.add(this.queens.get(i).duplicate() );
				}
			}
			
			for (int i = 0; i < bishops.size(); ++i) {
				newPlayer.bishops.add(this.bishops.get(i).duplicate() );
			}
			
			for (int i = 0; i < knights.size(); ++i) {
				newPlayer.knights.add(this.knights.get(i).duplicate() );
			}
			
			for (int i = 0; i < rooks.size(); ++i) {
				newPlayer.rooks.add(this.rooks.get(i).duplicate() );
			}
			
			for (int i = 0; i < pawns.size(); ++i) {
				newPlayer.pawns.add(this.pawns.get(i).duplicate() );
			}
			
			return newPlayer;
		}
		
		
		/* This re-sets a Player's pieces 
		 * so it can play another game
		 */
		public void reset() {

			this.king = new King(this.name);
			this.queens = new ArrayList<Queen>();
			this.bishops = new ArrayList<Bishop>();
			this.knights = new ArrayList<Knight>();
			this.rooks = new ArrayList<Rook>();
			this.pawns = new ArrayList<Pawn>();
		}
		
		
		
}