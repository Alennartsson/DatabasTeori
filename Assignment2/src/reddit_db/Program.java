package reddit_db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Program{
    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/comments?useSSL=false";
        String user = "testuser";
        String password = "1234";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            
            System.out.println("Connected to database..");
            File file = new File("");
            
            DBReddit db = new DBReddit(con);

            //db.createTables();
            //db.importFile(file);
            //db.dropTable();
            
            
            DBRedditTables dbT = new DBRedditTables (con);
           
            dbT.createTables();
            dbT.importData(file);
            //dbT.dropTables(); 
            
        } catch (SQLException ex) {
        } finally {
        try {
            
            if (rs != null) {
                rs.close();
            }
                
            if (st != null) {
                st.close();
            }
                
            if (con != null) {
                con.close();
            }

            } catch (SQLException ex) {
                
            }
        }
    }
}
