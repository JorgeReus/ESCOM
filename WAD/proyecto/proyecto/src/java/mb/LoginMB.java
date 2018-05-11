package mb;

import dao.UserDAO;
import entity.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends GenericMB implements Serializable {

    private UserDAO userDAO;
    private String user;
    private String password;

    public LoginMB() {
        super();
    }

    @PostConstruct
    public void init() {
        userDAO = new UserDAO();
    }

    public String validateLogin() {
        String redirect = "/login.xhtml";
        User userResult = userDAO.findByUserPassword(user, password);
        if (userResult != null) {
            getSession().setAttribute("user", userResult.getUser());
            getSession().setAttribute("userId", userResult.getUserId());
            redirect = "/admin.xhtml";
        }
        return redirect;
    }

    public String logout() {
        getSession().invalidate();
        return "/login.xhtml";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
