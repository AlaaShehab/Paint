/**
 *
 */
package eg.edu.alexu.csd.oop.draw.paintcontroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs01.DEImplementation;
import eg.edu.alexu.csd.oop.draw.paintview.View;

/**
 * @author Personal
 *
 */
public class Controller extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DEImplementation model;
	private View view;
	private ShapeFactory factory;
	private Contain containShape = new Contain();

	private Point position = null;
	private int lineCounter = 0;
	private int triangleCounter = 0;
	private int maxPropSize = 0;
	private Point p = null;

	private String command;
	private String preCommand = "";

	private Shape newShape = null;
	private Shape selectedShape = null;

	private boolean line = false;
	private boolean triangle = false;
	private boolean fomNewAdded = false;
	private boolean dragging = false;
	private boolean cloning = false;
	private boolean deleting = false;
	private boolean resize = false;

	private Double[] dimensions;
	private int counter = 0;

	Map<String, Double> triprop = new HashMap<String, Double>();

	public Controller (DEImplementation m, View v) {
		view = v;
		model = m;
		factory = new ShapeFactory();
		view.actions(new MultiAction());

		MouseClick mc = new  MouseClick();
		view.c.addMouseListener(mc);
		view.c.addMouseMotionListener(mc);
	}

	public class MultiAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			command = e.getActionCommand();
			String[] temp = command.split(" ");
			if (!command.equals(preCommand) && !command.equals("outline")
					&& !command.equals("fill")){
				if (temp.length != 2) {
					reset();
				}
			}
        	if(command.equals(view.b.load.getActionCommand())
        			|| command.equals(view.m.loadMenuItem.getActionCommand())) {
        		load();
        		paintComponent(view.c.getGraphics());

        	} else if(command.equals(view.b.save.getActionCommand())
        			|| command.equals(view.m.saveMenuItem.getActionCommand())) {
        		save();
        		paintComponent(view.c.getGraphics());
        	} else if (command.equals(view.b.undo.getActionCommand())
        			|| command.equals(view.m.undoMenuItem.getActionCommand())) {
        		model.undo();
        		paintComponent(view.c.getGraphics());

        	} else if (command.equals(view.b.redo.getActionCommand())
        			|| command.equals(view.m.redoMenuItem.getActionCommand())) {
        		model.redo();
        		paintComponent(view.c.getGraphics());
        	} else if (command.equals(view.b.refresh.getActionCommand())
        			|| command.equals(view.m.refreshMenuItem.getActionCommand())) {
        		paintComponent(view.c.getGraphics());
        	} else if (command.equals(view.b.newfile.getActionCommand())
        			|| command.equals(view.m.newMenuItem.getActionCommand())) {
        		model = new DEImplementation();
        		view.setDynamicShapesM(model.drawnShapes);
        		view.resetMenu();
        		view.clearCanvas();
        		checkUndoRedo();

        	} else if (command.equals(view.b.outlineshape.getActionCommand())) {
        			changeOutlineColor();
        			paintComponent(view.c.getGraphics());
        	} else if (command.equals(view.b.fillshape.getActionCommand())) {
        		changeFillColor();
        		paintComponent(view.c.getGraphics());
        	} else if (command.equals(view.b.background.getActionCommand())) {
        		setBackground();
        		paintComponent(view.c.getGraphics());
        	} else if (command.equals(view.m.shapeMenuItem.getActionCommand())) {
        		try {
					AddShape();
					if (!model.dynamicClass.isEmpty()) {
						view.setMenu(model.dynamicClass.size(), model.dynamicClass);
						view.newShapesActions(new newShapesAction());
					}
				} catch (ClassNotFoundException e1) {
				} catch (IOException e1) {
				}
        	}  else if (command.equals(view.m.cloneMenuItem.getActionCommand())
        			|| command.equals(view.b.clone.getActionCommand())) {
        		cloning = true;
        	} else if (command.equals(view.m.removeMenuItem.getActionCommand())
        			|| command.equals(view.b.remove.getActionCommand())) {
        			deleting = true;
        	} else if(command.equals(view.b.line.getActionCommand())) {
        		line = true;
        	} else if (command.equals(view.b.triangle.getActionCommand())) {
        		triangle = true;
        	} else if (command.equals(view.b.drag.getActionCommand())
        			|| command.equals(view.m.dragMenuItem.getActionCommand())) {
        		dragging = true;
        	} else if (command.equals(view.b.resize.getActionCommand())
        			|| command.equals(view.m.resizeMenuItem.getActionCommand())) {
        		resize = true;
        		view.changeVisibility();
        		view.pack();
        		model.refresh(view.c.getGraphics());
        	} else if (command.equals(view.b.enter.getActionCommand())) {
        		setResizeInput(view.getResizeInput());
        	} else {
        		reset();
        	}
		}

		private void reset () {
			dragging = false;
			cloning = false;
			deleting = false;
    		line = false;
    		triangle = false;
			newShape = null;
			selectedShape = null;
			fomNewAdded = false;
			p = null;

		}

		private void changeFillColor() {
			Color newColor = openPalette();
            Shape update = null;
            try {
            	if (selectedShape != null && newColor != null) {
            		update = (Shape) selectedShape.clone();
            		update.setFillColor(newColor);
                    model.updateShape(selectedShape, update);
            	}
			} catch (CloneNotSupportedException e) {
			}

		}

		private void changeOutlineColor() {
			Color newColor = openPalette();
            Shape update = null;
            try {
            	if (selectedShape != null && newColor != null) {
            		update = (Shape) selectedShape.clone();
            		update.setColor(newColor);
                    model.updateShape(selectedShape, update);
            	}
			} catch (CloneNotSupportedException e) {
			}


		}

		private void load() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open File");
    		if (fileChooser.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
    			File file = fileChooser.getSelectedFile();
    			model.load(file.getPath());
    			if (model.noFile) {
    				String[] extension = file.getPath().split("\\.");
    				view.FileError(extension[extension.length - 1]);
    			} else {
    				view.Fileloaded();
    			}
    		}
		}

		private void save() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setApproveButtonText("Save");
			fileChooser.setDialogTitle("Save File");
			fileChooser.setApproveButtonToolTipText("Save file in this folder");
	        if (fileChooser.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
	        	File file = fileChooser.getSelectedFile();
	            model.save(file.getPath());
	            if (model.noFile) {
        			  String[] extension = file.getPath().split("\\.");
        			  view.FileError(extension[extension.length - 1]);
        		  } else {
        			  view.Filesaved();
        		  }
	        }
		}

		private void setBackground() {
			Color newColor = openPalette();
            view.setBGcolor(newColor);
            paintComponent(view.c.getGraphics());
		}

		private Color openPalette() {
			JColorChooser cc = new JColorChooser();
            Color newColor = JColorChooser.showDialog(cc,
            		"Background Color", Color.WHITE);
            return newColor;
		}

		private void checkUndoRedo() {
			if (model.undoAvailable) {
    			view.b.undo.setEnabled(true);
    		} else {
    			view.b.undo.setEnabled(false);
    		}
    		if (model.redoAvailable) {
    			view.b.redo.setEnabled(true);
    		} else {
    			view.b.redo.setEnabled(false);
    		}
		}

		private void AddShape() throws ClassNotFoundException, IOException {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open File");
    		if (fileChooser.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
    			File file = fileChooser.getSelectedFile();
    			if (file.getName().contains(".jar")) {
    				model.addJAR(file.getPath());
    			}
    		}
		}
	}

	public class newShapesAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			command = e.getActionCommand();
			for (int i = 0; i < view.menuItems.size(); i++) {
				if (model.dynamicClass.get(i).getName().contains(command)) {
					fomNewAdded = true;
					if (command.toUpperCase().contains("LINE")) {
						line = true;
					} else if (command.toUpperCase().contains("TRIANGLE")) {
						triangle = true;
					}
				}
			}

		}

	}

	public class dynamicMenuAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			command = e.getActionCommand();
			String[] temp = command.split(" ");
			selectedShape = model.drawnShapes.get(Integer.valueOf(temp[1]));
		}

	}

	public class MouseClick extends MouseAdapter {

		@Override
        public void mousePressed(MouseEvent m) {
			position = m.getPoint();
        	selectedShape = findShape();
			if (checkCommand()) {
				drawShape();
			} else if (cloning) {
				cloneShape();
			} else if (dragging && selectedShape != null) {
				p = new Point(selectedShape.getPosition().x, selectedShape.getPosition().y);
				Map newprop = new HashMap<>();
				for (Map.Entry s: selectedShape.getProperties().entrySet())
					newprop.put(s.getKey(), s.getValue());
				triprop = newprop;
			} else if (resize && !dragging) {
				if (!(selectedShape == null)) {
					p = new Point(selectedShape.getPosition().x, selectedShape.getPosition().y);
					Map newprop = new HashMap<>();
					for (Map.Entry s: selectedShape.getProperties().entrySet())
						newprop.put(s.getKey(), s.getValue());
					triprop = newprop;
					resizeShape();
					Shape s = null;
					try {
						s = (Shape) selectedShape.clone();
					} catch (CloneNotSupportedException e) {
					}
					s.setPosition(p);
					s.setProperties(triprop);
					model.specialAdd(selectedShape, s);
					paintComponent(view.c.getGraphics());
					triprop.clear();
				}
	        }
        }

        @Override
        public void mouseReleased(MouseEvent m) {
         if (deleting && selectedShape != null) {
        		model.removeShape(selectedShape);
        		paintComponent(view.c.getGraphics());
        		checkUndoRedo();
        } else if (dragging && selectedShape != null) {
        	Shape s = null;
			try {
				s = (Shape) selectedShape.clone();
			} catch (CloneNotSupportedException e) {
			}
			s.setPosition(p);
			s.setProperties(triprop);
			model.specialAdd(selectedShape, s);
			paintComponent(view.c.getGraphics());
			triprop.clear();
        }
         if (resize) {
         	view.changeVisibility();
         	view.pack();
         	paintComponent(view.c.getGraphics());
         	//repaint();
         }
        	position = null;
        	dragging = false;
        	resize = false;
        }

        private void drawLine() {
        	if (line && lineCounter == 0) {
        		triprop.clear();
        		triprop.put("XEND", position.getX());
        		triprop.put("YEND", position.getY());
        		lineCounter++;
        	} else {
        		newShape.setProperties(triprop);
        		newShape.setPosition(position);
        		model.addShape(newShape);
        		paintComponent(view.c.getGraphics());
        		lineCounter = 0;
        		triprop.clear();

        	}
        }

		private void drawShape() {
			if (line) {
				drawLine();
			} else if (triangle) {
				drawTriangle();
			} else {
				newShape.setPosition(position);
				model.addShape(newShape);
				paintComponent(view.c.getGraphics());
			}
			getMaxPropSize();
		}

		private void cloneShape() {
			if (newShape != null) {
				if (getName(newShape).equals("Line")) {
					cloneLine();
				} else if (getName(newShape).equals("Triangle")) {
					cloneTriangle();
				} else {
					newShape.setPosition(position);
				}
				model.addShape(newShape);
    			paintComponent(view.c.getGraphics());
    			cloning = false;
			} else if (selectedShape != null) {
				try {
					newShape = (Shape) selectedShape.clone();
				} catch (CloneNotSupportedException e) {
					view.cloneFailed();
				}
			} else {
				cloning = false;
			}
		}

        private void drawTriangle() {

        	if (triangle && triangleCounter == 0) {
        		triprop.put("Px1", position.getX());
        		triprop.put("Py1", position.getY());
        		triangleCounter++;

        	} else if (triangle && triangleCounter == 1) {
        		triprop.put("Px2", position.getX());
        		triprop.put("Py2", position.getY());
        		triangleCounter++;
        	} else {
        		newShape.setPosition(position);
        		newShape.setProperties(triprop);
        		model.addShape(newShape);
        		paintComponent(view.c.getGraphics());
        		triangleCounter = 0;
        		triprop.clear();

        	}
        }

        private boolean checkCommand() {
        	if ((command == "line" || command == "rectangle"
        		|| command == "square" || command == "ellipse"
        		|| command == "circle"|| command == "triangle")
        			&& !fomNewAdded){
        		newShape = factory.getShape(command);
        		return true;
        	} else if (fomNewAdded) {
        		for (int i = 0; i < view.menuItems.size(); i++) {
    				if (model.dynamicClass.get(i).getName().contains(command)) {
    					try {
    						newShape = model.dynamicClass.get(i).newInstance();
    						return true;
    					} catch (InstantiationException e1) {
    					} catch (IllegalAccessException e1) {
    					}
    				}
    			}
        	}
        	return false;
        }

		   @Override
	    public void mouseDragged(MouseEvent m) {
	        int dx = m.getX() - position.x;
	        int dy = m.getY() - position.y;
	        if (dragging && selectedShape != null) {
		    changePosition(dx, dy);
	        paintComponent(view.c.getGraphics());
	        }
	       position = m.getPoint();
	       repaint();
	    }

        private void changePosition(int dx, int dy) {
        	if (getName(selectedShape).equalsIgnoreCase("Triangle")) {
        		changeTrianglePosition(dx, dy);
        	} else if (getName(selectedShape).equalsIgnoreCase("Line")) {
        		changeLinePosition(dx, dy);
        		line = false;
        	} else {
        		selectedShape.getPosition().x += dx;
        		selectedShape.getPosition().y += dy;
        	}
		}

        private void changeTrianglePosition(int dx, int dy) {
        	Double px1 = selectedShape.getProperties().get("Px1");
    		Double px2 = selectedShape.getProperties().get("Px2");
    		Double py1 = selectedShape.getProperties().get("Py1");
    		Double py2 = selectedShape.getProperties().get("Py2");
    		selectedShape.getPosition().x += dx;
    		selectedShape.getPosition().y += dy;
    		selectedShape.getProperties().replace("Px1", px1 += dx);
    		selectedShape.getProperties().replace("Px2", px2 += dx);
    		selectedShape.getProperties().replace("Py1", py1 += dy);
    		selectedShape.getProperties().replace("Py2", py2 += dy);
        }

        private void changeLinePosition(int dx, int dy) {
        	Double xEND = selectedShape.getProperties().get("XEND");
    		Double yEND = selectedShape.getProperties().get("YEND");
    		selectedShape.getPosition().x += dx;
    		selectedShape.getPosition().y += dy;
    		selectedShape.getProperties().replace("XEND", xEND += dx);
    		selectedShape.getProperties().replace("YEND", yEND += dy);
        }

		private Shape findShape() {
			String[] temp = command.split(" ");
			if (temp.length == 2) {
				return model.drawnShapes.get(Integer.valueOf(temp[1]));
			}
			for (int i = 0; i < model.drawnShapes.size(); i++) {
				containShape.setShapeName(getName(model.drawnShapes.get(i)));
				if (containShape.contains(model.drawnShapes.get(i), position)) {
					return model.drawnShapes.get(i);
				}
			}
			return null;
		}

		private void cloneLine() {

			Double xstart = newShape.getProperties().get("XEND");
			Double ystart = newShape.getProperties().get("YEND");
			Double xend = newShape.getPosition().getX();
			Double yend = newShape.getPosition().getY();

			triprop.put("XEND", position.getX());
        	triprop.put("YEND", position.getY());
        	newShape.setProperties(triprop);



        	Double deltaX = position.getX() + (Math.abs(xstart - xend));
        	Double deltaY = position.getY() - (Math.abs(ystart - yend));

        	Point endPosition = new Point();
        	endPosition.setLocation(deltaX, deltaY);
        	newShape.setPosition(endPosition);
       		triprop.clear();

		}

		private void cloneTriangle() {
			Double x1 = newShape.getProperties().get("Px1");
			Double y1 = newShape.getProperties().get("Py1");
			Double x2 = newShape.getProperties().get("Px2");
			Double y2 = newShape.getProperties().get("Px2");
			Double xpos = newShape.getPosition().getX();
			Double ypos = newShape.getPosition().getY();


			triprop.put("Px1", position.getX());
        	triprop.put("Py1", position.getY());

        	Double px2 = position.getX() - (Math.abs(x1 - x2));
        	Double py2 = position.getY() - (Math.abs(y1 - y2));

        	triprop.put("Px2", px2);
        	triprop.put("Py2", py2);

        	Double newPosX = position.getX() + (Math.abs(x1 - xpos));
        	Double newPosY = position.getY() - (Math.abs(y1 - ypos));

        	Point endPosition = new Point();
        	endPosition.setLocation(newPosX, newPosY);

        	newShape.setPosition(endPosition);
        	newShape.setProperties(triprop);

       		triprop.clear();
		}

		private void resizeShape() {
        	if (checkDimensions(selectedShape.getProperties().size())) {
        		System.out.println("resize shape");
        		resizeNewShape();
        	}
        	for (int i = 0; i < maxPropSize; i++) {
        		dimensions[i] = 0.0;
        	}
        	counter = 0;
			model.refresh(view.c.getGraphics());
		}
	}

	private void resizeNewShape () {
		int i = 0;
		for (Map.Entry s: selectedShape.getProperties().entrySet()) {
			selectedShape.getProperties().replace((String) s.getKey(), dimensions[i]);
			System.out.println(dimensions[i]);
			i++;
		}
	}
	public class Contain {
		private String shapeName;

		public void setShapeName(String name) {
			shapeName = name;
		}

		public boolean contains(Shape c, Point last) {
			if (shapeName.equalsIgnoreCase("Circle")) {
				return includeCircle(c, last);
			} else if (shapeName.equalsIgnoreCase("Ellipse")) {
				return includeEllipse(c, last);
			} else if (shapeName.equalsIgnoreCase("Square")) {
				return includeSquare(c, last);
			} else if (shapeName.equalsIgnoreCase("Rectangle")) {
				return includeRectangle(c, last);
			} else if (shapeName.equalsIgnoreCase("Triangle")) {
				return includeTriangle(c, last);
			} else if (shapeName.equalsIgnoreCase("Line")) {
				return includeLine(c, last);
			}
			return false;
		}

		private boolean includeLine(Shape c, Point last) {
			return new Line2D.Double(c.getPosition().x, c.getPosition().y,
					c.getProperties().get("XEND"), c.getProperties().get("YEND")).contains(last);
		}

		private boolean includeTriangle(Shape c, Point last) {
			int x[] = new int [3];
			x[0] = c.getPosition().x;
			x[1] = c.getProperties().get("Px1").intValue();
			x[2] = c.getProperties().get("Px2").intValue();

			int y[] = new int[3];
			y[0] = c.getPosition().y;
			y[1] = c.getProperties().get("Py1").intValue();
			y[2] = c.getProperties().get("Py2").intValue();
			return new Polygon(x,y,3).contains(last);
		}

		private boolean includeRectangle(Shape c, Point last) {
			int [] x = new int[4];
			int [] y = new int[4];
			x[0] = c.getPosition().x;
			x[1] = (int) (c.getPosition().x + c.getProperties().get("LENGTH"));
			x[2] = x[0];
			x[3] = x[1];

			y[0] = c.getPosition().y;
			y[2] = (int) (c.getPosition().y - c.getProperties().get("WIDTH"));
			y[1] = y[0];
			y[3] = y[2];
			return new Rectangle2D.Double(c.getPosition().x, c.getPosition().y,
					c.getProperties().get("LENGTH").intValue(), c.getProperties().get("WIDTH").intValue()).contains(last);
		}

		private boolean includeSquare(Shape c, Point last) {
			int [] x = new int[4];
			int [] y = new int[4];
			x[0] = c.getPosition().x;
			x[1] = (int) (c.getPosition().x + c.getProperties().get("LENGTH"));
			x[2] = x[0];
			x[3] = x[1];

			y[0] = c.getPosition().y;
			y[2] = (int) (c.getPosition().y - c.getProperties().get("LENGTH"));
			y[1] = y[0];
			y[3] = y[2];
			return new Rectangle(c.getPosition().x, c.getPosition().y,
					c.getProperties().get("LENGTH").intValue(), c.getProperties().get("LENGTH").intValue()).contains(last);
		}

		private boolean includeEllipse(Shape c, Point last) {
			return new Ellipse2D.Double(c.getPosition().x, c.getPosition().y,
					c.getProperties().get("R1"), c.getProperties().get("R2")).contains(last);
		}

		private boolean includeCircle(Shape c, Point last) {
			return new Ellipse2D.Double(c.getPosition().x, c.getPosition().y,
					c.getProperties().get("RADIUS"), c.getProperties().get("RADIUS")).contains(last);
		}


	}

    @Override
	public void paintComponent(Graphics g) {
    	view.setDynamicShapesM(model.drawnShapes);
    	view.dynamicActions(new dynamicMenuAction());
    	model.refresh(g);
    	checkUndoRedo();
    }

	private void checkUndoRedo() {
		if (model.undoAvailable) {
			view.b.undo.setEnabled(true);
			view.m.undoMenuItem.setEnabled(true);
		} else {
			view.b.undo.setEnabled(false);
			view.m.undoMenuItem.setEnabled(false);
		}
		if (model.redoAvailable) {
			view.b.redo.setEnabled(true);
			view.m.redoMenuItem.setEnabled(true);
		} else {
			view.b.redo.setEnabled(false);
			view.m.redoMenuItem.setEnabled(false);
		}
		model.refresh(view.c.getGraphics());
	}

	private String getName(Shape c) {
		String[] name = c.getClass().toString().split("\\.");
		return name[(name.length) - 1];
	}
	private void getMaxPropSize () {
		for (int i = 0; i < model.drawnShapes.size(); i++) {
			if (model.drawnShapes.get(i).getProperties().size() > maxPropSize) {
				maxPropSize = model.drawnShapes.get(i).getProperties().size();
			}
		}
		dimensions = new Double[maxPropSize];
		for (int i = 0; i < maxPropSize; i++) {
			dimensions[i] = 0.0;
		}
	}
	private boolean checkDimensions (int numOfDimensions) {
		for (int i = 0; i < numOfDimensions; i++) {
			if (dimensions[i] == 0.0) {
				return false;
			}
		}
		return true;
	}
	private void setResizeInput(Double input) {
		if (counter < maxPropSize) {
		dimensions[counter] = input;
		counter++;
		} else {
			counter = 0;
		}
	}
}
