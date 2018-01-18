package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Ellipse extends Shapes implements Cloneable{

	public Ellipse(){
		prop = new HashMap<>();
		prop.put("R1", 80.0);
		prop.put("R2", 50.0);
	}

	@Override
	public void draw(Graphics canvas) {
		Graphics2D g2 = (Graphics2D) canvas;
	    g2.setStroke(new BasicStroke(3));
		g2.setColor(fillCol);
		g2.fillOval(pos.x, pos.y, prop.get("R1").intValue(), prop.get("R2").intValue());
		g2.setColor(col);
		g2.drawOval(pos.x, pos.y, prop.get("R1").intValue(), prop.get("R2").intValue());

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clonedShape = new Ellipse();
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
		return new Ellipse2D.Double(pos.x, pos.y, prop.get("R1"), prop.get("R2")).contains(last);
	}
}
