package test;

import dao.GenericDAO;
import entity.User;
import entity.UserType;
import java.util.ArrayList;

/**
 *
 * @author reus
 */
public class Test {

    public static void main(String[] args) {
        GenericDAO genericDAO = new GenericDAO();
        // Finds any mapped object with the given id  and the class
        User user = (User) genericDAO.findByID(2, User.class);
        if (user != null) {
            System.out.println("User: " + user.getUser() + " UserType: " + user.getUserType().getTypeName());
        }

        // Finds all mapped object's given the class
        ArrayList<User> results = (ArrayList<User>) genericDAO.findAll(User.class);
        if (results != null) {
            results.forEach((userIter) -> {
                System.out.println("User: " + userIter.getUser() + " Password: " + userIter.getPassword());
            });
        }
        
        // Updates any mapped object given its reference
        User userUpdate = new User();
        userUpdate.setUserId(1);
        userUpdate.setUser("Bryan");
        userUpdate.setPassword("abcd");
        genericDAO.update(userUpdate);
        
        // Deletes any mapped object given its reference
        genericDAO.delete(userUpdate);
        
        // Adds a new mapped object given its values
        User userAdd = new User();
        UserType userType = new UserType();
        userType.setTypeId(1);
        userAdd.setUserType(userType);
        userAdd.setUser("Bryan");
        userAdd.setPassword("1232");
        genericDAO.add(userAdd);
    }

}
