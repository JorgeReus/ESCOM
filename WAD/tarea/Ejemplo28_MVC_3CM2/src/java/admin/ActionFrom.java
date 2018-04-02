package admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author reus
 */
public class ActionFrom extends org.apache.struts.action.ActionForm {
    private String parametro1;
    public ActionFrom() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getParametro1() {
        return parametro1;
    }

    public void setParametro1(String parametro1) {
        this.parametro1 = parametro1;
    }
    
    
}
