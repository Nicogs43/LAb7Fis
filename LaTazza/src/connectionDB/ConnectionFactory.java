package connectionDB;
import org.h2.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionFactory {

    private static final String URL = "jdbc:h2:~/Lab7fis";
    private static final String USER = "Unige";
    private static final String PASS = "latazza";

	public static String getUrl() {
		return URL;
	}
	public static String getUser() {
		return USER;
	}
	public static String getPass() {
		return PASS;
	}
	/**
	 * Connect to Database
	 */


	    /**
	     * Get a connection to database
	     * @return Connection object
	     */
	    public static Connection getConnection()
	    {
	      try {
	          DriverManager.registerDriver(new Driver());
	          return DriverManager.getConnection(getUrl(), getUser(), getPass());
	      } catch (SQLException ex) {
	          throw new RuntimeException("Error connecting to the database", ex);
	      }
	    }
}
