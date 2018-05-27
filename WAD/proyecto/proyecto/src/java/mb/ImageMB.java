package mb;

import dao.GenericDAO;
import entity.Image;
import entity.ImageCategory;
import entity.ImageType;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "imageMB")
@SessionScoped
public class ImageMB extends GenericMB implements Serializable {

    private List<Image> images;
    private Image realImage;
    private UploadedFile file;
    private GenericDAO genericDAO;

    private List<ImageType> imageTypes;
    private List<ImageCategory> imageCategories;

    public ImageMB() {
        super();
    }

    @PostConstruct
    public void init() {
        realImage = new Image();
        ImageType imageType = new ImageType();
        ImageCategory imageCategory = new ImageCategory();
        realImage.setImageCategory(imageCategory);
        realImage.setImageType(imageType);
        genericDAO = new GenericDAO();
        imageTypes = new ArrayList<>();
        imageCategories = new ArrayList<>();
        images = new ArrayList<>();
    }

    @Override
    public String prepareIndex() {
        realImage = new Image();
        ImageType imageType = new ImageType();
        ImageCategory imageCategory = new ImageCategory();
        realImage.setImageCategory(imageCategory);
        realImage.setImageType(imageType);
        images = (ArrayList<Image>) genericDAO.findAll(Image.class);
        return NavigationConstants.MANAGE_IMAGES_INDEX;
    }

    @Override
    public String prepareAdd() {
        imageTypes = (List<ImageType>) genericDAO.findAll(ImageType.class);
        imageCategories = (List<ImageCategory>) genericDAO.findAll(ImageCategory.class);
        return NavigationConstants.MANAGE_IMAGES_ADD;
    }

    @Override
    protected Boolean validateAdd() {
        boolean isValid = Boolean.TRUE;
        if (file == null) {
            addMessage("The file is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (realImage.getImageName().isEmpty()) {
            addMessage("The name is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (realImage.getImageCategory().getCategoryId() == null) {
            addMessage("The category is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        if (realImage.getImageType().getTypeId() == null) {
            addMessage("The type is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
    }

    @Override
    public String add() {
        String redirect = NavigationConstants.MANAGE_IMAGES_ADD;
        if (validateAdd()) {
            realImage.setImage(file.getContents());
            realImage.setImageSize(file.getContents().length);
            if (genericDAO.add(realImage)) {
                addMessage("Succesfully added image", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Can't Upload Image", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }


    @Override
    public String delete() {
        ImageType imageType = new ImageType();
        imageType.setTypeId(realImage.getImageType().getTypeId());
        ImageCategory imageCategory = new ImageCategory();
        imageCategory.setCategoryId(realImage.getImageCategory().getCategoryId());
        realImage.setImageCategory(imageCategory);
        realImage.setImageType(imageType);
        if (genericDAO.delete(realImage)) {
            addMessage("Succesfully Deleted Image", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndex();
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
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            realImage = (Image) genericDAO.findByID(Integer.valueOf(imageId), Image.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(realImage.getImage()));
        }
    }

    public void setRealImage(Image realImage) {
        this.realImage = realImage;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<ImageType> getImageTypes() {
        return imageTypes;
    }

    public void setImageTypes(List<ImageType> imageTypes) {
        this.imageTypes = imageTypes;
    }

    public List<ImageCategory> getImageCategories() {
        return imageCategories;
    }

    public void setImageCategories(List<ImageCategory> imageCategories) {
        this.imageCategories = imageCategories;
    }

    public Image getRealImage() {
        return realImage;
    }

}
