package base;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    boolean isConnected = false;
    NetworkUtil networkUtil;
    String username;

    Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        networkUtil = new NetworkUtil(socket);
        new ReadThreadClient(this);
    }

    public static void showOptions() {
        System.out.println("""
                1. Connect
                2. GetList
                3. SendMessage
                4. Broadcast""");
        System.out.println("Choose an option from 1 to 4");
    }

    public static void main(String[] args) throws IOException {
        String selfAddress = "127.0.0.1";
        int port = 33333;
        Client c = null;

        while (true) {
            showOptions();
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            try{
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e){
                e.printStackTrace();
            }
            switch (choice){
                case 1:
                    if (c != null && c.isConnected) System.out.println("You are already connected!");
                    else {
                        c = new Client(selfAddress, port);
                        System.out.print("Enter your username: ");
                        c.username = scanner.nextLine();
                        c.networkUtil.write(new Message(Message.Type.UserName, c.username, "Server", c.username));
                        c.isConnected = true;
                    }
                    break;
                case 2:
                    if (c != null && c.isConnected){
                        c.networkUtil.write(new Message(Message.Type.GetList, c.username, c.username, ""));
                    }
                    else System.out.println("You must connect to the server first");
                    break;
                case 3:
                    if (c!= null && c.isConnected) {
                        System.out.print("Enter the username of the client you want to send the message to: ");
                        String to = scanner.nextLine();
                        System.out.println("Enter your message:");
                        String text = scanner.nextLine();
                        c.networkUtil.write(new Message(Message.Type.SingleMessage, c.username, to, text));
                    }
                    else System.out.println("You must connect to the server first");
                    break;
                case 4:
                    if (c != null && c.isConnected) {
                        System.out.println("Enter your message:");
                        String text = scanner.nextLine();
                        c.networkUtil.write(new Message(Message.Type.Broadcast, c.username, "all", text));
                    }
                    else System.out.println("You must connect to the server first");
                    break;
                default:
                    System.out.println("You must input a choice between 1 to 4");
                    break;
            }
        }
    }
}