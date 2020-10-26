/*
 * Created by JFormDesigner on Mon Oct 26 17:50:43 IST 2020
 */

package user;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Diptarag Ray Chaudhuri
 */
public class UserProfile extends JFrame {
    private User profileOf;
    private Viewer watcher;

    public UserProfile(User user, Viewer self) {
        initComponents();
        profileOf = user;
        watcher = self;

        if(user.getUsername().equals(self.getUsername())) {
            button1.setVisible(false);
            button2.setVisible(false);

            label6.setVisible(true);
            label7.setVisible(true);
            label8.setVisible(true);
            textField1.setVisible(true);
            textField2.setVisible(true);
            button3.setVisible(true);
        }
        else {
            button1.setVisible(true);
            button2.setVisible(true);

            label6.setVisible(false);
            label7.setVisible(false);
            label8.setVisible(false);
            textField1.setVisible(false);
            textField2.setVisible(false);
            button3.setVisible(false);
        }
        label2.setText(user.getUsername());
        label3.setText(user.getStatus().toString());
        label4.setText(user.getBio());
        label5.setText(user.getName());
    }

    private void saveChangesPressed(ActionEvent e) {
        // TODO add your code here
        watcher.updateUserInfo(textField1.getText(), textField2.getText()); //newName, newBio pair
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
        label2 = new JLabel();
        label1 = new JLabel();
        label5 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        label6 = new JLabel();
        label7 = new JLabel();
        textField1 = new JTextField();
        label8 = new JLabel();
        textField2 = new JTextField();
        button3 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
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
            "[]"));

        //---- label2 ----
        label2.setText("Username");
        contentPane.add(label2, "cell 4 1");

        //---- label1 ----
        label1.setText("Profile pic");
        contentPane.add(label1, "cell 2 2");

        //---- label5 ----
        label5.setText("Name");
        contentPane.add(label5, "cell 4 2");

        //---- label3 ----
        label3.setText("Status");
        contentPane.add(label3, "cell 4 3");

        //---- label4 ----
        label4.setText("BIO");
        contentPane.add(label4, "cell 2 4");

        //---- button1 ----
        button1.setText("Follow");
        contentPane.add(button1, "cell 2 7");

        //---- button2 ----
        button2.setText("Subscribe");
        contentPane.add(button2, "cell 4 7 3 1");

        //---- label6 ----
        label6.setText("EDIT");
        contentPane.add(label6, "cell 2 9");

        //---- label7 ----
        label7.setText("Name: (Type same if no changes)");
        contentPane.add(label7, "cell 2 11");
        contentPane.add(textField1, "cell 4 11 6 1");

        //---- label8 ----
        label8.setText("Bio: (Type same if no changes)");
        contentPane.add(label8, "cell 2 12");
        contentPane.add(textField2, "cell 4 12 6 1");

        //---- button3 ----
        button3.setText("Save Changes");
        button3.addActionListener(e -> saveChangesPressed(e));
        contentPane.add(button3, "cell 2 13");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
    private JLabel label2;
    private JLabel label1;
    private JLabel label5;
    private JLabel label3;
    private JLabel label4;
    private JButton button1;
    private JButton button2;
    private JLabel label6;
    private JLabel label7;
    private JTextField textField1;
    private JLabel label8;
    private JTextField textField2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
