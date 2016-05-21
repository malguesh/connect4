package connect4;

//Connect 4 piece 

//imports 
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;

// class definition 
class Piece extends Group {

	// constructor for the class
	public Piece(int type) {
		// store the type, make a new Ellipse and Translate, add the Translate to the Ellipse, add the Ellipse to the Group
		this.type = type;
		e = new Ellipse();
		pos = new Translate();

		if (this.type == 1) {
			getChildren().addAll(e);
			e.getTransforms().add(pos);
			e.setFill(Color.RED);
		} else {
			getChildren().addAll(e);
			e.getTransforms().add(pos);
			e.setFill(Color.YELLOW);
		}
	}

	// overridden version of the resize method which resizes the piece
	@Override
	public void resize(double width, double height) {
		// call the super class method and update the centre and radius of the ellipse representing the piece
		super.resize(width, height);
		
		e.setCenterX(width / 2); e.setCenterY(height / 2);
		e.setRadiusX(width / 2); e.setRadiusY(height / 2);
	}

	//overridden version of the relocate method
	@Override
	public void relocate(double x, double y) {
		// call the superclass method and update the relevant transform
		super.relocate(x, y);
		
		pos.setX(x); pos.setY(y);
	}

	//private fields of the class
	private Ellipse e; 		//ellipse for rendering this piece
	private int type; 		//maintain a copy of the piece type (red or yellow) we have
	private Translate pos; 	//translate to set the position of this piece
}