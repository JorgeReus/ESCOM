package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import dao.GenericDAO;
import entity.Sound;
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
@ManagedBean(name = "soundMB")
@SessionScoped
public class SoundMB extends GenericMB implements Serializable {

    private ArrayList<Sound> sounds;
    private GenericDAO genericDAO;
    private Sound realSound;
    private UploadedFile file;

    @PostConstruct
    public void init() {
        genericDAO = new GenericDAO();
        realSound = new Sound();
        sounds = new ArrayList<>();
    }

    @Override
    public String prepareIndex() {
        String redirect = NavigationConstants.MANAGE_SOUNDS_INDEX;
        sounds = (ArrayList<Sound>) genericDAO.findAll(Sound.class);
        realSound = new Sound();
        return redirect;
    }

    public StreamedContent getSound() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the Sound. Return a real StreamedContent with the Sound bytes.
            String soundId = context.getExternalContext().getRequestParameterMap().get("soundId");
            Sound sou = (Sound) genericDAO.findByID(Integer.valueOf(soundId), Sound.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(sou.getSound()), "mp3");
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
    }

    @Override
    public String delete() {
        if (genericDAO.delete(realSound)) {
            addMessage("Succesfully Deleted Sound", "messages", FacesMessage.SEVERITY_INFO);
        } else {
            addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
        }
        return prepareIndex();
    }
    
    @Override
    public String prepareAdd() {
        return NavigationConstants.MANAGE_SOUNDS_ADD;
    }

    @Override
    public Boolean validateAdd() {
        boolean isValid = Boolean.TRUE;
        realSound.setSound(file.getContents());
        if (realSound.getSound() == null) {
            addMessage("Sound is Required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        } else if (realSound.getSoundName() == null || realSound.getSoundName().isEmpty()) {
            addMessage("Name is Required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = Boolean.FALSE;
        }
        return isValid;
    }
    
    @Override
    public String add() {
        String redirect = NavigationConstants.MANAGE_SOUNDS_ADD;
        if (validateAdd()) {
            if (genericDAO.add(realSound)) {
                addMessage("Succesfully added Sound", "messages", FacesMessage.SEVERITY_INFO);
                redirect = prepareIndex();
            } else {
                addMessage("Could't add Sound", "messages", FacesMessage.SEVERITY_ERROR);
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public ArrayList<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(ArrayList<Sound> sounds) {
        this.sounds = sounds;
    }

    public Sound getRealSound() {
        return realSound;
    }

    public void setRealSound(Sound realSound) {
        this.realSound = realSound;
    }

    
}
