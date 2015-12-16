package GUI;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import Model.Editor;

/**
 * @author Melinda Robertson
 * @version 20151215
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
	private MainPanel main;
	/**
	 * The name for the main panel, used when switching panels.
	 */
	public final static String MAINNAME = "Clause View";
	/**
	 * The edit panel where the user can make changes to the
	 * current entry or create a new entry.
	 */
	public final static String REMOVEPROPERTY = "remove";
	/**
	 * The remove property tells the card panel to remove an entry and reset
	 * the main panel.
	 */
	public final static String SELECTPROPERTY = "select";
	/**
	 * The filter property changes the contents of the table when it fires.
	 */
	public final static String FILTERPROPERTY = "filter";
	/**
	 * This panel is where changes to an entry can be made.
	 */
	private EditPanel edit;
	/**
	 * The name of the edit panel, used when switching panels.
	 */
	public final static String EDITNAME = "Edit View";
	/**
	 * This panel displays a table with entries that the user can choose between.
	 */
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
		
		main.addPropertyChangeListener(SWITCHPROPERTY, this);
		edit.addPropertyChangeListener(SWITCHPROPERTY, this);
		menu.addPropertyChangeListener(SWITCHPROPERTY, this);
		menu.addPropertyChangeListener(ADDPROPERTY, this);
		menu.addPropertyChangeListener(REMOVEPROPERTY, this);
		main.addPropertyChangeListener(SELECTPROPERTY, this);
		menu.addPropertyChangeListener(FILTERPROPERTY, this);

		
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
		} else if (FILTERPROPERTY.equals(event.getPropertyName())) {
			String[] list = (String[]) event.getOldValue();
			main.resetTable(list);
		}
	}
}
