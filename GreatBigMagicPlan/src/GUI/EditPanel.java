/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Model.Entry;

/**
 * Allows the user to edit or add an Entry.
 * @author Melinda Robertson
 * @author Sean Markus
 * @version 20151217
 */
public class EditPanel extends JPanel {
	
	/**
	 * Super Serial
	 */
	private static final long serialVersionUID = -8289922698140492731L;

	/**
	 * The text field that holds the entry's type.
	 */
	private JTextField type;
	/**
	 * The text field that holds the entry's title.
	 */
	private JTextField title;
	/**
	 * The text field that holds the entry's description.
	 */
	private JTextField description;
	/**
	 * Holds the entry itself.
	 */
	private JTextArea clause;
	/**
	 * Reference to the data.
	 */
	
	private boolean editing;
	/**
	 * Creates the panel.
	 * @param editor is the reference to the data.
	 */
	public EditPanel() {
		editing = false;
		this.setLayout(new BorderLayout());
		buildEditPanel();
		buildBtnPanel();
	}
	
	/**
	 * Adds the component with a JTextArea for
	 * each 4 Entry fields
	 */
	public void buildEditPanel() {
		JPanel edPanel = new JPanel();
		edPanel.setLayout(new GridBagLayout());
		JPanel titlepanel = new JPanel();
		titlepanel.setLayout(new GridBagLayout());
		
		//----------------LABELS---------------------------
		JLabel lbl_type = new JLabel("Type");
		JLabel lbl_title = new JLabel("Title");
		JLabel lbl_desc = new JLabel("Description");
		JLabel lbl_clause = new JLabel("Clause");
		
		//-----------------COMPONENTS--------------------------
		type = new JTextField();
		type.setMinimumSize(new Dimension(MainFrame.WIDTH/2 - 20, 0));
		type.setPreferredSize(new Dimension(MainFrame.WIDTH/2 - 20, 20));
		title = new JTextField();
		title.setMinimumSize(new Dimension(MainFrame.WIDTH/2 - 20, 0));
		title.setPreferredSize(new Dimension(MainFrame.WIDTH/2 - 20, 20));
		description = new JTextField();
		clause = new JTextArea();
		clause.setLineWrap(true);
		clause.setWrapStyleWord(true);
		JScrollPane sc = new JScrollPane(clause);
		clause.setMinimumSize(new Dimension(MainFrame.WIDTH-10-lbl_clause.getWidth(),
				MainFrame.HEIGHT/2-titlepanel.getHeight()-lbl_desc.getHeight()));
		sc.setMinimumSize(clause.getMinimumSize());
		sc.setPreferredSize(clause.getMinimumSize());
		JSeparator sep = new JSeparator();
		Insets in = new Insets(5,5,5,5);
		
		//-------------------GRIDBAG THINGYS-----------------------------------
		//These are the first four fields for the GridBagConstraints constructor
		//gridx,gridy,gridwidth,gridheight
		GridBagConstraints ctitle = new GridBagConstraints(0, 1, 1, 1, 1, 0,
				GridBagConstraints.NORTHWEST, 0, new Insets(0, 5, 0, 5), 0, 0);
		GridBagConstraints ctype = new GridBagConstraints(4, 1, 1, 1, 0, 0,
				GridBagConstraints.BASELINE_TRAILING, 0, new Insets(0, 5, 0, 5),
				0, 0);
		GridBagConstraints cdesc = new GridBagConstraints(1, 3, 4, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				in,	0, 0);
		GridBagConstraints cclause = new GridBagConstraints(1, 4, 4, 10, 1, 0,
				GridBagConstraints.BASELINE_LEADING, GridBagConstraints.BOTH,
				in, 0, 0);
		GridBagConstraints csep = new GridBagConstraints(0, 2, 8, 1, 0, 0,
				GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 0), 0, 0);
		// -----------------------LABEL CONTRAINTS---------------------------
		GridBagConstraints clbltitle = new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, 0, in, 0, 0);
		GridBagConstraints clbltype = new GridBagConstraints(4, 0, 1, 1, 0, 0,
				GridBagConstraints.BASELINE_TRAILING, 0, in, 0, 0);
		GridBagConstraints clbldesc = new GridBagConstraints(0, 3, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, 0, in,	0, 0);
		GridBagConstraints clblclause = new GridBagConstraints(0, 4, 1, 1, 0,
				0, GridBagConstraints.BASELINE_LEADING, 0, in, 0, 0);
		
		//-------------ADD ALL THE THINGS------------------------------------
		titlepanel.add(title, ctitle);
		titlepanel.add(lbl_title,clbltitle);
		titlepanel.add(type, ctype);
		titlepanel.add(lbl_type, clbltype);
		titlepanel.add(sep,csep);
		edPanel.add(description, cdesc);
		edPanel.add(lbl_desc, clbldesc);
		edPanel.add(sc, cclause);
		edPanel.add(lbl_clause, clblclause);
		
		this.add(titlepanel, BorderLayout.NORTH);
		this.add(edPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Creates two buttons and the listeners:
	 *  Save
	 *  Cancel
	 */
	public void buildBtnPanel() {
		JPanel btnPanel = new JPanel();
		JButton save = new JButton("Save");
		save.addActionListener((event)->{
			String[] param = new String[4];
			param[0] = title.getText();
			param[1] = type.getText();
			param[2] = description.getText();
			param[3] = clause.getText();
			if(!editing) {
				firePropertyChange(
						CardPanel.SAVEPROPERTY, param, CardPanel.MAINNAME);
			} else {
				firePropertyChange(
						CardPanel.UPDATEPROPERTY, param, CardPanel.MAINNAME);
			}
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((event)->{
			//nothing needs to change
			this.firePropertyChange(
					CardPanel.CANCELPROPERTY, CardPanel.EDITNAME, CardPanel.MAINNAME);
		});
		
		btnPanel.add(save);
		btnPanel.add(cancel);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the JTextAreas to the entrie's current values
	 * Displays the fields in the TextAreas.
	 * @param current is the current entry that is being edited.
	 */
	public void setCurrentEntry(Entry current) {
		// = editor.getCurrentEntry();
		if (current != null) {
			type.setText(current.getType());
			title.setText(current.getTitle());
			description.setText(current.getDescription());
			clause.setText(current.getContent());
			editing = true;
		}
	}
	
	/**
	 * Prepares to add a new entry to the database by setting
	 * the content to the provided String and empties all other
	 * fields.
	 * 
	 * @param newcontent the content of a new Entry.
	 */
	public void setCurrentEntry(String newcontent) {
		this.clause.setText(newcontent);
		type.setText("");
		title.setText("");
		description.setText("");
		editing = false;
	}
}
