/*
 * Created by JFormDesigner on Sun Oct 04 20:47:46 IST 2020
 */

package test;

import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;
import welcome.HomeScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author unknown
 */
public class Login {
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    public Login() throws ClassNotFoundException,SQLException{
        initComponents();
    }

    private void button2ActionPerformed(ActionEvent e) {


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/USER";
            connect=DriverManager.getConnection(url,"this","password");

            String username = textField.getText();
            String password = passwordField.getText();
            String query="SELECT * from UserInfo where username=?";
            preparedStatement=connect.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                if(password.equals(resultSet.getString("password")))
                {
                    JOptionPane.showMessageDialog(null,"Login Successful");
                }
                else
                    JOptionPane.showMessageDialog(null,"Password Incorrect");
            }
            else {
                JOptionPane.showMessageDialog(null,"User Does Not Exists");
            }
        }
        catch (SQLException err) {
            if (err.getErrorCode() == 1062)//1062 error code of duplicate keys
                System.out.println("UserName Already Exists");
        }
        catch (ClassNotFoundException err)
        {
            err.printStackTrace();
        }
        // TODO add your code here
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        jf2 = new JPanel();
        label1 = new JLabel();
        textField = new JTextField();
        label2 = new JLabel();
        passwordField = new JPasswordField();
        button2 = new JButton();
        button1 = new JButton();

        //======== jf2 ========
        {
            jf2.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder (
            0, 0 ,0 , 0) ,  "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder
            . BOTTOM, new java. awt .Font ( "Dialo\u0067", java .awt . Font. BOLD ,12 ) ,java . awt. Color .
            red ) ,jf2. getBorder () ) ); jf2. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java .
            beans. PropertyChangeEvent e) { if( "borde\u0072" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            jf2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- label1 ----
            label1.setText("Username");
            jf2.add(label1, "cell 1 4 3 1");
            jf2.add(textField, "cell 4 4 8 1");

            //---- label2 ----
            label2.setText("Password");
            jf2.add(label2, "cell 1 5 3 1");
            jf2.add(passwordField, "cell 4 5 8 1");

            //---- button2 ----
            button2.setText("login");
            button2.addActionListener(e -> button2ActionPerformed(e));
            jf2.add(button2, "cell 4 9 3 1");

            //---- button1 ----
            button1.setText("register");
            jf2.add(button1, "cell 11 9");
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel jf2;
    private JLabel label1;
    private JTextField textField;
    private JLabel label2;
    private JPasswordField passwordField;
    private JButton button2;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public static void main(String[] args) throws ClassNotFoundException,SQLException{
        JFrame frame=new JFrame("Login");
        frame.setContentPane(new Login().jf2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
