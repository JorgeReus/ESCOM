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
    private static final String FIND_UNANSWERED_BY_USER = "SELECT a FROM Activity a WHERE a NOT IN (SELECT ua FROM User u JOIN u.activities ua WHERE u.userId = :id)";

    public ActivityDAO() {

    }

    /**
     * Method that finds all Activities from a subject
     * @param subjectId
     * @return 
     */
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
    
    public List<Activity> findUnansweredByUser(Integer userId) {
        List<Activity> results;
        try {          
            startOperation();
            results = (List<Activity>) session.createQuery(FIND_UNANSWERED_BY_USER)
                    .setParameter("id", userId).list();
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
