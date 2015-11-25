package GUI;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * @author Melinda Robertson
 * @version 20151125
 */
public class CardPanel extends JPanel implements PropertyChangeListener{
	
	public final static String PROPERTYNAME = "switch";
	
	private JPanel main;
	public final static String MAINNAME = "Clause View";
	
	private JPanel edit;
	public final static String EDITNAME = "Edit View";
	
	public CardPanel(/*ObservableEditor editor*/) {
		this.setLayout(new CardLayout());
		
		main = new JPanel(/*editor*/);
		edit = new JPanel(/*editor*/);
		
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
		}
	}
}
