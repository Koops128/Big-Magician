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

/**
 * This Class is responsible for connecting to the Database and providing the 
 * Editor the entries that it needs. Data is stored in the Database and when the program
 * runs, the data entries that are stored there are loaded into a TableModel for storage 
 * and to populate the GUI with a table. Entries that are added, removed, or otherwise 
 * modified are done so both in the TableModel and the Database.
 * 
 * @author Matthew Cles
 * @version 2.3
 */
public class Bank {
    
    /**The entries the are stored in the bank.*/
    private TableModel myEntries;
    /**The connection to the database.*/
    private Connection conn;
    
    /**
     * The Constructor
     */
    public Bank() {
        myEntries = new EntryTableModel();
        conn = null;
        createConnection();
        loadEntries();
    }

    /**
     * Creates a connection to the database.
     */
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
    
    /**
     * creates the local database if the database is missing.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Loads all data from the database and loads it into the Bank.
     */
    private void loadEntries() {
        Statement stmt = null;
        String query = "select Title, Type, Description, Content "
                + "from data order by Title";
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
    
    /**
     * Deletes an entry based on its Title.
     * @param e The entry that is to be deleted
     */
    public void deleteEntry(Entry e) {
        deleteEntry(e.getTitle());
    }
    
    /**
     * Deletes an entry in the bank with the passed Title.
     * @param s The String that represents the Title of the Entry to be deleted 
     */
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
    
    /**
     * Adds a new entry to the bank.
     * @param e The new entry to be added
     * @return returns false if the passed entry was invalid
     */
    public boolean addEntry(Entry e) {
        if (((EntryTableModel) myEntries).contains(e)) {
            System.out.println("Entry: " + e.getTitle() + " already in the Bank"); //TODO REMOVE AFTER TESTING
            return false;
        } else if (((EntryTableModel) myEntries).contains(e.getTitle())) {
            System.out.println("Another Entry already has Title: " + e.getTitle()); //TODO REMOVE AFTER TESTING    
            return false;
        } else {
            ((EntryTableModel) myEntries).add(e);
            String sql = "insert into data values " + "(?, ?, ?, ?); ";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, e.getTitle());
                preparedStatement.setString(2, e.getType());
                preparedStatement.setString(3, e.getDescription());
                preparedStatement.setString(4, e.getContent());
                preparedStatement.executeUpdate();
                System.out.println("New Entry added with title: " + e.getTitle()); //TODO REMOVE AFTER TESTING
            } catch (SQLException e1) {
                System.out.println(e1);
                e1.printStackTrace();
            } 
            return true;
        }
    }
    
    /**
     * returns the entry with the passed string as its title.
     * @param x The String Title of the Entry to grab
     * @return The Entry with the given Title
     */
    public Entry getEntry(String x) {
        return ((EntryTableModel) myEntries).get(x);
    }
    
    /**
     * Modifies an old entry to match the new Entry.
     * @param newEntry The new Entry 
     * @param oldEntry The old Entry that will be overwritten
     * @return returns false if the  new entry Title is invalid
     */
    public boolean modifyEntry(Entry newEntry, Entry oldEntry) {
        if (!newEntry.getTitle().equals(oldEntry.getTitle()) && ((EntryTableModel) myEntries).contains(newEntry.getTitle())) {
            System.out.println("File not modified, Naming Conflict - Title: " + newEntry.getTitle() + " is already in use"); //TODO REMOVE AFTER TESTING
            return false; 
        } else {
            ((EntryTableModel) myEntries).remove(oldEntry);
            ((EntryTableModel) myEntries).add(newEntry);
            String sql = "update data set title = ?,type = ?, description = ?, content = ? where title = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, newEntry.getTitle());
                preparedStatement.setString(2, newEntry.getType());
                preparedStatement.setString(3, newEntry.getDescription());
                preparedStatement.setString(4, newEntry.getContent());
                preparedStatement.setString(5, oldEntry.getTitle());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                e.printStackTrace();
            }
            System.out.println("File modified, Named: " + newEntry.getTitle() + " is already in use"); //TODO REMOVE AFTER TESTING
            return true;
        }
    }
    
    /**
     * Returns the table of the Title, Type, and Description for drawing in the GUI.
     * @return myEnties the TableModel for the GUI
     */
    public TableModel getTable() {
        return myEntries;
    }
    
    /**
     * Returns a filtered table from the entries.
     * @param Filters a String array of the filters
     * @return A filtered TableModel
     */
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
    
    /**
     * Returns a String array of Types for easy filtering.
     * @return a String array of Types
     */
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
//      Entry newEntry = new Entry ("New Entry 5a", "Sample x", "a test for the bank", "Why are you reading this? GET BACK TO CODE!!!!!!!!1!!");
//      Entry newEntry2 = new Entry ("New Entry 6a", "Sample y", "another test for the bank", "Seriously why are you still reading these? Like wasting time much??");
//      Entry newEntry3 = new Entry ("New Entry 7a", "Sample z", "yet another test for the bank", "Im not even gonna dignify that you're still reading these...");
//      Entry newEntry2b = new Entry ("New Entry 8a", "Sample", "test test testy test", "ohhhhh i bet you didnt expect that! duplicate Title son!");
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