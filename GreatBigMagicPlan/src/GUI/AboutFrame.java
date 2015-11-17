package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * @author Melinda Robertson
 * To test out a way to create the about pane Easter Egg.
 */
public class AboutFrame extends JFrame {
	
	/**
	 * It's super serial.
	 */
	private static final long serialVersionUID = -2902400071493857246L;

	/**
	 * Sets up the frame with a main panel that holds multiple JPanels
	 * with labels and text fields.
	 */
	public AboutFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("About Big Magician");
		JLabel lbl = new JLabel("We are...");
		lbl.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN, 16));
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(lbl);
		main.add(createLabel("Melinda Robertson", "The prettiest princess in all the realm."));
		main.add(createLabel("Sean Markus", "The Old Man by the coding sea."));
		main.add(createLabel("Ash", "The Fool."));
		main.add(createLabel("Matthew Cles", "The Doctor will see your code now."));
		main.add(createLabel("Paul Gray", "The Potentate of Feather and Stone."));
		//TODO add more names here		
		this.add(main);

		Dimension d = main.getPreferredSize();
		this.setBounds(200, 200, (int) d.getWidth() + 25, (int) d.getHeight() + 50);
		this.setVisible(true);
	}
	
	/**
	 * Makes a panel that displays the name and description of a person.
	 * @param name is the name.
	 * @param description is the person's description.
	 * @return a JPanel that holds the person's name and description
	 * 			lined up horizontally.
	 */
	private JPanel createLabel(String name, String description) {
		JPanel person = new JPanel();
		person.setLayout(new BoxLayout(person, BoxLayout.X_AXIS));
		JLabel lbl = new JLabel(name);
		lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		person.add(lbl);
		JTextField tf = new JTextField(description);
		tf.setDisabledTextColor(Color.GRAY);
		tf.setEnabled(false);
		person.add(tf);
		return person;
	}

	/**
	 * It's ok folks, it's only main.
	 * @param args nothing to see here...
	 */
	public static void main(String[] args) {
		try {
			new AboutFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
