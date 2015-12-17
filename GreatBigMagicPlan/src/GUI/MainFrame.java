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

	/**
	 * Super serial.
	 */
	private static final long serialVersionUID = 8923934457439431633L;
	
	/**
	 * The width of this frame.
	 */
	public static final int WIDTH = 500;
	/**
	 * The height of this frame.
	 */
	public static final int HEIGHT = 500;
	/**
	 * The current size of the screen used for positioning this frame.
	 */
	private static final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * Sets the horizontal position of this frame in the screen.
	 */
	public static final int x = (int) ((screensize.getWidth()-WIDTH) / 2);
	/**
	 * Sets the vertical position of this fram in the screen.
	 */
	public static final int y = (int) ((screensize.getHeight()-HEIGHT) / 2);
	
	/**
	 * Constructs the frame that holds the majority of the program contents.
	 */
	public MainFrame() {
		this.setTitle("Clause");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(x,y,WIDTH,HEIGHT);
		Editor editor = new Editor();
		Menu menu = new Menu(editor);
		CardPanel cards = new CardPanel(editor, menu);
		this.setJMenuBar(menu);
		this.setContentPane(cards);
		this.setVisible(true);
	}
	
	/**
	 * Begin the program here.
	 * @param args are the args.
	 */
	public static void main(String[] args) {
		try {
			new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
