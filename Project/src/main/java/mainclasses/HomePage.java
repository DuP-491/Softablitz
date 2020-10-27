/*
 * Created by JFormDesigner on Tue Oct 20 17:50:01 IST 2020
 */

package mainclasses;

import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;
import stream.Category;
import stream.PreStream;
import user.Streamer;
import user.Viewer;

import java.awt.*;
import java.net.Socket;

/**
 * @author Diptarag Ray Chaudhuri
 */
public class HomePage extends JPanel {
    private Viewer viewer;
    private Streamer streamer;

    public HomePage(Viewer viewer, Streamer streamer) {
        initComponents();
        //Edit all jpanel componenents here
        for(Category i : Category.values()) {
            comboBox1.addItem(i);
        }
        this.viewer = viewer;
        this.streamer = streamer;
        textArea1.setEditable(false);
        textArea2.setEditable(false);
    }

    public void setNotifications(String block) {
        textArea1.setText(block);
    }

    private void browsePressed(ActionEvent e) {
        // TODO add your code here
        Category cat = (Category) comboBox1.getSelectedItem();
        String text = "Live streamers in " + cat + " are: \n";

        try {
            String streams[] = viewer.getStreamsByCategory(cat);
            for (int i = 0; i < streams.length; i++) {
                text += streams[i] + "\n";
            }
        }
        catch (NullPointerException ee) {
            ;
        }

        textArea2.setText(text);
    }

    private void startWatchingPressed(ActionEvent e) {
        // TODO add your code here
        String streamerUsername = textField2.getText();
        viewer.startWatching(streamerUsername);

    }

    private void startStreamingPressed(ActionEvent e) {
        // TODO add your code here
        PreStream prestream = new PreStream(streamer);
        prestream.setVisible(true);
        prestream.setSize(640,480);
        prestream.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void checkOutPressed(ActionEvent e) {
        // TODO add your code here
        viewer.checkOutUser(textField3.getText());
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
        label4 = new JLabel();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();
        label7 = new JLabel();
        textField3 = new JTextField();
        button5 = new JButton();
        label5 = new JLabel();
        textField2 = new JTextField();
        button3 = new JButton();
        label6 = new JLabel();
        button4 = new JButton();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder ( 0
        , 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
        , new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) ,
         getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "bord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
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
        add(label3, "cell 1 6 2 1");
        add(comboBox1, "cell 2 6 4 1");

        //---- button2 ----
        button2.setText("Browse");
        button2.addActionListener(e -> browsePressed(e));
        add(button2, "cell 6 6");

        //---- label4 ----
        label4.setText("Browse Results");
        add(label4, "cell 1 8");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(textArea2);
        }
        add(scrollPane2, "cell 1 9 2 1,width 400:400:400,height 200:200:200");

        //---- label7 ----
        label7.setText("Check out User by Handle:");
        add(label7, "cell 1 12");
        add(textField3, "cell 2 12 4 1");

        //---- button5 ----
        button5.setText("Check out");
        button5.addActionListener(e -> checkOutPressed(e));
        add(button5, "cell 6 12");

        //---- label5 ----
        label5.setText("Start watching streamer by Handle");
        add(label5, "cell 1 14");
        add(textField2, "cell 2 14 4 1");

        //---- button3 ----
        button3.setText("Start Watching");
        button3.addActionListener(e -> startWatchingPressed(e));
        add(button3, "cell 6 14");

        //---- label6 ----
        label6.setText("OR");
        add(label6, "cell 1 16");

        //---- button4 ----
        button4.setText("Start Streaming");
        button4.addActionListener(e -> startStreamingPressed(e));
        add(button4, "cell 2 16");
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
    private JLabel label4;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    private JLabel label7;
    private JTextField textField3;
    private JButton button5;
    private JLabel label5;
    private JTextField textField2;
    private JButton button3;
    private JLabel label6;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
