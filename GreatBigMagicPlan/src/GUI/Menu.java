/**
 * 
 */
package GUI;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	}
	
	public void buildFilterMenu() {
		filterMenu = new JMenu("Filters");
		//get list of distinct types from database
		//SELECT DISTINCT Type FROM data
	}
	
	public void buildAboutMenu() {
		aboutMenu = new JMenu("About");
		JMenuItem about = new JMenuItem("About BigMagician");
		about.addActionListener((event)->{
			
		});
	}
}
