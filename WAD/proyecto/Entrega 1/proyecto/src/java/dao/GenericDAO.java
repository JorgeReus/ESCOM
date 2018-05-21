package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Reus Gaming PC
 */
public class GenericDAO {

    private Session session;
    private Transaction tx;

    public GenericDAO() {

    }

    public Object findByID(Integer id, Class clazz) {
        Object obj = null;
        try {
            startOperation();
            obj = session.load(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        }
        return obj;
    }

    public List findAll(Class clazz) {
        List objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        }
        return objects;
    }

    public Boolean update(Object obj) {
        Boolean successful;
        try {
            startOperation();
            session.update(obj);
            tx.commit();
            successful = Boolean.TRUE;
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            successful = Boolean.FALSE;
        }
        return successful;
    }

    public Boolean delete(Object obj) {
        Boolean successful;
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
            successful = Boolean.TRUE;
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            successful = Boolean.FALSE;
        }
        return successful;
    }
    
    public Boolean add(Object obj) {
        Boolean successful;
        try {
            startOperation();
            session.save(obj);
            tx.commit();
            successful = Boolean.TRUE;
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            successful = Boolean.FALSE;
        }
        return successful;
    }

    protected void startOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

}
