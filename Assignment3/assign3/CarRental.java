package assignment3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CarRental {
	   private Connection con;
	   private Statement st;
	   private String query;
	   private PreparedStatement pSt;
	   private long startTime;
	   private long time;
	   private ObjectMapper map = new ObjectMapper();
	
	public CarRental(Connection con) {
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
	
	
	
}
