/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;


import Model.Editor;

/**
 * @author Melinda Robertson
 * @version 20151211
 */
public class MainPanel extends JPanel{

	/**
	 * Super Serial
	 */
	private static final long serialVersionUID = 5986050981069086758L;
	/**
	 * Holds the data for all entries.
	 */
	private JTable table;
	/**
	 * Displays the clause that is selected from the table.
	 */
	private JTextArea content;
	/**
	 * Opens the current entry to edit and fires an event to
	 * prompt the card panel to switch panes.
	 */
	private JButton edit;
	/**
	 * Opens the default text editor with the current entry's content loaded.
	 */
	private JButton use;
	/**
	 * Deletes the current entry from the database.
	 */
	private JButton delete;
	/**
	 * Conveys information to and from the database.
	 */
	private Editor editor;
	/**
	 * Constructs a panel that displays a list of entries.
	 * @param editor is the go-between for the database.
	 */
	public MainPanel(Editor editor) {
		this.editor = editor;
		this.setLayout(new BorderLayout());
		buildTablePanel();
		buildBtnPanel();
	}
	
	/**
	 * Creates a panel for the table and content view and
	 * adds them to this Component.
	 */
	private void buildTablePanel() {
		JPanel tblPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		gb.rowHeights = new int[]{MainFrame.HEIGHT/2,50};
		tblPanel.setLayout(gb);
		table = new JTable();
		resetTable();
		JScrollPane sc = new JScrollPane(table);
		sc.setMinimumSize(new Dimension(MainFrame.WIDTH-10,
				MainFrame.HEIGHT/2+100));
		ListSelectionModel lsm = table.getSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsm.addListSelectionListener((event)->{
			listSelection();
		});
		
		//------------------GRIDBAG THINGYS------------------
		GridBagConstraints gb_sc = new GridBagConstraints();
		gb_sc.gridx = 0;
		gb_sc.gridy = 0;
		gb_sc.fill = GridBagConstraints.HORIZONTAL;
		gb_sc.weightx = 1;
		gb_sc.weighty = 1;
		
		GridBagConstraints gb_lbl = new GridBagConstraints();
		gb_lbl.gridx = 0;
		gb_lbl.gridy = 1;
		
		GridBagConstraints gb_desc = new GridBagConstraints();
		gb_desc.gridx = 0;
		gb_desc.gridy = 2;
		gb_desc.fill = GridBagConstraints.BOTH;
		
		
		content = new JTextArea();
		content.setEditable(false);
		content.setMinimumSize(new Dimension(MainFrame.WIDTH-10,
				(int) (MainFrame.HEIGHT-sc.getMinimumSize().getHeight())));
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		JLabel lbl_desc = new JLabel("Content");
		lbl_desc.setLabelFor(content);
		
		tblPanel.add(sc,gb_sc);
		tblPanel.add(lbl_desc,gb_lbl);
		tblPanel.add(content,gb_desc);
		this.add(tblPanel, BorderLayout.CENTER);
	}
	/**
	 * Gets the currently selected value from the table
	 * and sets the current entry for the editor.
	 */
	public void listSelection() {
		int row = table.getSelectedRow();
		if (row >= 0) {
			editor.setCurrentEntry((String) table.getValueAt(row, 0));
			content.setText(editor.getCurrentEntry().getContent());
			edit.setEnabled(true);
			use.setEnabled(true);
			delete.setEnabled(true);
		} else {
			editor.setCurrentEntry(null);
			content.setText("");
			edit.setEnabled(false);
			use.setEnabled(false);
			delete.setEnabled(false);
		}
	}
	
	/**
	 * Builds the box that holds three buttons:
	 * 	Use Clause
	 *  Edit Clause
	 *  Delete Clause
	 */
	private void buildBtnPanel() {
		JPanel btnPanel = new JPanel();
		use = new JButton("Use Clause");
		use.setEnabled(false);
		use.addActionListener((event)->{
			//Reference: http://www.avajava.com/tutorials/lessons/how-do-i-run-another-application-from-java.html
			//@author Deron Eriksson
			try {
				Runtime runTime = Runtime.getRuntime();
				Process process = runTime.exec("notepad");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		edit = new JButton("Edit Clause");
		edit.setEnabled(false);
		edit.addActionListener((event)->{
			//TODO set the Entry that is being edited
			//get the number of the Entry from the JTable
			//send to the editor
			//this.editor.setEditing(          );
			this.firePropertyChange(
					CardPanel.SWITCHPROPERTY, CardPanel.MAINNAME, CardPanel.EDITNAME);
		});
		
		delete = new JButton("Delete Clause");
		delete.setEnabled(false);
		delete.addActionListener((event)->{
			//TODO ask editor to delete Entry
			
			//get the number of the Entry from the JTable
			//send to the editor
			int choice = JOptionPane.showConfirmDialog(this, "Confirm delete?");
			if (choice == JOptionPane.YES_OPTION) {
				this.editor.remove();
				resetTable();
				listSelection();
			}
		});
		
		btnPanel.add(use);
		btnPanel.add(edit);
		btnPanel.add(delete);
		
		this.add(btnPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Resets the table so that changes can be viewed.
	 */
	public void resetTable() {
		this.table.setModel(editor.getTable());
		this.repaint();
	}
	
}
