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
	
	public final static String PROPERTYNAME = "switch";
	
	private MainPanel main;
	public final static String MAINNAME = "Clause View";
	
	private EditPanel edit;
	public final static String EDITNAME = "Edit View";
	
	public CardPanel(Editor editor) {
		this.setLayout(new CardLayout());
		
		main = new MainPanel(editor);
		edit = new EditPanel(editor);
		
		main.addPropertyChangeListener("switch", this);
		edit.addPropertyChangeListener("switch", this);
		
		this.add(main, MAINNAME);
		this.add(edit, EDITNAME);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (PROPERTYNAME.equals(event.getPropertyName())) {
			CardLayout cd = (CardLayout) this.getLayout();
			cd.show(this, (String) event.getNewValue());
			edit.setCurrentEntry();
			main.resetTable();
		}
	}
}
