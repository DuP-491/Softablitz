package test.welcome;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class register {
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void main(String args[])throws ClassNotFoundException,SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/USER";
        connect=DriverManager.getConnection(url,"this","password");

        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your username");
        String username=sc.next();
        System.out.println("Enter your password");
        String password=sc.next();

        try{
            String query="INSERT into UserInfo(username,password) VALUES(?,?)";

            preparedStatement=connect.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();

            System.out.println("Registered Succesfully");
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 1062)//1062 error code of duplicate keys
                System.out.println("UserName Already Exists");
        }

    }
}