/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import entity.HibernateUtil;
import entity.UserType;
import org.hibernate.Session;

/**
 *
 * @author reus
 */
public class Test {
    public static void main(String[] args) {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        UserType userType = (UserType) hibernateSession.createQuery("FROM user_type WHERE typeId='1'");
        System.out.println(">>>>>>" + userType.getTypeName());
    }
    
}
