/*
 * Created by JFormDesigner on Tue Oct 20 12:27:32 IST 2020
 */

package mainclasses;

import java.awt.event.*;
import javax.swing.*;

import connections.ServerRequests;
import net.miginfocom.swing.*;
import user.User;

import java.io.*;
import java.net.Socket;

/**
 * @author unknown
 */
public class LoginRegister extends JPanel {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public LoginRegister() {
        try {
            socket = new Socket("localhost", 5434); //LoginServer
            initComponents();
            //Edit all jpanel components here
            textArea1.setEditable(false);

            System.out.print("Form started");
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

        }
        catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void loginPressed(ActionEvent e) {
        // TODO add your code here
        try {
            oos.writeInt(ServerRequests.LOGIN.geti());
            oos.writeUTF(textField1.getText()); //write username
            oos.flush();
            oos.writeUTF(String.valueOf(passwordField1.getPassword())); //write password
            oos.flush();

            int result = ois.readInt();
            if(result == -1) {
                textArea1.append("Login failed\n");
            }
            else {
                User self = (User) ois.readObject();
                textArea1.append("Login successful\n");
                Thread t = new Thread(new HomeMain(self));
                t.start();
            }
        }
        catch(Exception ee) {
            ee.printStackTrace();
        }
    }

    private void registerPressed(ActionEvent e) {
        // TODO add your code here
        try {
            oos.writeInt(ServerRequests.REGISTERUSER.geti());
            oos.flush();

            oos.writeUTF(textField3.getText()); // write username
            oos.flush();
            oos.writeUTF(textField4.getText()); // write name
            oos.flush();
            oos.writeUTF(String.valueOf(passwordField2.getPassword())); // write password
            oos.flush();

            int result = ois.readInt();

            if(result == -1) { textArea1.append("Register failed\n"); }
            else { textArea1.append("Register succesfull. You can log in now\n"); }
        }
        catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
        label1 = new JLabel();
        label2 = new JLabel();
        textField1 = new JTextField();
        label3 = new JLabel();
        passwordField1 = new JPasswordField();
        button1 = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        textField3 = new JTextField();
        label6 = new JLabel();
        textField4 = new JTextField();
        label7 = new JLabel();
        passwordField2 = new JPasswordField();
        button2 = new JButton();
        label8 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
        new javax.swing.border.EmptyBorder(0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion"
        ,javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM
        ,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12)
        ,java.awt.Color.red), getBorder())); addPropertyChangeListener(
        new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
        ){if("bord\u0065r".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});
        setLayout(new MigLayout(
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
            "[]" +
            "[]" +
            "[]" +
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
        label1.setText("Login");
        add(label1, "cell 8 2 1 2");

        //---- label2 ----
        label2.setText("Username");
        add(label2, "cell 4 5");
        add(textField1, "cell 6 5 5 1");

        //---- label3 ----
        label3.setText("Password");
        add(label3, "cell 4 6");
        add(passwordField1, "cell 6 6 5 1");

        //---- button1 ----
        button1.setText("Log In");
        button1.addActionListener(e -> loginPressed(e));
        add(button1, "cell 8 8");

        //---- label4 ----
        label4.setText("Register");
        add(label4, "cell 8 10");

        //---- label5 ----
        label5.setText("Username");
        add(label5, "cell 4 12");
        add(textField3, "cell 6 12 7 1");

        //---- label6 ----
        label6.setText("Name");
        add(label6, "cell 4 13");
        add(textField4, "cell 6 13 7 1");

        //---- label7 ----
        label7.setText("Password");
        add(label7, "cell 4 14");
        add(passwordField2, "cell 6 14 7 1");

        //---- button2 ----
        button2.setText("Register");
        button2.addActionListener(e -> registerPressed(e));
        add(button2, "cell 8 16");

        //---- label8 ----
        label8.setText("Feedback");
        add(label8, "cell 3 19");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }
        add(scrollPane1, "cell 5 18 6 4,width 200:200:200,height 50:50:50");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
    private JLabel label1;
    private JLabel label2;
    private JTextField textField1;
    private JLabel label3;
    private JPasswordField passwordField1;
    private JButton button1;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField3;
    private JLabel label6;
    private JTextField textField4;
    private JLabel label7;
    private JPasswordField passwordField2;
    private JButton button2;
    private JLabel label8;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
