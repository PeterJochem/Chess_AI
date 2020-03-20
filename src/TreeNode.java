
/* This class describes a node in the game tree. For example,
 * when we expand the game tree and all the possible states of the game 
 * over the next N moves, we get a tree structure of these TreeNode objects
 */
public class TreeNode {

	/* This describes the value of the gameState
	* More positive is good for P1. More negative is good for P2
	* See the score method for more details on how this is scored
	*/
	public int score = 0;
	
	/* This field allows us to back track within the game tree structure
	* myPredecessor is the parent node of the given node
	*/
	TreeNode myPredecessor = null;
	
	/* This is the move leading from myPredecessor to the current treeNode
	 * This helps us backtrack and know which move to execute
	 */
	Move priorMove = null;
	
	// Constructor
	public TreeNode(GameState currentGame, TreeNode myPredecessor, Move priorMove) {
		this.score = currentGame.score();
		this.myPredecessor = myPredecessor;
		this.priorMove = priorMove;
	}	
}
