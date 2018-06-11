package mb;

import dao.GenericDAO;
import dao.UserDAO;
import entity.Subject;
import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import util.BussinessConstants;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends GenericMB implements Serializable {

    private UserDAO userDAO;
    private GenericDAO genericDAO;
    private String user;
    private String password;
    private String home;
    private List<Subject> subjects;

    public LoginMB() {
        super();
    }

    @PostConstruct
    public void init() {
        userDAO = new UserDAO();
        genericDAO = new GenericDAO();
        subjects = (ArrayList<Subject>) genericDAO.findAll(Subject.class);
    }

    public String validateLogin() {
        String redirect = null;
        User userResult = userDAO.findByUserPassword(user, password);
        // If its correct, redirect based on the user type
        if (userResult != null) {
            getSession().setAttribute("user", userResult.getUser());
            getSession().setAttribute("userId", userResult.getUserId());
            getSession().setAttribute("userType", userResult.getUserType().getTypeName());
            switch (userResult.getUserType().getTypeId()) {
                case BussinessConstants.USER_TYPE_ADMINISTRATOR:
                    redirect = NavigationConstants.LOGIN_ADMIN;
                    break;
                case BussinessConstants.USER_TYPE_TEACHER:
                    redirect = NavigationConstants.LOGIN_TEACHER;
                    break;
                case BussinessConstants.USER_TYPE_STUDENT:
                    redirect = NavigationConstants.LOGIN_STUDENT;
                    break;
                default:
                    redirect = NavigationConstants.LOGIN_INDEX;
                    break;
            }
        } else {
            // If not, show a message
            addMessage("User/Password Incorrect", "messages", FacesMessage.SEVERITY_ERROR);
        }
        home = redirect;
        return redirect;
    }

    public String logout() {
        getSession().invalidate();
        return NavigationConstants.LOGIN_INDEX;
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
   
    public String gotoHome() {
        return home;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}
