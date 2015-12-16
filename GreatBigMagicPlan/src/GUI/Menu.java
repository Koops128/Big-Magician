/**
 * 
 */

package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Editor;

/**
 *
 *@author: Ash MInoo
 *@author: Melinda Robertson
 *@version 20151215
 */
public class Menu extends JMenuBar {

	/**
	 * The file menu where entries can be added, deleted or modified.
	 * The user can also exit the program.
	 */
	private JMenu fileMenu;
	/**
	 * Choosing filters changes the entries in the table.
	 */
	private JMenu filterMenu;
	/**
	 * The about menu displays information about the program and designers.
	 */
	private JMenu aboutMenu;
	/**
	 * Removes the current entry.
	 */
	private JMenuItem removeEntry;
	/**
	 * Edits the curent entry.
	 */
	private JMenuItem editEntry;
	/**
	 * Bridge to access the database.
	 */
	private Editor editor;
	/**
	 * List of filters used to determine what is displayed.
	 */
	private List<String> filterList = new ArrayList<String>();

	/**
	 * Creates the menu.
	 * @param editor allows access to the database.
	 */
	public Menu(Editor editor){
		this.editor = editor;
		buildFileMenu();
		buildFilterMenu();
		buildAboutMenu();
		this.add(fileMenu);
		this.add(filterMenu);
		this.add(aboutMenu);
	}
	
	/*
	 * Method that builds the file menu with menu items and actions
	 * for those menu items.
	 */
	public void buildFileMenu() {	
		fileMenu = new JMenu("File");
		JMenuItem addentry = new JMenuItem("Add Entry");
		addentry.addActionListener((event)->{
			//TODO open JFileChooser -> if file selected -> 
			//save file contents to String -> fire property change.
			StringBuffer stringBuffer = new StringBuffer();
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(null);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files",
					"txt");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(null);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = new BufferedReader(new FileReader(file));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String line = null;
				try {
					while((line = bufferedReader.readLine()) != null) {

						stringBuffer.append(line);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				System.out.println("you ADDED an ITEM");
				this.firePropertyChange(
						CardPanel.ADDPROPERTY, stringBuffer.toString(), CardPanel.EDITNAME);
		}
				
			
				
		});
		
		//////////////////////////////////////////////////
		removeEntry = new JMenuItem("Remove Entry");
		removeEntry.addActionListener((event)->{
			editor.remove();
			System.out.println("oh great now you REMOVED ONE");
			this.firePropertyChange(CardPanel.REMOVEPROPERTY, null, CardPanel.MAINNAME);
		});

		//////////////////////////////////////////////////

		editEntry = new JMenuItem("Edit Entry");
		editEntry.addActionListener((event)->{
			this.firePropertyChange(
					CardPanel.SWITCHPROPERTY, CardPanel.MAINNAME, CardPanel.EDITNAME);
		});
		
		//////////////////////////////////////////////////

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener((event)->{
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to quit?", 
					"Confirm Quit", JOptionPane.YES_NO_OPTION);
			if (result == 0) System.exit(0);
			

		});
		//////////////////////////////////////////////////
		setEnabled(false);
		fileMenu.add(addentry);
		fileMenu.add(removeEntry);
		fileMenu.add(editEntry);
		fileMenu.add(exit);
	}
	
	/*
	 * edit and remove menu items are set to false until an element in the 
	 * table is selected. Boolean value is determined by setEnabled
	 */
	public void setEnabled(boolean b) {
		removeEntry.setEnabled(b);
		editEntry.setEnabled(b);
	}
	/*
	 * builds the elements/items of the filter menu
	 */
	public void buildFilterMenu() {
		filterMenu = new JMenu("Filters");
//		ButtonGroup group = new ButtonGroup();

		createCbButtons(editor.getTypes());
		//you're going to add the createCbButtons method here!
		//but there's no database reference to types ;c
		
		//get list of distinct types from database
		//SELECT DISTINCT Type FROM data
	}
	
	/*
	 * creates the list of filters made in the button menu
	 */
	public void createCbButtons(String[] theType) {
//		group.add(allCb);
//		JCheckBoxMenuItem allCb = new JCheckBoxMenuItem("All");
//		allCb.setSelected(true);
//		allCb.addActionListener((event)-> {
//			editor.getTable();
//		});
//		filterMenu.add(allCb);
		//get the array and loop through the values
		for (int i = 0; i < theType.length; i++) {
			final String typeName = theType[i];
			JCheckBoxMenuItem button = new JCheckBoxMenuItem(typeName);
			button.addActionListener((event)-> {
//				editor.getTable(theType);
				if (button.isSelected()) {
//					allCb.setSelected(false);
					filterList.add(typeName);
					this.firePropertyChange(CardPanel.FILTERPROPERTY, filterList.toArray(new String[filterList.size()]),
							CardPanel.MAINNAME);
				} else {
					filterList.remove(typeName);
					if (filterList.size() == 0) {
//						allCb.doClick();
						this.firePropertyChange(CardPanel.FILTERPROPERTY, null,
								CardPanel.MAINNAME);
					} else {
						this.firePropertyChange(CardPanel.FILTERPROPERTY, filterList.toArray(new String[filterList.size()]),
								CardPanel.MAINNAME);
					}
				}
//				System.out.println(filterList.toString());
				filterMenu.doClick(0);
			});
//			theGroup.add(button);
			filterMenu.add(button);
		}


	}
	
	/*
	 * adds an action to the about menu button which pops up the about
	 * frame which's contents are described in it's unique class.
	 */
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
