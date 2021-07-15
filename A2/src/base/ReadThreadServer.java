package base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable{
    Thread t;
    NetworkUtil networkUtil;
    HashMap<String, ClientCredentials> clientMap;

    ReadThreadServer(NetworkUtil networkUtil, HashMap<String, ClientCredentials> clientMap){
        this.networkUtil = networkUtil;
        this.clientMap = clientMap;
        t = new Thread(this, "Read Thread Server");
        t.start();
    }

    @Override
    public void run() {
        while (true){
            Object next;
            while(true) {
                try {
                    next = networkUtil.read();
                    break;
                } catch (IOException | ClassNotFoundException ignored){}
            }
            if (next instanceof Message){
                var message = (Message)next;
                if (message.type == Message.Type.SingleMessage){
                    if (clientMap.containsKey(message.to)){
                        try {
                            clientMap.get(message.to).getNetworkUtil().write("From " + message.from + ": \n" + message.text);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("Sent to " + message.to);
                        System.out.println("List is following:");
                        for (var c:clientMap.keySet()) System.out.println(c);
                    }
                }
                else if (message.type == Message.Type.Broadcast){
                    var from = message.from;
                    for (var c : clientMap.entrySet()){
                        if (!c.getKey().equalsIgnoreCase(from)){
                            try {
                                c.getValue().getNetworkUtil().write("Broadcast Message from " + message.from + ": " + message.text);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else if (message.type == Message.Type.GetList){
                    if (clientMap.containsKey(message.from)) {
                        List<String> activeList = new ArrayList<>();
                        for (var c : clientMap.entrySet()) {
                            if (!c.getKey().equalsIgnoreCase(message.from)) activeList.add(c.getKey());
                        }
                        try {
                            clientMap.get(message.from).getNetworkUtil().write(new Message(message.from, message.to, Message.Type.SendList, activeList));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("Sent from " + message.from);
                        System.out.println("List is following:");
                        for (var c:clientMap.keySet()) System.out.println(c);
                    }
                }
            }
        }
    }
}
