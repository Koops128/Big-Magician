/**
 * 
 */
package GUI;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 *
 */
public class EditPanel extends JPanel implements Observer{
	
	private JTextField type;
	
	private JTextField title;
	
	private JTextField description;
	
	private JTextArea clause;
	
	public EditPanel() {
		//TODO
	}
	
	public void buildEditPanel() {
		//TODO
	}
	
	public void buildBtnPanel() {
		//TODO
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
