package mb;

import dao.GenericDAO;
import entity.Activity;
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
@ManagedBean(name = "activityMB")
@SessionScoped
public class ActivityMB extends GenericMB implements Serializable{

    private List<Activity> activities;
    private Activity activity;
    private GenericDAO genericDAO;
    
    public ActivityMB () {
        super();
    }   
    
    @PostConstruct
    public void init() {
        activities = new ArrayList<>();
        activity = new Activity();
        genericDAO = new GenericDAO();
    }
    
    public String prepareIndexBySubject(Subject subjectRef) {
        System.out.println(">>>>>>>>" + subjectRef.getSubjectId());
//        activities = (ArrayList<Activity>)genericDAO.findAll(Activity.class);
        return NavigationConstants.MANAGE_ACTIVITIES_INDEX;
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

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    
    
}


