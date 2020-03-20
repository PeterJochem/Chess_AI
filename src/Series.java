
/* This class will be the object that allows 
 * us to let two players play multiple times 
 * and record the meta-data associated with 
 * this
 */

/* Implements Runnable so that we can create
* a new thread for a Series object
*/
public class Series implements Runnable {

	int numberGames;
	int currentGameNumber;
	Game currentGame;
	Player player1;
	Player player2;
	
	// Constructor
	public Series(Player player1, Player player2, int numberGames) {
		
		this.numberGames = numberGames;
		this.currentGameNumber = 0;
		this.currentGame = null;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	/* This method lets us run the 
	 *  series and collect the meta-data
	 */
	public void startSeries() {
		
		// Create a new thread to run this series
		Thread t1 = new Thread(this); 
		t1.start();
	}
	
	
	/* This is called when the new thread is created. 
	 * It has the highest level logic that runs the series 
	 */
	@Override
	public void run() {

		// Sequentially run games 
		for (int i = 0; i < this.numberGames; ++i) {
			
			System.out.println("Current Game: " + (i + 1) );
			
			// Re-set each player's fields for each new game
			this.player1.reset();
			this.player2.reset();
			
			// The boolean indicates we want to create a new thread for the new game
			this.currentGame = new Game(this.player1, this.player2, true);
			
			// Update information for the graphics class
			UserInterface.currentGame = this.currentGame;
			
			initializeBoard();
			
			/* Wait for the game to end. The game has it's own thread so
			* we are using a join to wait for it
			*/ 
			try {
				this.currentGame.t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
		
		// Print the series's sttistics
		System.out.println(player1.gamesWon);
		System.out.println("Player1 won " + ( ( (double)player1.gamesWon) * 100 ) / ( (double)(this.numberGames) )
				+ "% of the " + this.numberGames + " games");
		System.out.println("Player2 won " + ( ( (double)player2.gamesWon) * 100 ) / ( (double)(this.numberGames) )
				+ "% of the " + this.numberGames + " games");
			
	}
	
	/* Initialize the player's pieces
	 *  for the start of a new game
	 */
	public void initializeBoard() {
		
			player1.queens.add( new Queen(player1.name) );
			player2.queens.add( new Queen(player2.name) );

			player1.king = new King(player1.name);
			player2.king = new King(player2.name);

			for (int i = 0; i < 2; ++i) {
				player1.bishops.add(new Bishop(player1.name) );
				player2.bishops.add(new Bishop(player2.name) );
			}
			for (int i = 0; i < 2; ++i) {
				player1.knights.add(new Knight(player1.name) );
				player2.knights.add(new Knight(player2.name) );
			}
			for (int i = 0; i < 2; ++i) {
				player1.rooks.add(new Rook(player1.name) );
				player2.rooks.add(new Rook(player2.name) );
			}
			for (int i = 0; i < 8; ++i) {
				player1.pawns.add(new Pawn(player1.name) );
				player2.pawns.add(new Pawn(player2.name) );
			}

			// Set each piece
			player1.king.x = 3;
			player1.king.y = 0;

			// 0th index because to start a game, theres always only one queen
			player1.queens.get(0).x = 4;
			player1.queens.get(0).y = 0;

			for (int i = 0; i < player1.pawns.size(); ++i) {
				player1.pawns.get(i).x = i;
				player1.pawns.get(i).y = 1; 	
			}

			player1.rooks.get(0).x = 0;
			player1.rooks.get(0).y = 0;
			player1.rooks.get(0).x = 7;
			player1.rooks.get(0).y = 0;

			player1.knights.get(0).x = 1;
			player1.knights.get(0).y = 0;	
			player1.knights.get(1).x = 6;
			player1.knights.get(1).y = 0;

			player1.bishops.get(0).x = 2;
			player1.bishops.get(0).y = 0;
			player1.bishops.get(1).x = 5;
			player1.bishops.get(1).y = 0;

			// Place player2's pieces
			player2.king.x = 3;
			player2.king.y = 7;

			player2.queens.get(0).x = 4;
			player2.queens.get(0).y = 7;

			for (int i = 0; i < player1.pawns.size(); ++i) {
				player1.pawns.get(i).x = i;
				player1.pawns.get(i).y = 1; 	
			}

			for (int i = 0; i < player2.pawns.size(); ++i) {
				player2.pawns.get(i).x = i;
				player2.pawns.get(i).y = 6; 	
			}

			player2.rooks.get(0).x = 0;
			player2.rooks.get(0).y = 7;
			player2.rooks.get(1).x = 7;
			player2.rooks.get(1).y = 7;

			player2.knights.get(0).x = 1;
			player2.knights.get(0).y = 7;	
			player2.knights.get(1).x = 6;
			player2.knights.get(1).y = 7;

			player2.bishops.get(0).x = 2;
			player2.bishops.get(0).y = 7;
			player2.bishops.get(1).x = 5;
			player2.bishops.get(1).y = 7;

			// set the GameState variable 
			this.currentGame.currentGame = new GameState(this.currentGame, true);
		}
}
