
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *  Clase que realiza el mapeo de la tabla "groups" mediante anotaciones
 */

@Entity
@Table(name="groups")
public class Group implements Serializable {

    public Group() {
        teacherId = new User();
    }
    
    /*
       Columna que mapea la llave primaria de la tabla
    */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="groupId")
    private Integer groupId;
    
    /*
       Columna que mapea el campo groupName 
    */
    @Column(name="groupName")
    private String groupName;
    
    /*
       Columna que mapea la llave foranea que hace referencia al id del profesor
    asignado al grupo
    */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherId")
    private User teacherId;

    /*
        Seccion de getter y setter de los atributos de la clase
    */
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(User teacherId) {
        this.teacherId = teacherId;
    }
    
    
    
}
