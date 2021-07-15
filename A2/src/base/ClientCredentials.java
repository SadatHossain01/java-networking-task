package base;

public class ClientCredentials {
    String username;
    NetworkUtil networkUtil;

    public ClientCredentials(String username, NetworkUtil networkUtil) {
        this.username = username;
        this.networkUtil = networkUtil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }
}
