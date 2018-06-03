package actionsupportpackage;

import com.opensymphony.xwork2.ActionSupport;
import entity.Login;
import entity.HibernateUtil;
import org.hibernate.Session;

public class LoginActionSupport extends ActionSupport {

    String userName, password;
    Session hibernateSession;
    Login login;

    public String execute() throws Exception {
        hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        System.out.println("session get");
        if (userName != null && password != null && (!userName.equals("")) && (!password.equals(""))) {
            login = (Login) hibernateSession.createQuery("from Login where userName='" + userName + "'AND password='" + password + "'").uniqueResult();
        } else {
            addActionError("User Name does not exist");
            return INPUT;
        }
        if (login == null) {
            addActionError("User Name does not exist");
            return INPUT;
        }
        addActionMessage("Welcome you logined");
        return SUCCESS;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
