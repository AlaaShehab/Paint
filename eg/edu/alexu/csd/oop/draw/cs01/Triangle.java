package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Triangle extends Shapes implements Cloneable {
	private int[] x;
	private int[] y;

	public Triangle(){

		prop = new HashMap<>();
		prop.put("Px1", 150.0);
		prop.put("Py1", 150.0);
		prop.put("Px2", 200.0);
		prop.put("Py2", 200.0);


	}

	@Override
	public void draw(Graphics canvas) {
		x = new int[3];

		x[0] = pos.x;
		x[1] = prop.get("Px1").intValue();
		x[2] = prop.get("Px2").intValue();

		y = new int[3];
		y[0] = pos.y;
		y[1] = prop.get("Py1").intValue();
		y[2] = prop.get("Py2").intValue();

		final int three = 3;

		Graphics2D g2 = (Graphics2D) canvas;
	    g2.setStroke(new BasicStroke(3));
		g2.setColor(fillCol);
		g2.fillPolygon(x, y, three);
		g2.setColor(col);
		g2.drawPolygon(x, y, three);

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape clonedShape = new Triangle();
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
		return new Polygon(x,y,3).contains(last);
	}


}
