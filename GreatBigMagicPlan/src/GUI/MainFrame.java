/**
 * 
 */
package GUI;

import javax.swing.JFrame;

import Model.Editor;

/**
 * Starts the program.
 * @author Sean Markus
 * @author Melinda Robertson
 * @version 20151203
 */
public class MainFrame extends JFrame{

	private Menu menu; 
	
	private CardPanel cards;
	
	public MainFrame() {
		this.setTitle("Clause");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Editor editor = new Editor();
		cards = new CardPanel(editor);
		menu = new Menu(/*editor*/);
		this.setJMenuBar(menu);
		this.setContentPane(cards);
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
