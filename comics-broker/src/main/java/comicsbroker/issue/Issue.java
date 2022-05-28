package comicsbroker.issue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import comicsbroker.arc.Arc;
import comicsbroker.image.Image;
import comicsbroker.util.DateTimeAdapter;
import comicsbroker.volume.Volume;

@XmlRootElement
@Entity
@Table(name = "broker_issue")
public class Issue {
	
	// deprecated
	public static Integer ISSUE_STATUS_UNREAD = -1;
	public static Integer ISSUE_STATUS_READ_PARTIAL = 0;
	public static Integer ISSUE_STATUS_READ_COMPLETE = 1;
	
	public static ArrayList<String> ISSUE_FILE_EXTENSION_SUPPORTED = new ArrayList<String> (Arrays.asList("cbr", "rar", "cbz", "zip", "pdf"));

	@Lob
	@Column(name = "issue_aliases")
	private String issueAliases;
	
	@Column(name = "issue_api_detail_url")
	private String issueApiDetailURL;
	
	@Column(name = "issue_cover_date")
	private String issueCoverDate;

	@Column(name = "issue_date_added")
	private Date issueDateAdded;

	@Column(name = "issue_date_last_updated")
	private Date issueDateLastUpdated;

	@Lob
	@Column(name = "issue_deck")
	private String issueDeck;

	@Lob
	@Column(name = "issue_description")
	private String issueDescription;

	@Id
	@Column(name = "issue_id")
	private Long issueID;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_image", referencedColumnName = "image_id")
	private Image issueImage;

	@Column(name = "issue_number")
	private String issueNumber;

	//@Size(max = 512, message = "issue.issueName.Size")
	@Column(name = "issue_name")
	private String issueName;
	
	@Column(name = "issue_site_detail_url")
	private String issueSiteDetailURL;

	@Column(name = "issue_status")
	private Integer issueStatus;

	@Column(name = "issue_store_date")
	private String issueStoreDate;

	@ManyToOne 
	@JoinColumn(name = "issue_volume")
	private Volume issueVolume;
	
	@Column(name = "issue_file")
	private String issueFile;
	
	@Column(name = "issue_file_extension")
	private String issueFileExtension;

	@Column(name = "issue_file_size")
	private Long issueFileSize;

	@Column(name = "issue_file_original")
	private String issueFileOriginal;

	@Column(name = "issue_force_update")
	private boolean issueForceUpdate;
	
	@Column(name = "issue_created_from_arc")
    private boolean issueeCreatedFromArc;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "broker_arcissue", joinColumns = @JoinColumn(name = "arcissue_issue"), inverseJoinColumns = @JoinColumn(name = "arcissue_arc"))
	Set<Arc> issueArcs = new HashSet<>(); // = new ArrayList<Arc>(); 
    
	@Column(name = "issue_created_on")
	private Date issueCreatedOn;
	
	@Column(name = "issue_updated_on")
	private Date issueUpdatedOn;

	@Transient
	private long issueUpdatedDays;

	// GET

	public String getIssueAliases() {
		return issueAliases;
	}

	public String getIssueApiDetailURL() {
		return issueApiDetailURL;
	}

	public String getIssueCoverDate() {
		return issueCoverDate;
	}

	public Date getIssueDateAdded() {
		return issueDateAdded;
	}

	public Date getIssueDateLastUpdated() {
		return issueDateLastUpdated;
	}

	public String getIssueDeck() {
		return issueDeck;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public Long getIssueID() {
		return issueID;
	}
	
	public Image getIssueImage() {
		return issueImage;
	}
	
	public String getIssueNumber() {
		return issueNumber;
	}

	public String getIssueName() {
		return issueName;
	}

	public String getIssueLongName() {
		if (issueVolume == null || issueNumber == null) {
			return ""; 
			
		} else {
			if (issueVolume.getVolumeName() == null || issueVolume.getVolumeStartYear() == null) {
				return ""; 
				
			} else {
				return 
						issueVolume.getVolumeName()
						.concat(" (").concat(issueVolume.getVolumeStartYear().toString()).concat(")")
						.concat(" #").concat(issueNumber);
			}
		}
	}

	public String getIssueSiteDetailURL() {
		return issueSiteDetailURL;
	}

	public Integer getIssueStatus() {
		return issueStatus;
	}

	public String getIssueStoreDate() {
		return issueStoreDate;
	}

	public Volume getIssueVolume() {
		return issueVolume;
	}

	public String getIssueFile() {
		return issueFile;
	}

	public String getIssueFileExtension() {
		return issueFileExtension;
	}

	public Long getIssueFileSize() {
		return issueFileSize;
	}

	public String getIssueFileOriginal() {
		return issueFileOriginal;
	}

	public boolean isIssueForceUpdate() {
		return issueForceUpdate;
	}

	public boolean isIssueeCreatedFromArc() {
		return issueeCreatedFromArc;
	}

	public Set<Arc> getIssueArcs() {
		return issueArcs;
	}
	
	public Date getIssueCreatedOn() {
		return issueCreatedOn;
	}

	public Date getIssueUpdatedOn() {
		return issueUpdatedOn;
	}

	public long getIssueUpdatedDays() {
		if (issueDateLastUpdated != null && issueUpdatedOn != null) {
			long diff = issueUpdatedOn.getTime() - issueDateLastUpdated.getTime();
		    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} else {
			return -1;
		}
	}

	// SET
	
	@XmlElement(name = "aliases")
	public void setIssueAliases(String issueAliases) {
		this.issueAliases = issueAliases;
	}

	@XmlElement(name = "api_detail_url")
	public void setIssueApiDetailURL(String issueApiDetailURL) {
		this.issueApiDetailURL = issueApiDetailURL;
	}

	@XmlElement(name = "cover_date")
	public void setIssueCoverDate(String issueCoverDate) {
		this.issueCoverDate = issueCoverDate;
	}

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlElement(name = "date_added")
	public void setIssueDateAdded(Date issueDateAdded) {
		this.issueDateAdded = issueDateAdded;
	}

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlElement(name = "date_last_updated")
	public void setIssueDateLastUpdated(Date issueDateLastUpdated) {
		this.issueDateLastUpdated = issueDateLastUpdated;
	}

	@XmlElement(name = "deck")
	public void setIssueDeck(String issueDeck) {
		this.issueDeck = issueDeck;
	}

	@XmlElement(name = "description")
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	@XmlElement(name = "id")
	public void setIssueID(Long issueID) {
		this.issueID = issueID;
	}

	@XmlElement(name = "image")
	public void setIssueImage(Image issueImage) {
		this.issueImage = issueImage;
	}
	
	@XmlElement(name = "issue_number")
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	@XmlElement(name = "name")
	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	@XmlElement(name = "site_detail_url")
	public void setIssueSiteDetailURL(String issueSiteDetailURL) {
		this.issueSiteDetailURL = issueSiteDetailURL;
	}

	public void setIssueStatus(Integer issueStatus) {
		this.issueStatus = issueStatus;
	}

	@XmlElement(name = "store_date")
	public void setIssueStoreDate(String issueStoreDate) {
		this.issueStoreDate = issueStoreDate;
	}

	@XmlElement(name = "volume")
	public void setIssueVolume(Volume issueVolume) {
		this.issueVolume = issueVolume;
	}

	public void setIssueFile(String issueFile) {
		this.issueFile = issueFile;
	}

	public void setIssueFileExtension(String issueFileExtension) {
		this.issueFileExtension = issueFileExtension;
	}

	public void setIssueFileSize(Long issueFileSize) {
		this.issueFileSize = issueFileSize;
	}

	public void setIssueFileOriginal(String issueFileOriginal) {
		this.issueFileOriginal = issueFileOriginal;
	}

	public void setIssueForceUpdate(boolean issueForceUpdate) {
		this.issueForceUpdate = issueForceUpdate;
	}

	public void setIssueeCreatedFromArc(boolean issueeCreatedFromArc) {
		this.issueeCreatedFromArc = issueeCreatedFromArc;
	}
	
	@XmlElementWrapper(name="story_arc_credits")
	@XmlElement(name = "story_arc")
	public void setIssueArcs(Set<Arc> issueArcs) {
		this.issueArcs = issueArcs;
	}
	
	//
	
    @Override
    public int hashCode() {
        return Long.hashCode(issueID);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Issue) {
            if (((Issue) obj).getIssueID().equals(getIssueID())) {
                return true;
            }
        }
        return false;
    }

}


