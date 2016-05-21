package connect4;

// Connect4 board and the game logic

// imports necessary for this class
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;


// class definition 
class Board extends Pane {

	// constructor for the class
	public Board() {
		// initialize the background of the board and add it to the board  
		background = new Rectangle();
		background.setFill(Color.BLUE);
		getChildren().addAll(background);

		// initialize board array to the correct size		
		board = new int[boardWidth][boardHeight];
		
		// initialize pieces array to the correct size
		pieces = new Piece[7][6];
		
		// initialize windows array to the correct size
		windows = new Window[7][6];
		
		// for loop to populate all arrays to default values and add the windows to the board		 
		for(int i = 0; i < boardWidth; i++) {
			for(int j = 0; j < boardHeight; j++) {
				board[i][j] = EMPTY;
				pieces[i][j] = null;
				windows[i][j] = new Window();
				getChildren().add(windows[i][j]);
			}
		}
		
		// set the current player to red
		current_player = PlayerRed;
		
		buttonReplay = new ButtonType("Replay", ButtonData.CANCEL_CLOSE);
		Platform.runLater(() -> {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("You Win !");
				alert.setHeaderText(null);
		});
	}

	// override resize method to ensure board appears correctly
	@Override
	public void resize(double width, double height) {
		//call the superclass resize method
		super.resize(width, height);

		// resize the rectangle to take the full size of the widget
		background.setWidth(width); background.setHeight(height);

		
		// calculate the width and height of a cell in which a windows and a piece will sit
		cell_width = width / 7.0;
		cell_height = height / 6.0;

		// nested for loop to reset the sizes and positions of all pieces that were already placed 
		// and update the position of the windows in the board
		for(int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				if (board[i][j] != 0) {
					pieces[i][j].relocate(i * cell_width, j * cell_height);
					pieces[i][j].resize(cell_width, cell_height);
					windows[i][j].relocate(i * cell_width, j * cell_height);
					windows[i][j].resize(cell_width, cell_height);
				} else {
					windows[i][j].relocate(i * cell_width, j * cell_height);
					windows[i][j].resize(cell_width, cell_height);					
				}
			}
		}
	}

	// method for resetting the game
	public void resetGame() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				board[i][j] = 0;
				getChildren().remove(pieces[i][j]);
				pieces[i][j] = null;
			}
		}
	}

	// method that tries to place a piece in the board
	public void placePiece(final double x) {

		// calculate what column the piece should be placed in 
		int indexx = (int) (x / cell_width);
		int indexy = boardHeight - 1;
		// for loop to find the next available row in that column
		while (indexy >= 0) {
		
		// if the available row is less than the height of the board proceed 
			/* if the current player is red
			   put the int value corresponding to the red piece in the relevant position in the board array 
			   put a red piece in the relevant position in the pieces array and resize and relocate the piece			   
			   add the piece to the board 
			   change the current player to yellow
			*/
			if (board[indexx][indexy] == EMPTY && current_player == PlayerRed) {
				board[indexx][indexy] = PlayerRed;
				pieces[indexx][indexy] = new Piece(PlayerRed);
				pieces[indexx][indexy].resize(cell_width, cell_height);
				pieces[indexx][indexy].relocate(indexx * cell_width,  indexy * cell_height);
				getChildren().add(pieces[indexx][indexy]);
				current_player = PlayerYellow;
				break;
			}
			
			/* else if current player is yellow
			   put the int value corresponding to the yellow piece in the relevant position in the board array 
			   put a yellow piece in the relevant position in the pieces array and resize and relocate the piece
			   add the piece to the board 
			   change the current player to red
			 */
			 else if (board[indexx][indexy] == EMPTY && current_player == PlayerYellow) {
				 board[indexx][indexy] = PlayerYellow;
				 pieces[indexx][indexy] = new Piece(PlayerYellow);
				 pieces[indexx][indexy].resize(cell_width, cell_height);
				 pieces[indexx][indexy].relocate(indexx * cell_width,  indexy * cell_height);
				 getChildren().add(pieces[indexx][indexy]);
				 current_player = PlayerRed;
				 break;
				}
			indexy--;
			}
		
		// call the winner method to see if there is a winner
		int winner = winner(indexx, indexy);
		if (winner == 1) {
			if (current_player == PlayerRed) {
				alert.setContentText("The player Yellow wins, click on space to replay.");
			} else {
				alert.setContentText("The player Red wins, click on space to replay.");
			}
			alert.getButtonTypes().setAll(buttonReplay);
			Optional<ButtonType> replay = alert.showAndWait();
			if (replay.get() == buttonReplay) {
				resetGame();
			}

		}
	}
	
	// function to check the pieces on the right direction
	public int check_right(int x, int y) {
		int count = 0;
		int player = 0;
		
		if (current_player == PlayerYellow)
			player = PlayerRed;
		else
			player = PlayerYellow;
		while (x < boardWidth) {
			if (board[x][y] == player)
				count += 1;
			else
				count = 0;
			if (count == 4) {
				break;
			}
			x += 1;
		}
		if (count == 4)
			return 1;
		return 0;
	}
	
	// check the pieces on the top direction
	public int check_up(int x, int y) {
		int count = 0;
		int player = 0;
		
		if (current_player == PlayerYellow)
			player = PlayerRed;
		else
			player = PlayerYellow;
		while (y < boardHeight) {
			if (board[x][y] == player)
				count += 1;
			else
				count = 0;
			if (count == 4)
				break;
			y += 1;
		}
		if (count == 4)
			return 1;
		return 0;
	}
	
	// check the pieces on the left diagonal direction
	public int check_diagoLeft(int x, int y) {
		int count = 0;
		int player = 0;
		
		if (current_player == PlayerYellow)
			player = PlayerRed;
		else
			player = PlayerYellow;
		while (y < boardHeight && x < boardWidth) {
			if (board[x][y] == player)
				count += 1;
			else
				count = 0;
			if (count == 4)
				break;
			if (y != 6)
				y += 1;
			x += 1;
		}
		if (count == 4)
			return 1;
		return 0;
	}
	
	// check the pieces on the right diagonal direction
	public int check_diagoRight(int x, int y) {
		int count = 0;
		int player = 0;
		
		if (current_player == PlayerYellow)
			player = PlayerRed;
		else
			player = PlayerYellow;
		while (y <= 5 && x >= 0) {
			if (board[x][y] == player)
				count += 1;
			else
				count = 0;
			if (count == 4)
				break;
			if (y <= 5)
				y += 1;
			x -= 1;
		}
		if (count == 4)
			return 1;
		return 0;
	}
	
	/* method to determine who the current winner is and display a message (additional variable and/or
	   updates to the layout may be required) - this method should only be attempted on completion of
	   the other steps
	 */
	public int winner(int col , int row){
		int y = 0;
		int x = 0;
		while (y < boardHeight) {
			winner = check_right(x, y);
			if (winner == 1)
				return 1;
			y++;
		}
		y = 0;
		while (x < boardWidth) {
			winner = check_up(x, y);
			if (winner == 1)
				return 1;
			x++;
		}
		x = 0;
		y = 0;
		while (y < boardHeight) {
			while (x < boardWidth) {
				winner = check_diagoLeft(x, y);
				if (winner == 1)
					return 1;
				x += 1;
			}
			x = 0;
			y += 1;
		}
		y = 0;
		while (y < boardHeight) {
			while (x >= 0) {
				winner = check_diagoRight(x, y);
				if (winner == 1)
					return 1;
				x -= 1;
			}
			x = 6;
			y += 1;
		}
		return winner;
	}

	//private fields of the class
	private int boardWidth = 7;			// the width of the Connect4 board
	private int boardHeight = 6;		// the height of the Connect4 board
	private int[][] board; 				// 2D array that holds  int values representing the pieces
	private Piece[][] pieces; 			// 2D array that holds the renders of the pieces
	private Window[][] windows; 		// 2D array that holds all the windows (white circles) for the board 
	private Rectangle background; 		// background of the board
	private double cell_width;			// width of a cell in the board
	private double cell_height; 		// height of a cell in the board
	private int current_player; 		// hold the value of the current player (PlayerRed or Player Yellow) 

	// constants to be inserted into the 2D board array to keep track of the location of cells containing 
	// empty, red and yellow pieces 
	private final int EMPTY = 0;		// 0 is used to indicate that a cell in the board is unoccupied
	private final int PlayerRed = 1;	// 1 is used to indicate that a cell in the board is occupied by a red piece
	private final int PlayerYellow = 2; // 2 is used to indicate that a cell in the board is occupied by a yellow piece
	
	int winner = 0;						// variable to determine who the current winner is: 0 - no current winner, 
										// 1 - red is the current winner, 2 - yellow is the current winner
	
	// private variables to use an alert to inform about the winner
	private ButtonType buttonReplay; // variable to create a replay button on the alert box
	private Alert alert; // variable to create the box alert
		
}