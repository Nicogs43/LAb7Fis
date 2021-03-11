package connectionDB;
import org.h2.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionFactory {


	/**
	 * Connect to Database
	 */

	    public static final String URL = "jdbc:h2:~/Lab7fis";
	    public static final String USER = "Unige";
	    public static final String PASS = "latazza";

	    /**
	     * Get a connection to database
	     * @return Connection object
	     */
	    public static Connection getConnection()
	    {
	      try {
	          DriverManager.registerDriver(new Driver());
	          return DriverManager.getConnection(URL, USER, PASS);
	      } catch (SQLException ex) {
	          throw new RuntimeException("Error connecting to the database", ex);
	      }
	    }
	    /**
	     * Test Connection
	     */
	    public static void main(String[] args) {
	        @SuppressWarnings("unused")
			Connection connection = ConnectionFactory.getConnection();
	    }


}
