package base;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    String from, to;
    String text;
    Message.Type type;
    List<String>activeList;

    public enum Type{
        UserName, SingleMessage, Broadcast, GetList, SendList
    }

    Message(Message.Type type, String from, String to, String message){
        this.type = type;
        this.from = from;
        this.to = to;
        this.text = message;
    }

    public Message(String from, String to, Type type, List<String> activeList) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.activeList = activeList;
    }
}
