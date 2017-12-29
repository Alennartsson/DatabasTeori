package carRental;

import carRental.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CarRental {
	   private Connection con;
	   private Statement st;
	   private ResultSet rs;
	   private String query;
	   private PreparedStatement pSt;
	   private List<List<String>> result = new ArrayList<>(); 
	   private ResultSetMetaData metadata;
	//   private Car car;

	
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
	
	//Inte fixat
	@SuppressWarnings("rawtypes")
	public List licencePlateCheck(Connection con) throws SQLException {
		query = "SELECT * FROM comments.car";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int numcols = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(numcols);
		    int i = 1;
		    while (i <= numcols) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row); // add it to the result
			}	
		rs.close();
		pSt.close();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List showCustomerInfo(Connection con, String name) throws SQLException {
		query = "SELECT * FROM comments.customer WHERE customer_name='" +name+"'";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery(); 
		
		metadata = rs.getMetaData();
		int numcols = metadata.getColumnCount();

		while (rs.next()) {
			  	List<String> row = new ArrayList<>(numcols);
			    
			    row.add(rs.getString(1));
			    row.add(rs.getString(2));
			    row.add(rs.getString(3));
			    row.add(rs.getString(4));
			    row.add(rs.getString(5));
			    row.add(rs.getString(6));
			    row.add(rs.getString(7));
			    row.add(rs.getString(8));
			    result.add(row);
			}	
		rs.close();
		pSt.close();
		return result;
	}
	
	
	
	public void checkAndUpdateMileage() {
		
	}
	
	
	//Funkar inte än, 
	public void updateCustomer(Connection con, String licence_plate, String name, String pNumber, String end_date) throws SQLException {
		query = "SELECT* FROM comments.customer";
		st = con.createStatement();
		
		rs =st.executeQuery(query);
		
		if(!rs.next()) {
			pSt= con.prepareStatement("  INTO comments.customer VALUES(?,?,?,?,?,?,?,?,?)");
			
			pSt.setString(1, "123415");
			pSt.setString(2, licence_plate);
			pSt.setString(3,  name);
			pSt.setString(4, pNumber);
			pSt.setString(5, "hej");
			pSt.setString(6, "hej");
			pSt.setString(7, "hej");
			pSt.setString(8, end_date);
			
			pSt.executeUpdate();
		System.out.println("Då");
			
		}
		else {
			pSt= con.prepareStatement("UPDATE comments.customer SET licence_plate=?, customer_name =?, customer_personal_number=?, rent_end_date=? WHERE customer_name ='" +name+"'");
			
			pSt.setString(1, licence_plate);
			pSt.setString(2, name);
			pSt.setString(3,  pNumber);
			pSt.setString(8, end_date);
			pSt.executeUpdate(); 
		System.out.println("BÄÄÄ");
			
		}
		rs.close();
		pSt.close();
	}
	
	public void updateCarRented() {
		
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public List showAllCars(Connection con) throws SQLException {
		query = "SELECT * FROM comments.car";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int numcols = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(numcols);
		    int i = 1;
		    while (i <= numcols) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row); // add it to the result
			}
		rs.close();
		pSt.close();
		return result;
	}
	

	
	// FUnkar inte än
	@SuppressWarnings("rawtypes")
	public List carRented(Connection con) throws SQLException {
		query = "SELECT * FROM comments.car WHERE rented ='no'";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int numcols = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(numcols);
		    int i = 1;
		    while (i <= numcols) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row);
			}	
		rs.close();
		pSt.close();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List selectCarByLicence(Connection con,String licence) throws SQLException {
		query = "SELECT * FROM comments.car WHERE licence_plate='" +licence+"'";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery(); 
		
		metadata = rs.getMetaData();
		int numcols = metadata.getColumnCount();

		while (rs.next()) {
			  	List<String> row = new ArrayList<>(numcols);
			    
			    row.add(rs.getString(1));
			    row.add(rs.getString(2));
			    row.add(rs.getString(3));
			    row.add(rs.getString(4));
			    row.add(rs.getString(5));
			    row.add(rs.getString(6));
			    row.add(rs.getString(7));
			    row.add(rs.getString(8));
			    row.add(rs.getString(9));
			    result.add(row);
			}	
		rs.close();
		pSt.close();
		return result;
	}

	@SuppressWarnings("rawtypes") //Ligger på rad 302 i console. Inte kollat så mycket om den funkar helt 100.
	public void registerNewCar(Connection con, String licence_plate, String price_car, String car_free, String model, String seats, String brand, String rented, String production_year, String color, String mileage) throws SQLException {
		try {
			Statement st = con.createStatement();


			String query = " INSERT INTO car (licence_plate, seats, brand, production_year,mileage,price_car,car_free,model,rented, colour) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, licence_plate);
			preparedStmt.setString(2, seats);
			preparedStmt.setString(3, brand);
			preparedStmt.setString(4, production_year);
			preparedStmt.setString(5, mileage);
			preparedStmt.setString(6, price_car);
			preparedStmt.setString(7, car_free);
			preparedStmt.setString(8, model);
			preparedStmt.setString(9, rented);
			preparedStmt.setString(10, color);
			// execute the preparedstatement
			preparedStmt.execute();
			con.close();

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
}
