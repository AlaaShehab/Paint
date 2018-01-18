/**
 *
 */
package eg.edu.alexu.csd.oop.draw.paintcontroller;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs01.Circle;
import eg.edu.alexu.csd.oop.draw.cs01.Ellipse;
import eg.edu.alexu.csd.oop.draw.cs01.Line;
import eg.edu.alexu.csd.oop.draw.cs01.Rectangle;
import eg.edu.alexu.csd.oop.draw.cs01.Square;
import eg.edu.alexu.csd.oop.draw.cs01.Triangle;

/**
 * @author Personal
 *
 */
public class ShapeFactory {

	public Shape getShape(String shape) {

		 if(shape == null){
	         return null;
	      }
	      if(shape.equalsIgnoreCase("CIRCLE")){
	         return new Circle();

	      } else if(shape.equalsIgnoreCase("RECTANGLE")){
	         return new Rectangle();

	      } else if(shape.equalsIgnoreCase("SQUARE")){
	         return new Square();

	      } else if(shape.equalsIgnoreCase("ELLIPSE")){
		         return new Ellipse();

		  } else if(shape.equalsIgnoreCase("LINE")){
		         return new Line();

		  } else if(shape.equalsIgnoreCase("TRIANGLE")){
		         return new Triangle();
		  }

		return null;
	}

}
