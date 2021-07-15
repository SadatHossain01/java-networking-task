package base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    ServerSocket serverSocket;
    HashMap<String, ClientCredentials> clientMap = new HashMap<>();

    Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        var networkUtil = new NetworkUtil(clientSocket);
        var next = networkUtil.read();
        String username = "";
        if (next instanceof Message){
            if (((Message) next).type == Message.Type.UserName){
                username = ((Message) next).text;
            }
        }
        System.out.println("New user connected: " + username);
        clientMap.put(username, new ClientCredentials(username, networkUtil));
        new ReadThreadServer(networkUtil, clientMap);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 33333;
        Server server = new Server(port);
        while (true){
            var cs = server.serverSocket.accept();
            server.serve(cs);
        }
    }
}