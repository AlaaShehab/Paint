/**
 *
 */
package eg.edu.alexu.csd.oop.draw.paintview;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Personal
 *
 */
public class Buttons {

	public JButton load = new JButton("");
	public JButton save = new JButton("");
	public JButton undo = new JButton("");
	public JButton redo = new JButton("");
	public JButton newfile = new JButton("");
	public JButton refresh = new JButton("");
	public JButton select = new JButton("");
	public JButton drag = new JButton("");
	public JButton clone = new JButton("");
	public JButton remove = new JButton("");
	public JButton resize = new JButton("");
	public JButton enter = new JButton("Enter");


	public JButton line = new JButton("");
	public JButton rectangle = new JButton("");
	public JButton square = new JButton("");
	public JButton circle = new JButton("");
	public JButton ellipse = new JButton("");
	public JButton triangle = new JButton("");


	public JButton outlineshape = new JButton("");
	public JButton background = new JButton("");
	public JButton fillshape = new JButton("");

	public JTextField resizeInput = new JTextField();


	public JPanel buttons = new JPanel();
	public JPanel colors = new JPanel();

	private JLabel outline = new JLabel("Outline");
	private JLabel fill = new JLabel("Fill");
	private JLabel backgroundC = new JLabel("Background");

	private Box box1;
	private Box box2;

	public Buttons() {

		setLayout();
		createActionB();
		createDrawB();
		addActionB();
		addDrawB();
		createColorB();
		addColorB();
	}

	private void addDrawB() {
	    box2.add(line);
	    box2.add(Box.createRigidArea(new Dimension(5,0)));
	    box2.add(rectangle);
	    box2.add(Box.createRigidArea(new Dimension(5,0)));
	    box2.add(square);
	    box2.add(Box.createRigidArea(new Dimension(5,0)));
	    box2.add(circle);
	    box2.add(Box.createRigidArea(new Dimension(5,0)));
	    box2.add(ellipse);
	    box2.add(Box.createRigidArea(new Dimension(5,0)));
	    box2.add(triangle);
	    box2.add(Box.createRigidArea(new Dimension(5,0)));
	}

	private void createActionB() {
		clone.setActionCommand("clone");
		clone.setToolTipText("Clone Shape");
		clone.setBackground(Color.WHITE);
		clone.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\clone.png"));

	    remove.setActionCommand("remove");
	    remove.setToolTipText("Remove Shape");
	    remove.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\remove.png"));
	    remove.setBackground(Color.WHITE);

	    drag.setActionCommand("drag");
	    drag.setToolTipText("Drag Shape");
	    drag.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\drag.png"));
	    drag.setBackground(Color.WHITE);


		load.setActionCommand("load");
		load.setToolTipText("Load");
		load.setBackground(Color.WHITE);
		load.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\load.png"));

	    newfile.setActionCommand("new");
	    newfile.setToolTipText("New File");
	    newfile.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\new.png"));
	    newfile.setBackground(Color.WHITE);

	    undo.setActionCommand("undo");
	    undo.setToolTipText("Undo");
	    undo.setEnabled(false);
	    undo.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\undo1.png"));
	    undo.setBackground(Color.WHITE);

	    redo.setActionCommand("redo");
	    redo.setEnabled(false);
	    redo.setToolTipText("Redo");
	    redo.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\redo.png"));
	    redo.setBackground(Color.WHITE);

	    save.setActionCommand("save");
	    save.setToolTipText("Save");
	    save.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\save.png"));
	    save.setBackground(Color.WHITE);

	    refresh.setActionCommand("refresh");
	    refresh.setToolTipText("Refresh");
	    refresh.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\refresh.png"));
	    refresh.setBackground(Color.WHITE);

	    select.setActionCommand("select");
	    select.setToolTipText("Select");
	    select.setBackground(Color.WHITE);
	    select.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\select.png"));

	    enter.setActionCommand("enter");
	    enter.setToolTipText("Enter");

	    resize.setActionCommand("resize");
	    resize.setToolTipText("Resize");
	    resize.setBackground(Color.WHITE);
	    resize.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\resize.jpg"));

	}

	private void addActionB() {
		box1.add(load);
		box1.add(Box.createRigidArea(new Dimension(5, 0)));
		box1.add(save);
		box1.add(Box.createRigidArea(new Dimension(5, 0)));
		box1.add(undo);
		box1.add(Box.createRigidArea(new Dimension(5, 0)));
		box1.add(redo);
		box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(newfile);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(refresh);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(select);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(clone);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(remove);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(drag);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
	    box1.add(resize);
	}

	private void setLayout() {
		box1 = Box.createHorizontalBox();
		box2 = Box.createHorizontalBox();

		BoxLayout boxLayout1 = new BoxLayout(buttons, BoxLayout.Y_AXIS);
		buttons.setLayout(boxLayout1);

		BoxLayout boxLayout2 = new BoxLayout(colors, BoxLayout.Y_AXIS);
		colors.setLayout(boxLayout2);

		buttons.add(box1);
		buttons.add(Box.createRigidArea(new Dimension(0,5)));
		buttons.add(box2);
		buttons.add(Box.createRigidArea(new Dimension(0,5)));
	}

	private void createDrawB() {
		line.setActionCommand("line");
	    line.setToolTipText("Draw Line");
	    line.setBackground(Color.WHITE);
	    line.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\line.png"));

	    rectangle.setActionCommand("rectangle");
	    rectangle.setToolTipText("Draw Rectangle");
	    rectangle.setBackground(Color.WHITE);
	    rectangle.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\rectangle.png"));

	    ellipse.setActionCommand("ellipse");
	    ellipse.setToolTipText("Draw Ellipse");
	    ellipse.setBackground(Color.WHITE);
	    ellipse.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\ellipse.png"));

	    square.setActionCommand("square");
	    square.setToolTipText("Draw Square");
	    square.setBackground(Color.WHITE);
	    square.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\square.png"));

	    circle.setActionCommand("circle");
	    circle.setToolTipText("Draw Circle");
	    circle.setBackground(Color.WHITE);
	    circle.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\circle.png"));

	    triangle.setActionCommand("triangle");
	    triangle.setToolTipText("Draw Triangle");
	    triangle.setBackground(Color.WHITE);
	    triangle.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\triangle.png"));
	}

	private void createColorB() {
		outline.setBounds(30, 70, 40, 10);
	    outlineshape.setBounds(33, 85, 35, 35);
	    outlineshape.setBackground(Color.WHITE);
	    outlineshape.setActionCommand("outline");
	    outlineshape.setToolTipText("Color Shape Outline");
	    outlineshape.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\paint.jpg"));

	    fill.setBounds(30, 150, 40, 10);
	    fillshape.setBounds(33, 165, 35, 35);
	    fillshape.setBackground(Color.WHITE);
	    fillshape.setActionCommand("fill");
	    fillshape.setToolTipText("Fill Shape");
	    fillshape.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\paint.jpg"));

	    backgroundC.setBounds(30, 220, 40, 10);
	    background.setBounds(33, 235, 35, 35);
	    background.setBackground(Color.WHITE);
	    background.setActionCommand("background");
	    background.setToolTipText("Set Background Color");
	    background.setIcon(new ImageIcon("C:\\Users\\Personal\\Desktop\\Java\\Paint\\resources\\paint.jpg"));
	}

	private void addColorB() {
		colors.add(outline);
	    colors.add(Box.createRigidArea(new Dimension(0,5)));
	    colors.add(outlineshape);
	    colors.add(Box.createRigidArea(new Dimension(0,15)));
	    colors.add(fill);
	    colors.add(Box.createRigidArea(new Dimension(0,5)));
	    colors.add(fillshape);
	    colors.add(Box.createRigidArea(new Dimension(0,15)));
	    colors.add(backgroundC);
	    colors.add(Box.createRigidArea(new Dimension(0,5)));
	    colors.add(background);
	    colors.add(Box.createRigidArea(new Dimension(0,15)));
	    resizeInput.setMaximumSize(new Dimension(130, 50));
	    colors.add(resizeInput);
	    colors.add(Box.createRigidArea(new Dimension(0,5)));
	    colors.add(enter);
	}

}
