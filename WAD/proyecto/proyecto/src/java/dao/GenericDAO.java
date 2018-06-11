package dao;

import entity.Activity;
import entity.Group;
import entity.Image;
import entity.User;
import entity.Video;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Reus Gaming PC
 */
public class GenericDAO {

    protected Session session;
    protected Transaction tx;

    public GenericDAO() {

    }

    public Object findByID(Integer id, Class clazz) {
        Object obj = null;
        try {
            startOperation();
            obj = session.load(clazz, id);
            if (clazz.equals(Image.class)) {
                Image img = (Image) obj;
                Hibernate.initialize(img.getImageType());
                Hibernate.initialize(img.getImageCategory());
            } else if (clazz.equals(User.class)) {
                User u = (User) obj;
                Hibernate.initialize(u.getUserType());
            } else if (clazz.equals(Activity.class)) {
                Activity a = (Activity) obj;
                Hibernate.initialize(a.getImages());
            } else if (clazz.equals(Video.class)) {
                Video vid = (Video) obj;
                Hibernate.initialize(vid.getVideo());
            } 
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
            if (clazz.equals(Image.class)) {
                for (Object object : objects) {
                    Image img = (Image) object;
                    Hibernate.initialize(img.getImageType());
                    Hibernate.initialize(img.getImageCategory());
                }
            } else if (clazz.equals(User.class)) {
                for (Object object : objects) {
                    User u = (User) object;
                    Hibernate.initialize(u.getUserType());
                }
            } else if (clazz.equals(Group.class)) {
                for (Object object : objects) {
                    Group g = (Group) object;
                    Hibernate.initialize(g.getTeacherId().getUser());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        } finally {
            session.close();
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
        } finally {
            session.close();
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
        } finally {
            session.close();
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
        } finally {
            session.close();
        }
        return successful;
    }

    public Object safeAdd(Object obj) {
        Integer newObjectId;
        try {
            startOperation();
            newObjectId = (Integer) session.save(obj);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            newObjectId = null;
        } finally {
            session.close();
        }
        return newObjectId;
    }

    protected void startOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

}
