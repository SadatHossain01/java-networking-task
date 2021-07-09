package tcpforward;

import util.NetworkUtil;

import java.util.Scanner;

public class Client {
    private boolean isRegistered, isLoggedIn;

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

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
            new ReadThreadClient(this, networkUtil);
            new WriteThreadClient(this, username, networkUtil, input);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
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


