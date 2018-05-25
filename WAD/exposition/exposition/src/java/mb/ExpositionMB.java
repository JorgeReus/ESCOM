package mb;


import entity.Exposition;
import entity.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author reus
 */
@SessionScoped
@ManagedBean(name = "expositionMB")
public class ExpositionMB implements Serializable{

    private Date testDate;
    private List<Exposition> items;   
    private Session session;
    private Date auxDate;
    private Transaction tx;
     
    @PostConstruct
    public void init() {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        items = (ArrayList<Exposition>)findAll(Exposition.class);
        auxDate = new Date();
    }
    
    public List findAll(Class clazz) {
        List objects = null;
        try {
            Query query = session.createQuery("from " + clazz.getName());
            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
        }
        return objects;
    }
    
    public String evaluate() {
        return "/evaluation.xhtml";
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public List<Exposition> getItems() {
        return items;
    }

    public void setItems(List<Exposition> items) {
        this.items = items;
    }

    public Date getAuxDate() {
        return auxDate;
    }

    public void setAuxDate(Date auxDate) {
        this.auxDate = auxDate;
    }

      
}


