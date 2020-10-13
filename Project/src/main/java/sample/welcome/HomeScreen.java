package sample.welcome;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class HomeScreen
{
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static String username;
    private static ResultSet resultSet = null;

    HomeScreen(String username)
    {
        this.username=username;
    }
    static void displayInfo()throws SQLException
    {
        System.out.println("Name: "+resultSet.getString("name"));
    }
    static void upadateProfile()throws ClassNotFoundException,SQLException
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your name");
        String name=sc.next();

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/USER";
        connect=DriverManager.getConnection(url,"this","password");

        String query="UPDATE UserInfo set name=? where username=?";

        preparedStatement=connect.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,username);
        preparedStatement.executeUpdate();
    }

    public static void main(String[] args) throws ClassNotFoundException,SQLException
    {
        upadateProfile();
        displayInfo();
    }
}
