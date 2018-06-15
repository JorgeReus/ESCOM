package mb;

import dao.ActivityDAO;
import dao.GenericDAO;
import entity.Activity;
import entity.ActivityType;
import entity.Image;
import entity.Question;
import entity.Subject;
import entity.Video;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.BussinessConstants;
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
    private List<Video> videos;
    private List<ActivityType> activityTypes;
    private Activity activity;
    private GenericDAO genericDAO;
    private ActivityDAO activityDAO;
    private UploadedFile file;
    private Video realVideo;
    private List<Question> questions;
    private Subject subject;

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
        activityTypes = (ArrayList<ActivityType>) genericDAO.findAll(ActivityType.class);
        videos = new ArrayList<>();
        realVideo = new Video();
        subject = new Subject();
        questions = new ArrayList<>();
    }

    /**
     * Method that handles the uploaded file asynchronously
     * @param event
     * @throws IOException 
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
    }

    /**
     * Method to add a new question
     */
    public void extendQuestions() {
        questions.add(new Question());
    }

    /**
     * Method to remove a question
     */
    public void removeQuestion() {
        if (questions.size() > 0) {
            questions.remove(questions.size() - 1);
        } else {
            addMessage("Minimum one question", "messages", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    @Override
    public String prepareIndex() {
        return prepareIndexBySubject(subject.getSubjectId());
    }

    /**
     * Method for persisting a video
     * @return 
     */
    public String addVideo() {
        String redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_VIDEO;
        activity.getSubject().setSubjectId(subject.getSubjectId());
        activity.setVideo(file.getContents());
        boolean isValid = Boolean.TRUE;
        // Check question's validity
        for (Question question : questions) {
            if (question.getQuestion() == null || question.getQuestion().trim().isEmpty()) {
                addMessage("Question is Required Field", "messages", FacesMessage.SEVERITY_ERROR);
                isValid = Boolean.FALSE;
                break;
            }
        }
        if (activity.getVideo() == null) {
            addMessage("Field Video is Required", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getActivityName() == null || activity.getActivityName().trim().isEmpty()) {
            addMessage("Field Name is Required", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (isValid) {
            // First add the activity
            Integer newActivityId = (Integer) genericDAO.safeAdd(activity);
            if (newActivityId != null) {
                boolean hasErrors = false;
                Activity newActivity = new Activity();
                newActivity.setActivityId(newActivityId);
                // then the questions related to that activity
                for (Question question : questions) {
                    question.setActivity(newActivity);
                    if (!genericDAO.add(question)) {
                        hasErrors = true;
                        break;
                    }
                }
                if (!hasErrors) {
                    redirect = prepareIndexBySubject(subject.getSubjectId());
                    addMessage("Succesfully Added Activity", "messages", FacesMessage.SEVERITY_INFO);
                } else {
                    addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        return redirect;
    }

    /**
     * Method that adds a sound
     * @return 
     */
    public String addSound() {
        String redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_SOUND;
        activity.getSubject().setSubjectId(subject.getSubjectId());
        activity.setAudio(file.getContents());
        boolean isValid = Boolean.TRUE;
        // Validate questions
        for (Question question : questions) {
            if (question.getQuestion() == null || question.getQuestion().trim().isEmpty()) {
                addMessage("Question is Required Field", "messages", FacesMessage.SEVERITY_ERROR);
                isValid = Boolean.FALSE;
                break;
            }
        }
        if (activity.getAudio() == null) {
            addMessage("Field Audio is Required", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getActivityName() == null || activity.getActivityName().trim().isEmpty()) {
            addMessage("Field Name is Required", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (isValid) {
            // First add the activity
            Integer newActivityId = (Integer) genericDAO.safeAdd(activity);
            if (newActivityId != null) {
                boolean hasErrors = false;
                Activity newActivity = new Activity();
                newActivity.setActivityId(newActivityId);
                for (Question question : questions) {
                    question.setActivity(newActivity);
                    // Then the quesitons related to the activity
                    if (!genericDAO.add(question)) {
                        hasErrors = true;
                        break;
                    }
                }
                if (!hasErrors) {
                    redirect = prepareIndexBySubject(subject.getSubjectId());
                    addMessage("Succesfully Added Activity", "messages", FacesMessage.SEVERITY_INFO);
                } else {
                    addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        return redirect;
    }

    public String addTexts() {
        String redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_TEXTS;
        activity.getSubject().setSubjectId(subject.getSubjectId());
        boolean isValid = Boolean.TRUE;
        // Validate questions
        for (Question question : questions) {
            if (question.getQuestion() == null || question.getQuestion().trim().isEmpty()) {
                addMessage("Question is Required Field", "messages", FacesMessage.SEVERITY_ERROR);
                isValid = Boolean.FALSE;
                break;
            }
        }
        if (activity.getActivityName() == null || activity.getActivityName().trim().isEmpty()) {
            addMessage("Field Name is Required", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (isValid) {
            Integer newActivityId = (Integer) genericDAO.safeAdd(activity);
            if (newActivityId != null) {
                boolean hasErrors = false;
                Activity newActivity = new Activity();
                newActivity.setActivityId(newActivityId);
                for (Question question : questions) {
                    question.setActivity(newActivity);
                    if (!genericDAO.add(question)) {
                        hasErrors = true;
                        break;
                    }
                }
                if (!hasErrors) {
                    redirect = prepareIndexBySubject(subject.getSubjectId());
                    addMessage("Succesfully Added Activity", "messages", FacesMessage.SEVERITY_INFO);
                } else {
                    addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        return redirect;
    }

    public String prepareIndexBySubject(Integer idSubject) {
        images = new ArrayList<>();
        selectedImages = new ArrayList<>();
        activity = new Activity();
        activities = (ArrayList<Activity>) activityDAO.findBySubjectId(idSubject);
        return NavigationConstants.MANAGE_ACTIVITIES_INDEX;
    }

    @Override
    public String prepareAdd() {
        String redirect;
        activity.setImages(new ArrayList<>());
        activity.setVideo(new byte[0]);
        activity.setAudio(new byte[0]);
        // Redirect based on the type of the activity selected
        switch (activity.getActivityType().getTypeId()) {
            case BussinessConstants.ACTIVITY_TYPE_IMAGES:
                images = (ArrayList<Image>) genericDAO.findAll(Image.class);
                redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_IMAGES;
                break;
            case BussinessConstants.ACTIVITY_TYPE_VIDEO:
                questions.add(new Question());
                redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_VIDEO;
                break;
            case BussinessConstants.ACTIVITY_TYPE_SOUND:
                questions.add(new Question());
                redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_SOUND;
                break;
            case BussinessConstants.ACTIVITY_TYPE_TEXT:
                questions.add(new Question());
                redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_TEXTS;
                break;
            default:
                addMessage("Activity Type is a required field", "messages", FacesMessage.SEVERITY_ERROR);
                redirect = NavigationConstants.MANAGE_ACTIVITIES_INDEX;
        }
        return redirect;
    }

    /**
     * Method that retuns the StreamedContent of an image
     * @return
     * @throws IOException 
     */
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

    /**
     * Hook that gets and image for the drag and drop
     * @param ddEvent 
     */
    public void onImageDrop(DragDropEvent ddEvent) {
        selectedImages.add(((Image) ddEvent.getData()));
    }

    /**
     * Method to ad an activity of images
     * @return 
     */
    public String addImages() {
        String redirect = NavigationConstants.MANAGE_ACTIVITIES_ADD_IMAGES;
        activity.setSubject(subject);
        if (selectedImages.isEmpty()) {
            addMessage("Select one image at least", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getActivityName() == null || activity.getActivityName().isEmpty()) {
            addMessage("Activity Name is a required field", "messages", FacesMessage.SEVERITY_ERROR);
        } else if (activity.getSubject().getSubjectId() == null) {
            addMessage("Subject is a required field", "messages", FacesMessage.SEVERITY_ERROR);
        } else {
            activity.setImages(selectedImages);
            if (genericDAO.add(activity)) {
                addMessage("Succesfully added Activity", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndexBySubject(subject.getSubjectId());
            }
        }
        return redirect;
    }

    /**
     * Method that deletes adn activity
     * @return 
     */
    @Override
    public String delete() {
        if (genericDAO.delete(activity)) {
            addMessage("Succesfully Deleted", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("Couldn't not delete activity " + activity.getActivityName(), "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndexBySubject(subject.getSubjectId());
    }

    /**
     * Method that redirects based on the activity type
     * @return 
     */
    @Override
    public String prepareView() {
        String redirect;
        switch (activity.getActivityType().getTypeId()) {
            case BussinessConstants.ACTIVITY_TYPE_IMAGES:
                redirect = NavigationConstants.MANAGE_ACTIVITIES_VIEW_IMAGES;
                break;
            case BussinessConstants.ACTIVITY_TYPE_VIDEO:
                redirect = NavigationConstants.MANAGE_ACTIVITIES_VIEW_VIDEO;
                break;
            case BussinessConstants.ACTIVITY_TYPE_SOUND:
                redirect = NavigationConstants.MANAGE_ACTIVITIES_VIEW_SOUND;
                break;
            case BussinessConstants.ACTIVITY_TYPE_TEXT:
                redirect = NavigationConstants.MANAGE_ACTIVITIES_VIEW_TEXTS;
                break;
            default:
                redirect = NavigationConstants.MANAGE_ACTIVITIES_INDEX;
        }
        return redirect;
    }

    /**
     * Method that returns the StreamedContent of a video
     * @return
     * @throws IOException 
     */
    public StreamedContent getVideo() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the video. Return a real StreamedContent with the video bytes.
            return new DefaultStreamedContent(new ByteArrayInputStream(activity.getVideo()), "video/quicktime");
        }
    }

    /**
     * Method that returns the streamed content of an audio
     * @return
     * @throws IOException 
     */
    public StreamedContent getAudio() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the video. Return a real StreamedContent with the video bytes.
            return new DefaultStreamedContent(new ByteArrayInputStream(activity.getAudio()), "mp3");
        }
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

    public List<ActivityType> getActivityTypes() {
        return activityTypes;
    }

    public void setActivityTypes(List<ActivityType> activityTypes) {
        this.activityTypes = activityTypes;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Video getRealVideo() {
        return realVideo;
    }

    public void setRealVideo(Video realVideo) {
        this.realVideo = realVideo;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

   
    
    

}
