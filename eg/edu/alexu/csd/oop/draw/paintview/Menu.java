/**
 *
 */
package eg.edu.alexu.csd.oop.draw.paintview;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Personal
 *
 */
public class Menu {

	public JMenuItem newMenuItem = new JMenuItem("New");
	public JMenuItem loadMenuItem = new JMenuItem("Open");
	public JMenuItem saveMenuItem = new JMenuItem("Save");
	public JMenuItem exitMenuItem = new JMenuItem("Exit");

	public JMenuItem undoMenuItem = new JMenuItem("Undo");
	public JMenuItem redoMenuItem = new JMenuItem("Redo");
	public JMenuItem shapeMenuItem = new JMenuItem("Add Shape");
	public JMenuItem refreshMenuItem = new JMenuItem("Refresh");
	public JMenuItem selectMenuItem = new JMenuItem("Select");
	public JMenuItem cloneMenuItem = new JMenuItem("Clone");
	public JMenuItem removeMenuItem = new JMenuItem("Remove");
	public JMenuItem dragMenuItem = new JMenuItem("Drag");
	public JMenuItem resizeMenuItem = new JMenuItem("Resize");


    public JMenu newShapes;
	public JMenuBar menuBar = new JMenuBar();
	public JMenu dynamicShapes;

	public Menu() {
		addFileItem();
	    addEditItem();
	    addnewMenu();
	    addDynamicShapes();
	}

	private void addFileItem() {
		 JMenu fileMenu = new JMenu("File");
		 fileMenu.setMnemonic(KeyEvent.VK_F);
		 menuBar.add(fileMenu);

		 newMenuItem.setActionCommand("new");
		 loadMenuItem.setActionCommand("load");
		 saveMenuItem.setActionCommand("save");
		 exitMenuItem.setActionCommand("exit");

		 fileMenu.add(newMenuItem);
		 fileMenu.add(loadMenuItem);
		 fileMenu.add(saveMenuItem);
		 fileMenu.add(exitMenuItem);

	}

	private void addEditItem() {
	    JMenu edit = new JMenu("Edit");
	    edit.setMnemonic(KeyEvent.VK_F);
	    menuBar.add(edit);

	    undoMenuItem.setActionCommand("undo");
	    undoMenuItem.setEnabled(false);
	    redoMenuItem.setActionCommand("redo");
	    redoMenuItem.setEnabled(false);
	    dragMenuItem.setActionCommand("drag");
	    resizeMenuItem.setActionCommand("resize");
	    refreshMenuItem.setActionCommand("refresh");
	    selectMenuItem.setActionCommand("select");
	    cloneMenuItem.setActionCommand("clone");
	    removeMenuItem.setActionCommand("remove");
	    shapeMenuItem.setActionCommand("addShape");

		edit.add(undoMenuItem);
	    edit.add(redoMenuItem);
	    edit.add(dragMenuItem);
	    edit.add(resizeMenuItem);
	    edit.add(refreshMenuItem);
	    edit.add(selectMenuItem);
	    edit.add(cloneMenuItem);
	    edit.add(removeMenuItem);
	    edit.add(shapeMenuItem);
	}

	private void addnewMenu() {
		newShapes = new JMenu("New Shapes");
	    newShapes.setMnemonic(KeyEvent.VK_F);
	    menuBar.add(newShapes);
	    newShapes.setEnabled(false);
	}

	private void addDynamicShapes() {
		dynamicShapes = new JMenu("Select Shapes");
		dynamicShapes.setMnemonic(KeyEvent.VK_F);
	    menuBar.add(dynamicShapes);
	    dynamicShapes.setVisible(false);
	}

}
