package mainclasses;

import javax.swing.*;

public class LoginStart {
    static {
        System.setProperty("sun.java2d.opengl","true");
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new LoginRegister());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(640,480);
        frame.setTitle("Revenge Live: Login/Register");
    }
}
