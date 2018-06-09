
package mb;

import dao.GenericDAO;
import dao.UserDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import util.NavigationConstants;
import entity.Group;
import entity.User;
import entity.UserType;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import util.BussinessConstants;

@ManagedBean(name = "groupMB")
@SessionScoped
public class GroupMB extends GenericMB implements Serializable{
    
    Group group;
    private ArrayList<User> users;
    private ArrayList<UserType> userTypes;
    private GenericDAO genericDAO;
    private UserDAO userDAO;
    private Boolean canProceed;
    User user;

    public GroupMB() {
        super();
    }
    
    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        userDAO = new UserDAO();
        canProceed = Boolean.TRUE;
        UserType userType = new UserType();
        userTypes = new ArrayList<>();
        user = new User();
        user.setUserType(userType);
        group = new Group();
    }
    
    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_GROUPS_INDEX;
        return redirect;
    }
    
    @Override
    public String prepareAdd() {
        users = (ArrayList<User>) userDAO.findByUserType(BussinessConstants.USER_TYPE_TEACHER);
        for (User user1 : users) {
            System.out.println(": "+user1.getUser());
        }
        
        
        if (users == null || users.isEmpty()) {
            addMessage("Couldn't load User type information", "messages", FacesMessage.SEVERITY_ERROR);
            canProceed = Boolean.FALSE;
        }
        return NavigationConstants.MANAGE_GROUPS_ADD;
    }
    
    @Override
    protected Boolean validateAdd() {
        Boolean isValid = Boolean.TRUE;
        if (group.getGroupName().isEmpty()) {
            addMessage("The Group name field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (group.getTeacherId().getUserId() == null ) {
            addMessage("The teacher field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }
    
    @Override
    public String add() {
        String redirect = prepareAdd();
        if (validateAdd()) {
            if (genericDAO.add(group)) {
                addMessage("Successfully Registered", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Error!, couln't add the user", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
