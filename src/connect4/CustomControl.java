package connect4;

/* Custom control for the Connect 4 board 
 * This will have a CustomControlSkin & a Board
 * The control will detect mouse and keyboard interactions and overlay the board 
*/

//imports for the class
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

//class definition
class CustomControl extends Control {
	//constructor 
	public CustomControl() {
		// set a default skin, generate a board and add the board to the control
		setSkin(new CustomControlSkin(this));
		
		connect4Board = new Board();
		
		getChildren().add(connect4Board);

		// mouse clicked event handler that will try to place a piece on the board
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				connect4Board.placePiece(event.getX());
			}
		});
	
		// key stroke event handler which will call the restGame method when the space bar is pressed
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.SPACE)
					connect4Board.resetGame();
			}
		});
	}

	//override the resize method
	@Override
	public void resize(double width, double height) {
		// call the super class method and update the size of the board
		super.resize(width, height);
		connect4Board.resize(width, height);
	}

	//private fields of the class
	private Board connect4Board;	// a Connect 4 board
}
