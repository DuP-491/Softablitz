package connections.db;

import connections.IDAssigner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDB {
    private Connection connection;

    public LoginDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String password = "Bananasql1!";
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/livestreamdb?characterEncoding=latin1&useConfigs=maxPerformance","root",password);
        }
        catch (Exception e) {
            System.out.println("Meme");
        }
    }

    public synchronized int registerUser(String username, String name, String password) {
        try {
            //First check if username exists in Users
            String query = "Select * from authentication where username=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,username);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) return -1; //Already exists

            String update = "insert into users (username,name) values (?,?)";
            ps = connection.prepareStatement(update);
            ps.setString(1,username);
            ps.setString(2,name);

            ps.executeUpdate();

            update = "insert into authentication (username,password) values (?,?)";
            ps = connection.prepareStatement(update);
            ps.setString(1,username);
            ps.setString(2,password);

            ps.executeUpdate();

            return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
