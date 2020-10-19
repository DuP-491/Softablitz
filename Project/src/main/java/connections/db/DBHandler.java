package connections.db;

import java.sql.*;

import stream.Category;
import user.Status;
import user.Streamer;
import user.User;

public class DBHandler {
    private Connection connection;

    public DBHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String password = "password";
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/livestreamdb?characterEncoding=latin1&useConfigs=maxPerformance","this",password);
        }
        catch (Exception e) {
            System.out.println("Meme");
        }
    }

    public Streamer[] getStreams(Category cat) {
        return null;
    }

    public Streamer[] getNotifications() {
        return null;
    }

    public User getUserByName(String searchTerm) {
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
                return (new User(searchTerm,name,bio,dpLocation,status));
            }
        }
        catch(SQLException e) {
            System.out.println("Meme");
        }
        return null;
    }

    public User[] getSubList() {
        return null;
    }

    public User[] getFollowList(User self) {
        User[] followList = new User[0];
        try {
            String query = "Select * from Follows where follower=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, self.getUsername());
            ResultSet rs = ps.executeQuery();
            int size = getSize(rs);
            followList = new User[size];
            int index = 0;
            while(rs.next()) {
                String streamerUsername = rs.getString("streamer");
                User user = this.getUserByName(streamerUsername);
                followList[index++] = user;
            }
        }
        catch(SQLException e) {
            System.out.println("Meme");
        }
        return followList;
    }

    public void addStreamtoDB(Streamer self) {

    }

    public void removeStreamfromDB(Streamer self) {

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
