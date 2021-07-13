package tcpforward;

import java.io.Serializable;

public class RequestAcceptance implements Serializable {
    private boolean RegistrationSuccessful, LoginSuccessful;

    public RequestAcceptance(boolean registrationSuccessful, boolean loginSuccessful) {
        RegistrationSuccessful = registrationSuccessful;
        LoginSuccessful = loginSuccessful;
    }

    public boolean isRegistrationSuccessful() {
        return RegistrationSuccessful;
    }

    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        RegistrationSuccessful = registrationSuccessful;
    }

    public boolean isLoginSuccessful() {
        return LoginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        LoginSuccessful = loginSuccessful;
    }
}
