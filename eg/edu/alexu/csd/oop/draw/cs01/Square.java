package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Carnival Stores
 *
 */
public class Square extends Shapes implements Cloneable{

	public Square(){
		prop = new HashMap<>();
		prop.put("LENGTH", 50.0);
	}

	@Override
	public void draw(Graphics canvas) {
		Graphics2D g2 = (Graphics2D) canvas;
	    g2.setStroke(new BasicStroke(3));
		g2.setColor(fillCol);
		g2.fillRect(pos.x, pos.y, prop.get("LENGTH").intValue(), prop.get("LENGTH").intValue());
		g2.setColor(col);
		g2.drawRect(pos.x, pos.y, prop.get("LENGTH").intValue(), prop.get("LENGTH").intValue());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clonedShape = new Square();
		clonedShape.setColor(col);
		clonedShape.setFillColor(fillCol);
		clonedShape.setPosition(pos);
        Map newprop = new HashMap<>();
        for (Map.Entry s: prop.entrySet())
            newprop.put(s.getKey(), s.getValue());
        clonedShape.setProperties(newprop);
        return clonedShape;
	}

	@Override
	public boolean contains (Point last) {
		int [] x = new int[4];
		int [] y = new int[4];
		x[0] = pos.x;
		x[1] = (int) (pos.x + prop.get("LENGTH"));
		x[2] = x[0];
		x[3] = x[1];

		y[0] = pos.y;
		y[2] = (int) (pos.y - prop.get("LENGTH"));
		y[1] = y[0];
		y[3] = y[2];
		return new Rectangle(pos.x, pos.y, prop.get("LENGTH").intValue(), prop.get("LENGTH").intValue()).contains(last);
	}

}
