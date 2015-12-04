/**
 * 
 */
package GUI;

import javax.swing.JFrame;

/**
 * @author Sean
 * @author Melinda Robertson
 * @version 20151203
 */
public class MainFrame extends JFrame{

	private Menu menu; 
	
	private CardPanel cards;
	
	public MainFrame() {
		this.setTitle("Clause");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new CardPanel();
		menu = new Menu();
		this.add(cards);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
