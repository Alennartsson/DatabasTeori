package carRental;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) throws IOException, SQLException {
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

            CarRental rental = new CarRental(con);
            Console console = new Console(con);

            rental.createTables();
            //rental.dropTables();

            console.startPage();

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
