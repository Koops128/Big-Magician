/**
 * 
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Model.Editor;
import Model.Entry;

/**
 * @author Melinda Robertson
 * @author Sean Markus
 * @version 20151125
 */
public class EditPanel extends JPanel implements Observer{
	
	/**
	 * Super Serial
	 */
	private static final long serialVersionUID = -8289922698140492731L;

	private JTextField type;
	
	private JTextField title;
	
	private JTextField description;
	
	private JTextArea clause;
	
	private Editor editor;
	private Entry current;
	
	public EditPanel(Editor editor) {
		//editor.registerListener(this);
		this.editor = editor;
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
		title = new JTextField();
		description = new JTextField();
		clause = new JTextArea();
		clause.setLineWrap(true);
		clause.setWrapStyleWord(true);
		JScrollPane sc = new JScrollPane(clause);
		JSeparator sep = new JSeparator();
		Insets in = new Insets(5,5,5,5);
		//These are the first four fields for the GridBagConstraints constructor
		//gridx,gridy,gridwidth,gridheight
		GridBagConstraints ctitle = new GridBagConstraints(0, 1, 1, 1, 1, 0,
				GridBagConstraints.BASELINE_LEADING, 0,
				new Insets(0, 5, 0, 5), 0, 0);
		GridBagConstraints ctype = new GridBagConstraints(4, 1, 1, 1, 0, 0,
				GridBagConstraints.BASELINE_TRAILING, 0, new Insets(0, 5, 0, 5),
				0, 0);
		GridBagConstraints cdesc = new GridBagConstraints(1, 3, 4, 1, 0, 0,
				GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL,
				in,	0, 0);
		GridBagConstraints cclause = new GridBagConstraints(1, 4, 4, 2, 1, 0,
				GridBagConstraints.BASELINE_LEADING, GridBagConstraints.BOTH,
				in, 0, 0);
		GridBagConstraints csep = new GridBagConstraints(0, 2, 8, 1, 0, 0,
				GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 0), 0, 0);
		// -----------------------LABEL CONTRAINTS---------------------------
		GridBagConstraints clbltitle = new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.BASELINE_LEADING, 0,
				new Insets(0, 5, 0, 5), 0, 0);
		GridBagConstraints clbltype = new GridBagConstraints(4, 0, 1, 1, 0, 0,
				GridBagConstraints.BASELINE_TRAILING, 0, new Insets(0, 5, 0, 5),
				0, 0);
		GridBagConstraints clbldesc = new GridBagConstraints(0, 3, 1, 1, 0, 0,
				GridBagConstraints.BASELINE_LEADING, 0, in,
				0, 0);
		GridBagConstraints clblclause = new GridBagConstraints(0, 4, 1, 1, 0,
				0, GridBagConstraints.BASELINE_LEADING, 0, in, 0, 0);
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
	 * Two buttons:
	 *  Save
	 *  Cancel
	 */
	public void buildBtnPanel() {
		JPanel btnPanel = new JPanel();
		JButton save = new JButton("Save");
		save.addActionListener((event)->{
			current.setType(type.getText());
			current.setTitle(title.getText());
			current.setDescription(description.getText());
			current.setContent(clause.getText());
			//editor.changeEntry(current);
			this.firePropertyChange(
					CardPanel.PROPERTYNAME, CardPanel.EDITNAME, CardPanel.MAINNAME);
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener((event)->{
			//nothing needs to change
			this.firePropertyChange(
					CardPanel.PROPERTYNAME, CardPanel.EDITNAME, CardPanel.MAINNAME);
		});
		
		btnPanel.add(save);
		btnPanel.add(cancel);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Takes in the Entry that will be edited, this method will be called by something?
	 * Displays the fields in the TextAreas.
	 * 
	 * @param e The Entry
	 */
	public void setCurrentEntry() {
		this.current = editor.getCurrentEntry();
		type.setText(current.getType());
		title.setText(current.getTitle());
		description.setText(current.getDescription());
		clause.setText(current.getContent());
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO gets the current Entry to edit from
		//the editor
//		if (o instanceof ObservableEditor) {
//			if (arg instanceof Entry) {
//				setCurrentEntry(e);
//			}
//		}
		
	}
}
