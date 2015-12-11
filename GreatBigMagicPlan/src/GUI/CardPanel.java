package GUI;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import Model.Editor;

/**
 * @author Melinda Robertson
 * @version 20151125
 */
public class CardPanel extends JPanel implements PropertyChangeListener{
	
	/**
	 * Super serial.
	 */
	private static final long serialVersionUID = 3034268517929753111L;
	/**
	 * The switch property tells the card panel to get the current entry
	 * from the table and switch to the edit panel.
	 */
	public final static String SWITCHPROPERTY = "switch";
	/**
	 * The add property tells the card panel to set the current entry
	 * to null to prepare
	 */
	public final static String ADDPROPERTY = "add";
	
	private MainPanel main;
	public final static String MAINNAME = "Clause View";
	
	private EditPanel edit;
	public final static String EDITNAME = "Edit View";
	
	public CardPanel(Editor editor, Menu menu) {
		this.setLayout(new CardLayout());
		
		main = new MainPanel(editor);
		edit = new EditPanel(editor);
		
		main.addPropertyChangeListener("switch", this);
		edit.addPropertyChangeListener("switch", this);
		menu.addPropertyChangeListener("switch", this);
		menu.addPropertyChangeListener("add", this);
		
		this.add(main, MAINNAME);
		this.add(edit, EDITNAME);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (SWITCHPROPERTY.equals(event.getPropertyName())) {
			CardLayout cd = (CardLayout) this.getLayout();
			cd.show(this, (String) event.getNewValue());
			edit.setCurrentEntry();
			main.resetTable();
		} else if (ADDPROPERTY.equals(event.getPropertyName())) {
			edit.setCurrentEntry((String) event.getOldValue());
			CardLayout cd = (CardLayout) this.getLayout();
			cd.show(this, (String) event.getNewValue());
		}
	}
}
