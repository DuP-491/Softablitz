package connections.db;

import connections.IDAssigner;
import user.Status;
import user.User;

import java.sql.*;

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

    public synchronized User loginUser(String username, String password) {
        try {
            //Check if correct credentials
            String query = "Select * from authentication where username=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,username);

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) return null; //Already exists
            if(!rs.getString("password").equals(password)) return null;

            User self = getUserByUsername(username);
            System.out.println("logindb done");
            return self;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized User getUserByUsername(String searchTerm) {
        try {
            String query = "Select * from Users where username=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, searchTerm);
            ResultSet rs = ps.executeQuery();
            int size = getSize(rs);
            if(size==0) { return null; }
            else {
                rs.next();
                String name = rs.getString("name");
                String bio = rs.getString("bio");
                String dpLocation = rs.getString("dplocation");
                int stat = rs.getInt("status");
                Status status = Status.OFFLINE;
                switch(stat) {
                    case 0: status = Status.OFFLINE; break;
                    case 1: status = Status.ONLINE; break;
                    case 2: status = Status.WATCHING; break;
                    case 3: status = Status.STREAMING; break;
                    default: break;
                }
                return (new User(searchTerm,name,bio,status));
            }
        }
        catch(SQLException e) {
            System.out.println("Meme");
        }
        return null;
    }

    static int getSize(ResultSet rs) {
        int size = 0;
        try {
            if (rs != null) {
                rs.last();
                size = rs.getRow();
                rs.beforeFirst();
            }
        } catch (Exception e) {
        }
        return size;
    }
}
