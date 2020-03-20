import java.util.ArrayList;


/* This class describes an AI player of a game.
 * It inherits from the Player class and also contains
 * methods that allow the user to make "intelligent" moves 
 */
public class AIPlayer extends Player {

	// This describes the level of rigor the AI will have
	public int skillLevel;

	// Constructor
	public AIPlayer(String name) {
		super(name);
	}

	/* This method will choose a greedy move for a given
	 * player and execute it
	 * First, we generate all the moves from a given gameState 
	 * and then we get the greedyResponse prediction for each of our moves. 
	 * We now choose the state that leads to the most favorable outcome 
	 */
	public 	Move chooseGreedyMove(GameState currentGame) {

		Player currentPlayer = null;
		ArrayList<Move> allMoves = null;
		ArrayList<TreeNode> terminalNodes = new ArrayList<TreeNode>();

		if ( currentGame.player1Turn ) {
			currentPlayer = currentGame.player1;
			allMoves = generateAllMoves(currentGame, true);
		}
		else {
			currentPlayer = currentGame.player2;
			allMoves = generateAllMoves(currentGame, false);
		}

		GameState newGame = null;
		for (int i = 0; i < allMoves.size(); ++i) {

			newGame = currentGame.duplicate();

			updateGameState(newGame, allMoves.get(i) );
			executeMove(newGame, allMoves.get(i) );
			newGame.player1Turn = !newGame.player1Turn;

			TreeNode newNode = new TreeNode(newGame, null, allMoves.get(i) );

			// Get the opponenet's probable greedyResponse to our move
			Move greedyResponse = chooseGreedyResponse(newGame);
			newGame.player1Turn = !newGame.player1Turn;

			// Add this exchange to the tree
			newNode = new TreeNode(newGame, newNode, greedyResponse);
			terminalNodes.add(newNode);
		}

		// Sort through all the TreeNodes
		TreeNode bestNode = null;
		for (int i = 0; i < terminalNodes.size(); ++i) {

			if (currentGame.player1Turn ) {
				if ( bestNode == null ) {
					bestNode = terminalNodes.get(i);
				}
				else if ( bestNode.score < terminalNodes.get(i).score ) {
					bestNode = terminalNodes.get(i);
				}
				// FIX ME - could shuffle the list??
				// FIX ME
				// FIX ME
				// FIX ME - equal scores
			}
			else {
				if (bestNode == null) {
					bestNode = terminalNodes.get(i);
				}
				else if ( bestNode.score > terminalNodes.get(i).score ) {
					bestNode = terminalNodes.get(i);
				}
			}	
		}

		// Trace back to the original move (the move we should make now)
		Move chosenMove = null;
		TreeNode traversalNode = bestNode;

		if (traversalNode == null || traversalNode.priorMove == null ) {
			return null;
		}

		while ( traversalNode != null) {
			chosenMove = traversalNode.priorMove;
			traversalNode = traversalNode.myPredecessor;
		}

		// Execute the given move
		updateGameState(currentGame, chosenMove);
		executeMove(currentGame, chosenMove);

		return chosenMove;
	}


	/* This method chooses the greedy respone of the opponent. It allows us 
	 * to expand the gameTree. It generates all the moves from the given state
	 * and then chooses the one that leads to the most desirable state at the 
	 * next move for the given player.
	 */
	public Move chooseGreedyResponse(GameState currentGame) {

		Player currentPlayer;
		ArrayList<TreeNode> terminalNodes = new ArrayList<TreeNode>();
		ArrayList<Move> allMoves = null;

		if ( currentGame.player1Turn ) {
			currentPlayer = currentGame.player1;
			allMoves = generateAllMoves(currentGame, true);
		}
		else {
			currentPlayer = currentGame.player2;
			allMoves =  generateAllMoves(currentGame, false);
		}

		GameState newGame = null;
		// execute each move and score each move
		for (int i= 0; i < allMoves.size(); ++i) {

			newGame = currentGame.duplicate();
			updateGameState(newGame, allMoves.get(i) );
			executeMove(newGame, allMoves.get(i) );
			newGame.player1Turn = !newGame.player1Turn;

			TreeNode newNode = new TreeNode(newGame, null, allMoves.get(i) );
			terminalNodes.add(newNode);
		}

		Move chosenMove = null;

		// Sort through the game tree to find the most desirable state
		TreeNode bestNode = null;
		for (int i = 0; i < terminalNodes.size(); ++i) {
			if (currentGame.player1Turn ) {
				if ( bestNode == null ) {
					bestNode = terminalNodes.get(i);
				}
				else if ( bestNode.score < terminalNodes.get(i).score ) {
					bestNode = terminalNodes.get(i);
				}
				// TO DO - could shuffle the list??
				// TO DO
				// TO DO
				// TO DO - equal scores
			}
			else {
				if (bestNode == null ) {
					bestNode = terminalNodes.get(i);
				}
				else if ( bestNode.score > terminalNodes.get(i).score ) {
					bestNode = terminalNodes.get(i);
				}
			}	
		}

		/* This is indicates an end of game state. Return null
		* to indicate this to the calling method */
		if ( bestNode == null ) {
			return null;
		}

		// Execute the given move
		chosenMove = bestNode.priorMove;
		updateGameState(currentGame, chosenMove);
		executeMove(currentGame, chosenMove);

		return chosenMove;
	}


	/* This method generates all the possible moves for the AI
	 * from a given GameState, chooses one of them randomly
	 * and then executes it 
	 * 
	 * This method is for testing and experimenting 
	 */
	public Move chooseRandomMove(GameState currentGame) {

		ArrayList<Move> allMoves = new ArrayList<Move>();

		Player currentPlayer;

		if ( currentGame.player1Turn ) {
			currentPlayer = currentGame.player1;
		}
		else {
			currentPlayer = currentGame.player2;
		}

		// King
		if ( currentPlayer.king != null ) {
			addMoves(allMoves, currentPlayer.king.generateMoves(currentGame) );
		}

		// Queen
		if ( currentPlayer.queens != null ) {
			for (int i = 0; i < currentPlayer.queens.size(); ++i) {
				addMoves(allMoves, currentPlayer.queens.get(i).generateMoves(currentGame) );
			}
		}

		// Bishops 
		if ( currentPlayer.bishops != null ) {
			for (int i = 0; i < currentPlayer.bishops.size(); ++i) {
				addMoves(allMoves, currentPlayer.bishops.get(i).generateMoves(currentGame) );
			}
		}

		// Knights
		if ( currentPlayer.knights != null ) {
			for (int i = 0; i < currentPlayer.knights.size(); ++i) {
				addMoves(allMoves, currentPlayer.knights.get(i).generateMoves(currentGame) );
			}
		}

		// Rooks
		if ( currentPlayer.rooks != null ) {
			for (int i = 0; i < currentPlayer.rooks.size(); ++i) {
				addMoves(allMoves, currentPlayer.rooks.get(i).generateMoves(currentGame) );
			}
		}

		// Pawns
		if ( currentPlayer.pawns != null ) {
			for (int i = 0; i < currentPlayer.pawns.size(); ++i) {
				addMoves(allMoves, currentPlayer.pawns.get(i).generateMoves(currentGame) );
			}
		}

		int randomIndex = (int) Math.round( Math.random() * allMoves.size() );

		if ( randomIndex == allMoves.size()  ) {
			randomIndex = randomIndex - 1;
		}

		if (allMoves.size() == 0) {
			return null;
		}

		Move chosenMove = allMoves.get(randomIndex);

		updateGameState(currentGame, chosenMove);
		executeMove(currentGame, chosenMove);

		// End Game check. Returns null to indicate no moves are possible
		if ( endGameCheck(currentGame)  ) {				
			return null;
		}	

		return chosenMove;
	}

	/* This method executes a given move by updating all
	 * the data structures tracking a player and it's pieces.
	 * chosenMove is the given move we want to execute
	 */
	public void executeMove(GameState currentGame, Move chosenMove)  {

		Player currentPlayer;

		if ( currentGame.player1Turn ) {
			currentPlayer = currentGame.player1;
			currentPlayer.possibleMoves = new ArrayList<Move>();
		}
		else {
			currentPlayer = currentGame.player2;
			currentPlayer.possibleMoves = new ArrayList<Move>();
		}

		// King
		if ( currentPlayer.king != null && currentPlayer.king.dead != true && currentPlayer.king.x == chosenMove.oldX
				&&  currentPlayer.king.y == chosenMove.oldY ) {

			currentPlayer.king.x = chosenMove.newX;
			currentPlayer.king.y = chosenMove.newY;
			currentPlayer.possibleMoves.add(chosenMove);

			// Remove the captured pieces from the board
			for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
				removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
			}

			return;
		}

		// Queen
		for (int i = 0 ; i < currentPlayer.queens.size(); ++i) {
			if ( currentPlayer.queens != null && currentPlayer.queens.get(i).dead != true && currentPlayer.queens.get(i).x == chosenMove.oldX
					&&  currentPlayer.queens.get(i).y == chosenMove.oldY ) {

				currentPlayer.queens.get(i).x = chosenMove.newX;
				currentPlayer.queens.get(i).y = chosenMove.newY;
				currentPlayer.possibleMoves.add(chosenMove);

				// Remove the captured pieces from the board
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
				currentPlayer.possibleMoves.add(chosenMove);

				// Remove the captured pieces from the board
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
				currentPlayer.possibleMoves.add(chosenMove);

				// Remove the captured pieces from the board
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
				currentPlayer.possibleMoves.add(chosenMove);

				// Remove the captured pieces from the board
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
				currentPlayer.possibleMoves.add(chosenMove);

				// Remove the captured pieces from the board
				for (int j = 0; j < chosenMove.capturedPieces.size(); ++j) {
					removePiece(currentGame, chosenMove.capturedPieces.get(j).x, chosenMove.capturedPieces.get(j).y);
				}

				// Piece upgrade code. This checks if we have a pawn that reached the other side of the board
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

	/* This method is called to decide on a move for an AI to make.
	 * It expands the gameTree from the current state and chooses 
	 * the most favorable move
	 * 
	 * currentGame is state of the game when we call this method
	 * Depth -> how may levels to expand the gameTree
	 * isPlayer1 -> is the AI we are optimizing for player1 or player2
	 * 
	 * Returns the move we should execute now
	 */
	public Move search(GameState currentGame, int depth, boolean isPlayer1) {

		// dupicate the current game's state
		GameState duplicate = currentGame.duplicate();

		// This is the list of ALL the nodes at the given depth
		ArrayList<TreeNode> terminalNodes = expandTree(duplicate, new ArrayList<TreeNode>(), null,
				depth, isPlayer1);

		/* This is a subset of the terminalNodes. It is a list of the most 
		*  favorable nodes in the game tree for the given player. If multiple nodes 
		*  have the same score, then all will go into this list
 		*/
		ArrayList<TreeNode> bestNodes = new ArrayList<TreeNode>();

		/* Traverse all the terminal nodes in the game tree. Find the one 
		 * that is the most favorable. If multiple have the same score, then
		 * put both/all in the bestNodes list
		 */
		for (int i = 0; i < terminalNodes.size(); ++i) {

			// On the loop's first iteration, add the given node
			if ( bestNodes.size() == 0) {
				bestNodes.add(terminalNodes.get(i) );
			}
			else if ( currentGame.player1Turn ) {
				if ( bestNodes.get(0).score < terminalNodes.get(i).score ) {
					bestNodes = new ArrayList<TreeNode>();
					bestNodes.add(terminalNodes.get(i) );
				}
				else if ( bestNodes.get(0).score == terminalNodes.get(i).score ) {
					bestNodes.add(terminalNodes.get(i) );
				}
			}
			else if ( !currentGame.player1Turn ) {
				if ( bestNodes.get(0).score > terminalNodes.get(i).score ) {
					bestNodes = new ArrayList<TreeNode>();
					bestNodes.add(terminalNodes.get(i) );
				}
				else if ( bestNodes.get(0).score == terminalNodes.get(i).score ) {
					bestNodes.add(terminalNodes.get(i) );
				}
			}
		}

		/* This breaks ties, of multiple nodes have the same score,
		 * choose randomnly between them 
		 */
		int randomIndex = (int) (Math.random() * bestNodes.size());
		if ( randomIndex == bestNodes.size() ) {
			randomIndex--;
		}
		TreeNode chosenTreeNode = bestNodes.get(randomIndex);

		/* This indicates we are at an end of game. Return null to indicate  
		 * this to the calling method 
		 */
		if ( terminalNodes == null || terminalNodes.size() == 0 ) {
			return null;
		}

		// Trace back to the move we should make now from the given
		// gameState to get to this most desirable state in the future
		Move chosenMove = chosenTreeNode.priorMove;
		// This is an end game scenario. Return null to indicate this
		if ( chosenTreeNode.priorMove == null ) {
			return null;
		}
		while (chosenTreeNode.myPredecessor != null) {
			chosenTreeNode = chosenTreeNode.myPredecessor;
			chosenMove = chosenTreeNode.priorMove;
		}

		// Execute the given move
		updateGameState(currentGame, chosenMove);
		executeMove(currentGame, chosenMove);

		// End Game check
		if ( endGameCheck(currentGame)  ) {		
			return null;
		}

		return chosenMove;
	}

	/* This method will expand the given tree
	 * It returns the list of all the terminal states in 
	 * the tree at a given depth
	 * This implements a breath first search/expansion of the game tree
	 */
	public ArrayList<TreeNode> expandTree(GameState currentGame, ArrayList<TreeNode> terminalNodes, 
			TreeNode myPredecessor, int depth, boolean isPlayer1) {

		// Base case 
		if ( depth <= 0 ) {
			// add the node to the list of terminal states 
			terminalNodes.add(new TreeNode(currentGame, myPredecessor, myPredecessor.priorMove) );
			return terminalNodes;
		}

		// generateAllMoves
		ArrayList<Move> allMoves = generateAllMoves(currentGame, isPlayer1);

		// Edge Cases
		if ( allMoves.size() == 0 ) {
			if ( myPredecessor == null ) {
				terminalNodes.add(myPredecessor);
			}
			else {
				terminalNodes.add(myPredecessor);
			}
			return terminalNodes;
		}

		// For each move, execute it and get the opponent's greedy response
		GameState newGame = null;
		for (int i = 0; i < allMoves.size(); ++i) {

			// Duplicate the GameState
			newGame = currentGame.duplicate();

			// Execute the chosenMove
			updateGameState(newGame, allMoves.get(i) );
			executeMove(newGame, allMoves.get(i) );

			// Get the greedy response - this also executes it
			newGame.player1Turn = !newGame.player1Turn;
			Move greedyResponse = chooseGreedyMove(newGame);

			/* Then we are at an end of game scenario and we can stop recursing
			 * Simply add to the game tree and let the method return
			 */
			if (greedyResponse == null) {
				terminalNodes.add(new TreeNode(newGame, myPredecessor, allMoves.get(i) ) );	
			}
			else {
				// Add/Update the game tree
				TreeNode newNode = new TreeNode(newGame, myPredecessor, allMoves.get(i) );

				// call expandTree - recurse!
				newGame.player1Turn = !newGame.player1Turn;
				expandTree(newGame, terminalNodes, newNode, depth - 1, isPlayer1);
			}
		}

		return terminalNodes;
	}

	/* This method goes through the board
	 * and generates all the possible moves 
	 * from a given state and returns them 
	 * all in an ArrayList
	 */
	public ArrayList<Move> generateAllMoves(GameState currentGame, boolean isPlayer1) {

		ArrayList<Move> allMoves = new ArrayList<Move>();
		Player currentPlayer = null;

		if ( isPlayer1 ) {
			currentPlayer = currentGame.player1;
		}
		else {
			currentPlayer = currentGame.player2;
		}

		// King
		if ( currentPlayer.king != null && (!currentPlayer.king.dead)  ) {
			addMoves(allMoves, currentPlayer.king.generateMoves(currentGame) );
		}

		// Queen
		for (int i = 0 ; i < currentPlayer.queens.size(); ++i) {
			if ( currentPlayer.queens != null && !currentPlayer.queens.get(i).dead  ) {
				addMoves(allMoves, currentPlayer.queens.get(i).generateMoves(currentGame) );
			}
		}

		// Rooks
		if ( currentPlayer.rooks != null) {
			for (int i = 0; i < currentPlayer.rooks.size(); ++i) {
				addMoves(allMoves, currentPlayer.rooks.get(i).generateMoves(currentGame) );
			}
		}

		// Bishops
		if ( currentPlayer.bishops != null) {
			for (int i = 0; i < currentPlayer.bishops.size(); ++i) {
				addMoves(allMoves, currentPlayer.bishops.get(i).generateMoves(currentGame) );
			}
		}

		// Knights
		if ( currentPlayer.knights != null) {
			for (int i = 0; i < currentPlayer.knights.size(); ++i) {
				addMoves(allMoves, currentPlayer.knights.get(i).generateMoves(currentGame) );
			}
		}

		// Pawns
		if ( currentPlayer.pawns != null) {
			for (int i = 0; i < currentPlayer.pawns.size(); ++i) {
				addMoves(allMoves, currentPlayer.pawns.get(i).generateMoves(currentGame) );
			}
		}

		return allMoves;
	}

	/*  This method is called when a piece is captured and 
	 *  we want to remove he piece from the lists of pieces 
	 *  and from the board
	 */
	public void removePiece(GameState currentGame, int x, int y) {

		Player currentPlayer;

		// NOTE! This is because when it's P1's turn, we remove P2's pieces
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
			if ( currentPlayer.queens != null && currentPlayer.queens.get(i).dead != true && currentPlayer.queens.get(i).x == x
					&&  currentPlayer.queens.get(i).y == y ) {

				// here is a comment
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

	/* This method takes two lists and merges the second
	 * one into the first one
	 */
	public void addMoves(ArrayList<Move> allMoves, ArrayList<Move> subset) {

		if (subset == null) {
			return;
		}

		for (int i = 0; i < subset.size(); ++i) {
			allMoves.add(subset.get(i) );
		}	
	}

	/* This method does the same as the method above 
	 * but if the move involves a capture, also adds the capture move
	 * to the list of capture moves
	 */
	public void addMoves(ArrayList<Move> allMoves, ArrayList<Move> allJumpMoves, ArrayList<Move> subsetOfAll) {

		if (subsetOfAll == null) {
			return;
		}

		for (int i = 0; i < subsetOfAll.size(); ++i) {
			allMoves.add(subsetOfAll.get(i) );
			if ( subsetOfAll.get(i).capturedPieces.size() > 0) {
				allJumpMoves.add(subsetOfAll.get(i) );
			}
		}
	}

	/* This method checks whether a GameState is 
	 *  over or not
	 *  The game is over if a player is in check and cannot 
	 *  move his/her Queen
	 */
	public boolean endGameCheck(GameState currentGame) {

		if ( isKingInCheck(currentGame) && !canKingMove(currentGame) ) {
			return true;
		}

		return false;
	}

	/* This method checks whether or not 
	 * a king is in check at a given GameState
	 */
	public boolean isKingInCheck(GameState currentGame) {

		Player currentPlayer = null;
		if ( currentGame.player1Turn  ) {
			currentPlayer = currentGame.player2;
		}
		else {
			currentPlayer = currentGame.player1;
		}

		// Edge Case/Errors
		if ( currentPlayer.king == null ) {
			return true;
		}
		else if ( currentPlayer.king.dead ) {
			return true;
		}

		/* The move from this turn just executed
		* generate all the moves for opponent
		* go through the list and see if any of the moves will 
		* capture the king
		*/

		ArrayList<Move> allMoves = generateAllMoves(currentGame, currentGame.player1Turn);

		for (int i = 0; i < allMoves.size(); ++i) {
			if ( allMoves.get(i).newX == currentPlayer.king.x &&  
					allMoves.get(i).newY == currentPlayer.king.y ) {
				return true;
			}
		}

		return false;
	}


	/* This method checks whether the current player's 
	 * king can make a move or not
	 * This method is used as part of when we check if a game is over
	 */
	public boolean canKingMove(GameState currentGame) {

		Player currentPlayer = null;

		if ( currentGame.player1Turn ) {
			currentPlayer = currentGame.player1;
		}
		else {
			currentPlayer = currentGame.player2;
		}

		// Edge Case/Errors
		if ( currentPlayer.king == null ) {
			System.out.println("Error, king is null when we are "
					+ "checking if the king can move");
		}
		else if ( currentPlayer.king.dead ) {
			System.out.println("Error, king is dead when we are "
					+ "checking if king can move");
		}

		if ( king.generateMoves(currentGame).size() > 0) {
			return true;
		}

		return false;
	}

	
	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}



}
		