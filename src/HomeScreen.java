import java.awt.Font;
import java.util.ArrayList;

/* This class describes the graphic objects 
 * that are in the home screen
 */
public class HomeScreen {

	MessageGraphic message1;
	MessageGraphic playSingleGame;
	MessageGraphic simulate;
	MessageGraphic message4;
	
	// Use a list to store all the MessageGraphics
	ArrayList<MessageGraphic> allMessages;
	
	// Constructor
	public HomeScreen() {
		
		this.message1 = new MessageGraphic("WELCOME TO PETER'S CHESS ENGINE", 1, 1);
		this.playSingleGame = new MessageGraphic("PLAY SINGLE GAME", 0.2, 3);
		this.simulate = new MessageGraphic("SIMULATE SERIES", 0.2, 4);
		this.message4 = new MessageGraphic("DEVELOPED BY PETER JOCHEM", 0.2, 7.7);
		
		this.allMessages = new ArrayList<MessageGraphic>();
		
		allMessages.add(message1);
		allMessages.add(playSingleGame);
		allMessages.add(simulate);
	//	allMessages.add(message4);
	}
	
	
}
