 package GUI;
 
 import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 /**
  * 
  * @author Melinda Robertson
  * @author Ashcon Minoo
  * @version November 2015
  * It's an egg, an Easter Egg.
  */
 public class AboutFrame extends JFrame {
 	
 	/**
 	 * It's super serial.
 	 */
 	private static final long serialVersionUID =  2902400071493857246L;
 	
 	private JPanel main;
 	
 	private final String[][] information = new String[][] {
 			{"Copyright","License",""},
 			{"2015", "This program is licensed under General Public License, version 3 GPL-3.0.",""}
 	};
 	
 	private int counter = 0;
 
 	/**
 	 * Sets up the frame with a main panel that holds multiple JPanels
 	 * with labels and text fields.
 	 */
 	public AboutFrame() {
 		//this should change to DISPOSE_ON_CLOSE for the actual program
 		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
 		this.setTitle("About Big Magician");
 		JLabel lbl = new JLabel("We are...");
 		lbl.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN, 16));
 		main = new JPanel();
 		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
 		main.add(lbl);
 		main.add(createLabel("Melinda Robertson", "The prettiest princess in all the realm."));
 		main.add(createLabel("Sean Markus", "The Old Man by the coding sea."));
 		main.add(createLabel("Ash", "The Fool who followed him."));
 		main.add(createLabel("Matthew Cles", "The Doctor will see your code now."));
 		main.add(createLabel("Paul Gray", "The Potentate of Feather and Stone."));
 		JButton btn = new JButton("More Things!");
 		btn.addActionListener((event)-> {
 			String name = "";
 			String desc = "";
 			if(counter < information[0].length) {
 				name = information[0][counter];
 				desc = information[1][counter];
 				counter ++;
 				addNewLabel(name,desc);
 			} else {
 				addNewLabel("Sorry", "That's all.");
 			}
 			
 		});
 		main.add(btn);
 		this.add(main);
 		int x = MainFrame.x + ((MainFrame.WIDTH / 4)-(this.getWidth()/2));
 		int y = MainFrame.y + ((MainFrame.HEIGHT / 4)-(this.getHeight()/2));
 		resetSize(x, y);
 		this.setVisible(true);
 	}
 	
 	//for custom javadoc tags...add:
 	// tag mmr.pre:a:"The Pre condition"
 	//to the Extra JavaDoc options when you generate the javadoc
 	/**
 	 * Makes a panel that displays the name and description of a person.
 	 * @mmr.pre Assumes that the name is shorter than the description and
 	 * 			that both are not longer than 82 characters.
 	 * @mmr.post Resulting JPanel will not have wrapped text.
 	 * @param name is the name.
 	 * @param description is the person's description.
 	 * @return a JPanel that holds the person's name and description
 	 * 			lined up horizontally.
 	 */
 	private JPanel createLabel(String name, String description) {
 		JPanel person = new JPanel();
 		person.setLayout(new BoxLayout(person, BoxLayout.X_AXIS));
 		JLabel lbl = new JLabel(" " + name + " ");
 		lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
 		person.add(lbl);
 		JTextField tf = new JTextField(description);
 		tf.setDisabledTextColor(Color.DARK_GRAY);
 		tf.setEnabled(false);
 		person.add(tf);
 		return person;
 	}
 	
 	/**
 	 * This is for the test. For reals, there is no reason for this
 	 * method to exist beyond using it for the JUnit test.
 	 * @mmr.pre Assumes that the name is shorter than the description and
 	 * 			that both are not longer than 82 characters.
 	 * @mmr.post Resulting JPanel will not have wrapped text.
 	 * @param name is the name.
 	 * @param description is the person's description.
 	 */
 	public void addNewLabel(String name, String description) {
 		main.add(createLabel(name, description));
 		resetSize(this.getX(), this.getY());
 		this.setVisible(true);
 	}
 	
 	private void resetSize(int x, int y) {
 		Dimension d = main.getPreferredSize();
 		this.setBounds(x,y, (int) d.getWidth() + 25, (int) d.getHeight() + 50);
 	}
}
