/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Mindy
 * @author Matthew Cles
 */
public class EntryTableModel extends AbstractTableModel {
    
    public static final String[] COL_NAMES = {"Title", "Type", "Description"};
    
    /**
     * Awwww yeeeeeeaaaahhhhhhhhhhhhhhhhhhh, yuh gotta get Shwifty
     */
    private static final long serialVersionUID = -8580338694528729389L;
    
    List<Entry>  myList;
    
    public EntryTableModel() {
        myList = new ArrayList<Entry>();
    }
    
    public void add(Entry e) {
        myList.add(e);
    }
    
    public void remove(Entry e) {
        myList.remove(e);
    }

    public void remove(String s) {
        for (Entry e : myList) {
            if (e.getTitle().equals(s)) {
                myList.remove(e);
                return;
            }
        }
    }
    
    public boolean contains(Entry e) {
        return myList.contains(e);
    }
    
    public boolean contains(String s) {
        for (Entry e : myList) {
            if (e.getTitle().equals(s))
                return true;
        }
        return false;
    }
    
    public Entry get(String s) {
        for (Entry e : myList) {
            if (e.getTitle().equals(s))
                return e;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        return 3;
    }
    

    
    public String getColumnName(int x) {
        return COL_NAMES[x];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        return myList.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
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