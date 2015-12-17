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
 * @version 3.1
 */
public class Bank {
    
    /**The entries the are stored in the bank.*/
    private TableModel myEntries;
    /**The connection to the database.*/
    private Connection conn;
    /**A boolean value that will stop the program from trying to populate the database again if the population failed the first time.*/
    private boolean populateAttempted;
    
    /**
     * The Constructor
     */
    public Bank() {
        myEntries = new EntryTableModel();
        conn = null;
        populateAttempted = false;
        createConnection();
        loadEntries();
    }

    /**
     * Creates a connection to the database.
     * @author Matthew Cles
     */
    public void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:C:/Temp/360-Big-Magician-entries.db");
        } catch (SQLException e) {
            try {
                createDatabase();
                this.conn = DriverManager.getConnection("jdbc:sqlite:C:/Temp/360-Big-Magician-entries.db");
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * creates the local database if the database is missing.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @author Matthew Cles
     */
    public void createDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Temp/360-Big-Magician-entries.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists data;");
        stat.executeUpdate("create table data (title unique, type, description, content);");
        populateDatabase();
        conn.close();
    }
    
    /**
     * Loads all data from the database and loads it into the Bank.
     * @author Matthew Cles
     */
    private void loadEntries() {
        Statement stmt = null;
        String query = "select Title, Type, Description, Content "
                + "from data order by Title";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String content = rs.getString("Content");
                Entry e = new Entry(title, type, description, content);
                ((EntryTableModel) myEntries).add(e);
            }
        } catch (SQLException e) {
            if(!populateAttempted) {
                try {
                    populateDatabase();
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
                loadEntries();
            } else {
                System.out.println(e);
                e.printStackTrace();
            }
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
     * @author Matthew Cles
     */
    public void deleteEntry(Entry e) {
        deleteEntry(e.getTitle());
    }
    
    /**
     * Deletes an entry in the bank with the passed Title.
     * @param s The String that represents the Title of the Entry to be deleted 
     * @author Matthew Cles
     */
    public void deleteEntry(String s) {
        ((EntryTableModel) myEntries).remove(s);
        PreparedStatement stmt = null;
        String query = "delete from data where Title = ?";
          try { 
            stmt = conn.prepareStatement(query);
            stmt.setString(1, s);
            stmt.execute();
        } catch (SQLException e1) {
            System.out.println(e1);
            e1.printStackTrace();
        }
    }
    
    /**
     * Adds a new entry to the bank.
     * @param e The new entry to be added
     * @return returns false if the passed entry was invalid
     * @author Matthew Cles
     */
    public boolean addEntry(Entry e) {
        if (((EntryTableModel) myEntries).contains(e)) {
            return false;
        } else if (((EntryTableModel) myEntries).contains(e.getTitle())) {
            return false;
        } else {
            ((EntryTableModel) myEntries).addInOrder(e);
            String sql = "insert into data values " + "(?, ?, ?, ?); ";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, e.getTitle());
                preparedStatement.setString(2, e.getType());
                preparedStatement.setString(3, e.getDescription());
                preparedStatement.setString(4, e.getContent());
                preparedStatement.executeUpdate();
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
     * @author Matthew Cles
     */
    public Entry getEntry(String x) {
        return ((EntryTableModel) myEntries).get(x);
    }
    
    /**
     * Modifies an old entry to match the new Entry.
     * @param newEntry The new Entry 
     * @param oldEntry The old Entry that will be overwritten
     * @return returns false if the  new entry Title is invalid
     * @author Matthew Cles
     */
    public boolean modifyEntry(Entry newEntry, Entry oldEntry) {
        if (!newEntry.getTitle().equals(oldEntry.getTitle()) && ((EntryTableModel) myEntries).contains(newEntry.getTitle())) {
            return false; 
        } else {
            ((EntryTableModel) myEntries).remove(oldEntry);
            ((EntryTableModel) myEntries).addInOrder(newEntry);
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
            return true;
        }
    }
    
    /**
     * Returns the table of the Title, Type, and Description for drawing in the GUI.
     * @return myEnties the TableModel for the GUI
     * @author Matthew Cles
     */
    public TableModel getTable() {
        return myEntries;
    }
    
    /**
     * Returns a filtered table from the entries.
     * @param Filters a String array of the filters
     * @return A filtered TableModel
     * @author Matthew Cles
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
                while (rs.next()) {
                    String title = rs.getString("Title");
                    String type = rs.getString("Type");
                    String description = rs.getString("Description");
                    String content = rs.getString("Content");
                    Entry e = new Entry(title, type, description, content);
                    ((EntryTableModel) x).addInOrder(e);
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
     * @author Matthew Cles
     */
    public String[] getTypes() {
        Statement stmt = null;
        String query = "select Type from data group by Type ";
        List<String> types = new ArrayList<String>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String type = rs.getString("Type");
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
    
    /**
     * creates the local database if the database is missing.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @author Matthew Cles
     */
    public void populateDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Temp/360-Big-Magician-entries.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists data;");
        stat.executeUpdate("create table data (title unique, type, description, content);");
        PreparedStatement prep = conn.prepareStatement("insert into data values (?, ?, ?, ?);");

        prep.setString(1, "Legal Document 1a");
        prep.setString(2, "Legal");
        prep.setString(3, "Contract for private customer");
        prep.setString(4, "This software is free for personal use, provided that: 1) the user makes no profit from using the said software. 2) the software is properly credited. 3) you say thank you very sincerly.");
        prep.addBatch();

        prep.setString(1, "Legal Document 1b");
        prep.setString(2, "Legal");
        prep.setString(3, "Contract for small business");
        prep.setString(4, "This software is availible for a small business to use, provided that: 1) the business agrees to pay 10 cents on the dollar to our software development team. 2) the software is properly credited. 3) we get a plaque on the wall of your headquarters.");
        prep.addBatch();

        prep.setString(1, "Legal Document 1c");
        prep.setString(2, "Legal");
        prep.setString(3, "Contract for large business");
        prep.setString(4, "This software is unavailible for large businesses because you guys are rich enough to make your own software");
        prep.addBatch();

        prep.setString(1, "Legal Document 2");
        prep.setString(2, "Legal");
        prep.setString(3, "Copyright");
        prep.setString(4, "All of the software developed by 'Big Magician' is maybe, I think, protected by some sort of copyright laws, but then again it is on github so maybe it is totally free to be used by anyone. Long story shory, we aren't really sure but we do think that it would be nice if you didn't steal our program... but if you do maybe you could throw us into the credits or something? Even a thank you email would be nice... we aren't really picky...");
        prep.addBatch();

        prep.setString(1, "Legal Document 3a");
        prep.setString(2, "Legal");
        prep.setString(3, "Lawyer busy-work 1");
        prep.setString(4, "Thank you for signing up to be a lawyer at our firm. Please answer the following when there is no 'law-y' work to be done: Question #1: Arthur and Cassie worked at an art gallery. They wanted to make some extra money by selling art from the gallery on eBay. They intended to share the proceeds with the owner of the gallery, but did not tell him about it because Arthur and Cassie did not think the owner would approve. The building in which the gallery was located was undergoing earthquake renovations, which resulted in the building being open through the roof to the building next door. Arthur approached Woody, an employee of the building contractor, and offered him $500 to take a wrapped package from the gallery and stash it in the building next door so that Arthur and Cassie could pick it up later. Arthur gave the wrapped package and $500 to Woody after the gallery had closed for the evening. Woody took the package up to the roof and, as he was crossing into the next building, he fell through the gap and tumbled three stories, landed on the package and was killed. The art in the wrapped package was destroyed. Cassie was waiting outside the building to get the package from Woody. When Woody did not arrive with the package, Cassie went back into the gallery, took several more paintings and took them home. Woody’s body was found by a construction worker. Arthur and Cassie were arrested. 1. What criminal offense or offenses, if any, can be reasonably argued were committed by Arthur? Discuss. 2. What criminal offense or offenses, if any, can be reasonably argued were committed by Cassie? Discuss. 3. What defenses, if any, can each of them raise? Discuss.");
        prep.addBatch();

        prep.setString(1, "Legal Document 3b");
        prep.setString(2, "Legal");
        prep.setString(3, "Lawyer busy-work 2");
        prep.setString(4, "Thank you for signing up to be a lawyer at our firm. Please answer the following when there is no 'law-y' work to be done: Question #2: Betsy owns a business in South City. Her friend, Walter, lived in Northville, some distance away. Over the years, Betsy had often suggested to Walter that he move to South City and work for her. A short time ago, Walter decided to follow Betsy’s suggestion. He called Betsy and asked if she was still interested in hiring him. Betsy replied, “Of course. Get down here as soon as possible and we can see where you would fit in.” Walter agreed and told her that he would give notice at his current job and would be in South City by the end of the month. Walter gave notice at work and shipped his furniture to South City at a cost of $5,000 and bought a one-way plane ticket for $250. When Walter called Betsy upon his arrival in South City, she told him that she had just lost a major customer and had to impose rigorous cost-cutting. She therefore could no longer employ him. Walter tried for two months to find another job in South City but nothing was available. Walter’s previous employer was willing to rehire him, so he moved back to Northville, paying another $5,000 for movers and $250 for airfare. 1. What claim or claims, if any, does Walter have against Betsy? Discuss. 2. What damages, if any, should Walter be awarded? Discuss.");
        prep.addBatch();

        prep.setString(1, "Sample Text File");
        prep.setString(2, "Sample");
        prep.setString(3, "This is a sample file");
        prep.setString(4, "This is a sample file because, lets be honest, we needed more filters");
        prep.addBatch();

        prep.setString(1, "HR-Lunch");
        prep.setString(2, "HR");
        prep.setString(3, "Why you should not steal a fellow employee's lunch");
        prep.setString(4, "Because it's bad... seriously? what else did you expect. A logical reason? there is no logic in HR!");
        prep.addBatch();

        prep.setString(1, "HR-offencive Fake HR Files");
        prep.setString(2, "HR");
        prep.setString(3, "Do not make fake HR Files.");
        prep.setString(4, "HR is a very serious part of workplace culture and the tampering with, or false creation of, HR Files will not be accepted. Please disregard the Lunch theft HR File. While we in HR do agree that stealing lunch is wrong, we find it very offencive that the author stated that we in HR have no logic. It will be removed as soon as IT can help us remove it. Thank you for disregarding.");
        prep.addBatch();

        prep.setString(1, "Thank you customer for your purchase");
        prep.setString(2, "Public Relations");
        prep.setString(3, "Use this when the boss tells you to write a thank you");
        prep.setString(4, "Dear (INSERT 'VALUED' CUSTOMERS NAME HERE), Thank you SO much for your generous perchase of our latest and greatest product: (INSERT THE 'INNOVATIVE' PRODUCT NAME HERE). I (INSERT YOUR NAME HERE), would like to take the time to personally thank you for supporting our company as we will continue to support you in your ventures. Speaking of ventures, don't forget to order our new product: (INSERT CURRENT PRODUCT THAT YOU ARE BEHIND YOUR QUOTA ON), I am sure that it will help you in the project that you mentioned to me. Thank you again (INSERT 'VALUED' CUSTOMERS NAME AGAIN HERE), Sincerly and hand typed, (INSERT YOUR NAME HERE)");
        prep.addBatch();

        prep.setString(1, "Order ready Email");
        prep.setString(2, "Public Relations");
        prep.setString(3, "Use this when you have to email the customer to pick up the order");
        prep.setString(4, "Dear (INSERT 'VALUED' CUSTOMERS NAME HERE), your order of our latest and greatest product: (INSERT THE 'INNOVATIVE' PRODUCT NAME HERE) is ready. Please pick it up at your earliest convenience. I mean, it's not like we have limited space in our warehouse or anything. But seriously, take your time, we won't start kicking it every time we walk by it or anything, we swear. Sincerly and hand typed, (INSERT YOUR NAME HERE)");
        prep.addBatch();

        prep.setString(1, "*NOTE");
        prep.setString(2, "NOTE");
        prep.setString(3, "Why did you populate my nice new database?!?");
        prep.setString(4, "For the purpose of this assignment, I decided that it would be better to give you a program that would auto-populate when run from a jar. I was able to do this by calling a populate method when the program is loading and the database is not found. This is so that you can test all of the nice little functions of our project without having to add a new file every time you want to delete one for testing. Also I tried to be funny with some of the entries so I hope you enjoy... Wait what? Comedy ISN'T part of the grading rubric? Dang...");
        prep.addBatch();

        prep.setString(1, "*NOTE 2");
        prep.setString(2, "NOTE");
        prep.setString(3, "Where did you hide the database?!?");
        prep.setString(4, "This database is created and stored in side of your hard-drives Temp folder, or more specifically, the path is: \"C:/Temp/360-Big-Magician-entries.db\" if you need to delete it for any reason (I don't think that the file should get too bulky... 50kb maybe? its only around 15kb when generated) it will not harm this program if it is ran again, but all modifications to the original database will of course be lost.");
        prep.addBatch();
        
        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();
    }
}