/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Reus Gaming PC
 */
public class LoginAction extends org.apache.struts.action.Action {

    private final static String SUCCESS = "success";
    private final static String FAILURE = "failure";
    private Integer tries = 3;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginForm loginForm = (LoginForm) form;
        LoginBean loginBean = new LoginBean();
        if (tries <= 0) {
            request.getServletContext().log(loginForm.getUserName() + "," + loginForm.getPassword());
        }
        if (loginBean.validate(loginForm.getUserName(), loginForm.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", loginForm.getUserName());
            session.setAttribute("type", loginBean.getType(loginForm.getUserName()));
            session.setAttribute("users", loginBean.getUsers());
            return mapping.findForward(SUCCESS);
        } else {
            tries--;
            return mapping.findForward(FAILURE);
        }
    }
}
