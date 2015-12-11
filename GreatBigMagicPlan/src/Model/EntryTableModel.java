/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * This Class is a table representation of the data from the bank Class to interact with the GUI
 * Javadoc author Paul Gray
 * @author Melinda Robertson
 * @author Matthew Cles
 */
public class EntryTableModel extends AbstractTableModel {
    /** A string array that holds the title, type, and description of each entry.*/
    public static final String[] COL_NAMES = {"Title", "Type", "Description"};
    
    /** Silencing the screams of a thousand warnings.*/
    private static final long serialVersionUID = -8580338694528729389L;
    
    List<Entry>  myList;
    /**
     * A constructor for the Entry Table Model.
     */
    public EntryTableModel() {
        myList = new ArrayList<Entry>();
    }
    
    /**
     * This method adds an entry to the table model.
     * @param e An Entry to add to the table model.
     */
    public void add(Entry e) {
        myList.add(e);
    }
    
    /**
     * This method removes an entry from the table model.
     * @param e An entry to remove from the table model.
     */
    public void remove(Entry e) {
        myList.remove(e);
    }
    
    /**
	 * This method removes an entry from the table model.
	 * @param s A string title of the entry to be removed from the table model.
	 */
    public void remove(String s) {
        for (Entry e : myList) {
            if (e.getTitle().equals(s)) {
                myList.remove(e);
                return;
            }
        }
    }
    
    /**
     * This method returns a boolean value of whether the Entry is in the table or not.
     * @param e An Entry to check if it is in the table or not.
     * @return boolean whether the Entry is present.
     */
    public boolean contains(Entry e) {
        return myList.contains(e);
    }
    /**
     * This method returns a boolean value of whether the Entry is in the table or not, 
     * based on a string representation of it's title.
     * @param s A String title to check if it is in the table or not.
     * @return boolean whether the Entry is present.
     */
    public boolean contains(String s) {
        for (Entry e : myList) {
            if (e.getTitle().equals(s))
                return true;
        }
        return false;
    }
    /**
     * This method returns an Entry from the table based on it's title.
     * @param s A string title to retrieve the Entry with.
     * @return Entry An Entry with the matching title.
     */
    public Entry get(String s) {
        for (Entry e : myList) {
            if (e.getTitle().equals(s))
                return e;
        }
        return null;
    }

    /**
     * This method returns the column count for the table.
     * @return int The column count for the table.
     */
    @Override
    public int getColumnCount() {
        return 3;
    }
    

    /**
     * This method takes an index and returns the corresponding column's name.
     * @param x The column index
     * @return String A string representation of the column name.
     */
    public String getColumnName(int x) {
        return COL_NAMES[x];
    }

    /**
     * This method returns the row count of the table model.
     * @return int The row count for the table.
     */
    @Override
    public int getRowCount() {
        return myList.size();
    }

    /**
     * This method returns the value at a given location of the table model.
     * @param x An int representation of the column.
     * @param y An int representation of the row. 
     * @return Object The object stored at the x, y location of the table model.
     */
    @Override
    public Object getValueAt(int x, int y) {
        Entry e = myList.get(x);
        if (y == 0) {
            return e.getTitle();
        } else if (y == 1) {
            return e.getType();
        } else {
            return e.getDescription();
        }
    }

}