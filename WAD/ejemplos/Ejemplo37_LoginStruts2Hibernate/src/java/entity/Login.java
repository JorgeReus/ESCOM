package entity;

public class Login implements java.io.Serializable {

    private int idLogin;
    private String username;
    private String password;

    public Login() {
    }

    public Login(int idLogin) {
        this.idLogin = idLogin;
    }

    public Login(int idLogin, String username, String password) {
        this.idLogin = idLogin;
        this.username = username;
        this.password = password;
    }

    public int getIdLogin() {
        return this.idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
