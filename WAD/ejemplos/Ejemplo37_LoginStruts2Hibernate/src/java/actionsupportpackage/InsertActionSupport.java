package actionsupportpackage;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import entity.Login;
import entity.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InsertActionSupport extends ActionSupport {
 String userName,password;
 int idLogin;
 Session hibernateSession;
 Login login;
    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Session getHibernateSession() {
        return hibernateSession;
    }

    public void setHibernateSession(Session hibernateSession) {
        this.hibernateSession = hibernateSession;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

 public String execute() throws Exception 
 {
 hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
 
 //Insert
 Transaction t0=hibernateSession.beginTransaction();
 Login user0 = new Login();
 user0.setIdLogin(idLogin);
 user0.setUsername(userName);
 user0.setPassword(password);
 hibernateSession.save(user0);
 t0.commit();
 
 //Select
 Login user1=(Login)hibernateSession.load(Login.class, 1);
 System.out.println(user1.getIdLogin());
 System.out.println(user1.getUsername());
 System.out.println(user1.getPassword());
 
 //Update
 Transaction t1=hibernateSession.beginTransaction();
 user1.setPassword("456");
 hibernateSession.update(user1);
 t1.commit();
 
 //Delete
 Transaction t2=hibernateSession.beginTransaction(); 
 Login user2=(Login)hibernateSession.load(Login.class,9);
 hibernateSession.delete(user2);
 t2.commit(); 
  return SUCCESS;
 }  
}
