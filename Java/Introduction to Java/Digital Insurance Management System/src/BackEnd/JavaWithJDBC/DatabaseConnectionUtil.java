
package BackEnd.JavaWithJDBC;

import java.sql.*;

public class DatabaseConnectionUtil {
    
    //Atrributes that contain the details for the database
    private static final String URL = "jdbc:mysql://localhost:3306/digital_insurance_managment_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "19781210";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    //This method is used to establish a connection to the database
    public static Connection GetConnection() throws SQLException {
        
        try {
            
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            return con;
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("Connection has not been initiated to the database" + e.getMessage());
            throw new SQLException("There was an error loading the JDBC driver ", e);
            
        }
        
    }
    
    //This method is used to terminate the connection if it exists to the database, close the prepared
    public static void CloseConnection(Connection con, PreparedStatement prepStmt, ResultSet result) throws SQLException {
        
        try {
            
            if (result != null) {
                result.close();
            }
            
            if (prepStmt != null) {
                prepStmt.close();
            }
            
            if (con != null) {
                con.close();
            }
            
        } catch (SQLException e) {
            
            System.out.println("There was an error closing the connection " + e.getMessage());
            throw new SQLException("The database can't be closed ", e);
            
        }
        
    }
    
}
