package GUI;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import Model.Editor;
import Model.Entry;

/**
 * Manages what happens when buttons are pressed in the two
 * child panels, main and edit. Listens for various property changes
 * and acts accordingly.
 * @author Melinda Robertson
 * @version 20151217
 */
public class CardPanel extends JPanel implements PropertyChangeListener{
	
	/**
	 * Super serial.
	 */
	private static final long serialVersionUID = 3034268517929753111L;
	/**
	 * The add property tells the card panel to set the current entry
	 * to null to prepare
	 */
	public final static String ADDPROPERTY = "add";
	/**
	 * The switch property tells the card panel to get the current entry
	 * from the table and switch to the edit panel.
	 */
	public final static String EDITPROPERTY = "edit";
	/**
	 * The remove property tells the card panel to remove an entry and reset
	 * the main panel.
	 */
	public final static String DELETEPROPERTY = "delete";
	/**
	 * Fired when save is pressed on the edit panel.
	 */
	public final static String SAVEPROPERTY = "save";
	/**
	 * Fired when save is pressed on the edit panel and it's updating
	 * the current entry.
	 */
	public final static String UPDATEPROPERTY = "update";
	/**
	 * Fired when cancel is pressed on the edit panel.
	 */
	public final static String CANCELPROPERTY = "cancel";
	/**
	 * Fired when an item is selected on the main panel's table.
	 */
	public final static String SELECTPROPERTY = "select";
	/**
	 * The filter property changes the contents of the table when it fires.
	 */
	public final static String FILTERPROPERTY = "filter";
	
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
	 * Accesses database information.
	 */
	private Editor editor;
	/**
	 * Constructs the card panel by making it a listener for
	 * certain property change events.
	 * @param editor is the go-between for the database.
	 * @param menu is the menu bar that adds new entries.
	 */
	public CardPanel(Editor editor, Menu menu) {
		this.setLayout(new CardLayout());
		this.editor = editor;
		main = new MainPanel(editor);
		edit = new EditPanel();
		this.menu = menu;
		
		main.addPropertyChangeListener(EDITPROPERTY, this);
		main.addPropertyChangeListener(SELECTPROPERTY, this);
		main.addPropertyChangeListener(DELETEPROPERTY, this);
		
		edit.addPropertyChangeListener(SAVEPROPERTY, this);
		edit.addPropertyChangeListener(UPDATEPROPERTY, this);
		edit.addPropertyChangeListener(CANCELPROPERTY, this);
		
		menu.addPropertyChangeListener(ADDPROPERTY, this);
		menu.addPropertyChangeListener(EDITPROPERTY, this);
		menu.addPropertyChangeListener(DELETEPROPERTY, this);
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
		CardLayout cd = (CardLayout) this.getLayout();
		cd.show(this, (String) event.getNewValue());
		switch(event.getPropertyName()) {
		case ADDPROPERTY:
			editor.setCurrentEntry(null);
			edit.setCurrentEntry((String)event.getOldValue());
			break;
		case EDITPROPERTY:
			edit.setCurrentEntry(editor.getCurrentEntry());
			break;
		case DELETEPROPERTY:
			System.out.println(editor.getCurrentEntry().getTitle());
			editor.remove();
			menu.createCbButtons();
			main.resetTable();
			break;
		case SAVEPROPERTY:
			String[] param = (String[]) event.getOldValue();
			Entry current = new Entry(param[0],param[1],
					param[2],param[3]);
			editor.add(current);
			menu.createCbButtons();
			main.resetTable();
			break;
		case UPDATEPROPERTY:
			String[] param2 = (String[]) event.getOldValue();
			editor.changeEntry(param2[0],param2[1],
					param2[2],param2[3]);
			menu.createCbButtons();
			main.resetTable();
			break;
		case CANCELPROPERTY:
			main.listSelection();
			break;
		case FILTERPROPERTY:
			main.resetTable((String[]) event.getOldValue());
			break;
		case SELECTPROPERTY:
			menu.setEnabled((boolean) event.getOldValue());
			break;
		default:
			//System.out.println(event.getPropertyName());
		}
	}
}
