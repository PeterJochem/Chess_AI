import java.util.*;
import java.applet.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


/* This class describes the graphics associated with the game
 * All the code manipulating graphics lives here
 */
public class UserInterface extends Applet implements MouseListener, Runnable {

	// Graphics object to access the applet
	Graphics g;

	public static Game currentGame = null;

	// Is the user viewing the homeScreen
	boolean homeScreenOn = true;

	// Is the user simulating a series
	public static boolean runningSeries = false;

	// Is the user playing a game and it's his/her turn
	public static boolean waitingHumanMove = true;

	// This is the current series we are running
	Series currentSeries;

	// Object tracks objects in the home screen
	HomeScreen homeScreen = new HomeScreen();

	// Home Screen Image
	Image homeScreenImg;

	// Load the images representing the pieces
	Image WhiteQueenDark;
	Image WhiteQueenLight; 
	Image BlackQueenDark;
	Image BlackQueenLight; 

	Image WhiteKingDark;
	Image WhiteKingLight;
	Image BlackKingDark;
	Image BlackKingLight;

	Image WhiteBishopDark;
	Image WhiteBishopLight; 
	Image BlackBishopDark;
	Image BlackBishopLight;

	Image WhiteRookLight;
	Image WhiteRookDark;
	Image BlackRookLight; 
	Image BlackRookDark;

	Image WhiteKnightLight; 
	Image WhiteKnightDark;
	Image BlackKnightLight; 
	Image BlackKnightDark;

	Image WhitePawnLight;
	Image WhitePawnDark;
	Image BlackPawnLight;
	Image BlackPawnDark;

	// Blank pieces
	Image light;
	Image dark;

	/* These are objects describing the flashing borders 
	 * around selected squares. These indicate a given square 
	 * is/has been indicated by the user
	 */
	FlashingSquare square1 = new FlashingSquare(0, 0, false);
	FlashingSquare square2 = new FlashingSquare(0, 0, false);

	/* These describe the dimensions of each cell in the board
	 * They are percentages of the whole window
	 */
	int squareX =  (int) (1.0/8.0 * getSize().width);
	int squareY = (int)(1.0/8.0 * getSize().height);

	/* These are the number of cells in each
	 *  row/number of columns  of cells/squares
	 */
	int boardX = 8;
	int boardY = 8;

	// This records if we have loaded the images yet
	boolean imagesLoaded = false;

	// Records if the game has been created
	boolean startRan = false;

	/* This method runs when the applet is created 
	 * (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {

		// Add a listener for keyBoard input
		addMouseListener(this);

		System.out.println("Welcome to Chess!");

		// Call method with high level control flow
		start();
	} 


	/* This method will draw the current board
	 * SideNote: When we draw to the applet, it must be from a 
	 * method called paint(Graphics g). Also, there is no need 
	 * to actually call the paint method 
	 */
	public void paint(Graphics g) {

		// Re-set these variables so the graphics change with changes in the window's size
		squareX =  (int) (1.0/8.0 * getSize().width);
		squareY = (int)(1.0/8.0 * getSize().height);

		// Only load the images from disk once
		if ( !imagesLoaded ) {

			this.imagesLoaded = true;

			homeScreenImg = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/homeScreen.jpg"); 
			WhiteQueenDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/QueenDark.png"); 
			WhiteQueenLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/QueenLight.png");
			BlackQueenDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/QueenDark.png"); 
			BlackQueenLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/QueenLight.png"); 

			WhiteKingDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/KingDark.png");
			WhiteKingLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/KingLight.png");
			BlackKingDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/KingDark.png");
			BlackKingLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/KingLight.png");

			WhiteBishopDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/BishopDark.png");
			WhiteBishopLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/BishopLight.png");
			BlackBishopDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/BishopDark.png");
			BlackBishopLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/BishopLight.png");

			WhiteRookLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/RookLight.png"); 
			WhiteRookDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/RookDark.png");
			BlackRookLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/RookLight.png"); 
			BlackRookDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/RookDark.png");

			WhiteKnightLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/KnightLight.png");
			WhiteKnightDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/KnightDark.png");
			BlackKnightLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/KnightLight.png");
			BlackKnightDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/KnightDark.png");

			WhitePawnLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/PawnLight.png");
			WhitePawnDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/White/PawnDark.png");
			BlackPawnLight = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/PawnLight.png");
			BlackPawnDark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Black/PawnDark.png");

			// Blank pieces
			light = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Light.png");
			dark = getImage(getDocumentBase(), "/Users/peterpennrichjochem/Desktop/Chess/Dark.png");
		}

		int windowHeight = getSize().height;
		int windowWidth = getSize().width;

		// Display the homeScreen to the user
		if ( homeScreenOn ) {

			g.drawImage(homeScreenImg, 0, 0, windowWidth, windowHeight, this);

			g.setFont( new Font("TimesRoman", Font.BOLD, ( (int)(0.3 * squareX) ) ) );
			Graphics2D g2 = (Graphics2D) g;

			g2.setStroke(new BasicStroke(7) );
			g2.setColor(Color.white);

			// Draw the homeScreen objects
			for (int i = 0; i < homeScreen.allMessages.size(); ++i) {
				g2.drawString(homeScreen.allMessages.get(i).myContent, (int)(squareX * homeScreen.allMessages.get(i).xScale),
						(int)(squareY * homeScreen.allMessages.get(i).yScale) );	
			}				
		}
		else if ( currentGame != null ){
			// The user wants to play a single game

			// First, lay down a blank board
			for (int i = 0; i < boardX; ++i) { 
				for (int j = 0; j < boardY; ++j) {

					if ( (i + j) % 2 == 0) {
						g.drawImage(dark, i * squareX, j * squareY, squareX, squareY, this);
					}
					else {
						g.drawImage(light, i * squareX, j * squareY, squareX, squareY, this);
					}
				}
			}

			// Then, write over the blank board

			// Draw the Queens
			for (int i = 0; i < currentGame.player1.queens.size(); ++i) {
				if ( currentGame.player1.queens.get(i) != null && !currentGame.player1.queens.get(i).dead ) {
					if ( (currentGame.player1.queens.get(i).x + currentGame.player1.queens.get(i).y) % 2 == 0) {
						g.drawImage(WhiteQueenDark, currentGame.player1.queens.get(i).x * squareX, 
								currentGame.player1.queens.get(i).y * squareY, squareX, squareY, this);
					}
					else {
						g.drawImage(WhiteQueenLight, currentGame.player1.queens.get(i).x * squareX, 
								currentGame.player1.queens.get(i).y * squareY, squareX, squareY, this);
					}
				}
			}

			for (int i = 0; i < currentGame.player2.queens.size(); ++i) {
				if ( currentGame.player2.queens.get(i) != null && !currentGame.player2.queens.get(i).dead ) {
					if ( (currentGame.player2.queens.get(i).x + currentGame.player2.queens.get(i).y) % 2 == 0) {
						g.drawImage(BlackQueenDark, currentGame.player2.queens.get(i).x * squareX,
								currentGame.player2.queens.get(i).y * squareY, squareX, squareY, this);
					}
					else {
						g.drawImage(BlackQueenLight, currentGame.player2.queens.get(i).x * squareX, 
								currentGame.player2.queens.get(i).y * squareY, squareX, squareY, this);
					}
				}
			}

			// Draw the Kings
			if (currentGame.player1.king != null && !currentGame.player1.king.dead ) {
				if ( (currentGame.player1.king.x + currentGame.player1.king.y) % 2 == 0) {
					g.drawImage(WhiteKingDark, currentGame.player1.king.x * squareX, 
							currentGame.player1.king.y * squareY, squareX, squareY, this);
				}
				else {
					g.drawImage(WhiteKingLight, currentGame.player1.king.x * squareX, 
							currentGame.player1.king.y * squareY, squareX, squareY, this);
				}
			}

			if (currentGame.player2.king != null && !currentGame.player2.king.dead ) {
				if ( (currentGame.player2.king.x + currentGame.player2.king.y) % 2 == 0 ) {
					g.drawImage(BlackKingDark, currentGame.player2.king.x * squareX, 
							currentGame.player2.king.y * squareY, squareX, squareY, this);
				}
				else {
					g.drawImage(BlackKingLight, currentGame.player2.king.x * squareX, 
							currentGame.player2.king.y * squareY, squareX, squareY, this);
				}
			}

			// Draw the Bishops
			if ( currentGame.player1.bishops != null) {
				for (int i = 0; i < currentGame.player1.bishops.size(); ++i) {
					if ( !currentGame.player1.bishops.get(i).dead ) {
						if ( (currentGame.player1.bishops.get(i).x + currentGame.player1.bishops.get(i).y) % 2 == 0) {
							g.drawImage(WhiteBishopDark, currentGame.player1.bishops.get(i).x * squareX, 
									currentGame.player1.bishops.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(WhiteBishopLight, currentGame.player1.bishops.get(i).x * squareX,
									currentGame.player1.bishops.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}
			}

			if ( currentGame.player2.bishops != null) {
				for (int i = 0; i < currentGame.player2.bishops.size(); ++i) {	
					if ( !currentGame.player2.bishops.get(i).dead ) {
						if ( (currentGame.player2.bishops.get(i).x + currentGame.player2.bishops.get(i).y) % 2 == 0 ) {
							g.drawImage(BlackBishopDark, currentGame.player2.bishops.get(i).x * squareX, 
									currentGame.player2.bishops.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(BlackBishopLight, currentGame.player2.bishops.get(i).x * squareX, 
									currentGame.player2.bishops.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}
			}

			// Draw the Rooks
			if ( currentGame.player1.rooks != null) {
				for (int i = 0; i < currentGame.player1.rooks.size(); ++i) {	
					if ( !currentGame.player1.rooks.get(i).dead ) {
						if (  (currentGame.player1.rooks.get(i).x + currentGame.player1.rooks.get(i).y) % 2 == 0  ) {
							g.drawImage(WhiteRookDark, currentGame.player1.rooks.get(i).x * squareX, 
									currentGame.player1.rooks.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(WhiteRookLight, currentGame.player1.rooks.get(i).x * squareX,
									currentGame.player1.rooks.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}
			}

			if ( currentGame.player2.rooks != null) {
				for (int i = 0; i < currentGame.player2.rooks.size(); ++i) {	
					if ( !currentGame.player2.rooks.get(i).dead ) {
						if (  (currentGame.player2.rooks.get(i).x + currentGame.player2.rooks.get(i).y) % 2 == 0  ) {
							g.drawImage(BlackRookDark, currentGame.player2.rooks.get(i).x * squareX,
									currentGame.player2.rooks.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(BlackRookLight, currentGame.player2.rooks.get(i).x * squareX,
									currentGame.player2.rooks.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}	
			}

			//Draw the Knights
			if ( currentGame.player1.knights != null) {
				for (int i = 0; i < currentGame.player1.knights.size(); ++i) {	
					if ( !currentGame.player1.knights.get(i).dead ) {
						if ( (currentGame.player1.knights.get(i).x + currentGame.player1.knights.get(i).y) % 2 == 0 ) { 
							g.drawImage(WhiteKnightDark, currentGame.player1.knights.get(i).x * squareX, 
									currentGame.player1.knights.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(WhiteKnightLight, currentGame.player1.knights.get(i).x * squareX, 
									currentGame.player1.knights.get(i).y * squareY, squareX, squareY, this);
						}		
					}
				}
			}

			if (currentGame.player2.knights != null) {
				for (int i = 0; i < currentGame.player2.knights.size(); ++i) {
					if (!currentGame.player2.knights.get(i).dead) {
						if ((currentGame.player2.knights.get(i).x + currentGame.player2.knights.get(i).y) % 2 == 0) {
							g.drawImage(BlackKnightDark, currentGame.player2.knights.get(i).x * squareX,
									currentGame.player2.knights.get(i).y * squareY, squareX, squareY, this);
						} 
						else {
							g.drawImage(BlackKnightLight, currentGame.player2.knights.get(i).x * squareX,
									currentGame.player2.knights.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}
			}

			// Draw the Pawns 
			if (currentGame.player1.pawns != null) {
				for (int i = 0; i < currentGame.player1.pawns.size(); ++i) {	
					if ( !currentGame.player1.pawns.get(i).dead ) {
						if ( (currentGame.player1.pawns.get(i).x + currentGame.player1.pawns.get(i).y) % 2 == 0 ) {
							g.drawImage(WhitePawnDark, currentGame.player1.pawns.get(i).x * squareX,
									currentGame.player1.pawns.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(WhitePawnLight, currentGame.player1.pawns.get(i).x * squareX,
									currentGame.player1.pawns.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}
			}

			if (currentGame.player2.pawns != null) {
				for (int i = 0; i < currentGame.player2.pawns.size(); ++i) {	
					if ( !currentGame.player2.pawns.get(i).dead ) {
						if  ( (currentGame.player2.pawns.get(i).x + currentGame.player2.pawns.get(i).y) % 2 == 0 ) {   

							// x-Location, y-Location, x-size, y-size
							g.drawImage(BlackPawnDark, currentGame.player2.pawns.get(i).x * squareX, 
									currentGame.player2.pawns.get(i).y * squareY, squareX, squareY, this);
						}
						else {
							g.drawImage(BlackPawnLight, currentGame.player2.pawns.get(i).x * squareX, 
									currentGame.player2.pawns.get(i).y * squareY, squareX, squareY, this);
						}
					}
				}
			}	

			// Draw the flashing squares
			if (square1.on) {
				// change the thickness of the border
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(7));
				g.drawRect(square1.x * squareX, square1.y * squareY, squareX, squareY);
				// square1.on = false;
			}
			if (square2.on) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(7));
				g.drawRect(square2.x * squareX, square2.y * squareY, squareX, squareY);		
			}
		}

		/* Then we are playing a single game and we should draw the indicator
		 *  on the locations pointed to by the user
		 */
		if ( !this.runningSeries && currentGame != null ) {

			for (int i = 0; i < currentGame.player1.possibleMoves.size(); ++i) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(7) );
				g2.setColor(Color.blue);
				g.drawRect(currentGame.player1.possibleMoves.get(i).newX * squareX, currentGame.player1.possibleMoves.get(i).newY * squareY, 
						squareX, squareY);
				g.drawRect(currentGame.player1.possibleMoves.get(i).oldX * squareX, currentGame.player1.possibleMoves.get(i).oldY * squareY, 
						squareX, squareY);
			}

			for (int i = 0; i < currentGame.player2.possibleMoves.size(); ++i) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(7) );
				g2.setColor(Color.orange);
				g.drawRect(currentGame.player2.possibleMoves.get(i).oldX * squareX, currentGame.player2.possibleMoves.get(i).oldY * squareY, 
						squareX, squareY);
				g.drawRect(currentGame.player2.possibleMoves.get(i).newX * squareX, currentGame.player2.possibleMoves.get(i).newY * squareY, 
						squareX, squareY);
			}
		}
	} 


	/* This method will set up the board in the 
	 * traditional way for the game's start
	 */
	public void initializeBoard(Game currentGame, Player player1, Player player2) {

		// Construct the player's pieces
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
		this.currentGame.currentGame = new GameState(currentGame, true);
	}


	/* This method is only for testing, it is called from 
	 * start and it loads a custom board as indicated by the 
	 * file, ./boardInput.txt
	 * This allows us to input interesting board configurations 
	 * and see what the system does and if it makes sense
	 */
	public void loadCustomBoard(Game curretGame, Player player1, Player player2) {

		File file = new File("/Users/peterpennrichjochem/Desktop/Chess/boardInput.txt");
		Scanner scnr = null;
		try {
			scnr = new Scanner(file);
		} 
		catch (FileNotFoundException e) {
			System.out.println("Load custom board: file not found");
			return;
		}

		String currentLine = null;
		int currentRow = 0;
		int currentColumn = 0;

		// Scan in th file and process each item
		while (scnr.hasNextLine() ) {
			currentLine = scnr.nextLine();

			// split the string on each blank space
			String[] entries = currentLine.split(" ");
			for (currentColumn = 0; currentColumn < 8; ++currentColumn) {
				if ( entries[currentColumn].equals("WR") ) {
					player1.rooks.add(new Rook(player1.name) );
					player1.rooks.get(player1.rooks.size() - 1).x = currentColumn;
					player1.rooks.get(player1.rooks.size() - 1).y = currentRow;
				}	
				else if ( entries[currentColumn].equals("BR") ) {
					player2.rooks.add(new Rook(player2.name) );
					player2.rooks.get(player2.rooks.size() - 1).x = currentColumn;
					player2.rooks.get(player2.rooks.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("WN") ) {
					player1.knights.add(new Knight(player1.name) );
					player1.knights.get(player1.knights.size() - 1).x = currentColumn;
					player1.knights.get(player1.knights.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("BN") ) {
					player2.knights.add(new Knight(player2.name) );
					player2.knights.get(player2.knights.size() - 1).x = currentColumn;
					player2.knights.get(player2.knights.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("WB") ) {
					player1.bishops.add(new Bishop(player1.name) );
					player1.bishops.get(player1.bishops.size() - 1).x = currentColumn;
					player1.bishops.get(player1.bishops.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("BB") ) {
					player2.bishops.add(new Bishop(player2.name) );
					player2.bishops.get(player2.bishops.size() - 1).x = currentColumn;
					player2.bishops.get(player2.bishops.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("WK") ) {
					player1.king = new King(player1.name);
					player1.king.x = currentColumn;
					player1.king.y = currentRow;
				}
				else if ( entries[currentColumn].equals("BK") ) {
					player2.king = new King(player2.name);
					player2.king.x = currentColumn;
					player2.king.y = currentRow;
				}
				else if ( entries[currentColumn].equals("WQ") ) {
					player1.queens.add(new Queen(player1.name) );
					player1.queens.get(player1.queens.size() - 1).x = currentColumn;
					player1.queens.get(player1.queens.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("BQ") ) {
					player2.queens.add( new Queen(player2.name) );
					player2.queens.get(player2.queens.size() - 1).x = currentColumn;
					player2.queens.get(player2.queens.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("WP") ) {
					player1.pawns.add(new Pawn(player1.name) );
					player1.pawns.get(player1.pawns.size() - 1).x = currentColumn;
					player1.pawns.get(player1.pawns.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("BP") ) {
					player2.pawns.add(new Pawn(player2.name) );
					player2.pawns.get(player2.pawns.size() - 1).x = currentColumn;
					player2.pawns.get(player2.pawns.size() - 1).y = currentRow;
				}
				else if ( entries[currentColumn].equals("EM") ) {
					// the square is empty
				}
			}

			currentRow++;
		}

		// set the GameState variable 
		this.currentGame.currentGame = new GameState(currentGame, true);
	}


	/* This method will run when the user clicks the mouse on the applet
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent event) {

		if ( homeScreenOn ) {

			if ( playSingleGameClicked(event) ) {

				Player player1 = new Player("DummyName1");
				Player player2 = new AIPlayer("DummyName2");

				currentGame = new Game(player1, player2, true);
				initializeBoard(currentGame, currentGame.player1, currentGame.player2);

				Thread t1 = new Thread(this); 
				t1.start();

				this.homeScreenOn = false;
			}
			else if ( simulateSeriesClicked(event) ) {

				Player player1 = new AIPlayer("DummyName1");
				Player player2 = new AIPlayer("DummyName2");

				currentSeries = new Series(player1, player2, 10);

				currentGame = currentSeries.currentGame;
				currentSeries.startSeries();

				this.homeScreenOn = false;
				Thread t1 = new Thread(this); 
				t1.start();

				this.runningSeries = true;
			}
		}
		else if ( currentGame != null & waitingHumanMove ){
			int cordX = getCordX(event);
			int cordY = getCordY(event);

			/* search player1 and player2's pieces to see
			 * if any of them have a piece in the position clicked
			 * by the user and then generate all the possible moves. 
			 * If the player has a piece in this postion, we 
			 * will indicate graphically which positons this piece 
			 * can move to on the board
			 */

			GameState currentState; 

			currentState = currentGame.currentGame;

			// Check the King
			if (currentGame.player1.king != null && currentGame.player1.king.x == cordX && currentGame.player1.king.y == cordY && 
					!currentGame.player1.king.dead  ) {
				ArrayList<Move> moves = currentGame.player1.king.generateMoves(currentState);
				addMoves(moves, true);
			}

			// Check the Queen
			for (int i = 0 ; i < currentGame.player1.queens.size(); ++i) {
				if ( currentGame.player1.queens.get(i) != null && currentGame.player1.queens.get(i).x == cordX && currentGame.player1.queens.get(i).y == cordY && 
						!currentGame.player1.queens.get(i).dead  ) {
					ArrayList<Move> moves = currentGame.player1.queens.get(i).generateMoves(currentState);
					addMoves(moves, true);
				}
			}

			// Check if a bishop is at this location
			for (int i = 0; i < currentGame.player1.bishops.size(); ++i) { 
				if ( currentGame.player1.bishops.get(i)!= null && currentGame.player1.bishops.get(i).x == cordX && currentGame.player1.bishops.get(i).y == cordY && 
						!currentGame.player1.bishops.get(i).dead  ) {
					ArrayList<Move> moves = currentGame.player1.bishops.get(i).generateMoves(currentState);
					addMoves(moves, true);
				}
			}

			// Check if a knight is at this location
			for (int i = 0; i < currentGame.player1.knights.size(); ++i) { 
				if ( currentGame.player1.knights.get(i) != null && currentGame.player1.knights.get(i).x == cordX && currentGame.player1.knights.get(i).y == cordY && 
						!currentGame.player1.knights.get(i).dead  ) {
					ArrayList<Move> moves = currentGame.player1.knights.get(i).generateMoves(currentState);
					addMoves(moves, true);
				}   
			}

			// Check if a pawn is at this location
			for (int i = 0; i < currentGame.player1.pawns.size(); ++i  ) {
				if (currentGame.player1.pawns.get(i) != null && currentGame.player1.pawns.get(i).x == cordX && currentGame.player1.pawns.get(i).y == cordY && 
						!currentGame.player1.pawns.get(i).dead  ) {
					ArrayList<Move> moves = currentGame.player1.pawns.get(i).generateMoves(currentState);
					addMoves(moves, true);
				}   
			}

			// Check if a rook is at this location
			for (int i = 0; i < currentGame.player1.rooks.size(); ++i  ) {
				if ( currentGame.player1.rooks.get(i) != null && currentGame.player1.rooks.get(i).x == cordX && currentGame.player1.rooks.get(i).y == cordY && 
						!currentGame.player1.rooks.get(i).dead  ) {
					ArrayList<Move> moves = currentGame.player1.rooks.get(i).generateMoves(currentState);
					addMoves(moves, true);
				}	   
			} 

			/* There a few cases for when a location on the board is clicked on
			 *  Case 1 - the user has already clicked on her/his piece at a valid 
			 *  location and NOW clicks 
			 *  
			 *  and then clicks on a spot where he/she cannot move that
			 *  piece to. In this case, turn the indicator off on original piece 
			 *  and all the places that this piece could have moved
			 *  
			 *  Case - 1 - The user has already clicked on a location where 
			 *  her/his piece already is. The user then clicks on a spot to move 
			 *  his or her piece. Check if the move is legal and if so, execute it
			 *  If illegal, turn off all the indicators and execute no move
			 *  
			 *  Case - 2 - The user has no indicators on and he/she clicks on a piece.
			 *  If it really is her/his piece, display/indicate where all he/she could 
			 *  move the given piece now
			 */

			// Case - 1 
			if ( square1.on ) {

				square2.x = cordX;
				square2.y = cordY;
				square2.on = false;
				square1.on = false;

				Move myDestination = isMoveLegal(square2.x, square2.y);

				if ( myDestination != null ) {
					currentGame.player1.executeHumanMove(currentGame.currentGame, myDestination);
				}

				currentGame.player1.possibleMoves = new ArrayList<Move>();
				currentGame.player1.possibleMoves = new ArrayList<Move>();
			}
			else { 
				// Case - 2: Turn square 1 on	
				square1.on = true;
				square1.x = cordX;
				square1.y = cordY;
			}
		}	   
	}

	/* This method takes a given mouseEvent and figures out 
	 * the x-coordinate of where the user tried to click.
	 * The mouseEvent x location comes in terms of pixels but 
	 * we need to convert this to a coordinate [0,7] on the board 
	 */
	public int getCordX(MouseEvent event) {

		int cordX = 0;

		if (event.getX() < 100) {
			cordX = 0;
		}
		else if ( event.getX() > 1 * squareX && event.getX() < 2 * squareX ) {
			cordX = 1;
		}
		else if ( event.getX() > 2 * squareX  && event.getX() < 3 * squareX ) {
			cordX = 2;
		}
		else if ( event.getX() > 3 * squareX  && event.getX() < 4 * squareX ) {
			cordX = 3;
		}
		else if ( event.getX() > 4 * squareX && event.getX() < 5 * squareX ) {
			cordX = 4;
		}
		else if ( event.getX() > 5 * squareX && event.getX() < 6 * squareX ) {
			cordX = 5;
		}
		else if ( event.getX() > 6 * squareX && event.getX() < 7 * squareX ) {
			cordX = 6;
		}
		else if ( event.getX() > 7 * squareX && event.getX() < 8 * squareX ) {
			cordX = 7;
		}

		return cordX;
	}

	/* This method takes a given mouseEvent and figures out 
	 * the y-coordinate of where the user tried to click.
	 * The mouseEvent y location comes in terms of pixels but 
	 * we need to convert this to a coordinate [0,7] on the board 
	 */
	public int getCordY(MouseEvent event) {

		int cordY = 0;

		if (event.getY() < 1 * squareY) {
			cordY = 0;
		}
		else if ( event.getY() > 1 * squareY && event.getY() < 2 * squareY ) {
			cordY = 1;
		}
		else if ( event.getY() > 2 * squareY && event.getY() < 3 * squareY ) {
			cordY = 2;
		}
		else if ( event.getY() > 3 * squareY && event.getY() < 4 * squareY ) {
			cordY = 3;
		}
		else if ( event.getY() > 4 * squareY && event.getY() < 5 * squareY) {
			cordY = 4;
		}
		else if ( event.getY() > 5 * squareY && event.getY() < 6 * squareY  ) {
			cordY = 5;
		}
		else if ( event.getY() > 6 * squareY && event.getY() < 7 * squareY ) {
			cordY = 6;
		}
		else if ( event.getY() > 7 * squareY && event.getY() < 8 * squareY  ) {
			cordY = 7;
		}

		return cordY;
	}

	/* This method is called to check if a given mouseEvent 
	 * was the user clicking the playSingleGame button
	 * Return true if user clicked it
	 * Return false otherwise
	 */
	public boolean playSingleGameClicked (MouseEvent event) {

		if ( event.getX() <=  (squareX * 3.3) 
				&& event.getX() >= homeScreen.playSingleGame.xScale * (squareX * 0.8)  ) {
			if ( event.getY() <= homeScreen.playSingleGame.yScale * (squareY * 1) 
					&&  event.getY() >= homeScreen.playSingleGame.yScale * (squareY * 0.8)  ) {
				return true;
			}	
		}

		return false;
	}

	/* This method is called to check if a given mouseEvent 
	 * was the user clicking the simulateSeries button
	 * Return true if user clicked it
	 * Return false otherwise
	 */
	public boolean simulateSeriesClicked(MouseEvent event) {

		if ( event.getX() <=  (squareX * 3.3) 
				&& event.getX() >= homeScreen.simulate.xScale * (squareX * 0.8)  ) {
			if ( event.getY() <= homeScreen.simulate.yScale * (squareY * 1) 
					&&  event.getY() >= homeScreen.simulate.yScale * (squareY * 0.8)  ) {
				return true;
			}	
		}

		return false;
	}

	/* This method checks whether the move the human 
	 * wants to do is legal or not
	 * If legal, it returns this move
	 * If not legal, it returns null
	 */
	public Move isMoveLegal(int x, int y) {

		// Traverse player1's possible moves to see if any of them are match the human's desired move
		for (int i = 0; i < currentGame.player1.possibleMoves.size(); ++i) {

			if (currentGame.player1.possibleMoves.get(i).newX == x &&  currentGame.player1.possibleMoves.get(i).newY == y) {
				return currentGame.player1.possibleMoves.get(i);
			}
		}

		return null;
	}


	/* This method adds a list of Moves to the player's
	 * list of possible moves from a given spot 
	 */
	public void addMoves( ArrayList<Move> moves, boolean player1Turn) {

		currentGame.player1.possibleMoves = new ArrayList<Move>();
		currentGame.player2.possibleMoves = new ArrayList<Move>();

		if ( player1Turn ) {
			for (int i = 0; i < moves.size(); ++i) {
				currentGame.player1.possibleMoves.add(moves.get(i) );
			}
		}
		else {
			for (int i = 0; i < moves.size(); ++i) {
				currentGame.player1.possibleMoves.add(moves.get(i) );
			}
		}
	}


	/* This method checks if a piece is at a given
	 * location. If it is at the location, it returns true
	 * Else, it returns false
	 */
	public boolean coordinatesMatch(int x, int y, Piece piece) {

		if ( piece.x == x && piece.y == y && !piece.dead) { 
			return true;
		}

		return false;
	}

	/* I started a new thread with the sole 
	 * job of repainting the board
	 * This is the method that runs when
	 * we create a new thread in UserInterface
	 */
	@Override
	public void run() {

		while (true) {
			//wait(1);
			repaint();
		}	
	}

	/* This method just waits for a given number 
	 * of seconds before returning
	 */
	public void wait(int seconds) {

		try {
			TimeUnit.SECONDS.sleep(seconds);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*  The following mouseEvent methods need to be inlcuded 
	 * for the UserInterface class to implement Runnable
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}


