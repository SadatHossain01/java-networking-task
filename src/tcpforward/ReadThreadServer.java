package tcpforward;

import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
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
                    String to = obj.getTo();
                    NetworkUtil nu = clientMap.get(to).getNetworkUtil();
                    if (nu != null) {
                        nu.write(obj);
                    }
                }
                else if (o instanceof Authenticate){
                    if (((Authenticate) o).isRegistration){
                        System.out.println("Inside the registration process");
                    }
                    else{
                        username = ((Authenticate) o).getUsername();
                        password = ((Authenticate) o).getPassword();
                        if (clientMap.containsKey(username) && clientMap.get(username).getPassword().equals(password)){
                            var obtained = clientMap.get(username);
                            loggedInClients.put(username, obtained);
                            obtained.getNetworkUtil().write("Login Successful!");
                        }
                    }
                }
                else if (o instanceof ListRequest){
                    String from = ((ListRequest) o).getFrom();
                    String l = "List: \n";
                    Iterator<Map.Entry<String, Credentials>> it = loggedInClients.entrySet().iterator();
                    while (it.hasNext()){
                        Map.Entry<String, Credentials> set = it.next();
                        l += set.getKey() + "\n";
                    }
                    var nUtil = loggedInClients.get(from).getNetworkUtil();
                    if (nUtil != null) nUtil.write(l);
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



