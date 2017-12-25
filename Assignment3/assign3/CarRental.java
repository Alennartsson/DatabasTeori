package carRental;

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
			query = "CREATE TABLE IF NOT EXISTS SERVICE" +
	          		" (cars_service int(8) PRIMARY KEY," +
					"last_service int(8) NOT NULL, "+
					"problem varchar(40) NOT NULL)";    
	        st.executeUpdate(query);
	            	
	        query = "CREATE TABLE IF NOT EXISTS PRICE" +
		            " (price_car int(10) PRIMARY KEY, "+
	            	"total_price int(100) NOT NULL," +
	            	"car_breaks_price int(10) NOT NULL, " +
	            	"mileage_price int(10) NOT NULL)";
	        st.executeUpdate(query);
	            
			query = "CREATE TABLE IF NOT EXISTS CAR" +
			    	 " (licence_plate varchar(15) PRIMARY KEY," +
			         "cars_service int(8)," +
			         "price_car int(10) NOT NULL,"+
			         "cars_free varchar(5) NOT NULL,"+
			         "model varchar(20) NOT NULL , "+
			         "seats int(10) NOT NULL, "+
			         "brand varchar(25) NOT NULL ,"+
			         "rented varchar(5) NOT NULL,"+
			         "production_year int(4) NOT NULL,"+
			         "colour varchar(15) NOT NULL," +
			         "mileage int(10) NOT NULL," +
			         "FOREIGN KEY (price_car) REFERENCES " + "PRICE" + "(price_car) ON DELETE CASCADE, "+
			         "FOREIGN KEY (cars_service) REFERENCES " + "SERVICE" + "(cars_service) ON DELETE CASCADE)";
			st.executeUpdate(query);
			            
		    query = "CREATE TABLE IF NOT EXISTS CUSTOMER" +
		        	"(customer_name varchar(40) PRIMARY KEY ," +
		            "customer_personal_number int(10) NOT NULL ," +
		            "card_number int(20) NOT NULL , " +
		            "insurance varchar(20) NOT NULL ," +
		            "id_number_licence varchar(20) NOT NULL ," +
		            "rent_start_date varchar(255) NOT NULL ," +
		            "rent_end_date varchar(15) NOT NULL ," +
		            "licence_plate varchar(15) NOT NULL ,"+
		            "total_price INT(100) NOT NULL," +
		            "FOREIGN KEY (licence_plate) REFERENCES " + "CAR" +  " (licence_plate) ON DELETE CASCADE)," +
		           // "FOREIGN KEY (total_price) REFERENCES " + "PRICE" + " (total_price) ON DELETE CASCADE)";  
		   st.executeUpdate(query);
		   st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 	
}
