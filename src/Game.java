import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


/* This class describes the Game object.
 * It contains all the information relevant to 
 * a given game. It also has the high level code
 * that runs the game via a new thread. The high level
 * game code is in run()
 */
public class Game implements Runnable {
	
		// These are the two player objects that are competing
		Player player1;
		Player player2;
		
		// Every game has its own thread object
		Thread t1;
		
		// This records if the game is over or not
		boolean isOver;
		
		// This represents some internal information about the game
		GameState currentGame;
		
		// Constructor
		// Constructor
		public Game(Player player1, Player player2, boolean newThread) {
			
			this.player1 = player1;
			this.player2 = player2;
			this.isOver = false;
			this.player1.startingDirection = 0;
			this.player2.startingDirection = 1;
			
			/* Start the new thread to run the game logic/flow
			 * New threads are created unless we are debugging
			 */
			if ( newThread ) {
				t1 = new Thread(this); 
				t1.start();
			}	
		}
		
		
		/* This is the method that will be 
		 * called when we create a new thread to 
		 * simulate the game
		 * 
		 * This is where the highest level game flow logic 
		 * is located 
		 */
		public void run() {
			
			// Wait for the game's items to be initialized in the other thread
			 wait(1);
			 
			/* This runs if we want to run a series where 
			* two AI's play each other repeatedly. This method is 
			* mainly for testing purposes. It allows one to program
			* diffrent strategies and see which is better
			*/ 
			if (UserInterface.runningSeries ) {
				 while ( !isOver ) {
						
						currentGame.player1Turn = true;
						Move move = ((AIPlayer)(player1)).search(currentGame, 2, true);
						//Move move = ((AIPlayer)(player1)).chooseGreedyMove(currentGame);
						
						// Check if its the end of the game
						if ( move == null || player2.king.dead ) {
							// end the game
							System.out.println("Player1 Won");
							this.player1.gamesWon++;
							this.isOver = true;
							wait(1);
							continue;
						}
						
						// Let Player2 make a move
						currentGame.player1Turn = false;
						move = ((AIPlayer)(player2)).search(currentGame, 1, false);
						// move = ( (AIPlayer)player2).chooseGreedyMove(currentGame);
						// move = ( (AIPlayer)player2).chooseRandomMove(currentGame);
						
						// Check if the game ended
						if ( move == null || player1.king.dead ) {
							System.out.println("Player2 Won");
							this.player2.gamesWon++;
							this.isOver = true;
							wait(1);
							continue;
						}
					}
			 }
			 else {
				 
				 /* This code runs when the user wants to play a single 
				  * game against the AI
				  */
				 while ( !isOver ) {
					 currentGame.player1Turn = true;
					 while ( UserInterface.waitingHumanMove ) {
						 /* Wait until the human move is executed
						  * The wait is required to synchronize with other threads 
						  */
						 wait(1);
					 }

					 // Check if the game is over
					 if ( player2.king != null ) {
						 if ( player2.king.dead ) {
							 System.out.println("Player1 Won");
							 this.player1.gamesWon++;
							 this.isOver = true;
							 wait(1);
							 continue;
						 }
					 }

					 currentGame.player1Turn = false;

					/* The search method will expand the gameTree and
					 * use heurestics to make a good move
					 * The second parameter refers to the number of moves ahead
					 * the game tree will be expanded
					 */ 
					 Move move = ((AIPlayer)(player2)).search(currentGame, 2, false);

					// End Game Check 
					 if ( (move == null) || (player1.king != null & player1.king.dead) ) {
						 System.out.println("Player2 Won");
						 this.player2.gamesWon++;
						 this.isOver = true;
						 wait(1);
						 continue;
					 }
 
					 currentGame.player1Turn = true;
					 
					 // This relays information back to the graphics class
					 UserInterface.waitingHumanMove = true;
				 }
			 }
		 }
		
		
		/* This is a wrapper function that 
		 * calls the wait function with/for the given
		 * number of seconds specified in the argument
		 * I made a wrapper for the sake of code-readability
		 */
		public void wait(int seconds) {
			try {
				TimeUnit.SECONDS.sleep(seconds);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		/* This method will print a list of moves. It is for debugging purposes 
		 * It just traverses the list and prints it in a easy to read manner
		 */
		public void printMoves(ArrayList<Move> moves) {

			if (moves != null) {
				System.out.println("The number of possible moves is " + moves.size() );

				for (int i = 0; i < moves.size(); ++i) {
					System.out.print("(" + moves.get(i).oldX + ", " + moves.get(i).oldY + ") --> " );
					System.out.println("(" + moves.get(i).newX + ", " + moves.get(i).newY + ")" );
				}
			}
			else {
				System.out.println("The number of possible moves is 0");
			}
		}
		
	
}