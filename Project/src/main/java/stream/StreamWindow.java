/*
 * Created by JFormDesigner on Wed Oct 21 15:19:30 IST 2020
 */

package stream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Diptarag Ray Chaudhuri
 */
public class StreamWindow extends JFrame {
    public StreamWindow() {

        initComponents();
        //Edit jframe components here
        textArea2.setEditable(false);
        textArea3.setEditable(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
        label3 = new JLabel();
        panel1 = new JPanel();
        label5 = new JLabel();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();
        label6 = new JLabel();
        scrollPane3 = new JScrollPane();
        textArea3 = new JTextArea();
        label4 = new JLabel();

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
            "[]"));

        //---- label3 ----
        label3.setText("text");
        contentPane.add(label3, "cell 1 1");

        //======== panel1 ========
        {
            panel1.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax .
            swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border
            . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog"
            , java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) ,panel1. getBorder
            () ) ); panel1. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java
            . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException
            ( ) ;} } );
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- label5 ----
            label5.setText("Sub Only Chat");
            panel1.add(label5, "cell 0 0");

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(textArea2);
            }
            panel1.add(scrollPane2, "cell 0 1 5 1,width 500:500:500,height 200:200:200");

            //---- label6 ----
            label6.setText("All Users Chat");
            panel1.add(label6, "cell 0 2");

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(textArea3);
            }
            panel1.add(scrollPane3, "cell 0 3 5 1,width 500:500:500,height 200:200:200");
        }
        contentPane.add(panel1, "cell 23 1 21 5,height 500:500:500");
        contentPane.add(label4, "cell 1 2 13 1");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public void setIcon(BufferedImage image) {
        label4.setIcon(new ImageIcon(image));
    }

    public void setTitle(String title) {
        label3.setText(title);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Diptarag Ray Chaudhuri
    private JLabel label3;
    private JPanel panel1;
    private JLabel label5;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    private JLabel label6;
    private JScrollPane scrollPane3;
    private JTextArea textArea3;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
