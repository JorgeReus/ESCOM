package dao;

import entity.Activity;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

/**
 *
 * @author Reus Gaming PC
 */
public class ActivityDAO extends GenericDAO{

    private static final String FIND_BY_SUBJECT_ID = "FROM Activity a WHERE a.subject.subjectId=:subjectId";

    public ActivityDAO() {

    }

    public List<Activity> findBySubjectId(Integer subjectId) {
        List<Activity> results;
        try {          
            startOperation();
            results = (List<Activity>) session.createQuery(FIND_BY_SUBJECT_ID)
                    .setParameter("subjectId", subjectId).list();
            for(Activity a : results) {
                Hibernate.initialize(a.getImages());
                Hibernate.initialize(a.getSubject());             
                Hibernate.initialize(a.getActivityType());             
                Hibernate.initialize(a.getVideo());             
                Hibernate.initialize(a.getQuestions());             
            }
            tx.commit();
        } catch (HibernateException e){
            tx.rollback();
            System.err.println(e);
            results = null;
        } finally {
            session.close();
        }
        return results;
    }

}
