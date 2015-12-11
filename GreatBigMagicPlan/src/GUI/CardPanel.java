package GUI;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import Model.Editor;

/**
 * @author Melinda Robertson
 * @version 20151211
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
	/**
	 * The main panel that holds a list of entries.
	 */
	
	public final static String REMOVEPROPERTY = "remove";
	public final static String SELECTPROPERTY = "select";
	/**
	 * The remove property tells the card panel to remove an entry and reset
	 * the main panel.
	 */
	private MainPanel main;
	/**
	 * The name for the main panel, used when switching panels.
	 */
	public final static String MAINNAME = "Clause View";
	/**
	 * The edit panel where the user can make changes to the
	 * current entry or create a new entry.
	 */
	private EditPanel edit;
	/**
	 * The name of the edit panel, used when switching panels.
	 */
	public final static String EDITNAME = "Edit View";
	
	private Menu menu;
	/**
	 * Constructs the card panel by making it a listener for
	 * certain property change events.
	 * @param editor is the go-between for the database.
	 * @param menu is the menu bar that adds new entries.
	 */
	public CardPanel(Editor editor, Menu menu) {
		this.setLayout(new CardLayout());
		
		main = new MainPanel(editor);
		edit = new EditPanel(editor);
		this.menu = menu;
		
		main.addPropertyChangeListener("switch", this);
		edit.addPropertyChangeListener("switch", this);
		menu.addPropertyChangeListener("switch", this);
		menu.addPropertyChangeListener("add", this);
		menu.addPropertyChangeListener("remove", this);
		main.addPropertyChangeListener("select", this);
		
		this.add(main, MAINNAME);
		this.add(edit, EDITNAME);
	}

	/**
	 * The property change event is constructed so that the old
	 * value is only used when adding an entry and the new value
	 * is always the name of the panel to switch to.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (SWITCHPROPERTY.equals(event.getPropertyName())) {
			CardLayout cd = (CardLayout) this.getLayout();
			cd.show(this, (String) event.getNewValue());
			edit.setCurrentEntry();
			main.resetTable();
			main.listSelection();
		} else if (ADDPROPERTY.equals(event.getPropertyName())) {
			edit.setCurrentEntry((String) event.getOldValue());
			CardLayout cd = (CardLayout) this.getLayout();
			cd.show(this, (String) event.getNewValue());
		} else if (REMOVEPROPERTY.equals(event.getPropertyName())) {
			main.resetTable();
		} else if (SELECTPROPERTY.equals(event.getPropertyName())) {
			menu.setEnabled((boolean) event.getOldValue());
			System.out.println("went in here!");
		}
	}
}
