package connections.db;

import java.sql.*;
import java.time.LocalDateTime;

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

    public synchronized String[] browseCategory(Category cat) {
        String[] ans = null;
        try {
            int category = cat.geti();
            String query = "Select * from streams where category=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();


            int size = getSize(rs);
            if(size==0) { return null; }

            ans = new String[size];
            int index = 0;
            while(rs.next()) {
                ans[index++] = rs.getString("streamerusername");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public synchronized String[] getNotifications(String selfUsername) {
        String[] ans = null;
        try {
            String query = "Select * from follows where followerusername=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, selfUsername);
            ResultSet rs = ps.executeQuery();


            int size = getSize(rs);
            if(size==0) { return null; }

            ans = new String[size];
            int index = 0;
            while(rs.next()) {
                //Check if followed streamer is live or not
                String subquery = "Select * from streams where streamerusername=?";
                PreparedStatement pss = connection.prepareStatement(subquery);
                ps.setString(1, rs.getString("streamerusername"));
                ResultSet rss = ps.executeQuery();

                if(rss.next()) { ans[index++] = rss.getString("streamerusername"); }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
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
                return (new User(searchTerm,name,bio,dpLocation,status));
            }
        }
        catch(SQLException e) {
            System.out.println("Meme");
        }
        return null;
    }

    public synchronized User[] getSubList() {
        return null;
    }

    public synchronized User[] getFollowList(User self) {
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
                User user = this.getUserByUsername(streamerUsername);
                followList[index++] = user;
            }
        }
        catch(SQLException e) {
            System.out.println("Meme");
        }
        return followList;
    }

    public synchronized void addStreamtoDB(String streamerUsername, String title, int category, Time starttime) {
        try {
            PreparedStatement pst = connection.prepareStatement("insert into streams (streamerusername, title, category, viewcount, starttime) values(?,?,?,?,?)");

            pst.setString(1,streamerUsername);
            pst.setString(2,title);
            pst.setInt(3,category);
            pst.setInt(4,0);
            pst.setTime(5,starttime);

            pst.executeUpdate();

        }
        catch(Exception e) {
            e.printStackTrace();;
        }
    }

    public synchronized void removeStreamfromDB(String streamerUsername) {
        try {
            String query = "delete from streams where streamerusername=?";
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1,streamerUsername);

            pst.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
