package tcpforward;

import util.NetworkUtil;

import java.util.Scanner;

public class Client {

    public Client(String serverAddress, int serverPort, Scanner input) {
        try {
            System.out.println("Welcome");
            NetworkUtil networkUtil = new NetworkUtil(serverAddress, serverPort);
            System.out.print("Enter a username: ");
            String username = input.nextLine();
            System.out.print("Enter a password: ");
            String password = input.nextLine();
            Authenticate newClient = new Authenticate(username, password, true);
            networkUtil.write(newClient);
            new ReadThreadClient(networkUtil);
            new WriteThreadClient(username, networkUtil, input);
            System.out.println("All threads opened");
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            WriteThreadClient.showOptions();
            int command = Integer.parseInt(scanner.nextLine());
            if (command == 1) {
                Client c = new Client(serverAddress, serverPort, scanner);
                break;
            } else System.out.println("You must register first!");
        }
    }
}


