package test;

import java.sql.*;

public class DBDriver {

    public DBDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {

        }
    }
    public static void main(String args[]) {
        try {
            DBDriver d = new DBDriver();
            String password = "Bananasql1!";
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/helloworld?characterEncoding=latin1&useConfigs=maxPerformance","root",password);
            d.update(con);
            d.show(con);
            System.out.println("Done");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Connection con) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into Users (username, name, age) values(?,?,?)");
            pst.setString(1,"bruhther");
            pst.setString(2,"Diptarag");
            pst.setString(3,"19");
            pst.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();;
        }
    }

    public static void show(Connection con) {
        try {
            PreparedStatement pst = con.prepareStatement("select * from Users");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
