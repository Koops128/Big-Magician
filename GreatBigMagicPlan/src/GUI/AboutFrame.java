 package GUI;
 
 import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

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
 	/**
 	 * Is the main content pane for this frame.
 	 */
 	private JPanel main;
 	/**
 	 * Defines the content to display when the button is pushed.
 	 */
 	private final String[][] information = new String[][] {
 			{"Copyright","2015"},
 			{"License", "This program is licensed under General Public License, version 3 GPL-3.0."},
            {"", ""},
            {"Sorry", "That's all."},
            {"", ""},
            {"...", "..."},
            {"Ouch", "..."},
            {"Okay", "Now you're hurting me"},
            {"Please", "Please stop that"},
            {"Seriously", "My insides hurt"},
            {"STOP", "CLICKING"},
            {"I'M GETTING MAD!", "DON'T MAKE ME TERMINATE THIS PROGRAM!"},
            {"You're", "Asking for it!"},
            {"I AM", "WARNING YOU!!!"}
 	};
 	/**
 	 * Counts what information was already displayed.
 	 */
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
 			if(counter < information.length) {
 				name = information[counter][0];
 				desc = information[counter][1];
 				counter ++;
 				addNewLabel(name,desc);
 			} else {
 				addNewLabel("", "");
 				try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
 				
 				System.exit(0); 	
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
 	/**
 	 * Resets the size of this frame when new things are added
 	 * so that they fit.
 	 * @param x is the new horizontal position of the window.
 	 * @param y is the new vertical position of the window.
 	 */
 	private void resetSize(int x, int y) {
 		Dimension d = main.getPreferredSize();
 		this.setBounds(x,y, (int) d.getWidth() + 25, (int) d.getHeight() + 50);
 	}
}
