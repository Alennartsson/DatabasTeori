package reddit_db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBRedditTables {
	private Connection con;
	private Statement st;
	private String query;
	private long startTime;
	private long time;
	private ObjectMapper map = new ObjectMapper();

	public DBRedditTables(Connection con) {
		this.con = con;
	}

	public void createTables() {
	try {
		st = con.createStatement();
	            query = "CREATE TABLE IF NOT EXISTS " + "USER" +
	                    " (author varchar(20) PRIMARY KEY)";        
	            st.executeUpdate(query);

	            query = "CREATE TABLE IF NOT EXISTS " + "REDDIT" +
	                    " (subreddit_id varchar(15) PRIMARY KEY," +
	                    "subreddit varchar(15) NOT NULL UNIQUE)";
	            st.executeUpdate(query);
	            
	            query = "CREATE TABLE IF NOT EXISTS " + "COMMENTS" +
	                    " (id varchar(10) PRIMARY KEY ," +
	                    "parent_id varchar(10) NOT NULL ," +
	                    "link_id varchar(20) NOT NULL , " +
	                    "name varchar(20) NOT NULL ," +
	                    "author varchar(20) NOT NULL ," +
	                    "body varchar(255) NOT NULL ," +
	                    "subreddit_id varchar(15) NOT NULL ," +
	                    "subreddit varchar(15) NOT NULL ,"+
	                    "score INT(100) NOT NULL ," +
	                    "create_utc INT(10) NOT NULL , " +
	                    "FOREIGN KEY (author) REFERENCES " + "USER" + "(author) ON DELETE CASCADE," +
	                    "FOREIGN KEY (subreddit_id) REFERENCES " + "REDDIT" + "(subreddit_id) ON DELETE CASCADE," +
	                    "FOREIGN KEY (subreddit) REFERENCES " + "REDDIT" + "(subreddit) ON DELETE CASCADE)";
	            st.executeUpdate(query);
	            st.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } 

	    public void importData(File file) {
	    	System.out.println("Importing data...");
	        startTime = System.nanoTime();
	        map.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	        try {
	            PreparedStatement userSt = con.prepareStatement
	                    ("INSERT IGNORE INTO USER VALUES(?)");
	            PreparedStatement commentSt = con.prepareStatement
	                    ("INSERT IGNORE INTO COMMENTS VALUES(?,?,?,?,?,?,?,?,?,?)");
	            PreparedStatement subredditSt = con.prepareStatement
	                    ("INSERT IGNORE INTO REDDIT VALUES(?,?)");

	            int lCount = 0;
	            BufferedReader br= new BufferedReader(new FileReader(file));
	            String line = br.readLine();
	            while (line != null) {
	                TableColumns obj = map.readValue(line, TableColumns.class);
	                userSt.setString(1, obj.author);
	                
	                subredditSt.setString(1, obj.subreddit_id);
	                subredditSt.setString(2, obj.subreddit);

	                commentSt.setString(1, obj.id);
	                commentSt.setString(2, obj.parent_id);
	                commentSt.setString(3, obj.link_id);
	                commentSt.setString(4, obj.name);
	                commentSt.setString(5, obj.author);
	                commentSt.setString(6, obj.body);
	                commentSt.setString(7, obj.subreddit_id);
	                commentSt.setString(8, obj.subreddit);
	                commentSt.setInt(9, obj.score);
	                commentSt.setInt(10, obj.created_utc);

	                userSt.executeUpdate();
	                subredditSt.executeUpdate();
	                commentSt.executeUpdate();
	                lCount++;
	                line = br.readLine();
	            }

	            con.commit();
	            con.setAutoCommit(true);
	            time = (System.nanoTime() - startTime) / 1000000000;
	            System.out.println("Done " + lCount + " objects. Time: " + time);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	public void dropTables() {
		try {
			st = con.createStatement();
			query = "DROP TABLE IF EXISTS USER, REDDIT, COMMENTS";
			st.execute(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
