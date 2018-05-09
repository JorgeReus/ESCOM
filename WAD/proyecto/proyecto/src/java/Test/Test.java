/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import entity.HibernateUtil;
import entity.UserType;
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
        UserType userType = (UserType) hibernateSession.createQuery("FROM UserType WHERE typeId='1'").uniqueResult();
        System.out.println(">>>>>>" + userType.getTypeName());
        t.commit();
        hibernateSession.close();
        sessionFactory.close();
    }
    
}
