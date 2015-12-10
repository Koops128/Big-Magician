package Model;

public class Entry {
	
	private String myTitle, myType, myDescription, myContent;
	
	public Entry (String title, String type, String description, String content) {
		myTitle = title;
		myType = type;
		myDescription = description;
		myContent = content;
	}

	public void setTitle(String title) {
		myTitle = title;
	}
	
	public void setType(String type) {
		myType = type;
	}
	
	public void setDescription(String description) {
		myDescription = description;
	}
	
	public void setContent(String content) {
		myContent = content;
	}

	public String getTitle() {
		return myTitle;
	}
	
	public String getType() {
		return myType;
	}
	
	public String getDescription() {
		return myDescription;
	}
	
	public String getContent() {
		return myContent;
	}
}
