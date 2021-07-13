package tcpforward;

import util.NetworkUtil;

public class Credentials {
    private String password;
    private NetworkUtil networkUtil;

    public Credentials(String password, NetworkUtil networkUtil) {
        this.password = password;
        this.networkUtil = networkUtil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }
}
