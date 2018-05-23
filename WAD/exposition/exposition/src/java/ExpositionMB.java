
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author reus
 */
@SessionScoped
@ManagedBean(name = "expositionMB")
public class ExpositionMB implements Serializable{

    private Date testDate;
    
    
    @PostConstruct
    public void init() {
        
    }
    
    public String evaluate() {
        return "/evaluation.xhtml";
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    
    
}


