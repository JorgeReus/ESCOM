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

    public void update(Object obj) {
        try {
            startOperation();
            session.update(obj);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        }
    }

    public void delete(Object obj) {
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        }
    }
    
    public void add(Object obj) {
        try {
            startOperation();
            session.save(obj);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        }
    }

    protected void startOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

}
