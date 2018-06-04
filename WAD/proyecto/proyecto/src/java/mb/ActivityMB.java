package mb;

import dao.ActivityDAO;
import dao.GenericDAO;
import entity.Activity;
import entity.ActivityType;
import entity.Image;
import entity.Subject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "activityMB")
@SessionScoped
public class ActivityMB extends GenericMB implements Serializable {

    private List<Activity> activities;
    private List<Image> images;
    private List<Image> selectedImages;
    private List<Subject> subjects;
    private List<ActivityType> activityTypes;
    private Activity activity;
    private GenericDAO genericDAO;
    private ActivityDAO activityDAO;

    public ActivityMB() {
        super();
    }

    @PostConstruct
    public void init() {
        activities = new ArrayList<>();
        activity = new Activity();
        genericDAO = new GenericDAO();
        activityDAO = new ActivityDAO();
        images = new ArrayList<>();
        selectedImages = new ArrayList<>();
        subjects = new ArrayList<>();
        activityTypes = new ArrayList<>();
    }

    public String prepareIndexBySubject(Integer idSubject) {
        images = new ArrayList<>();
        selectedImages = new ArrayList<>();
        activity = new Activity();
        // Se tiene que cambiar por tema
        subjects = (ArrayList<Subject>) genericDAO.findAll(Subject.class);
        activities = (ArrayList<Activity>) activityDAO.findBySubjectId(idSubject);
        activityTypes = (ArrayList<ActivityType>) genericDAO.findAll(ActivityType.class);
        return NavigationConstants.MANAGE_ACTIVITIES_INDEX;
    }

    @Override
    public String prepareAdd() {
        images = (ArrayList<Image>) genericDAO.findAll(Image.class);
        return NavigationConstants.MANAGE_ACTIVITIES_ADD;
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            Image realImage = (Image) genericDAO.findByID(Integer.valueOf(imageId), Image.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(realImage.getImage()));
        }
    }

    public void onImageDrop(DragDropEvent ddEvent) {
        selectedImages.add(((Image) ddEvent.getData()));
    }

    @Override
    protected Boolean validateAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public String add() {
        String redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD;
        if (selectedImages.isEmpty()) {
            addMessage("Select one image at least", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getActivityName() == null || activity.getActivityName().isEmpty())  {
            addMessage("Activity Name is a required field", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getActivityType().getTypeId() == null) {
            addMessage("Activity Type is a required field", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getSubject().getSubjectId() == null){
            addMessage("Subject is a required field", "messages", FacesMessage.SEVERITY_ERROR);
        } else {
            activity.setImages(selectedImages);
            if (genericDAO.add(activity)) {
                addMessage("Succesfully add Activity", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndexBySubject(1);
            } 
        }
        return redirect;
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
    public String delete() {
        if (genericDAO.delete(activity)) {
            addMessage("Succesfully Deleted", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("Couldn't not delete activity " + activity.getActivityName(), "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndexBySubject(1);
    }

    @Override
    public String prepareView() {
        return NavigationConstants.MANAGE_ACTIVITIES_VIEW;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(List<Image> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<ActivityType> getActivityTypes() {
        return activityTypes;
    }

    public void setActivityTypes(List<ActivityType> activityTypes) {
        this.activityTypes = activityTypes;
    }
    
    
}
