package com.escom;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;

public class Body extends BodyTagSupport
{
    protected String elemSep =  ",";
    protected String lineSep = ";";

    public int doEndTag() throws JspException
    {
       String bodyText = bodyContent.getString();

       try {
        pageContext.getOut().print(bodyText);
       }
       catch (Exception e) {
        throw new JspTagException(e.getMessage());
       }

       StringBuffer tableOut = new StringBuffer();
       StringTokenizer bodyTk = new StringTokenizer(bodyText, lineSep, false);

       tableOut.append("<TABLE>");
       while (bodyTk.hasMoreTokens())
       {
           String str = (String)bodyTk.nextToken();
           StringTokenizer token = new StringTokenizer(str, elemSep, false);
           tableOut.append("<TR>");
           while(token.hasMoreTokens())
           {
                tableOut.append("<TD>");
                tableOut.append((String)token.nextToken());
                tableOut.append("</TD>");
           }
           tableOut.append("</TR>");
       }
      tableOut.append("</TABLE>");

      try {
        pageContext.getOut().print(tableOut);
       }
       catch (Exception e) {
        throw new JspTagException(e.getMessage());
       }
       return EVAL_PAGE;
    }

    public void release()
    {
       elemSep = ",";
       lineSep = ";";
    }

}
