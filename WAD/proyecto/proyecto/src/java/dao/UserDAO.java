package dao;

import entity.User;
import java.util.ArrayList;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

/**
 *
 * @author Reus Gaming PC
 */
public class UserDAO extends GenericDAO {

    private static final String FIND_BY_USER_PASSWORD = "from User u where u.user=:userParam and u.password=:passwordParam";
    private static final String FIND_BY_USER_TYPE = "from User u where u.userType.typeId=:typeIdParam";
    private static final String FIND_BY_GROUP_ID = "from User u where u.groupId.groupId=:groupIdParam";

    public UserDAO() {

    }

    /**
     * Method that find an user based on hius username nad password, returns null if not found
     * @param user
     * @param password
     * @return 
     */
    public User findByUserPassword(String user, String password) {
        User u;
        try {
            startOperation();
            u = (User) session.createQuery(FIND_BY_USER_PASSWORD)
                    .setParameter("userParam", user)
                    .setParameter("passwordParam", password).uniqueResult();
            if (u != null) {
                Hibernate.initialize(u.getUserType());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            u = null;
        } finally {
            session.close();
        }
        return u;
    }
    
    public ArrayList<User> findByUserType(Integer typeId) {
        ArrayList<User> users;
        try {
            startOperation();
            users = (ArrayList<User>) session.createQuery(FIND_BY_USER_TYPE)
                    .setParameter("typeIdParam", typeId).list();
            if (users != null) {
                Hibernate.initialize(typeId);
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            users = null;
        } finally {
            session.close();
        }
        return users;
    }
    
    public ArrayList<User> findByGroupId(Integer param) {
        ArrayList<User> users;
        try {
            startOperation();
            users = (ArrayList<User>) session.createQuery(FIND_BY_GROUP_ID)
                    .setParameter("groupIdParam", param)
                    .list();
            if (users != null) {
                for (User u : users){
                    Hibernate.initialize(u.getUserType());
                    Hibernate.initialize(u.getGroupId());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            users = null;
        } finally {
            session.close();
        }
        return users;
    }

}
