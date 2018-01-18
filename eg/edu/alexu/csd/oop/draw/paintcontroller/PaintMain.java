/**
 *
 */
package eg.edu.alexu.csd.oop.draw.paintcontroller;

import eg.edu.alexu.csd.oop.draw.cs01.DEImplementation;
import eg.edu.alexu.csd.oop.draw.paintview.View;

/**
 * @author Personal
 *
 */
public class PaintMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DEImplementation model = new DEImplementation();
		View view = new View();

		Controller control = new Controller(model, view);
		view.setVisible(true);

	}

}
