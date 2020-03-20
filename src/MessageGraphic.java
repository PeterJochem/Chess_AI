import java.awt.Font;

/* This method describes the information relevant to displaying an
 * object in the homeScreen/pre-game flow
 */
public class MessageGraphic {

	// This is where we are displaying message in the window
	double xScale;
	double yScale;
	
	Font myFont;
	
	// This is the information, ie string, we are trying to display
	String myContent;
	
	// Constructor
	public MessageGraphic(String myContent, double xScale, double yScale) {
		this.myContent = myContent;
		this.xScale = xScale;
		this.yScale = yScale;
	}
}
