package paquete;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "userBean1")
@SessionScoped
public class UserBean implements Serializable {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String Value) {
        name = Value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Value) {
        password = Value;
    }
}
