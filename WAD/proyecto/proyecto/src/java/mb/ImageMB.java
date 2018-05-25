package mb;

import dao.GenericDAO;
import entity.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "imageMB")
@SessionScoped
public class ImageMB extends GenericMB implements Serializable{

    private List<Image> images;
    private Image image;
    private GenericDAO genericDAO;
    
    public ImageMB () {
        super();
    }   
    
    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();   
    }
    
    @Override
    public String prepareIndex() {
        images = (ArrayList<Image>)genericDAO.findAll(Image.class);
        return NavigationConstants.MANAGE_IMAGES_INDEX;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            image = (Image)genericDAO.findByID(Integer.valueOf(imageId), Image.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getImage()));
        }
    }
    

    
    
}


