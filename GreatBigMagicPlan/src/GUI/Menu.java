/**
 * 
 */
package GUI;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 *
 */
public class Menu extends JMenuBar {

	private JMenu fileMenu;
	
	private JMenu filterMenu;
	
	private JMenu aboutMenu;	
	
	public Menu(){
		buildFileMenu();
		buildFilterMenu();
		buildAboutMenu();
		//TODO
	}
	
	public void buildFileMenu() {	
		fileMenu = new JMenu("File");
		JMenuItem addentry = new JMenuItem("Add Entry");
		JMenuItem removeEntry = new JMenuItem("Remove Entry");
		JMenuItem editEntry = new JMenuItem("Edit Entry");
		JMenuItem exit = new JMenuItem("Exit");
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
			
		});
	}
}
