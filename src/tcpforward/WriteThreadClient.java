package tcpforward;

import util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadClient implements Runnable {

    private Thread thr;
    private NetworkUtil networkUtil;
    Scanner input;
    String name;

    public WriteThreadClient(String name, NetworkUtil networkUtil, Scanner scanner) {
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
                        System.out.print("Enter your username: ");
                        username = input.nextLine();
                        System.out.print("Enter the password: ");
                        password = input.nextLine();
                        networkUtil.write(new Authenticate(username, password, false));
                        break;
                    case 3:
                        networkUtil.write(new ListRequest(name));
                        break;
                    case 4:
                        System.out.println("Enter username of the client to send: ");
                        String to = input.nextLine();
                        System.out.println("Enter the message: ");
                        String text = input.nextLine();
                        Message message = new Message();
                        message.setFrom(name);
                        message.setTo(to);
                        message.setBroadcast(false);
                        message.setText(text);
                        networkUtil.write(message);
                        break;
                    case 5:
                        System.out.println("Enter the message: ");
                        text = input.nextLine();
                        message = new Message();
                        message.setFrom(name);
                        message.setTo("all");
                        message.setBroadcast(true);
                        message.setText(text);
                        networkUtil.write(message);
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



