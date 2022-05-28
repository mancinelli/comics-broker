package comicsbroker.volume;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import comicsbroker.image.Image;
import comicsbroker.issue.Issue;
import comicsbroker.publisher.Publisher;
import comicsbroker.util.DateTimeAdapter;

@XmlRootElement
@Entity
@Table(name = "broker_volume")
public class Volume {
	
	@Lob
	@Column(name = "volume_aliases")
	private String volumeAliases;
	
	@Column(name = "volume_api_detail_url")
	private String volumeApiDetailURL;
	
	@Column(name = "volume_count_of_issues")
	private Integer volumeCountOfIssues;
	
	@Column(name = "volume_date_added")
	private Date volumeDateAdded;

	@Column(name = "volume_date_last_updated")
	private Date volumeDateLastUpdated;

	@Lob
	@Column(name = "volume_deck")
	private String volumeDeck;

	@Lob
	@Column(name = "volume_description")
	private String volumeDescription;

	//@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "volume_first_issue", referencedColumnName = "issue_id")
	@Transient
	private Issue volumeFirstIssue;
	
	@Id
	@Column(name = "volume_id")
	private Long volumeID;

	//@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "volume_last_issue", referencedColumnName = "issue_id")
	@Transient
	private Issue volumeLastIssue;

	@Size(max = 256, message = "volume.volumeName.Size")
	@Column(name = "volume_name")
	private String volumeName;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER) 
	@JoinColumn(name = "volume_publisher")
	private Publisher volumePublisher;
	
	@Column(name = "volume_site_detail_url")
	private String volumeSiteDetailURL;

	@Column(name = "volume_start_year")
	private Integer volumeStartYear;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "volume_image", referencedColumnName = "image_id")
	private Image volumeImage;
	
    @OneToMany(mappedBy = "issueVolume", cascade = CascadeType.ALL)
    @OrderBy(value = "issueNumber ASC")
	private Set<Issue> volumeIssues = new HashSet<Issue>();
    
	@Column(name = "volume_force_update")
    private boolean volumeForceUpdate;
	
	@Column(name = "volume_created_from_arc")
    private boolean volumeCreatedFromArc;

	@Transient
	private Integer volumeIssueFilePercent;
	
	@Column(name = "volume_created_on")
	private Date volumeCreatedOn;

	@Column(name = "volume_updated_on")
	private Date volumeUpdatedOn;
	
	@Column(name = "volume_error")
    private boolean volumeError;
	
	@Lob
	@Column(name = "volume_error_reason")
    private String volumeErrorReason;

	@Transient
	private long volumeUpdatedDays;

	@Column(name = "volume_completed")
    private boolean volumeCompleted;

	// GET

	public String getVolumeAliases() {
		return volumeAliases;
	}

	public String getVolumeApiDetailURL() {
		return volumeApiDetailURL;
	}

	public Integer getVolumeCountOfIssues() {
		return volumeCountOfIssues;
	}

	public Date getVolumeDateAdded() {
		return volumeDateAdded;
	}

	public Date getVolumeDateLastUpdated() {
		return volumeDateLastUpdated;
	}

	public String getVolumeDeck() {
		return volumeDeck;
	}

	public String getVolumeDescription() {
		return volumeDescription;
	}

	public Long getVolumeID() {
		return volumeID;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public String getVolumeSiteDetailURL() {
		return volumeSiteDetailURL;
	}

	public Integer getVolumeStartYear() {
		return volumeStartYear;
	}

	public Publisher getVolumePublisher() {
		return volumePublisher;
	}

	public Image getVolumeImage() {
		return volumeImage;
	}

	public Issue getVolumeFirstIssue() {
		return volumeFirstIssue;
	}

	public Issue getVolumeLastIssue() {
		return volumeLastIssue;
	}

	public Set<Issue> getVolumeIssues() {
		return volumeIssues;
	}

	public boolean isVolumeForceUpdate() {
		return volumeForceUpdate;
	}

	public boolean isVolumeCreatedFromArc() {
		return volumeCreatedFromArc;
	}

	public Integer getVolumeIssueFilePercent() {
		return volumeIssueFilePercent;
	}
	
	public Date getVolumeCreatedOn() {
		return volumeCreatedOn;
	}

	public Date getVolumeUpdatedOn() {
		return volumeUpdatedOn;
	}

	public boolean isVolumeError() {
		return volumeError;
	}

	public String getVolumeErrorReason() {
		return volumeErrorReason;
	}

	public boolean isVolumeCompleted() {
		return volumeCompleted;
	}

	public long getVolumeUpdatedDays() {
		if (volumeDateLastUpdated != null && volumeUpdatedOn != null) {
			long diff = volumeUpdatedOn.getTime() - volumeDateLastUpdated.getTime();
		    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} else {
			return -1;
		}
	}

	// SET
	
	@XmlElement(name = "api_detail_url")
	public void setVolumeApiDetailURL(String volumeApiDetailURL) {
		this.volumeApiDetailURL = volumeApiDetailURL;
	}

	@XmlElement(name = "count_of_issues")
	public void setVolumeCountOfIssues(Integer volumeCountOfIssues) {
		this.volumeCountOfIssues = volumeCountOfIssues;
	}

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlElement(name = "date_added")
	public void setVolumeDateAdded(Date volumeDateAdded) {
		this.volumeDateAdded = volumeDateAdded;
	}

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlElement(name = "date_last_updated")
	public void setVolumeDateLastUpdated(Date volumeDateLastUpdated) {
		this.volumeDateLastUpdated = volumeDateLastUpdated;
	}

	@XmlElement(name = "deck")
	public void setVolumeDeck(String volumeDeck) {
		this.volumeDeck = volumeDeck;
	}

	@XmlElement(name = "description")
	public void setVolumeDescription(String volumeDescription) {
		this.volumeDescription = volumeDescription;
	}

	@XmlElement(name = "id")
	public void setVolumeID(Long volumeID) {
		this.volumeID = volumeID;
	}

	@XmlElement(name = "name")
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	@XmlElement(name = "site_detail_url")
	public void setVolumeSiteDetailURL(String volumeSiteDetailURL) {
		this.volumeSiteDetailURL = volumeSiteDetailURL;
	}

	@XmlElement(name = "start_year")
	public void setVolumeStartYear(Integer volumeStartYear) {
		this.volumeStartYear = volumeStartYear;
	}

	@XmlElement(name = "publisher")
	public void setVolumePublisher(Publisher volumePublisher) {
		this.volumePublisher = volumePublisher;
	}

	@XmlElement(name = "image")
	public void setVolumeImage(Image volumeImage) {
		this.volumeImage = volumeImage;
	}

	@XmlElement(name = "first_issue")
	public void setVolumeFirstIssue(Issue volumeFirstIssue) {
		this.volumeFirstIssue = volumeFirstIssue;
	}

	@XmlElement(name = "last_issue")
	public void setVolumeLastIssue(Issue volumeLastIssue) {
		this.volumeLastIssue = volumeLastIssue;
	}

	@XmlElementWrapper(name="issues")
	@XmlElement(name = "issue")
	public void setVolumeIssues(Set<Issue> volumeIssues) {
		this.volumeIssues = volumeIssues;
	}

	public void setVolumeAliases(String volumeAliases) {
		this.volumeAliases = volumeAliases;
	}

	public void setVolumeForceUpdate(boolean volumeForceUpdate) {
		this.volumeForceUpdate = volumeForceUpdate;
	}

	public void setVolumeCreatedFromArc(boolean volumeCreatedFromArc) {
		this.volumeCreatedFromArc = volumeCreatedFromArc;
	}

	public void setVolumeIssueFilePercent(Integer volumeIssueFilePercent) {
		this.volumeIssueFilePercent = volumeIssueFilePercent;
	}

	public void setVolumeCreatedOn(Date volumeCreatedOn) {
		this.volumeCreatedOn = volumeCreatedOn;
	}

	public void setVolumeUpdatedOn(Date volumeUpdatedOn) {
		this.volumeUpdatedOn = volumeUpdatedOn;
	}

	public void setVolumeError(boolean volumeError) {
		this.volumeError = volumeError;
	}

	public void setVolumeErrorReason(String volumeErrorReason) {
		this.volumeErrorReason = volumeErrorReason;
	}

	public void setVolumeCompleted(boolean volumeCompleted) {
		this.volumeCompleted = volumeCompleted;
	}

	//
	
	@PostLoad
	private void onLoad() {
		if (volumeIssues.size() == 0) {
			this.volumeIssueFilePercent = 0;
		} else {
			int issuesWithFile = 0;
			for (Issue issue: volumeIssues) {
				if (issue.getIssueFile() != null) {
					issuesWithFile++;
				}
			}
			this.volumeIssueFilePercent = (issuesWithFile * 100) / volumeIssues.size();
		}
	}

    @Override
    public int hashCode() {
        return Long.hashCode(volumeID);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Volume) {
            if (((Volume) obj).getVolumeID().equals(getVolumeID())) {
                return true;
            }
        }
        return false;
    }

}


