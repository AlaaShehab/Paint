package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Line extends Shapes implements Cloneable {
	public Line(){
		prop = new HashMap<>();
		prop.put("XEND", 50.0);
		prop.put("YEND", 50.0);
	}

	@Override
	public void draw(Graphics canvas) {
		Graphics2D g2 = (Graphics2D) canvas;
	    g2.setColor(col);
        g2.setStroke(new BasicStroke(5));
        g2.draw(new Line2D.Float(pos.x, pos.y, prop.get("XEND").intValue(), prop.get("YEND").intValue()));
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clonedShape = new Line();
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
		return new Line2D.Double(pos.x, pos.y, prop.get("XEND"), prop.get("YEND")).contains(last);
	}

}
