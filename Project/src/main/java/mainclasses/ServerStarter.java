package mainclasses;

import connections.Server;
import connections.login.LoginServer;

public class  ServerStarter {
    public static void main(String[] args) {
        Thread t = new Thread(new Server()); //start main server
        t.start();

        t = new Thread(new LoginServer()); //start login server
        t.start();
    }
}
