/**
 * @author Matthew Cles
 * @version 2.0
 */

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

public class Bank {
    
    //the entries the are stored in the bank
    private TableModel myEntries;
    //the connection to the database
    private Connection conn;
    
    //constructor
    public Bank() {
        myEntries = new EntryTableModel();
        conn = null;
        createConnection();
        loadEntries();
    }

    //creates a connection to the database
    public void createConnection() {
        System.out.println("opening connection"); //TODO REMOVE AFTER TESTING
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            try {
                createDatabase();
                try {
                    Class.forName("org.sqlite.JDBC");
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Connected to database"); //TODO REMOVE AFTER TESTING
        } catch (SQLException e) {
            System.out.println("Could not connect to the database. SQL ERR: " + e);
        }
    }
    
    //creates the local database if the database is missing
    public void createDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists data;");
        stat.executeUpdate("create table data (title unique, type, description, content);");
        PreparedStatement prep = conn.prepareStatement("insert into data values (?, ?, ?, ?);");

        prep.setString(1, "Sample Text File 1");
        prep.setString(2, "Sample");
        prep.setString(3, "This is a file containing sample text");
        prep.setString(4, "OH hey look, guess you found it 'hyuck hyuck' woot de woot");
        prep.addBatch();
        
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();
    }
    
    //loads all data from the database and loads it into the Bank
    private void loadEntries() {
        Statement stmt = null;
        String query = "select Title, Type, Description, Content "
                + "from data ";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Found Entries:"); //TODO REMOVE AFTER TESTING
            while (rs.next()) {
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String content = rs.getString("Content");
                Entry e = new Entry(title, type, description, content);
                ((EntryTableModel) myEntries).add(e);
                System.out.println("  Title: " + title); //TODO REMOVE AFTER TESTING
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
    
    //Deletes an entry based on its Title
    public void deleteEntry(Entry e) {
        deleteEntry(e.myTitle);
    }
    
    //Deletes an entry in the bank with the passed Title
    public void deleteEntry(String s) {
        ((EntryTableModel) myEntries).remove(s);
        PreparedStatement stmt = null;
        String query = "delete from data where Title = ?";
          try { 
            stmt = conn.prepareStatement(query);
            stmt.setString(1, s);
            stmt.execute();
            System.out.println("Entry with title: '" + s + "' has been deleted from the Bank"); //TODO REMOVE AFTER TESTING
        } catch (SQLException e1) {
            System.out.println(e1);
            e1.printStackTrace();
        }
    }
    
    //Adds a new entry to the bank, returns false if the passed entry was invalid
    public boolean addEntry(Entry e) {
        if (((EntryTableModel) myEntries).contains(e)) {
            System.out.println("Entry: " + e.myTitle + " already in the Bank"); //TODO REMOVE AFTER TESTING
            return false;
        } else if (((EntryTableModel) myEntries).contains(e.myTitle)) {
            System.out.println("Another Entry already has Title: " + e.myTitle); //TODO REMOVE AFTER TESTING    
            return false;
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
                System.out.println("New Entry added with title: " + e.myTitle); //TODO REMOVE AFTER TESTING
            } catch (SQLException e1) {
                System.out.println(e1);
                e1.printStackTrace();
            } 
            return true;
        }
    }
    
    //returns the entry with the passed string as its title
    public Entry getEntry(String x) {
        return ((EntryTableModel) myEntries).get(x);
    }
    
    //modifies an old entry to match the 
    public boolean modifyEntry(Entry newEntry, Entry oldEntry) {
        if (((EntryTableModel) myEntries).contains(newEntry.myTitle)) {
            System.out.println("File not modified, Naming Conflict - Title: " + newEntry.myTitle + " is already in use"); //TODO REMOVE AFTER TESTING
            return false; 
        } else {
            ((EntryTableModel) myEntries).remove(oldEntry);
            ((EntryTableModel) myEntries).add(newEntry);
            String sql = "update data set title = ?,type = ?, description = ?, content = ? where title = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, newEntry.myTitle);
                preparedStatement.setString(2, newEntry.myType);
                preparedStatement.setString(3, newEntry.myDescription);
                preparedStatement.setString(4, newEntry.myContent);
                preparedStatement.setString(5, newEntry.myTitle);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                e.printStackTrace();
            }
            return true;
        }
    }
    
    //returns the table of the Title, Type, and Description for drawing in the gui
    public TableModel getTable() {
        return myEntries;
    }
    
    //returns a filtered table from the entries 
    public TableModel getTable(String[] filters) {
        TableModel x = new EntryTableModel();
        for (int i = 0; i < filters.length; i++) {
            Statement stmt = null;
            String query = "select Title, Type, Description, Content "
                    + "from data where Type = '" + filters[i] + "' ";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("Found Entries with filter '" + filters[i] + "':"); //TODO REMOVE AFTER TESTING
                while (rs.next()) {
                    String title = rs.getString("Title");
                    String type = rs.getString("Type");
                    String description = rs.getString("Description");
                    String content = rs.getString("Content");
                    Entry e = new Entry(title, type, description, content);
                    ((EntryTableModel) x).add(e);
                    System.out.println("  Title: " + title); //TODO REMOVE AFTER TESTING
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
        return x;
    }
    
    //returns a string array of types for easy filtering
    public String[] getTypes() {
        Statement stmt = null;
        String query = "select Type from data group by Type ";
        List<String> types = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Found Types:"); //TODO REMOVE AFTER TESTING
            while (rs.next()) {
                String type = rs.getString("Type");
                System.out.println("  Type: " + type); //TODO REMOVE AFTER TESTING
                types.add(type);
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
        return types.toArray(new String[types.size()]);
    }
        
//  //a main method for testing the bank
//  public static void main(String[] args) {
//      System.out.println("Start Bank Test:");
//      Bank b = new Bank();
//      Entry newEntry = new Entry ("New Entry", "Sample x", "a test for the bank", "Why are you reading this? GET BACK TO CODE!!!!!!!!1!!");
//      Entry newEntry2 = new Entry ("New Entry 2", "Sample y", "another test for the bank", "Seriously why are you still reading these? Like wasting time much??");
//      Entry newEntry3 = new Entry ("New Entry 3", "Sample z", "yet another test for the bank", "Im not even gonna dignify that you're still reading these...");
//      Entry newEntry2b = new Entry ("New Entry 2", "Sample", "test test testy test", "ohhhhh i bet you didnt expect that! duplicate Title son!");
//      b.addEntry(newEntry);
//      b.addEntry(newEntry2);
//      b.addEntry(newEntry);
//      b.addEntry(newEntry2b);
////        b.deleteEntry(newEntry);
////        b.deleteEntry(newEntry2);
////        b.addEntry(newEntry3);
////        b.deleteEntry(newEntry3);
//      b.addEntry(newEntry3);
//      String[] x = b.getTypes();
//      for (int i = 0; i < x.length; i++) 
//          System.out.println(x[i]);
//        TableModel garbage = b.getTable(x);
//        String y[] = new String [2];
//        y[0] = "Sample";
//        y[1] = "Sample x";
//        System.out.println("Smaller Sample Size:");
//        garbage = b.getTable(y);
//      System.out.println("End Bank Test:");
//  }
}