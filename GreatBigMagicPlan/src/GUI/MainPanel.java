/**
 * 
 */
package GUI;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 *
 */
public class MainPanel extends JPanel implements Observer {

	private JTable table;
	
	private JTextArea clause;
	
	public MainPanel() {
		buildTableBox();
		buildBtnBox();
		//TODO
	}
	
	public void buildTableBox() {
		table = new JTable();
		add(table);
		//TODO
	}
	
	public void buildBtnBox() {
		//TODO
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
