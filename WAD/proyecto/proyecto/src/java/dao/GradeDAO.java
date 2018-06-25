package dao;

import entity.Grade;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

/**
 *
 * @author Reus Gaming PC
 */
public class GradeDAO extends GenericDAO{

    private static final String FIND_BY_ACTIVITY_USER = "SELECT g FROM Grade g WHERE g.activity.activityId=:activityId AND g.user.userId=:userId";
    private static final String FIND_BY_USER = "SELECT g FROM Grade g WHERE g.user.userId=:userId";

    public GradeDAO() {

    }

    public Grade findByActivityUser(Integer activityId, Integer userId) {
        Grade grade;
        try {          
            startOperation();
            grade = (Grade) session.createQuery(FIND_BY_ACTIVITY_USER)
                    .setParameter("activityId", activityId)
                    .setParameter("userId", userId)
                    .uniqueResult();
            tx.commit();
        } catch (HibernateException e){
            tx.rollback();
            System.err.println(e);
            grade = null;
        } finally {
            session.close();
        }
        return grade;
    }
    
    public List<Grade> findByUser(Integer userId) {
        List<Grade> grades;
        try {          
            startOperation();
            grades = (List<Grade>) session.createQuery(FIND_BY_USER)
                    .setParameter("userId", userId)
                    .list();
            for (Grade grade : grades) {
                Hibernate.initialize(grade.getActivity());
                Hibernate.initialize(grade.getUser());
            }
            tx.commit();
        } catch (HibernateException e){
            tx.rollback();
            System.err.println(e);
            grades = null;
        } finally {
            session.close();
        }
        return grades;
    }

}
