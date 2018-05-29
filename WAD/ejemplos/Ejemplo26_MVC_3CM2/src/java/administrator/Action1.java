
package administrator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Action1 extends org.apache.struts.action.Action {
    
    private static final String ADMINISTRATOR = "administrator";
    private static final String FAIL = "fail";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
        ActionForm1 forma = (ActionForm1)form;
        
        if(forma.getParametro1().compareTo("bryan")==0)        
            return mapping.findForward(ADMINISTRATOR);
        else
            return mapping.findForward(FAIL);
    }
}
