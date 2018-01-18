package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Carnival Stores
 *
 */
public abstract class Shapes implements Shape, Cloneable {

	protected Point pos = new Point(100, 1000);
	protected Map<String, Double> prop;
	protected Color col = new Color(0, 0, 0);
	protected Color fillCol = new Color(0, 0, 0);

	@Override
	public void setPosition(final Point position) {
		if (position != null) {
			pos = position;
		}

	}

	@Override
	public Point getPosition() {
		return pos;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		if (properties != null) {
			prop.putAll(properties);
		}

	}

	@Override
	public Map<String, Double> getProperties() {
		return prop;
	}

	@Override
	public void setColor(Color color) {
		if (color != null) {
			col = color;
		}

	}

	@Override
	public Color getColor() {
		return col;
	}

	@Override
	public void setFillColor(Color color) {
		if (fillCol != null) {
			fillCol = color;
		}

	}

	@Override
	public Color getFillColor() {
		return fillCol;
	}


	@Override
	public void draw(Graphics canvas) {
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}

	public boolean contains(Point last) {
		return false;

	}

}
