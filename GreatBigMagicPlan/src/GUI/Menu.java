/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.MenuItem;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 *
 */
public class Menu {

	private JMenu fileMenu;
	
	private JMenu filterMenu;
	
	private JMenu aboutMenu;

	
	Panel myPanel = new Panel();
	
	
	public Menu(){
		
		myPanel.setLayout(new BorderLayout(1, 3));
		buildFileMenu();
		buildFilterMenu();
		buildAboutMenu();
		//TODO
	}
	
	public void buildFileMenu() {	
		fileMenu = new JMenu("File");
		myPanel.add(fileMenu, BorderLayout.NORTH);
		//TODO method stub
	}
	
	public void buildFilterMenu() {
		filterMenu = new JMenu("Filters");
		myPanel.add(filterMenu, BorderLayout.NORTH);
		//TODO method stub
	}
	
	public void buildAboutMenu() {
		aboutMenu = new JMenu("About");
		myPanel.add(aboutMenu, BorderLayout.NORTH);
		//AboutFrame af = new AboutFrame();
		
	}
}
