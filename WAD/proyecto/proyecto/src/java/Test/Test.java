/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import entity.HibernateUtil;
import entity.User;
import entity.UserType;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author reus
 */
public class Test {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session hibernateSession = sessionFactory.openSession();
        Transaction t = hibernateSession.beginTransaction();
        for (UserType userType : (ArrayList<UserType>) hibernateSession.createQuery("FROM entity.UserType").list()) {
            System.out.println(">>>>>>" + userType.getTypeName());
        }
        for (User user : (ArrayList<User>) hibernateSession.createQuery("FROM entity.User").list()) {
            System.out.println(">>>>>>" + user.getUser());
        }
        t.commit();
        hibernateSession.close();
        sessionFactory.close();
    }

}
