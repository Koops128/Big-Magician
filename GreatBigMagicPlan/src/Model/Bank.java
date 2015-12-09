/**
 * @author Matthew Cles
 * @version 1.1
 */

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.TableModel;

public class Bank {
	
	private TableModel myEntries;
	private Connection conn;
	
	public Bank() {
		myEntries = new EntryTableModel();
		conn = null;
		createConnection();
		loadEntries();
	}

	public void createConnection() {
		System.out.println("opening connection");
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			try {
				startConnection();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			createConnection();
		}
		try {
			this.conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. SQL ERR: " + e);
		}
	}
	
	private void loadEntries() {
		Statement stmt = null;
		String query = "select Title, Type, Description, Content "
				+ "from data ";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Found Entries:");
			while (rs.next()) {
				String title = rs.getString("Title");
				String type = rs.getString("Type");
				String description = rs.getString("Description");
				String content = rs.getString("Content");
				Entry e = new Entry(title, type, description, content);
				((EntryTableModel) myEntries).add(e);
				System.out.println("  Title: " + title);
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void deleteEntry(Entry e) {
		((EntryTableModel) myEntries).remove(e);
		PreparedStatement stmt = null;
		String query = "delete from data where Title = ?";
	      try { 
	    	stmt = conn.prepareStatement(query);
			stmt.setString(1, e.myTitle);
			stmt.execute();
			System.out.println("Entry with title: '" + e.myTitle + "' has been deleted from the Bank");
		} catch (SQLException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}
	}
	
	public void addEntry(Entry e) {
		if (((EntryTableModel) myEntries).contains(e)) {
			System.out.println("Entry: " + e.myTitle + " already in the Bank");
		} else if (((EntryTableModel) myEntries).contains(e.myTitle)) {
			System.out.println("Another Entry already has Title: " + e.myTitle);			
		} else {
			((EntryTableModel) myEntries).add(e);
			String sql = "insert into data values " + "(?, ?, ?, ?); ";
			PreparedStatement preparedStatement = null;
			try {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, e.myTitle);
				preparedStatement.setString(2, e.myType);
				preparedStatement.setString(3, e.getDescription());
				preparedStatement.setString(4, e.myContent);
				preparedStatement.executeUpdate();
				System.out.println("New Entry added with title: " + e.myTitle);
			} catch (SQLException e1) {
				System.out.println(e1);
				e1.printStackTrace();
			} 
		}
	}
	
	public Entry getEntry(String x) {
		return ((EntryTableModel) myEntries).get(x);
	}
	
	public TableModel getTable() {
		return myEntries;
	}
		
	public static void main(String[] args) {
		System.out.println("Start Bank Test:");
		Bank b = new Bank();
		Entry newEntry = new Entry ("New Entry", "Sample", "a test for the bank", "Why are you reading this? GET BACK TO CODE!!!!!!!!1!!");
		Entry newEntry2 = new Entry ("New Entry 2", "Sample", "another test for the bank", "Seriously why are you still reading these? Like wasting time much??");
		Entry newEntry3 = new Entry ("New Entry 3", "Sample", "yet another test for the bank", "Im not even gonna dignify that you're still reading these...");
		Entry newEntry2b = new Entry ("New Entry 2", "Sample", "test test testy test", "ohhhhh i bet you didnt expect that! duplicate Title son!");
		b.addEntry(newEntry);
		b.addEntry(newEntry2);
		b.addEntry(newEntry);
		b.addEntry(newEntry2b);
		b.deleteEntry(newEntry);
		b.deleteEntry(newEntry2);
		b.deleteEntry(newEntry3);
		System.out.println("End Bank Test:");
	}
	
    public void startConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists data;");
        stat.executeUpdate("create table data (title unique, type, description, content);");
        PreparedStatement prep = conn.prepareStatement(
            "insert into data values (?, ?, ?, ?);");

        prep.setString(1, "Sample Text File 1");
        prep.setString(2, "Sample");
        prep.setString(3, "This is a file containing sample text");
        prep.setString(4, "OH hey look, guess you found it 'hyuck hyuck' woot de woot");
        prep.addBatch();
        prep.setString(1, "Sample Text File 2");
        prep.setString(2, "Sample");
        prep.setString(3, "This is another file containing sample text");
        prep.setString(4, "OH hey look, guess you found it 'hyuck hyuck' woot de woot");
        prep.addBatch();
        prep.setString(1, "Sample Text File 3");
        prep.setString(2, "Sample");
        prep.setString(3, "This is yet another file containing sample text");
        prep.setString(4, "OH hey look, guess you found it 'hyuck hyuck' woot de woot");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();
    }
}
