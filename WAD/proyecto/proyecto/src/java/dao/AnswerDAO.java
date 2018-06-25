package dao;

import entity.Answer;
import org.hibernate.HibernateException;

/**
 *
 * @author Reus Gaming PC
 */
public class AnswerDAO extends GenericDAO {

    private static final String FIND_BY_QUESTION_ID_USER_ID = "FROM Answer a WHERE a.question.questionId=:questionId AND a.user.userId=:userId";

    public AnswerDAO() {

    }

    public Answer findByQuestionAndUser(Integer questionId, Integer userId) {
        Answer result;
        try {
            startOperation();
            result = (Answer) session.createQuery(FIND_BY_QUESTION_ID_USER_ID)
                    .setParameter("questionId", questionId)
                    .setParameter("userId", userId)
                    .uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.err.println(e);
            result = null;
        } finally {
            session.close();
        }
        return result;
    }

}
