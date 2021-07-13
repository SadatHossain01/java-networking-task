package tcpforward;

import java.io.Serializable;

public class Authenticate implements Serializable {
    private String username, password;
    boolean isRegistration;

    public Authenticate(String username, String password, boolean isRegistration) {
        this.username = username;
        this.password = password;
        this.isRegistration = isRegistration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
