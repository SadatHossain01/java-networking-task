package tcpforward;

import java.io.Serializable;

public class ListRequest implements Serializable {
    private String from;

    public ListRequest(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
}