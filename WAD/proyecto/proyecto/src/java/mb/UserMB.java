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
public class UserMB extends GenericMB implements Serializable{

    private ArrayList<User> users;
       
    public UserMB () {
        super();
    }   
    
    @PostConstruct
    public void init() {
        // Inicializar objetos
    }

    @Override
    public String prepareAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    protected Boolean validateAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    public String add() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
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
        String redirect = null;
        /*GenericDAO genericDAO = new GenericDAO();
        users = (ArrayList<User>) genericDAO.findAll(User.class);
        if (users != null) {
            users.forEach((userIter) -> {
                System.out.println("User: " + userIter.getUser() + " Password: " + userIter.getPassword());
            });
        }*/
        redirect = NavigationConstants.MANAGE_USERS;
        
        return redirect;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
}


