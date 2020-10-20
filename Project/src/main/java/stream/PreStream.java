/*
 * Created by JFormDesigner on Tue Oct 20 23:59:13 IST 2020
 */

package stream;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;
import user.Streamer;

/**
 * @author Diptarag Ray Chaudhuri
 */
public class PreStream extends JFrame {
    private Streamer streamer;

    public PreStream(Streamer streamer) {

        initComponents();
        //Edit components here
        for(Category i : Category.values()) {
            comboBox1.addItem(i);
        }
        this.streamer = streamer;
    }

    private void startStreamingPressed(ActionEvent e) {
        // TODO add your code here
        streamer.startStreaming(textField1.getText(), (Category) comboBox1.getSelectedItem());
        this.dispose();
    }

    private void cancelPressed(ActionEvent e) {
        // TODO add your code here
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        comboBox1 = new JComboBox();
        button1 = new JButton();
        button2 = new JButton();

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
            "[]"));

        //---- label1 ----
        label1.setText("Title");
        contentPane.add(label1, "cell 3 3");
        contentPane.add(textField1, "cell 4 3 4 1");

        //---- label2 ----
        label2.setText("Category");
        contentPane.add(label2, "cell 3 4");
        contentPane.add(comboBox1, "cell 4 4");

        //---- button1 ----
        button1.setText("Start Streaming");
        button1.addActionListener(e -> startStreamingPressed(e));
        contentPane.add(button1, "cell 3 9");

        //---- button2 ----
        button2.setText("Cancel");
        button2.addActionListener(e -> cancelPressed(e));
        contentPane.add(button2, "cell 7 9");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JComboBox comboBox1;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
