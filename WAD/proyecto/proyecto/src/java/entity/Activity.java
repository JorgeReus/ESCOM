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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author reus
 */
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activityId")
    private Integer activityId;

    @Column(name = "activityName")
    private String activityName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId")
    private Subject subject;

    @ManyToMany
    @JoinTable(
            name = "activity_image",
            joinColumns = {
                @JoinColumn(name = "activityId")},
            inverseJoinColumns = {
                @JoinColumn(name = "imageId")}
    )
    
    private List<Image> images;
    
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    
}
