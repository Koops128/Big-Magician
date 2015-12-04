package Model;

import javax.swing.table.TableModel;

/**
 * DIS EDITR B GAWD CALSS
 * @author Paul Gray(Captain EagleFort)
 */
public class Editor {
	
	Bank myBank = new Bank();
	Entry myCurrentEntry = null;
	private boolean editing = false;
	private boolean canEdit = false;
	
	public void add(Entry theEntry) {
		myBank.addEntry(theEntry);
	}
	public void remove(Entry theEntry){
		myBank.deleteEntry(theEntry);
		
	}
	public Entry getCurrentEntry(){
		return myCurrentEntry;
		
	}
	
	public void setCurrentEntry(String theEntry) {
		myCurrentEntry = myBank.getEntry(theEntry);
	}
	
	public TableModel getTable() {
		return myBank.getTable();
	}
	
			
			
}