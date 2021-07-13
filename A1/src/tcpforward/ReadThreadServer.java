package tcpforward;

import util.NetworkUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    public HashMap<String, Credentials> clientMap = new HashMap<>();
    public HashMap<String, Credentials> loggedInClients = new HashMap<>();


    public ReadThreadServer(HashMap<String, Credentials> map, NetworkUtil networkUtil) {
        this.clientMap = map;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                String username, password;
                if (o instanceof Message) {
                    Message obj = (Message) o;
                    if (!((Message) o).isBroadcast()) {
                        String to = obj.getTo();
                        clientMap.get(to).getNetworkUtil().write(obj);
                    } else {
                        for (var set : loggedInClients.entrySet()) {
                            if (!set.getKey().equals(obj.getFrom())) set.getValue().getNetworkUtil().write(obj);
                        }
                    }
                } else if (o instanceof Authenticate) {
                    if (!((Authenticate) o).isRegistration) {
                        username = ((Authenticate) o).getUsername();
                        password = ((Authenticate) o).getPassword();
                        if (clientMap.containsKey(username)) {
                            if (clientMap.get(username).getPassword().equals(password)) {
                                var obtained = clientMap.get(username);
                                loggedInClients.put(username, obtained);
                                obtained.getNetworkUtil().write("Login Successful!");
                                obtained.getNetworkUtil().write(new RequestAcceptance(true, true));
                            }
                            else clientMap.get(((Authenticate) o).getUsername()).getNetworkUtil().write("Login Unsuccessful. Please input your correct password");
                        }
                    }
                } else if (o instanceof ListRequest) {
                    String from = ((ListRequest) o).getFrom();
                    String l = "List: \n";
                    for (var set : loggedInClients.entrySet()) {
                        l += set.getKey() + "\n";
                    }
                    ((ListRequest) o).setText(l);
                    loggedInClients.get(from).getNetworkUtil().write(o);
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
}



