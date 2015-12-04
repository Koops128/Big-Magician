/**
 * 
 */
package Model;

import javax.swing.table.AbstractTableModel;

/**
 * @author Mindy
 *
 */
public class EntryTableModel extends AbstractTableModel {
	
	Entry[][] stuff;

	/**
	 * 
	 */
	public EntryTableModel(Entry[][] stuff) {
		this.stuff = stuff;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return stuff.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return stuff[0].length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int x, int y) {
		return stuff[x][y];
	}

}
