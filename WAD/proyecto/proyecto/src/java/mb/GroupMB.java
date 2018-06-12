
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
    
    private Group group;
    private ArrayList<Group> groups;
    private ArrayList<User> users;
    private ArrayList<User> students;
    private ArrayList<User> selectedStudents;
    private ArrayList<UserType> userTypes;
    private ArrayList<User> studentsInGroup;
    private GenericDAO genericDAO;
    private UserDAO userDAO;
    private Boolean canProceed;
    private User user;
    UserType userType;

    public GroupMB() {
        super();
    }
    
    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        userDAO = new UserDAO();
        canProceed = Boolean.TRUE;
        userType = new UserType();
        userTypes = new ArrayList<>();
        user = new User();
        group = new Group();
        groups = new ArrayList<>();
        selectedStudents = new ArrayList<>();
        studentsInGroup = new ArrayList<>();
    }
    
    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_GROUPS_INDEX;
        groups = (ArrayList<Group>) genericDAO.findAll(Group.class);
        return redirect;
    }
    
    public String viewGroup() {
        String redirect = NavigationConstants.MANAGE_GROUPS_VIEW;
        studentsInGroup = (ArrayList<User>) userDAO.findByGroupId(group.getGroupId());
        user = new User();
        user = (User) genericDAO.findByID(group.getTeacherId().getUserId(), user.getClass());
        return redirect;
    }
    
    @Override
    public String prepareAdd() {
        canProceed = Boolean.TRUE;
        users = (ArrayList<User>) userDAO.findByUserType(BussinessConstants.USER_TYPE_TEACHER);
        userDAO = new UserDAO();
        students = (ArrayList<User>) userDAO.findByUserType(BussinessConstants.USER_TYPE_STUDENT);
        if (users == null || users.isEmpty()) {
            addMessage("Couldn't load teacher information", "messages", FacesMessage.SEVERITY_ERROR);
            canProceed = Boolean.FALSE;
        }
        if (students == null || students.isEmpty()) {
            addMessage("Couldn't load students information", "messages", FacesMessage.SEVERITY_ERROR);
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
        if (selectedStudents.isEmpty()) {
            addMessage("Select at least one student", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }
    
    @Override
    public String add() {
        String redirect = prepareAdd();
        if (validateAdd()) {
            if (genericDAO.add(group)) {
                //System.out.println("List: "+selectedStudents.size());
                //System.out.println("First: "+selectedStudents.get(0).getUser());
                 
                for (User selectedStudent : selectedStudents) {
                    
                    selectedStudent.setGroupId(group);
                    if(!genericDAO.update(selectedStudent)){
                        addMessage("Error!, couln't add the students", "messages", FacesMessage.SEVERITY_ERROR);
                    }
                }
                addMessage("Successfully Registered", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Error!, couln't add the group", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }
    
    @Override
    public String delete() {
        studentsInGroup = (ArrayList<User>) userDAO.findByGroupId(group.getGroupId());
        for (User student : studentsInGroup) {
            student.setGroupId(null);
            genericDAO.update(student);
        }
        if (genericDAO.delete(group)) {
            addMessage("Successfully Deleted", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("Couldn't delete the group, maybe there are students asociated", "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndex();
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

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
    }

    public ArrayList<User> getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(ArrayList<User> selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    public ArrayList<User> getStudentsInGroup() {
        return studentsInGroup;
    }

    public void setStudentsInGroup(ArrayList<User> studentsInGroup) {
        this.studentsInGroup = studentsInGroup;
    }
    
    
    
}
