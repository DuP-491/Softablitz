package connections.db;

import java.sql.*;

import stream.Category;
import user.Streamer;
import user.User;

public class UserToDB {
    private Connection connection;

    public UserToDB() {
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

    public User[] getUserBySearchingHandle(String searchTerm) {
        return null;
    }

    public User[] getSubList() {
        return null;
    }

    public User[] getFollowList() {
        return null;
    }
}
