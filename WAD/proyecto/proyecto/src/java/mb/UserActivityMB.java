package mb;

import dao.GenericDAO;
import entity.Activity;
import entity.Image;
import entity.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.BussinessConstants;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "userActivityMB")
@SessionScoped
public class UserActivityMB extends GenericMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;
    
    private Activity activity;
    private GenericDAO genericDAO;
    private Image realImage;
    private List<Image> selectedImages;
    private User student;

    @PostConstruct
    public void init() {
        activity = new Activity();
        genericDAO = new GenericDAO();
        realImage = new Image();
        selectedImages = new ArrayList<>();
        student = new User();
    }

    @Override
    public String prepareUpdate() {
        String redirect;
        selectedImages = new ArrayList<>();
        realImage = new Image();
        switch (activity.getActivityType().getTypeId()) {
            case BussinessConstants.ACTIVITY_TYPE_IMAGES:
                redirect = NavigationConstants.USER_ACTIVITY_ADD;
                break;
            default:
                redirect = loginMB.gotoHome();
        }
        return redirect;
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            realImage = (Image) genericDAO.findByID(Integer.valueOf(imageId), Image.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(realImage.getImage()));
        }
    }

    public void onImageDrop(DragDropEvent ddEvent) {
        selectedImages.add(((Image) ddEvent.getData()));
    }

    @Override
    protected Boolean validateUpdate() {
        Boolean isValid = Boolean.TRUE;
        if (selectedImages == null) {
            isValid = Boolean.FALSE;
        }
        return isValid;
    }
    
    

    @Override
    public String update() {
        String redirect = NavigationConstants.USER_ACTIVITY_ADD;
        if (validateUpdate()) {
            student.setImages(selectedImages);
            if (student.getActivities() != null) {
                student.getActivities().add(activity);
            } else {
                student.setActivities(Collections.singletonList(activity));
            }
            if (genericDAO.update(student)) {
                addMessage("Succesfully completed exercise", "messages", FacesMessage.SEVERITY_INFO);
                redirect = loginMB.gotoHome();
            } else {
                addMessage("System error", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Image> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(List<Image> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    
}
