package tcpforward;

import java.io.Serializable;

public class ListRequest implements Serializable {
    private String from;
    private String text;

    public ListRequest(String from) {
        this.from = from;
    }

    public ListRequest(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}