package tcpforward;

import util.NetworkUtil;

import java.io.IOException;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private Client client;

    public ReadThreadClient(Client client, NetworkUtil networkUtil) {
        this.client = client;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o instanceof Message) {
                    Message obj = (Message) o;
                    if (!((Message) o).isBroadcast()) System.out.println("New message received from " + obj.getFrom() + ": " + obj.getText());
                    else System.out.println("New broadcast message received from " + obj.getFrom() + ": " + obj.getText());
                }
                else if (o instanceof ListRequest){
                    System.out.println("List of clients logged in right now: ");
                    System.out.print(((ListRequest) o).getText());
                }
                else if (o instanceof RequestAcceptance){
                    if (((RequestAcceptance) o).isRegistrationSuccessful()) client.setRegistered(true);
                    else client.setRegistered(false);
                    if (((RequestAcceptance) o).isLoginSuccessful()) client.setLoggedIn(true);
                    else client.setLoggedIn(false);
                }
                else System.out.println(o);
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
}



