package reddit_db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBReddit {

   private String TABLE_NAME = "COMMENTS";
   private Connection con;
   private Statement st;
   private String query;
   private PreparedStatement pSt;
   private long startTime;
   private long time;
   
   public DBReddit(Connection con) {
       this.con = con;
   }

   public void createTables() {
       try {
           st = con.createStatement();
           query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+
                   " (id TEXT not," +
                   "parent_id TEXT," +
                   "link_id TEXT, " +
                   "name TEXT," +
                   "autor TEXT," +
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

   public void importData(File file) {
       try {
           System.out.println("Importing data to " + TABLE_NAME + "...");
           startTime = System.nanoTime();
           System.out.println("Importing data..");
           ObjectMapper mapper = new ObjectMapper();
           mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

           con.setAutoCommit(false);
           pSt = con.prepareStatement
                   ("INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?,?,?,?,?,?,?)");
           int lCount = 0;
           BufferedReader br = new BufferedReader(new FileReader(file));
           String line = br.readLine();
           while (line != null) {
               TableColumns obj = mapper.readValue(line, TableColumns.class);
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
               line = br.readLine();//Next line
           }
           br.close();
           con.commit();
           time = (System.nanoTime() - startTime) / 1000000000;
           System.out.println("Done inserting " + lCount + " objects. Time: " + time);
       } catch(SQLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   
   public void dropTable() {
       try {
           st = con.createStatement();
           query = "DROP TABLE IF EXISTS " + TABLE_NAME;
           st.execute(query);
       }catch(SQLException e) {
           e.printStackTrace();
       }
   }
}