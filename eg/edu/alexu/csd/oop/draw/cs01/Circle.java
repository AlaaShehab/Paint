package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Carnival Stores
 *
 */
public class Circle extends Shapes implements Cloneable {
	public Circle(){
		prop = new HashMap<>();
		prop.put("RADIUS", 50.0);
	}

	@Override
	public void draw(Graphics canvas) {
		Graphics2D g2 = (Graphics2D) canvas;
	    g2.setStroke(new BasicStroke(3));
		g2.setColor(fillCol);
		g2.fillOval(pos.x, pos.y, prop.get("RADIUS").intValue(), prop.get("RADIUS").intValue());
		g2.setColor(col);
		g2.drawOval(pos.x, pos.y, prop.get("RADIUS").intValue(), prop.get("RADIUS").intValue());

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clonedShape = new Circle();
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
		return new Ellipse2D.Double(pos.x, pos.y, prop.get("RADIUS"), prop.get("RADIUS")).contains(last);
	}
}
