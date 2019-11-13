package sg.edu.np.cosign;

public class UserData {
    private String MyUsername;
    private String MyPassword;
    private String MyEmail;

    public UserData()
    {

    }

    public UserData(String myUsername, String myPassword, String myEmail)
    {
        MyUsername = myUsername;
        MyPassword = myPassword;
        MyEmail = myEmail;
    }

    public String getMyUsername() {
        return MyUsername;
    }

    public void setMyUsername(String myUsername) {
        MyUsername = myUsername;
    }

    public String getMyPassword() {
        return MyPassword;
    }

    public void setMyPassword(String myPassword) {
        MyPassword = myPassword;
    }

    public String getMyEmail() { return MyEmail; }

    public void setMyEmail(String myEmail) { MyEmail = myEmail; }
}
