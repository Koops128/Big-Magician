

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.Statement;

	public class SqliteStarter {
	    public void main(String[] a) throws Exception {
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

	        ResultSet rs = stat.executeQuery("select * from data;");
	        while (rs.next()) {
	            System.out.println("title = " + rs.getString("title"));
	            System.out.println("type = " + rs.getString("type"));
	            System.out.println("description = " + rs.getString("description"));
	            System.out.println("content = " + rs.getString("content"));
	        }
	        rs.close();
	        conn.close();
	    }
	  }

