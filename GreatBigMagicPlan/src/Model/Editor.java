package Model;

import javax.swing.table.TableModel;

/**
 * This Editor connects functionality of the database to the GUI
 * @author Paul Gray(Captain EagleFort)
 * @version 10.45.3.13a
 */
public class Editor {
	/** An instance of the language library.*/
	private Bank myBank = new Bank();
	
	/** An instance of an entry, set to be the currently selected entry.*/
	public Entry myCurrentEntry = null;
	
	/**
	 * This method adds the newly constructed entry to the language library.
	 * @param theEntry The Entry object you want to add to the library.
	 */
	public void add(Entry theEntry) {
		myBank.addEntry(theEntry);
	}
	
	/**
	 * This method removes the currently selected entry from the language library.
	 * @param theEntry The Entry object to be deleted from the library.
	 */
	public void remove(){
		myBank.deleteEntry(myCurrentEntry);
		myCurrentEntry = null;
	}
/**
 * This method returns the currently selected Entry object.
 * @return Entry The selected entry.
 */
	public Entry getCurrentEntry() {
		return myCurrentEntry;
	}
	
	/**
	 * This method sets the current Entry to the value passed in.
	 * @param theEntry The Entry object to be set.
	 */
	public void setCurrentEntry(String theEntry) {
		if (theEntry == null) {
			myCurrentEntry = null;
			return;
		}
		myCurrentEntry = myBank.getEntry(theEntry);
	}
	/**
	 * This method changes the values of the currently selected 
	 * Entry object to the parameters passed to it.
	 * @param theTitle The new title for the Entry.
	 * @param theType The new type for the Entry.
	 * @param theDesc The new description for the Entry.
	 * @param theContent The new content for the Entry.
	 * @throws NullPointerException
	 */
	public void changeEntry(String theTitle, String theType, String theDesc, 
			String theContent) throws NullPointerException {		
		
		if(myCurrentEntry == null) throw new NullPointerException();
		Entry theNewEntry = new Entry(theTitle, theType, theDesc, theContent);
		myBank.modifyEntry(theNewEntry, myCurrentEntry);

	}
	/**
	 * This method returns the current table model object.
	 * @return TableModel the current state of the database.
	 */
	public TableModel getTable() {
		return myBank.getTable();
	}			
}