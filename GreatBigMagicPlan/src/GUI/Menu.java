/**
 * 
 */
package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import Model.Editor;

/**
 *
 *
 */
public class Menu extends JMenuBar {

	private JMenu fileMenu;
	
	private JMenu filterMenu;
	
	private JMenu aboutMenu;
	private Editor editor;
	
	public Menu(Editor editor){
		this.editor = editor;
		buildFileMenu();
		buildFilterMenu();
		buildAboutMenu();
		this.add(fileMenu);
		this.add(filterMenu);
		this.add(aboutMenu);
	}
	
	public void buildFileMenu() {	
		fileMenu = new JMenu("File");
		JMenuItem addentry = new JMenuItem("Add Entry");
		addentry.addActionListener((event)->{
			//TODO open JFileChooser -> if file selected -> 
			//save file contents to String -> fire property change.
				System.out.println("you ADDED an ITEM");
				this.firePropertyChange(
						CardPanel.ADDPROPERTY, CardPanel.MAINNAME, CardPanel.EDITNAME);
		});

		JMenuItem removeEntry = new JMenuItem("Remove Entry");
		removeEntry.addActionListener((event)->{
			editor.remove();
				System.out.println("oh great now you REMOVED ONE");
			
		});

		JMenuItem editEntry = new JMenuItem("Edit Entry");
		editEntry.addActionListener((event)->{
			this.firePropertyChange(
					CardPanel.SWITCHPROPERTY, CardPanel.MAINNAME, CardPanel.EDITNAME);
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener((event)->{
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to quit?", 
					"Confirm Quit", JOptionPane.YES_NO_OPTION);
			if (result == 0) System.exit(0);
			

		});

		fileMenu.add(addentry);
		fileMenu.add(removeEntry);
		fileMenu.add(editEntry);
		fileMenu.add(exit);
	}
	
	public void buildFilterMenu() {
		filterMenu = new JMenu("Filters");
		ButtonGroup group = new ButtonGroup();
		//you're going to add the createRadioButtons method here!
		//but there's no database reference to types ;c
		
		//get list of distinct types from database
		//SELECT DISTINCT Type FROM data
	}
	
	public void createRadioButtons(String[] theType, ButtonGroup theGroup) {
		//get the array and loop through the values
		String name = "";
		JRadioButtonMenuItem allRb = new JRadioButtonMenuItem("All");
		for (int i = 0; i < theType.length; i++) {
			JRadioButtonMenuItem button = new JRadioButtonMenuItem(theType[i]);
			theGroup.add(button);
		}
		
		
	}
	
	
	public void buildAboutMenu() {
		aboutMenu = new JMenu("About");
		JMenuItem about = new JMenuItem("About BigMagician");
		about.addActionListener((event)->{
			try {
				new AboutFrame();
			} catch (Exception e) {
				System.out.println("Oh noes");
			}
		});
		aboutMenu.add(about);
	}
}
