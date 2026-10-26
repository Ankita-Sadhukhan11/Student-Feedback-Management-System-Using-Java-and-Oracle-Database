
package feedback;

import java.sql.*;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        String url =
            "jdbc:oracle:thin:@(description="
          + "(address=(protocol=tcps)(port=2484)"
          + "(host=db.freesql.com))"
          + "(connect_data=(service_name=26ai_un3c1)))";

        String user = "ANKITASADHUKHAN41_SCHEMA_IWLZC";
        String pass = System.getenv("oracle_db_password");

        if (pass == null || pass.isEmpty()) {
            throw new Exception("Database password missing");
        }

        Class.forName("oracle.jdbc.OracleDriver");

        Connection con = DriverManager.getConnection(url, user, pass);

        return con;
    }
}
