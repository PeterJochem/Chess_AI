
/* This method describes the state of a game.
 * The Game object has a GameState object. 
 */

public class GameState {
	
		// 8 by 8 board
		Piece[][] board = new Piece[8][8];
		
		// This record's whose turn it is
		boolean player1Turn = true;
		
		/* This describes the value of the gameState
		 More positive is good for P1. More negative is good for P2
		 See the score method for more details on how this is scored
		*/
		int score = 0;
		
		// Holds refrence to the given players
		Player player1;
		Player player2;
		
		/* Dummy constructor so we can duplicate 
		 * a given GameState
		 */
		public GameState() {
			
		}
		
	// Constructor
	public GameState(Game game, boolean turn) {
		
			// save references to the game's pieces
			this.player1 = game.player1;
			this.player2 = game.player2;
			this.player1Turn = turn;
				
			// copy the king over
			if (game.player1.king != null && !game.player1.king.dead ) {
				board[game.player1.king.y][game.player1.king.x] =  game.player1.king.duplicate();
			}
			if (game.player2.king != null && !game.player2.king.dead ) {
				board[game.player2.king.y][game.player2.king.x] =  game.player2.king.duplicate();
			}
			
			// copy the queen over
			for (int i = 0; i < game.player1.queens.size(); ++i) {
				if (game.player1.queens.get(i) != null && !game.player1.queens.get(i).dead ) {
					board[game.player1.queens.get(i).y][game.player1.queens.get(i).x] = game.player1.queens.get(i).duplicate();
				}
			}
			for (int i = 0; i < game.player2.queens.size(); ++i) {
				if (game.player2.queens.get(i) != null && !game.player2.queens.get(i).dead ) {
					board[game.player2.queens.get(i).y][game.player2.queens.get(i).x] =  game.player2.queens.get(i).duplicate();
				}
			}
			
			// copy the bishops over
			for (int i = 0; i < game.player1.bishops.size(); ++i) {
				if (game.player1.bishops.get(i) != null && !game.player1.bishops.get(i).dead ) {
					board[game.player1.bishops.get(i).y][game.player1.bishops.get(i).x] =  game.player1.bishops.get(i).duplicate();
				}
			}
			for (int i = 0; i < game.player2.bishops.size(); ++i) {
				if (game.player2.bishops.get(i) != null && !game.player2.bishops.get(i).dead ) {
					board[game.player2.bishops.get(i).y][game.player2.bishops.get(i).x] = game.player2.bishops.get(i).duplicate();
				}
			}
			
			// copy the rooks over
			for (int i = 0; i < game.player1.rooks.size(); ++i) {
				if (game.player1.rooks.get(i) != null && !game.player1.rooks.get(i).dead ) {
					board[game.player1.rooks.get(i).y][game.player1.rooks.get(i).x] = game.player1.rooks.get(i).duplicate();
				}
			}
			for (int i = 0; i < game.player2.rooks.size(); ++i) {
				if (game.player2.rooks.get(i) != null && ! game.player2.rooks.get(i).dead ) {
					board[game.player2.rooks.get(i).y][game.player2.rooks.get(i).x] = game.player2.rooks.get(i).duplicate();
				}
			}
			
			// copy knights over 
			for (int i = 0; i < game.player1.knights.size(); ++i) {
				if (game.player1.knights.get(i) != null && !game.player1.knights.get(i).dead ) {
					board[game.player1.knights.get(i).y][game.player1.knights.get(i).x] = game.player1.knights.get(i).duplicate();
				}
			}
			for (int i = 0; i < game.player2.knights.size(); ++i) {
				if (game.player2.knights.get(i) != null && !game.player2.knights.get(i).dead ) {
					board[game.player2.knights.get(i).y][game.player2.knights.get(i).x] = game.player2.knights.get(i).duplicate();
				}
			}
			
			// copy pawns over 
			for (int i = 0; i < game.player1.pawns.size(); ++i) {
				if (game.player1.pawns.get(i) != null && !game.player1.pawns.get(i).dead ) {
					board[game.player1.pawns.get(i).y][game.player1.pawns.get(i).x] = game.player1.pawns.get(i).duplicate();
				}
			}
			
			for (int i = 0; i < game.player2.pawns.size(); ++i) {
				if (game.player2.pawns.get(i) != null && !game.player2.pawns.get(i).dead ) {
					board[game.player2.pawns.get(i).y][game.player2.pawns.get(i).x] = game.player2.pawns.get(i).duplicate();
				}
			}
		}
	
	/* This method aesthetically prints this GameState's board state
	 * which is where all the given pieces are 
	 * This method is for testing as it allows me to 
	 * see what the board looks like at a givn point in time 
	 */
	public void printBoard( ) {
		
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j  < 8; ++j) {
				
				if ( board[i][j] == null) {
					System.out.print("EM ");
				}
				else { 
					System.out.print("PI ");
				}
			}
			System.out.println("");
		}	
	}

	
	/* This method will score a given move 
	 * with the given scoring algorithm
	 * 
	 * Scoring description:
	 * 		TO DO 1) Finish scoring function
	 * 			  2) Describe scoring here
	 * 
	 * A more positive score is good for player1
	 * A more negative score is good for player2
	 * 
	 */
	public int  score() {
		
		int score = 0;
		
		// How much each piece is worth
		int kingValue =  10000;
		int queenValue = 500;
		int pawnValue = 1;
		int rookValue = 1;
		int bishopValue = 50;
		int knightValue = 250;
		
		// Check if the kings are on the board
		if (this.player1.king != null && !this.player1.king.dead  ) {
			score = score + kingValue;
		}
		if (this.player2.king != null && !this.player2.king.dead  ) {
			score = score - kingValue;
		}
		
		// Check if the queens are on the board
		for (int i = 0; i < this.player1.queens.size(); ++i) {
			if (this.player1.queens != null && !this.player1.queens.get(i).dead  ) {
				score = score + queenValue;
			}
		}
		
		for (int i = 0 ; i < this.player2.queens.size(); ++i) {
			if (this.player2.queens != null && !this.player2.queens.get(i).dead ) {
				score = score - queenValue;
			}
		}
		
		// Check if the knights are on the board
		for (int i = 0 ; i < this.player1.knights.size(); ++i) {
			if ( !this.player1.knights.get(i).dead ) {
				score = score + knightValue;
			}
		}
		
		for (int i = 0 ; i < this.player2.knights.size(); ++i) {
			if ( !this.player2.knights.get(i).dead ) {
				score = score - knightValue;
			}
		}
		
		// Check if the bishops are on the board
		for (int i = 0 ; i < this.player1.bishops.size(); ++i) {
			if ( !this.player1.bishops.get(i).dead ) {
				score = score + bishopValue;
			}
		}
		
		for (int i = 0 ; i < this.player2.bishops.size(); ++i) {
			if ( !this.player2.bishops.get(i).dead ) {
				score = score - bishopValue;
			}
		}
		
		// Check if the rooks are on the board
		for (int i = 0 ; i < this.player1.rooks.size(); ++i) {
			if ( !this.player1.rooks.get(i).dead ) {
				score = score + rookValue;
			}
		}
		
		for (int i = 0 ; i < this.player2.rooks.size(); ++i) {
			if ( !this.player2.rooks.get(i).dead ) {
				score = score - rookValue;
			}
		}
		
		// Check if the pawns are on the board
		for (int i = 0 ; i < this.player1.pawns.size(); ++i) {
			if ( !this.player1.pawns.get(i).dead ) {
				score = score + pawnValue;
			}
		}
		
		for (int i = 0 ; i < this.player2.pawns.size(); ++i) {
			if ( !this.player2.pawns.get(i).dead ) {
				score = score - pawnValue;
			}
		}
		
		// FIX ME
		// FIX ME
		// Set the score field
		this.score = score;
		// FIX ME
		// FIX ME
		
		// FIX ME
		// FIX ME
		// Points based on configurations of pieces 
		for (int i = 0; i < this.board.length; ++i) {
			for (int j = 0; j < this.board[i].length; ++j) {
				
				if ( this.board[i][j] instanceof King ) {
					
				}
				else if ( this.board[i][j] instanceof King ) {
					
				}
				else if ( this.board[i][j] instanceof King  ) {
					
				}
				else if ( this.board[i][j] instanceof King  ) {
					
				}
				else if ( this.board[i][j] instanceof King  ) {
					
				}
				else if ( this.board[i][j] instanceof King  ) {
					
				}
			}
		}
		
		// FIX ME
		// FIX ME
		// FIX ME
		// FIX ME
		
		return this.score;
	}
	
	
	/* This method will create a deep copy of 
	 * a given GameState. By deep copy I mean make a copy
	 * of each field of a given GameState object
	 */
	public GameState duplicate() {
		
		// Create a new GameState Object and then fill it's fields 
		GameState newGame = new GameState();

		newGame.player1 = this.player1.duplicate();
		newGame.player2 = this.player2.duplicate();
		
		newGame.player1Turn = this.player1Turn;
		
		newGame.score = score();

		// Copy the board over
		for (int i = 0; i < this.board.length; ++i) {
			for (int j = 0; j < this.board[i].length; ++j) {
			
				if (  this.board[i][j] instanceof Queen ) {
					newGame.board[i][j] = ( (Queen)(this.board[i][j]) ).duplicate();
				}
				else if ( this.board[i][j] instanceof King  ) {
					newGame.board[i][j] = ( (King)(this.board[i][j]) ).duplicate();
				}
				else if ( this.board[i][j] instanceof Knight ) {
					newGame.board[i][j] = ( (Knight)(this.board[i][j]) ).duplicate();
				}
				else if ( this.board[i][j] instanceof Rook ) {
					newGame.board[i][j] = ( (Rook)(this.board[i][j]) ).duplicate();
				}
				else if ( this.board[i][j] instanceof Bishop ) {
					newGame.board[i][j] = ( (Bishop)(this.board[i][j]) ).duplicate();
				}
				else if ( this.board[i][j] instanceof Pawn ) {
					newGame.board[i][j] = ( (Pawn)(this.board[i][j]) ).duplicate();
				}
			}		
		}

		return newGame;
	}
}