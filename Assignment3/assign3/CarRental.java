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
		            query =
		            st.executeUpdate(query);
		            st.close();

		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    } 
	
	
	
}
