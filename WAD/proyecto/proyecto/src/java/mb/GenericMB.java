package mb;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Reus Gaming PC
 */
public class GenericMB {

    protected final static String NOT_SUPPORTED = "Not Supported";

    /**
     * Generic method to add a message for the faces context
     * @param message
     * @param component
     * @param severity 
     */
    protected void addMessage(String message, String component, FacesMessage.Severity severity) {
        FacesMessage m = new FacesMessage();
        String summary;
        m.setSeverity(severity);
        if (severity.equals(FacesMessage.SEVERITY_ERROR)) {
            summary = "Error!, ";
        } else {
            summary = "Info: ";
        }
        m.setSummary(summary);
        m.setDetail(message);
        FacesContext.getCurrentInstance().addMessage(component, m);
    }

    /**
     * Method that returns the httpSession
     * @return 
     */
    public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    /**
     * Method that returns the request
     * @return 
     */
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    /**
     * Method that returns the userName
     * @return 
     */
    public String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("user").toString();
    }

    /**
     * Methods that returns the userId
     * @return 
     */
    public String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userId");
        } else {
            return null;
        }
    }

    public String prepareAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    protected Boolean validateAdd() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String add() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String prepareUpdate() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    protected Boolean validateUpdate() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String update() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String prepareDelete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    protected Boolean validateDelete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String delete() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String prepareView() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    public String prepareIndex() {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

}
