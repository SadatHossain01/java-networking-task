package base;

import java.io.IOException;

public class ReadThreadClient implements Runnable{
    Thread t;
    Client c;
    ReadThreadClient(Client c){
        t = new Thread("ReadThreadClient");
        this.c = c;
        t.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                Object next = null;
                try {
                    next = c.ois.readUnshared();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (next instanceof Message && ((Message) next).type == Message.Type.SendList){
                    System.out.println("Showing the list of active clients:");
                    var l = ((Message) next).activeList;
                    int i = 1;
                    for (var c : l){
                        System.out.println(i++ + c);
                    }
                }
                else{
                    System.out.println("printing the other");
                    System.out.println(next);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
