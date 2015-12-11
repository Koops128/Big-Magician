package Model;

import javax.swing.table.TableModel;

/**
 * DIS EDITR B GAWD CALSS
 * @author Paul Gray(Captain EagleFort)
 */
public class Editor {
	
	private Bank myBank = new Bank();
	private Entry myCurrentEntry = null;
	private boolean editing = false;
	private boolean canEdit = false;
	
	public void add(Entry theEntry) {
		myBank.addEntry(theEntry);
	}
	public void remove(){
		myBank.deleteEntry(myCurrentEntry);
		myCurrentEntry = null;
	}
	public Entry getCurrentEntry() throws NullPointerException {
		if (myCurrentEntry == null) throw new NullPointerException();
		return myCurrentEntry;
	}
	
	public void setCurrentEntry(String theEntry) {
		if (theEntry == null) {
			myCurrentEntry = null;
			return;
		}
		myCurrentEntry = myBank.getEntry(theEntry);
	}
	
	public void changeEntry(String title, String type, String desc, 
			String content) throws NullPointerException {
		if(myCurrentEntry == null) throw new NullPointerException();
		myBank.deleteEntry(myCurrentEntry.getTitle());
		myCurrentEntry.setContent(content);
		myCurrentEntry.setDescription(desc);
		myCurrentEntry.setTitle(title);
		myCurrentEntry.setType(type);
		myBank.addEntry(myCurrentEntry);
	}
	
	public TableModel getTable() {
		return myBank.getTable();
	}
	
			
			
}