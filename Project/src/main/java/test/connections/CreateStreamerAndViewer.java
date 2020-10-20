package test.connections;

import java.util.Scanner;

public class CreateStreamerAndViewer {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Enter 1 to create viewer, 2 to create streamer");
            String ch = sc.nextLine();
            if(ch.equals("1")) {
                System.out.println("Enter streamer id to connect:");
                String id = sc.nextLine();
                Thread t = new Thread(new ViewerClient(id));
                t.start();
            }
            else {
                System.out.println("Enter id to assign this streamer:");
                String id = sc.nextLine();
                Thread t = new Thread(new StreamerClient(id));
                t.start();
            }
        }
    }
}
