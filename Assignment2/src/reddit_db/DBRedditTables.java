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
	private String USER_TABLE = "USER";
	private String REDDIT_TABLE = "REDDIT";
	private String COMMENT_TABLE = "COMMENTS";
	private Connection con;
	private Statement st;
	private String query;
	private PreparedStatement pSt;
	   
	public DBRedditTables(Connection con) {
		this.con = con;
	}

	public void createTables() {
	try {
		st = con.createStatement();
	            query = "CREATE TABLE IF NOT EXISTS " + USER_TABLE +
	                    " (author varchar(20) PRIMARY KEY)";

	            st.executeUpdate(query);
	            st.close();

	            st = con.createStatement();
	            query = "CREATE TABLE IF NOT EXISTS " + REDDIT_TABLE +
	                    " (subreddit_id varchar(10) PRIMARY KEY AUTO_INCREMENT," +
	                    "subreddit varchar(10) NOT NULL UNIQUE)";
	            st.executeUpdate(query);
	            st.close();
	            
	            st = con.createStatement();
	            query = "CREATE TABLE IF NOT EXISTS " + COMMENT_TABLE +
	                    " (id varchar(10) PRIMARY KEY AUTO_INCREMENT," +
	                    "parent_id varchar(10) NOT NULL ," +
	                    "link_id varchar(255) NOT NULL , " +
	                    "name varchar(255) NOT NULL ," +
	                    "author varchar(20) NOT NULL ," +
	                    "body varchar(255) NOT NULL ," +
	                    "subreddit_id varchar(10) NOT NULL ," +
	                    "subreddit varchar(10) NOT NULL ,"+
	                    "score INT(10) NOT NULL ," +
	                    "create_utc INT(10) NOT NULL , " +
	                    "FOREIGN KEY (author) REFERENCES " + USER_TABLE + "(author) ON DELETE CASCADE," +
	                    "FOREIGN KEY (subreddit_id) REFERENCES " + REDDIT_TABLE + "(subreddit_id) ON DELETE CASCADE," +
	                    "FOREIGN KEY (subreddit) REFERENCES " + REDDIT_TABLE + "(subreddit) ON DELETE CASCADE)";
	            st.executeUpdate(query);
	            st.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


	    public void importData(File file) {
	        Long startime = System.nanoTime();

	        ObjectMapper mapper = new ObjectMapper();
	        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	        try {
	            con.setAutoCommit(false);

	            PreparedStatement userStatement = con.prepareStatement
	                    ("INSERT OR IGNORE INTO " + USER_TABLE + " VALUES(?)");
	            PreparedStatement commentStatement = con.prepareStatement
	                    ("INSERT OR IGNORE INTO " + COMMENT_TABLE + " VALUES(?,?,?,?,?,?,?,?,?,?)");
	            PreparedStatement subredditStatement = con.prepareStatement
	                    ("INSERT OR IGNORE INTO" + REDDIT_TABLE + " VALUES(?,?)");


	            int lCount = 0;
	            BufferedReader br= new BufferedReader(new FileReader(file));
	            String line = br.readLine();
	            while (line != null) {
	                TableColumns obj = mapper.readValue(line, TableColumns.class);
	                userStatement.setString(1, obj.author);

	                commentStatement.setString(1, obj.id);
	                commentStatement.setString(2, obj.parent_id);
	                commentStatement.setString(3, obj.link_id);
	                commentStatement.setString(4, obj.name);
	                commentStatement.setString(5, obj.author);
	                commentStatement.setString(6, obj.body);
	                commentStatement.setString(7, obj.subreddit_id);
	                commentStatement.setString(8, obj.subreddit);
	                commentStatement.setInt(9, obj.score);
	                commentStatement.setInt(10, obj.created_utc);

	                subredditStatement.setString(1, obj.subreddit_id);
	                subredditStatement.setString(2, obj.subreddit);
					
	                userStatement.executeUpdate();
	                subredditStatement.executeUpdate();
	                commentStatement.executeUpdate();
	                lCount++;
	                line = br.readLine();
	            }

	            con.commit();
	            con.setAutoCommit(true);
	            System.out.println(lCount + " items inserted, time: " + (System.nanoTime() - startime) / 1000000 + "ms");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	   
	public void clearTables() {
		try {
			st = con.createStatement();
			query = "DROP TABLE IF EXISTS " + USER_TABLE + COMMENT_TABLE + REDDIT_TABLE ;
			st.execute(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
