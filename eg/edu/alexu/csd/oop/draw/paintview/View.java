/**
 *
 */
package eg.edu.alexu.csd.oop.draw.paintview;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Personal
 *
 */
public class View extends JFrame {

	public Buttons b;
	public Menu m;
	public Canvas c;
	public ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
	public ArrayList<JMenuItem> dynamicShapeItems = new ArrayList<JMenuItem>();

	public View() {

		initialize();
	}
	private void initialize() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);

		b = new Buttons();
	    m = new Menu();
	    JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 30, 70));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		c = new Canvas();
		c.setBackground(Color.WHITE);
		b.resizeInput.setVisible(false);
		b.enter.setVisible(false);

		this.add(b.buttons, BorderLayout.NORTH);
		this.add(b.colors, BorderLayout.WEST);
		this.add(c, BorderLayout.CENTER);
		this.setJMenuBar(m.menuBar);

	}

	public void actions (ActionListener e) {

		buttonActions(e);
		menuActions(e);


    }

	private void buttonActions (ActionListener e) {

        b.load.addActionListener(e);
        b.newfile.addActionListener(e);
        b.undo.addActionListener(e);
        b.redo.addActionListener(e);
        b.save.addActionListener(e);
        b.refresh.addActionListener(e);
        b.remove.addActionListener(e);
        b.clone.addActionListener(e);

    	b.line.addActionListener(e);
    	b.rectangle.addActionListener(e);
    	b.square.addActionListener(e);
    	b.circle.addActionListener(e);
    	b.ellipse.addActionListener(e);
    	b.triangle.addActionListener(e);
    	b.select.addActionListener(e);
    	b.drag.addActionListener(e);
    	b.resize.addActionListener(e);
    	b.resizeInput.addActionListener(e);
    	b.enter.addActionListener(e);

    	b.outlineshape.addActionListener(e);
    	b.background.addActionListener(e);
    	b.fillshape.addActionListener(e);

	}

	private void menuActions (ActionListener e) {

    	m.newMenuItem.addActionListener(e);
    	m.loadMenuItem.addActionListener(e);
    	m.saveMenuItem.addActionListener(e);
    	m.exitMenuItem.addActionListener(e);
    	m.undoMenuItem.addActionListener(e);
    	m.redoMenuItem.addActionListener(e);
    	m.refreshMenuItem.addActionListener(e);
    	m.shapeMenuItem.addActionListener(e);
    	m.cloneMenuItem.addActionListener(e);
    	m.removeMenuItem.addActionListener(e);
    	m.dragMenuItem.addActionListener(e);
    	m.selectMenuItem.addActionListener(e);
    	m.resizeMenuItem.addActionListener(e);
	}


	public JFrame getFrame() {
		return this;
	}

	public void FileError(String fileExtension) {
		JOptionPane.showMessageDialog(this, ". " + fileExtension +" extension "+ "is not supported");
	}

	public void Fileloaded() {
		JOptionPane.showMessageDialog(this, "file successfully loaded");
	}

	public void Filesaved() {
		JOptionPane.showMessageDialog(this, "file successfully saved");
	}

	public void clearCanvas() {
		c.setForeground(Color.WHITE);
		c.setBackground(Color.WHITE);
		c.repaint();
	}

	public void setBGcolor(Color newColor) {
		if (newColor != null) {
			c.setBackground(newColor);
		}
	}

	public void cloneFailed() {
		JOptionPane.showMessageDialog(this, "Clone Failed!!");

	}

	public void setMenu(int shapesNum, List<Class<? extends Shape>> classes) {
		m.newShapes.setEnabled(true);

		for (int i = 0; i < shapesNum; i++) {
			String[] name = classes.get(i).getName().split("\\.");
			JMenuItem item = new JMenuItem(name[(name.length) - 1]);
			item.setActionCommand(name[(name.length) - 1]);
		    m.newShapes.add(item);
		    menuItems.add(item);
		}
	}

	public void newShapesActions(ActionListener e) {
		for (int i = 0; i < menuItems.size(); i++) {
			menuItems.get(i).addActionListener(e);
		}
	}

	public void resetMenu() {
		m.newShapes.removeAll();
		m.newShapes.setEnabled(false);
	}

	public Double getResizeInput() {
		String input = b.resizeInput.getText();
		if (input.equals("")) {
			return 0.0;
		}
		if (validInput(input)) {
		b.resizeInput.setText("");
		return Double.parseDouble(input);
		}
		return 0.0;
	}

	public void changeVisibility () {
		boolean currentState = b.resizeInput.isVisible();
		b.resizeInput.setVisible(!currentState);
		b.enter.setVisible(!currentState);
	}

	private boolean validInput (String input) {
		for (int i = 0; i < input.length(); i++) {
			if(!(input.charAt(i) >= '0' && input.charAt(i) <= '9' )) {
				return false;
			}
		}
		return true;
	}

	public void setDynamicShapesM(ArrayList<Shape> shapes) {
		m.dynamicShapes.removeAll();
		if (shapes.isEmpty()) {
			m.dynamicShapes.setVisible(false);
		} else {
			m.dynamicShapes.setVisible(true);
			for (int i = 0; i < shapes.size(); i++) {
				String[] name = shapes.get(i).getClass().getName().split("\\.");
				JMenuItem item = new JMenuItem(name[name.length - 1]+" "+ i);
				item.setActionCommand(name[name.length - 1]+" "+ i);
			    m.dynamicShapes.add(item);
			    dynamicShapeItems.add(item);
			}
		}
	}

	public void dynamicActions(ActionListener e) {
		for (int i = 0; i < dynamicShapeItems.size(); i++) {
			dynamicShapeItems.get(i).addActionListener(e);
		}
	}
}
