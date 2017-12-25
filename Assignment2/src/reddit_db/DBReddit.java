package reddit_db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBReddit {
   private Connection con;
   private Statement st;
   private String query;
   private PreparedStatement pSt;
   private long startTime;
   private long time;
   private ObjectMapper map = new ObjectMapper();
   
   public DBReddit(Connection con) {
       this.con = con;
   }
   public void createTables() {
       try {
           st = con.createStatement();
           query = "CREATE TABLE IF NOT EXISTS COMMENT" +
                   " (id TEXT ," +
                   "parent_id TEXT," +
                   "link_id TEXT, " +
                   "name TEXT," +
                   "author TEXT," +
                   "body TEXT," +
                   "subreddit_id TEXT," +
                   "subreddit TEXT," +
                   "score INT," +
                   "create_utc INT)";
           st.executeUpdate(query);
           st.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   public void importFile(File file) {
       try {
           System.out.println("Importing data...");
           startTime = System.nanoTime();
           map.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
           pSt = con.prepareStatement
                   ("INSERT INTO COMMENT VALUES(?,?,?,?,?,?,?,?,?,?)");
           int lCount = 0;
           BufferedReader br = new BufferedReader(new FileReader(file));
           String line = br.readLine();
           while (line != null) {
               TableColumns obj = map.readValue(line, TableColumns.class);
               pSt.setString(1, obj.id);
               pSt.setString(2, obj.parent_id);
               pSt.setString(3, obj.link_id);
               pSt.setString(4, obj.name);
               pSt.setString(5, obj.author);
               pSt.setString(6, obj.body);
               pSt.setString(7, obj.subreddit_id);
               pSt.setString(8, obj.subreddit);
               pSt.setInt(9, obj.score);
               pSt.setInt(10, obj.created_utc);
               pSt.executeUpdate();
               lCount++;
               line = br.readLine();
           }
           br.close();
           con.commit();
           time = (System.nanoTime() - startTime) / 1000000000;
           System.out.println(lCount + " objects. Time: " + time);
       } catch(SQLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
   public void dropTable() {
       try {
           st = con.createStatement();
           query = "DROP TABLE IF EXISTS COMMENT";
           st.execute(query);
       }catch(SQLException e) {
           e.printStackTrace();
       }
   }
}
