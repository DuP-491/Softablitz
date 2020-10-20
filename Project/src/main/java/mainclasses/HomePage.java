/*
 * Created by JFormDesigner on Tue Oct 20 17:50:01 IST 2020
 */

package mainclasses;

import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Diptarag Ray Chaudhuri
 */
public class HomePage extends JPanel {
    public HomePage() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        label2 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();
        label3 = new JLabel();
        comboBox1 = new JComboBox();
        button2 = new JButton();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
        EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER, javax. swing
        . border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ),
        java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( )
        { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () ))
        throw new RuntimeException( ); }} );
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
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Notifications");
        add(label1, "cell 1 1");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }
        add(scrollPane1, "cell 1 2,width 200:200:200,height 50:50:50");

        //---- label2 ----
        label2.setText("Search User by Handle");
        add(label2, "cell 1 4 5 1");
        add(textField1, "cell 1 4 5 1");

        //---- button1 ----
        button1.setText("Search");
        add(button1, "cell 6 4");

        //---- label3 ----
        label3.setText("Browse Category");
        add(label3, "cell 1 6");
        add(comboBox1, "cell 2 6 4 1");

        //---- button2 ----
        button2.setText("Browse");
        add(button2, "cell 6 6");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JLabel label2;
    private JTextField textField1;
    private JButton button1;
    private JLabel label3;
    private JComboBox comboBox1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
