package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import dao.GenericDAO;
import entity.User;
import java.util.ArrayList;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "userMB")
@SessionScoped
public class UserMB extends GenericMB implements Serializable {

    private ArrayList<User> users;
    private GenericDAO genericDAO;
    User user;
    public UserMB() {
        super();
    }

    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        user = new User();
    }

    @Override
    public String prepareAdd() {
        String redirect = null;
        //genericDAO.add(user);
        redirect = NavigationConstants.MANAGE_USERS_ADD;

        return redirect;
    }

    @Override
    protected Boolean validateAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String add() {
        String redirect = null;
  
        genericDAO.add(user);
        
        redirect = NavigationConstants.MANAGE_USERS_INDEX;

        return redirect;
    }

    @Override
    public String prepareUpdate() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    protected Boolean validateUpdate() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String prepareDelete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    protected Boolean validateDelete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String prepareView() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_USERS_INDEX;
        users = (ArrayList<User>) genericDAO.findAll(User.class);

        return redirect;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
