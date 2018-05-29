/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import org.hibernate.HibernateException;

/**
 *
 * @author Reus Gaming PC
 */
public class UserDAO extends GenericDAO{

    private static final String FIND_BY_USER_PASSWORD = "from User u where u.user=:userParam and u.password=:passwordParam";

    public UserDAO() {

    }

    public User findByUserPassword(String user, String password) {
        User u;
        try {          
            startOperation();
            u = (User) session.createQuery(FIND_BY_USER_PASSWORD)
                    .setParameter("userParam", user)
                    .setParameter("passwordParam", password).uniqueResult();
            tx.commit();
        } catch (HibernateException e){
            tx.rollback();
            System.err.println(e);
            u = null;
        }
        return u;
    }

}
