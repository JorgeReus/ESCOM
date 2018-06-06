package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import dao.GenericDAO;
import entity.Image;
import entity.Video;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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

    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        videos = new ArrayList<>();
    }


    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_VIDEOS_INDEX;
        videos = (ArrayList<Video>) genericDAO.findAll(Video.class);
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
            realVideo = (Video) genericDAO.findByID(Integer.valueOf(videoId), Video.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(realVideo.getVideo()), "video/quicktime");
        }
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

   

}
