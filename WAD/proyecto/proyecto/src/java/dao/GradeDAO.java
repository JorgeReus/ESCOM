package dao;

import entity.Grade;
import org.hibernate.HibernateException;

/**
 *
 * @author Reus Gaming PC
 */
public class GradeDAO extends GenericDAO{

    private static final String FIND_BY_ACTIVITY_USER = "SELECT g FROM Grade g WHERE g.activity.activityId=:activityId AND g.user.userId=:userId";

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

}
