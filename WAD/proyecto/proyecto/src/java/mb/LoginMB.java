package mb;

import dao.ActivityDAO;
import dao.GenericDAO;
import dao.UserDAO;
import entity.Activity;
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
    private User student;
    private ActivityDAO activityDAO;
    private List<Activity> unansweredActivities;

    public LoginMB() {
        super();
    }

    @PostConstruct
    public void init() {
        userDAO = new UserDAO();
        genericDAO = new GenericDAO();
        subjects = (ArrayList<Subject>) genericDAO.findAll(Subject.class);
        student = new User();
        activityDAO = new ActivityDAO();
        unansweredActivities = new ArrayList<>();
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
                    student = userResult;
                    findUnansweredActivities();
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
    
    public void findUnansweredActivities() {
        unansweredActivities = activityDAO.findUnansweredByUser(student.getUserId());
    }
    
    public String gotoHome() {
        if (student != null && student.getUserType().getTypeId().equals(BussinessConstants.USER_TYPE_STUDENT)) {
            findUnansweredActivities();
        }
        return home;
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
   

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Activity> getUnansweredActivities() {
        return unansweredActivities;
    }

    public void setUnansweredActivities(List<Activity> unansweredActivities) {
        this.unansweredActivities = unansweredActivities;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
    

    
}
