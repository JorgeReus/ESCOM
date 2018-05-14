package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import dao.GenericDAO;
import entity.User;
import entity.UserType;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "userMB")
@SessionScoped
public class UserMB extends GenericMB implements Serializable {

    private ArrayList<User> users;
    private ArrayList<UserType> userTypes;
    private GenericDAO genericDAO;
    private Boolean canProceed;
    User user;

    public UserMB() {
        super();
    }

    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        canProceed = Boolean.TRUE;
        UserType userType = new UserType();
        userTypes = new ArrayList<>();
        user = new User();
        user.setUserType(userType);
    }

    @Override
    public String prepareAdd() {
        userTypes = (ArrayList<UserType>) genericDAO.findAll(UserType.class);
        if (userTypes == null || userTypes.isEmpty()) {
            addMessage("Error!, couldn't load User type information", "messages", FacesMessage.SEVERITY_ERROR);
            canProceed = Boolean.FALSE;
        }
        return NavigationConstants.MANAGE_USERS_ADD;
    }

    @Override
    protected Boolean validateAdd() {
        Boolean isValid = Boolean.TRUE;
        if (user.getUser().isEmpty()) {
            addMessage("The User field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (user.getPassword().isEmpty()) {
            addMessage("The password field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (user.getUserType().getTypeId() == null) {
            addMessage("The User Type field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }

    @Override
    public String add() {
        String redirect = prepareAdd();
        if (validateAdd()) {
            if (genericDAO.add(user)) {
                addMessage("Successfully Registered", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Error!, couln't add the user", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }

    @Override
    public String prepareUpdate() {
        userTypes = (ArrayList<UserType>) genericDAO.findAll(UserType.class);
        if (userTypes == null || userTypes.isEmpty()) {
            addMessage("Error!, couldn't load User type information", "messages", FacesMessage.SEVERITY_ERROR);
            canProceed = Boolean.FALSE;
        }
        return NavigationConstants.MANAGE_USERS_EDIT;
    }

    @Override
    protected Boolean validateUpdate() {
        Boolean isValid = Boolean.TRUE;
        if (user.getUser().isEmpty()) {
            addMessage("The User field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (user.getPassword().isEmpty()) {
            addMessage("The password field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (user.getUserType().getTypeId() == null) {
            addMessage("The User Type field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }

    @Override
    public String update() {
        String redirect = prepareUpdate();
        if (validateUpdate()) {
            UserType type = new UserType();
            type.setTypeId(user.getUserType().getTypeId());
            user.setUserType(type);
            if (genericDAO.update(user)) {
                addMessage("Successfully Updated", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Error!, couln't update the user", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }

    @Override
    public String delete() {
        if (genericDAO.delete(user)) {
            addMessage("Successfully Deleted", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("Error!, couln't delete the user", "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndex();
    }

    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_USERS_INDEX;
        users = (ArrayList<User>) genericDAO.findAll(User.class);
        user = new User();
        UserType userType = new UserType();
        user.setUserType(userType);
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

    public ArrayList<UserType> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(ArrayList<UserType> userTypes) {
        this.userTypes = userTypes;
    }

    public GenericDAO getGenericDAO() {
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public Boolean getCanProceed() {
        return canProceed;
    }

    public void setCanProceed(Boolean canProceed) {
        this.canProceed = canProceed;
    }

}
