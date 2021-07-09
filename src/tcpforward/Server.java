package tcpforward;

import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;
    public HashMap<String, Credentials> clientMap;

    Server() {
        clientMap = new HashMap<>();
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                System.out.println("Server waiting");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Accepted");
                serve(clientSocket);
                System.out.println("New Client Served");
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
            e.printStackTrace();
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        var any = networkUtil.read();
        if (any instanceof Authenticate && ((Authenticate) any).isRegistration){
            String username = ((Authenticate) any).getUsername();
            String password = ((Authenticate) any).getPassword();
            clientMap.put(username, new Credentials(password, networkUtil));
            System.out.println("New user registered: " + username);
        }
        new ReadThreadServer(clientMap, networkUtil);
    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}
