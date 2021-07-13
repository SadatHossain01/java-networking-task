package tcpforward;

import java.io.Serializable;

public class Message implements Serializable {
    private String from;
    private String to;
    private String text;
    private Boolean broadcast;

    public Message(String from, String to, String text, Boolean broadcast) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.broadcast = broadcast;
    }

    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    public Boolean isBroadcast() {
        return broadcast;
    }

    public String getText() {
        return text;
    }
}