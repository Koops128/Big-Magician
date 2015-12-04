/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import Model.Bank;

/**
 * @author Melinda Robertson
 * @version 20151125
 */
public class MainPanel extends JPanel implements Observer {

	/**
	 * Super Serial
	 */
	private static final long serialVersionUID = 5986050981069086758L;

	private JTable table;
	
	private JTextArea clause;
	
	private Bank editor;
	
	public MainPanel(Bank editor) {
		this.editor = editor;
		this.setLayout(new BorderLayout());
		buildTablePanel();
		buildBtnPanel();
	}
	
	public void buildTablePanel() {
		JPanel tblPanel = new JPanel();
		table = new JTable();
		//table.setModel(editor.getModel("All"));
		JScrollPane sc = new JScrollPane(table);
		tblPanel.add(sc);
		this.add(tblPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Builds the box that holds three buttons:
	 * 	Use Clause
	 *  Edit Clause
	 *  Delete Clause
	 */
	public void buildBtnPanel() {
		JPanel btnPanel = new JPanel();
		JButton use = new JButton("Use Clause");
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
		
		JButton edit = new JButton("Edit Clause");
		edit.addActionListener((event)->{
			//TODO set the Entry that is being edited
			//get the number of the Entry from the JTable
			//send to the editor
			//this.editor.setEditing(          );
			this.firePropertyChange(
					CardPanel.PROPERTYNAME, CardPanel.MAINNAME, CardPanel.EDITNAME);
		});
		
		JButton delete = new JButton("Delete Clause");
		delete.addActionListener((event)->{
			//TODO ask editor to delete Entry
			//get the number of the Entry from the JTable
			//send to the editor
			//this.editor.deleteEntry(              );
		});
		
		btnPanel.add(use);
		btnPanel.add(edit);
		btnPanel.add(delete);
		
		this.add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO must have these classes created
		//assumes editor returns a TableModel
//		if (o instanceof ObservableEditor) {
//			if (arg instanceof TableModel) {
//				table.setModel((TableModel) arg);
//			}
//		}
		
	}
	
}
