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
			query = "CREATE TABLE IF NOT EXISTS CAR" +
			    	 "(licence_plate varchar(10) PRIMARY KEY," +
			         "price_car int(50) NOT NULL,"+
			         "car_free varchar(10) NOT NULL,"+
			         "model varchar(25) NOT NULL , "+
			         "seats int(15) NOT NULL, "+
			         "brand varchar(25) NOT NULL ,"+
			         "rented varchar(10) NOT NULL,"+
			         "production_year int(10) NOT NULL,"+
			         "colour varchar(125) NOT NULL," +
			         "mileage int(50) NOT NULL)";
			st.executeUpdate(query);
			         
			query = "CREATE TABLE IF NOT EXISTS SERVICE" +
	          		"(service_stamp_number INT(10) PRIMARY KEY, "+
				"licence_plate varchar(10) NOT NULL, " +
	          		"car_service varchar(10) NOT NULL, " +
				"last_service int(15) NOT NULL, "+
				"problem varchar(100) NOT NULL, " +
				 "FOREIGN KEY(licence_plate) REFERENCES " + "CAR" + " (licence_plate) ON DELETE CASCADE)";		
	       		st.executeUpdate(query);
	        
	        	query = "CREATE TABLE IF NOT EXISTS CUSTOMER" +
		        	"(id_number_licence INT(25) PRIMARY KEY," +
		        	"licence_plate varchar(10) NOT NULL ,"+
		        	"customer_name varchar(40) NOT NULL ," +
		         	"customer_personal_number int(10) NOT NULL ," +
		            	"card_number int(50) NOT NULL , " +
		            	"insurance varchar(25) NOT NULL ," +
		            	"rent_start_date INT(10) NOT NULL ," +
		            	"rent_end_date INT(10) NOT NULL ," + 
		            	"FOREIGN KEY (licence_plate) REFERENCES " + "CAR" +  " (licence_plate) ON DELETE CASCADE)";
		   	st.executeUpdate(query);
	            	
	        	query = "CREATE TABLE IF NOT EXISTS PRICE" +
		        	"(receipt_number INT(20) PRIMARY KEY, " +
	        		"licence_plate VARCHAR(10) NOT NULL, " +
		        	"id_number_licence INT(25) NOT NULL, " +
	        		"price_car int(25) NOT NULL, "+
	            		"total_price int(50) NOT NULL," +
	            		"car_breaks_price int(25) NOT NULL, " +
	            		"mileage_price int(25) NOT NULL, " +
	            		"FOREIGN KEY(licence_plate) REFERENCES " + "CAR" + " (licence_plate) ON DELETE CASCADE, " +
	                	"FOREIGN KEY (id_number_licence) REFERENCES " + "CUSTOMER" +  " (id_number_licence) ON DELETE CASCADE)";
	        	st.executeUpdate(query);
		   	st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 	
	
	public void dropTables() {
		try {
			st = con.createStatement();
			query = "DROP TABLE IF EXISTS PRICE, SERVICE, CUSTOMER, CAR";
			st.execute(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
