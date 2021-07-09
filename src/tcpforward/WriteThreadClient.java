package tcpforward;

import util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadClient implements Runnable {

    private Thread thr;
    private NetworkUtil networkUtil;
    Scanner input;
    String name;
    private Client client;

    public WriteThreadClient(Client client, String name, NetworkUtil networkUtil, Scanner scanner) {
        this.client = client;
        this.name = name;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        this.input = scanner;
        thr.start();
    }

    public void run() {
        System.out.println("In write thread of client " + name);
        try {
            while (true) {
                showOptions();
                int command = Integer.parseInt(input.nextLine());
                String username, password;
                switch (command) {
                    case 1:
                        System.out.println("Client has already been registered to the server.");
                        break;
                    case 2:
                        if (client.isLoggedIn()) System.out.println("You are already logged in");
                        else {
                            System.out.print("Enter your username: ");
                            username = input.nextLine();
                            System.out.print("Enter the password: ");
                            password = input.nextLine();
                            if (username.equals(name)) networkUtil.write(new Authenticate(username, password, false));
                            else System.out.println("Incorrect username");
                        }
                        break;
                    case 3:
                        if (!client.isLoggedIn()) System.out.println("You must login first!");
                        else networkUtil.write(new ListRequest(name));
                        break;
                    case 4:
                        if (!client.isLoggedIn()) System.out.println("You must login first!");
                        else {
                            System.out.print("Enter username of the client to send: ");
                            String to = input.nextLine();
                            System.out.print("Enter the message: ");
                            String text = input.nextLine();
                            Message message = new Message(name, to, text, false);
                            networkUtil.write(message);
                        }
                        break;
                    case 5:
                        if (!client.isLoggedIn()) System.out.println("You must login first!");
                        else {
                            System.out.print("Enter the message: ");
                            String text = input.nextLine();
                            Message message = new Message(name, "all", text, true);
                            networkUtil.write(message);
                        }
                        break;
                    default:
                        System.out.println("You must enter a choice between 1 to 5");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showOptions() {
        System.out.println("Choose an option from 1 to 5:");
        System.out.println("1. Register\n2. Login\n3. GetList\n4. SendOne\n5. Broadcast");
    }
}



