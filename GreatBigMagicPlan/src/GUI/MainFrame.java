/**
 * 
 */
package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Model.Editor;

/**
 * Starts the program.
 * @author Sean Markus
 * @author Melinda Robertson
 * @version 20151209
 */
public class MainFrame extends JFrame{

	private Menu menu; 
	
	private CardPanel cards;
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	private static final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int x = (int) ((screensize.getWidth()-WIDTH) / 2);
	public static final int y = (int) ((screensize.getHeight()-HEIGHT) / 2);
	
	public MainFrame() {
		this.setTitle("Clause");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(x,y,WIDTH,HEIGHT);
		Editor editor = new Editor();
		cards = new CardPanel(editor);
		menu = new Menu(/*editor*/);
		this.setJMenuBar(menu);
		this.setContentPane(cards);
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
