/**
 *
 */
package eg.edu.alexu.csd.oop.draw.cs01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.paintcontroller.ShapeFactory;

/**
 * @author Personal
 *
 */
public class DEImplementation implements DrawingEngine{

	private final int MAX_SAVED_M = 20;
	public Stack<ArrayList<Shape>> undo = new Stack<ArrayList<Shape>>();
	private Stack<ArrayList<Shape>> redo = new Stack<ArrayList<Shape>>();
	public ArrayList<Shape> drawnShapes = new ArrayList<Shape>();

	public boolean undoAvailable = false;
	public boolean redoAvailable = false;
	public boolean noFile = false;

	private ShapeFactory factory = new ShapeFactory();

	public List<Class<? extends Shape>> dynamicClass = new ArrayList<Class<? extends Shape>>();

	public DEImplementation() {
		undo.add(new  ArrayList<Shape>());
	}

	@Override
	public void refresh(Graphics canvas) {
		canvas.clearRect(0, 0, 1250, 550);
		for (int i = 0; i < drawnShapes.size(); i++) {
			drawnShapes.get(i).draw(canvas);
		}

	}

	private void updateStack() {
		if (undo.size() + redo.size() <= MAX_SAVED_M) {
			ArrayList<Shape> temp = new ArrayList<Shape>();
			for (int i = 0; i < drawnShapes.size(); i++) {
				temp.add(drawnShapes.get(i));
			}
			undo.push(temp);
		} else {
			if (!undo.isEmpty()) {
				undo.remove(0);
			}
			ArrayList<Shape> temp = new ArrayList<Shape>();
			for (int i = 0; i < drawnShapes.size(); i++) {
				temp.add(drawnShapes.get(i));
			}
			undo.push(temp);
		}
		checkundoRedo();
	}

	@Override
	public void addShape(Shape shape) {
		redo.clear();
		drawnShapes.add(shape);
		updateStack();
	}

	@Override
	public void removeShape(Shape shape) {
		redo.clear();
		for (int i = 0; i < drawnShapes.size(); i++) {
			if (shape.equals(drawnShapes.get(i))) {
				drawnShapes.remove(i);
			}
		}
		updateStack();
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		redo.clear();
		for (int i = 0; i < drawnShapes.size(); i++) {
			if (oldShape.equals(drawnShapes.get(i))) {
				drawnShapes.add(i, newShape);
				drawnShapes.remove(i + 1);
			}
		}
		updateStack();

	}

	@Override
	public Shape[] getShapes() {
		if (drawnShapes == null) {
			return null;
		}
		Shape[] shapes = new Shape[drawnShapes.size()];
		for (int i = 0; i < drawnShapes.size(); i++) {
			shapes[i] = drawnShapes.get(i);
		}
		return shapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> list = new ArrayList<Class<? extends Shape>>();
		list.add(Circle.class);
		list.add(Line.class);
		list.add(Rectangle.class);
		list.add(Square.class);
		list.add(Triangle.class);
		list.add(Ellipse.class);
		return list;
	}

	@Override
	public void undo() {
		ArrayList<Shape> temp = new ArrayList<Shape>();
		if (undo.size() > 1) {
			redo.push(undo.pop());
			drawnShapes.clear();
			temp = undo.peek();
			for (int i = 0; i < temp.size(); i++) {
				drawnShapes.add(temp.get(i));
			}
		}
		checkundoRedo();

	}

	@Override
	public void redo() {
		ArrayList<Shape> temp = new ArrayList<Shape>();
		if (redo.size() > 0) {
			temp = redo.pop();
			undo.push(temp);
			drawnShapes.clear();
			for (int i = 0; i < temp.size(); i++) {
				drawnShapes.add(temp.get(i));
			}
		}
		checkundoRedo();
	}

	@Override
	public void save(String path) {
		noFile = false;
		String[] temp = path.split("\\.");
		String p = temp[temp.length - 1];

		if (p.equalsIgnoreCase("xml")) {
			saveToXml(path);
		} else if (p.equalsIgnoreCase("json")) {
			saveToJson(path);
		} else {
			noFile = true;
		}
	}

	private void saveToJson(String path) {
		FileWriter filename = null;
		try {
			filename = new FileWriter(path, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedWriter write = new BufferedWriter(filename);
			startFile(write);

			writeShapes(write);
			endFile(write);
	}

	private void startFile(BufferedWriter write) {
		try {
			write.write("{");
			write.newLine();
			write.write("\"Shapes\": [");
			write.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void endFile(BufferedWriter write) {
		try {
			write.write("]");
			write.newLine();
			write.write("}");
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int[] getRBGcol(int index) {
		int[] colors = new int[3];
		colors[0] = drawnShapes.get(index).getColor().getRed();
		colors[1] = drawnShapes.get(index).getColor().getGreen();
		colors[2] = drawnShapes.get(index).getColor().getBlue();
		return colors;
	}

	private int[] getRBGfillCol(int index) {
		int[] colors = new int[3];
		colors[0] = drawnShapes.get(index).getFillColor().getRed();
		colors[1] = drawnShapes.get(index).getFillColor().getGreen();
		colors[2] = drawnShapes.get(index).getFillColor().getBlue();
		return colors;
	}

	private void writeShapes(BufferedWriter write) {

		for (int i = 0; i < drawnShapes.size(); i++) {
			try {

				write.write("{");
				write.newLine();

				write.write("\"class\": " + "\"" + drawnShapes.get(i).toString() + "\"" + ",");
				write.newLine();

				if (drawnShapes.get(i).getColor() == null) {
					write.write("\"col\": " + null + ",");
					write.newLine();
				} else {
					int[] colors = getRBGcol(i);
					write.write("\"col\": " + "[" + colors[0] + ", " + colors[1] + ", " + colors[2] + "]" + ",");
					write.newLine();
				}

				if (drawnShapes.get(i).getFillColor() == null) {
					write.write("\"fillCol\": " + null + ",");
					write.newLine();
				} else {
					int[] colors = getRBGfillCol(i);
					write.write("\"fillCol\": " + "[" + colors[0] + ", " + colors[1] + ", " + colors[2] + "]" + ",");
					write.newLine();
				}

				if (drawnShapes.get(i).getPosition() != null) {
					write.write("\"pos\": " + "{");
					write.newLine();
					write.write("\"x\": " + drawnShapes.get(i).getPosition().x +",");
					write.newLine();
					write.write("\"y\": " + drawnShapes.get(i).getPosition().y);
					write.newLine();
					write.write("}"+ ",");
					write.newLine();
				} else {
					write.write("\"pos\": " + "{");
					write.newLine();
					write.write("\"x\": " + -1234 +",");
					write.newLine();
					write.write("\"y\": " + -1234);
					write.newLine();
					write.write("}"+ ",");
					write.newLine();
				}

				write.write("\"prop\": " + "{");
				write.newLine();
				int c = 1;
				for (Map.Entry s: (drawnShapes.get(i)).getProperties().entrySet()) {
			        write.write("\"" + s.getKey().toString() + "\"" + ": " + s.getValue().toString());
			       	 if (drawnShapes.get(i).getProperties().size() != c) {
			       		 write.write(",");
			       	 }
			       	 c++;
			       	 write.newLine();
			    }
				write.write("}");

				write.newLine();
				write.write("}");

				if (i != drawnShapes.size() - 1) {
					write.write(",");
				}
				write.newLine();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveToXml(String path) {
		FileOutputStream filename = null;
		try {
			filename = new FileOutputStream(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedOutputStream write = new BufferedOutputStream(filename);
		XMLEncoder encode = new XMLEncoder(write);
		encode.writeObject(drawnShapes);
		encode.close();
	}

	@Override
	public void load(String path) {
		noFile = false;
		drawnShapes.clear();
		undo.clear();
		redo.clear();

		String[] temp = path.split("\\.");
		String p = temp[temp.length - 1];
		if (p.equalsIgnoreCase("xml")) {
			loadToXml(path);
		} else if (p.equalsIgnoreCase("json")) {
			try {
				loadToJson(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			noFile = true;
		}
		updateStack();

	}

	private void loadToXml(String path) {
		FileInputStream filename = null;
		try {
			filename = new FileInputStream(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedInputStream read = new BufferedInputStream(filename);

		XMLDecoder decode = new XMLDecoder(read, null, null, this.getClass().getClassLoader());
		drawnShapes = new ArrayList<Shape>();
		drawnShapes = (ArrayList<Shape>) decode.readObject();
		decode.close();

	}

	private void loadToJson(String path) throws IOException {
		FileReader filename = null;
		try {
			filename = new FileReader(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader read = new BufferedReader(filename);

		String temp;
		temp = read.readLine();
		temp = read.readLine();


		while ((temp = read.readLine()).equals("{")) {
			String[] input = read.readLine().replace("\"", "").replace(",", "").replace(" ", "").split(":");
			input = input[1].split("\\.");
			input = input[input.length - 1].split("@");
			Shape shape = factory.getShape(input[0]);

			if (shape == null) {
				drawnShapes.add(shape);
				continue;

			}

			input = read.readLine().replace(" ", "").replace("[", "").replace("]", "").split(":");
			if (!input[1].contains("n")) {
				input = input[1].split(",");
				int r = Integer.parseInt(input[0]);
				int g = Integer.parseInt(input[1]);
				int b = Integer.parseInt(input[2]);
				Color color = new Color(r, g, b);
				shape.setColor(color);
			}

			input = read.readLine().replace(" ", "").replace("[", "").replace("]", "").split(":");
			if (!input[1].contains("n")) {
				input = input[1].split(",");
				int r = Integer.parseInt(input[0]);
				int g = Integer.parseInt(input[1]);
				int b = Integer.parseInt(input[2]);
				Color color = new Color(r, g, b);
				shape.setFillColor(color);
			}
			temp = read.readLine();

			Point position = new Point();
			input = read.readLine().replace(" ", "").replace(",", "").split(":");
			if (!input[1].contains("-")) {
				position.x = Integer.parseInt(input[1]);
				input = read.readLine().replace(" ", "").replace(",", "").split(":");
				position.y = Integer.parseInt(input[1]);
			} else {
				temp = read.readLine();
			}
			shape.setPosition(position);

			temp = read.readLine();
			temp = read.readLine();

			Map<String, Double> properties = new HashMap<String, Double>();
			while (!(temp = read.readLine()).equals("}")) {
				input = temp.replace(" ", "").replace("\"", "").replace(",", "").split(":");
				properties.put(input[0], Double.parseDouble(input[1]));

			}
			read.readLine();
			shape.setProperties(properties);

			drawnShapes.add(shape);

		}
		read.close();
	}

	public void checkundoRedo() {
		if (undo.size() <= 1) {
			undoAvailable = false;
		} else {
			undoAvailable = true;
		}

		if (redo.isEmpty()) {
			redoAvailable = false;
		} else {
			redoAvailable = true;
		}
	}

	public void addJAR(String pathToJar) throws IOException, ClassNotFoundException {

		dynamicClass.clear();

		JarFile jarFile = new JarFile(pathToJar);
		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		int i = 0;
		while (e.hasMoreElements()) {
		    JarEntry je = e.nextElement();
		    if(je.isDirectory() || !je.getName().endsWith(".class")){
		        continue;
		    }
		    // -6 because of .class
		    String className = je.getName().substring(0,je.getName().length()-6);
		    className = className.replace('/', '.');
		    Class c = cl.loadClass(className);
		    if (c.isInterface() || Modifier.isAbstract(c.getModifiers())) {
		    	continue;
		    }
		    dynamicClass.add(c);
		}
	}

	public void specialAdd(Shape oldShape,Shape newShape) {
		ArrayList<Shape> temp = new ArrayList<Shape>();
		 for (int i = 0; i < drawnShapes.size(); i++) {
			 if (drawnShapes.get(i).equals(oldShape)) {
				 continue;
			 }
				temp.add(drawnShapes.get(i));
		}
		 for (int i = 0; i < undo.size(); i++) {
			 for (int j = 0; j < undo.get(i).size(); j++) {
				 if (undo.get(i).get(j).equals(oldShape)) {
					 undo.get(i).remove(j);
					 undo.get(i).add(newShape);
				 }
			 }
		 }
		 temp.add(newShape);
		 undo.add(undo.size() - 1, temp);
	}
}
