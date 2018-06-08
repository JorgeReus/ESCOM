package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import dao.GenericDAO;
import entity.Video;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "videoMB")
@SessionScoped
public class VideoMB extends GenericMB implements Serializable {

    private ArrayList<Video> videos;
    private GenericDAO genericDAO;
    private Video realVideo;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        realVideo = new Video();
        videos = new ArrayList<>();
    }

    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_VIDEOS_INDEX;
        videos = (ArrayList<Video>) genericDAO.findAll(Video.class);
        realVideo = new Video();
        return redirect;
    }

    public StreamedContent getVideo() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the video. Return a real StreamedContent with the video bytes.
            String videoId = context.getExternalContext().getRequestParameterMap().get("videoId");
            Video vid = (Video) genericDAO.findByID(Integer.valueOf(videoId), Video.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(vid.getVideo()), "video/quicktime");
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
    }

    @Override
    public String delete() {
        if (genericDAO.delete(realVideo)) {
            addMessage("Succesfully Deleted Video", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndex();
    }
    
    @Override
    public String prepareAdd() {
        return NavigationConstants.MANAGE_VIDEOS_ADD;
    }

    @Override
    public Boolean validateAdd() {
        boolean isValid = Boolean.TRUE;
        realVideo.setVideo(file.getContents());
        if (realVideo.getVideo() == null) {
            addMessage("Video is Required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        } else if (realVideo.getVideoName() == null || realVideo.getVideoName().isEmpty()) {
            addMessage("Name is Required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }
    
    @Override
    public String add() {
        String redirect = NavigationConstants.MANAGE_VIDEOS_ADD;
        if (validateAdd()) {
            if (genericDAO.add(realVideo)) {
                addMessage("Succesfully added video", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Could't add video", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }

    public GenericDAO getGenericDAO() {
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public Video getRealVideo() {
        return realVideo;
    }

    public void setRealVideo(Video realVideo) {
        this.realVideo = realVideo;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
