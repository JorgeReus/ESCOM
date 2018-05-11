package mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "userMB")
@SessionScoped
public class UserMB extends GenericMB implements Serializable{

    public UserMB () {
        super();
    }   
    
    @PostConstruct
    public void init() {
        // Inicializar objetos
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


    @Override
    public String prepareIndex() {
        throw new UnsupportedOperationException(NOT_SUPPORTED); 
    }
    
}


