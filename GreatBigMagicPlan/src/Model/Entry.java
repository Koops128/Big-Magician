package Model;

/**
 * This class holds the data of an Entry.
 * @author Matthew Cles
 * @author Ashcon Minoo
 * Debugged - Paul Gray
 * @version 2.0
 */
public class Entry {
	
	/**The Strings that are in the Entry.*/
	private String myTitle, myType, myDescription, myContent;
	
	/**
	 * Constructor for the Entry.
	 * @param title The Title of the Entry
	 * @param type The Type of the Entry
	 * @param description The Description of the Entry
	 * @param content The Content of the Entry
	 */
	public Entry (String title, String type, String description, String content) {
		myTitle = title;
		myType = type;
		myDescription = description;
		myContent = content;
	}
	
	/**
	 * Sets the Title to a new value.
	 * @param title The new Title
	 */
	public void setTitle(String title) {
		myTitle = title;
	}
	
	/**
	 * Sets the Type to a new value.
	 * @param type The new Type
	 */
	public void setType(String type) {
		myType = type;
	}
	
	/**
	 * Sets the Description to a new value.
	 * @param desctiption The new Description
	 */
	public void setDescription(String description) {
		myDescription = description;
	}
	
	/**
	 * Sets the Content to a new value.
	 * @param content The new Content
	 */
	public void setContent(String content) {
		myContent = content;
	}

	/**
	 * Gets the Title.
	 * @return The Title
	 */
	public String getTitle() {
		return myTitle;
	}

	/**
	 * Gets the Type.
	 * @return The Type
	 */
	public String getType() {
		return myType;
	}

	/**
	 * Gets the Description.
	 * @return The Description
	 */
	public String getDescription() {
		return myDescription;
	}

	/**
	 * Gets the Content.
	 * @return The Content
	 */
	public String getContent() {
		return myContent;
	}
}
