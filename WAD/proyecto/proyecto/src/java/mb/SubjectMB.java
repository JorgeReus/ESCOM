package mb;

import dao.GenericDAO;
import entity.Subject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "subjectMB")
@SessionScoped
public class SubjectMB extends GenericMB implements Serializable{

    private List<Subject> subjects;
    private Subject subject;
    private GenericDAO genericDAO;
    
    public SubjectMB () {
        super();
    }   
    
    @PostConstruct
    public void init() {
        subjects = new ArrayList<>();
        subject = new Subject();
        genericDAO = new GenericDAO();
    }
    
    @Override
    public String prepareIndex() {
        subjects = (ArrayList<Subject>)genericDAO.findAll(Subject.class);
        System.out.println("si");
        return NavigationConstants.MANAGE_SUBJECTS_INDEX;
    }

    @Override
    public String prepareAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    protected Boolean validateAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    public String add() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    public String prepareUpdate() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    protected Boolean validateUpdate() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    public String prepareDelete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    protected Boolean validateDelete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }
    
    @Override
    public String prepareView() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);  
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    

    
    
}


