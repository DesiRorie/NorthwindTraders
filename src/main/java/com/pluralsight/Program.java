import java.sql.*;
import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = "8329135D";
        String query = "SELECT * FROM Products";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
