package entity;

/**
 *
 * @author reus
 */
public class UserType {
    private int typeId;
    private String typeName;
        
    public UserType() {
    }
    
    public UserType(int typeId) {
        this.typeId = typeId;
    }

    public UserType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
     
    
}
