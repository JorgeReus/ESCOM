package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author reus
 */
@Entity
@Table(name = "image")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "imageId")
    private Integer imageId;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "image")  
    @Lob
    private byte[] image;
    
    @Column(name = "imageSize")
    private Integer imageSize;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageCategory")
    private ImageCategory imageCategory;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageType")
    private ImageType imageType;
    
    @ManyToMany(mappedBy = "images")
    private List<Activity> activities;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    public ImageCategory getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(ImageCategory imageCategory) {
        this.imageCategory = imageCategory;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    
}
