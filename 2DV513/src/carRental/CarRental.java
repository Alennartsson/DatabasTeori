package carRental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class CarRental {
	   private Connection con;
	   private Statement st;
	   private ResultSet rs;
	   private String query;
	   private PreparedStatement pSt;
	   private List<List<String>> result = new ArrayList<>(); 
	   private ResultSetMetaData metadata;
	
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
	
	public void registerNewCar(Connection con, String licence_plate, String price_car, String car_free, String model, String seats, String brand, String rented, String production_year, String color, String mileage) throws SQLException {
		try {
			String query = " INSERT INTO comments.car VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, licence_plate);
			preparedStmt.setInt(2, Integer.parseInt(price_car));
			preparedStmt.setString(3, car_free);
			preparedStmt.setString(4, model);
			preparedStmt.setInt(5, Integer.parseInt(seats));
			preparedStmt.setString(6, brand);
			preparedStmt.setString(7, rented);
			preparedStmt.setInt(8, Integer.parseInt(production_year));
			preparedStmt.setString(9, color);
			preparedStmt.setInt(10, Integer.parseInt(mileage));

			preparedStmt.executeUpdate();
			con.close();	

		} catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
	
	
	// FIX
	public void updateDamageService(Connection con, String licence) throws SQLException {
		query = "SELECT* FROM comments.car WHERE licence_plate ='"+ licence+"'";
		pSt = con.prepareStatement(query);

		rs = pSt.executeQuery();
		
		if(rs.next()) {
			pSt = con.prepareStatement("UPDATE comments.car SET rented=? WHERE licence_plate ='"+licence+"'");
			pSt.setString(1, "no");
			pSt.executeUpdate();
		}
		pSt.close();
		
	}
	
	public void updateRented(Connection con, String licence_plate) throws SQLException {
		query = "SELECT* FROM comments.car WHERE licence_plate ='"+ licence_plate+"'";
		pSt = con.prepareStatement(query);

		rs = pSt.executeQuery();
		
		if(rs.next()) {
			pSt = con.prepareStatement("UPDATE comments.car SET rented=? WHERE licence_plate ='"+licence_plate+"'");
			pSt.setString(1, "no");
			pSt.executeUpdate();
		}
		pSt.close();
	}
	
	public void updateMileage(Connection con, String licence_plate, String mileage) throws SQLException {
		query = "SELECT* FROM comments.car WHERE licence_plate ='"+ licence_plate+"'";
		pSt = con.prepareStatement(query);

		rs = pSt.executeQuery();
	
		while(rs.next()) {
				pSt = con.prepareStatement("UPDATE comments.car SET mileage=? WHERE licence_plate ='"+licence_plate+"'");
				pSt.setString(1, mileage);
				pSt.executeUpdate();
		}
		pSt.close();
	}	
	
	public boolean isNewMilageHigher(Connection con, String licence_plate, String mileage) throws SQLException {
		query = "SELECT mileage FROM comments.car WHERE licence_plate ='"+ licence_plate+"'";
		pSt = con.prepareStatement(query);

		rs = pSt.executeQuery();
	
		if(rs.next()) {

			String mile = rs.getString("mileage");

			if(Integer.parseInt(mile) <= Integer.parseInt(mileage)) {
				return true;
			}				
		}
		pSt.close();
		return false;
	}
	
	public void updateCustomer(Connection con, String licence_plate, String name, String pNumber, String end_date) throws SQLException {
		query = "SELECT* FROM comments.customer WHERE customer_name ='"+name +"' AND customer_personal_number='"+ pNumber +"'";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();
		
		Random ran = new Random();
		int rand = 1 +ran.nextInt(100000);
		
		if(!rs.next()) {
			pSt= con.prepareStatement("INSERT INTO comments.customer VALUES(?,?,?,?,?,?,?,?)");
			pSt.setInt(1, rand);
			pSt.setString(2, licence_plate);
			pSt.setString(3, name);
			pSt.setInt(4,  Integer.parseInt(pNumber));
			pSt.setInt(5, 123);
			pSt.setString(6, "hej");
			pSt.setInt(7, 123);
			pSt.setInt(8,  Integer.parseInt(end_date));
			pSt.executeUpdate();
			
			pSt = con.prepareStatement("UPDATE comments.car SET rented=? WHERE licence_plate ='"+licence_plate+"'");
			pSt.setString(1, "yes");
			pSt.executeUpdate();

		}
		else {
			pSt= con.prepareStatement("UPDATE comments.customer SET licence_plate=?, rent_end_date=? WHERE customer_name ='"+ name+"' AND customer_personal_number='"+ pNumber +"'");
			
			pSt.setString(1, licence_plate);
			pSt.setInt(2,  Integer.parseInt(end_date));
			pSt.executeUpdate(); 	
			
			pSt = con.prepareStatement("UPDATE comments.car SET rented=? WHERE licence_plate ='"+licence_plate+"'");
			pSt.setString(1, "yes");
			pSt.executeUpdate();
			
		}
		rs.close();
		pSt.close();
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	public List showCustomerInfo(Connection con, String name, String pNumber) throws SQLException {
		query = "SELECT * FROM comments.customer WHERE customer_name='" +name+"' AND customer_personal_number='"+ pNumber +"'";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery(); 
		
		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();

		while (rs.next()) {
			  	List<String> row = new ArrayList<>(columns);
			    
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
	
	
	@SuppressWarnings("rawtypes")
	public List showCarByBrand(Connection con, String brand) throws SQLException {
		query = "SELECT * FROM comments.car WHERE brand='" +brand+"' ORDER BY brand";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery(); 
		
		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();

		while (rs.next()) {
			  	List<String> row = new ArrayList<>(columns);
			    
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
	
	@SuppressWarnings("rawtypes")
	public List showAllCars(Connection con) throws SQLException {
		query = "SELECT * FROM comments.car";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(columns);
		    int i = 1;
		    while (i <= columns) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row);
		}
		rs.close();
		pSt.close();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List showAllCarsLicencePlate(Connection con) throws SQLException {
		query = "SELECT licence_plate FROM comments.car ORDER BY colour";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(columns);
		    int i = 1;
		    while (i <= columns) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row);
			}	
		rs.close();
		pSt.close();
		return result;
	}
		
		
	@SuppressWarnings("rawtypes")
	public List removeList(List list) {
		 for(Iterator<ArrayList> itr = list.iterator(); itr.hasNext();){            
	            ArrayList lists = itr.next();            
	            if(lists != null){
	                itr.remove();
	            }
	        }
		 return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List carPrice(Connection con, String licence_plate) throws SQLException {
		query = "SELECT price_car FROM comments.car WHERE licence_plate ='" + licence_plate + "'" ;
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(columns);
		    int i = 1;
		    while (i <= columns) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row);
			}	
		rs.close();
		pSt.close();
		return result;
	}
	

	@SuppressWarnings("rawtypes")
	public List carRented(Connection con) throws SQLException {
		query = "SELECT * FROM comments.car WHERE rented ='no'";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(columns);
		    int i = 1;
		    while (i <= columns) {
		        row.add(rs.getString(i++));
		    }
		    result.add(row);
			}	
		rs.close();
		pSt.close();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List showLicencePlates(Connection con) throws SQLException {
		query = "SELECT licence_plate FROM comments.car";
		pSt= con.prepareStatement(query);
		rs = pSt.executeQuery();

		metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		while (rs.next()) {
		    List<String> row = new ArrayList<>(columns);
		    int i = 1;
		    while (i <= columns) {
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
		int columns = metadata.getColumnCount();

		while (rs.next()) {
			  	List<String> row = new ArrayList<>(columns);
			    
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
}
