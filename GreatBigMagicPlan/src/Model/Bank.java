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
import java.util.Properties;

import javax.swing.table.TableModel;

public class Bank {
	
	public static final String USERNAME = "root";
	public static final String PASSWORD = "";
	
	//private List<Entry> myEntries;
	private TableModel myEntries;
	private Connection conn;
	
	public Bank() {
		//myEntries = new ArrayList<Entry>();
		myEntries = new EntryTableModel();
		conn = null;
		createConnection();
		loadEntries();
	}

	public void createConnection() {
		Properties connectionProps = new Properties();
		connectionProps.put("user", USERNAME);
		connectionProps.put("password", PASSWORD);	
		System.out.println("opening connection");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.conn = DriverManager.getConnection(
			        "jdbc:mysql://localhost:3306/360Project", connectionProps);
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("Could not connect to the database. SQL ERR: " + e);
		}
	}
	
	private void loadEntries() {
		Statement stmt = null;
		String query = "select Title, Type, Description, Content "
				+ "from 360Project.data ";
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
		String query = "delete from 360Project.data where Title = ?";
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
			String sql = "insert into 360Project.data values " + "(?, ?, ?, ?); ";
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
		
//	public static void main(String[] args) {
//		System.out.println("Start Bank Test:");
//		Bank b = new Bank();
//		Entry newEntry = new Entry ("New Entry", "Sample", "a test for the bank", "Why are you reading this? GET BACK TO CODE!!!!!!!!1!!");
//		Entry newEntry2 = new Entry ("New Entry 2", "Sample", "another test for the bank", "Seriously why are you still reading these? Like wasting time much??");
//		Entry newEntry3 = new Entry ("New Entry 3", "Sample", "yet another test for the bank", "Im not even gonna dignify that you're still reading these...");
//		Entry newEntry2b = new Entry ("New Entry 2", "Sample", "test test testy test", "ohhhhh i bet you didnt expect that! duplicate Title son!");
//		b.addEntry(newEntry);
//		b.addEntry(newEntry2);
//		b.addEntry(newEntry);
//		b.addEntry(newEntry2b);
//		b.deleteEntry(newEntry);
//		b.deleteEntry(newEntry2);
//		b.deleteEntry(newEntry3);
//		System.out.println("End Bank Test:");
//	}
}
