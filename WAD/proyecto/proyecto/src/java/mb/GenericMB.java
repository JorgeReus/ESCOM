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

    protected void addMessage(String message, String component, FacesMessage.Severity severity, String... strings) {
        FacesContext.getCurrentInstance().addMessage(component, new FacesMessage(severity, "Incorrect Username and Passowrd",
                "Please enter correct username and Password"));
    }

    public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("user").toString();
    }

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
