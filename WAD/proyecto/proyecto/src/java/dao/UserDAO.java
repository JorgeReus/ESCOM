/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Reus Gaming PC
 */
public class UserDAO {

    private Session session;
    private Transaction tx;
    private static final String FIND_BY_USER_PASSWORD = "from User u where u.user=:userParam and u.password=:passwordParam";

    public UserDAO() {

    }

    public User findByUserPassword(String user, String password) {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        User u = (User)session.createQuery(FIND_BY_USER_PASSWORD)
        .setParameter("userParam", user)
        .setParameter("passwordParam", password).uniqueResult();
        tx.commit();
        return u;
    }

}
