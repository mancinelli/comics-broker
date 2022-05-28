package comicsbroker.arc;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "broker_arc")
public class Arc {
	
	@Lob
	@Column(name = "arc_aliases")
	private String arcAliases;
	
	@Column(name = "arc_api_detail_url")
	private String arcApiDetailURL;
					
	@Column(name = "arc_count_of_issue_appearancess")
	private Integer arcCountOfIssueAppearancess;

	@Column(name = "arc_date_added")
	private Date arcDateAdded;

	@Column(name = "arc_date_last_updated")
	private Date arcDateLastUpdated;

	@Lob
	@Column(name = "arc_deck")
	private String arcDeck;

	@Lob
	@Column(name = "arc_description")
	private String arcDescription;

	//@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "volume_first_issue", referencedColumnName = "issue_id")
	@Transient
	private Issue arcFirstAppearedInIssue;
	
	@Id
	@Column(name = "arc_id")
	private Long arcID;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arc_image", referencedColumnName = "image_id")
	private Image arcImage;
	
	@ManyToMany(mappedBy = "issueArcs")
	@OrderBy(value = "issueCoverDate ASC")
	private Set<Issue> arcIssues = new HashSet<>(); // = new ArrayList<Issue>();

    @Column(name = "arc_name")
	private String arcName;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER) 
	@JoinColumn(name = "arc_publisher")
	private Publisher arcPublisher;

	@Column(name = "arc_site_detail_url")
	private String arcSiteDetailURL;
	
	@Column(name = "arc_force_update")
	private boolean arcForceUpdate;
	
	@Column(name = "arc_force_download")
	private boolean arcForceDownload;

	@Column(name = "arc_publisher_released")
	private boolean arcPublisherReleased;
	
	@Column(name = "arc_created_on")
	private Date arcCreatedOn;
	
	@Column(name = "arc_updated_on")
	private Date arcUpdatedOn;
		
	@Transient
	private Integer arcIssueFilePercent;

	
	// GET

	public String getArcAliases() {
		return arcAliases;
	}

	public String getArcApiDetailURL() {
		return arcApiDetailURL;
	}

	public Integer getArcCountOfIssueAppearancess() {
		return arcCountOfIssueAppearancess;
	}

	public Date getArcDateAdded() {
		return arcDateAdded;
	}

	public Date getArcDateLastUpdated() {
		return arcDateLastUpdated;
	}

	public String getArcDeck() {
		return arcDeck;
	}

	public String getArcDescription() {
		return arcDescription;
	}

	public Issue getArcFirstAppearedInIssue() {
		return arcFirstAppearedInIssue;
	}

	public Long getArcID() {
		return arcID;
	}

	public Image getArcImage() {
		return arcImage;
	}

	public Set<Issue> getArcIssues() {
		return arcIssues;
	}

	public String getArcName() {
		return arcName;
	}

	public Publisher getArcPublisher() {
		return arcPublisher;
	}

	public String getArcSiteDetailURL() {
		return arcSiteDetailURL;
	}

	public boolean isArcForceUpdate() {
		return arcForceUpdate;
	}

	public boolean isArcForceDownload() {
		return arcForceDownload;
	}

	public boolean isArcPublisherReleased() {
		return arcPublisherReleased;
	}

	public Date getArcCreatedOn() {
		return arcCreatedOn;
	}

	public Date getArcUpdatedOn() {
		return arcUpdatedOn;
	}

	public Integer getArcIssueFilePercent() {
		return arcIssueFilePercent;
	}
	
	// SET
	
	@XmlElement(name = "aliases")
	public void setArcAliases(String arcAliases) {
		this.arcAliases = arcAliases;
	}

	@XmlElement(name = "api_detail_url")
	public void setArcApiDetailURL(String arcApiDetailURL) {
		this.arcApiDetailURL = arcApiDetailURL;
	}

	@XmlElement(name = "count_of_issue_appearances	")
	public void setArcCountOfIssueAppearancess(Integer arcCountOfIssueAppearancess) {
		this.arcCountOfIssueAppearancess = arcCountOfIssueAppearancess;
	}

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlElement(name = "date_added")
	public void setArcDateAdded(Date arcDateAdded) {
		this.arcDateAdded = arcDateAdded;
	}

	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlElement(name = "date_last_updated")
	public void setArcDateLastUpdated(Date arcDateLastUpdated) {
		this.arcDateLastUpdated = arcDateLastUpdated;
	}

	@XmlElement(name = "deck")
	public void setArcDeck(String arcDeck) {
		this.arcDeck = arcDeck;
	}

	@XmlElement(name = "description")
	public void setArcDescription(String arcDescription) {
		this.arcDescription = arcDescription;
	}

	public void setArcFirstAppearedInIssue(Issue arcFirstAppearedInIssue) {
		this.arcFirstAppearedInIssue = arcFirstAppearedInIssue;
	}

	@XmlElement(name = "id")
	public void setArcID(Long arcID) {
		this.arcID = arcID;
	}

	@XmlElement(name = "image")
	public void setArcImage(Image arcImage) {
		this.arcImage = arcImage;
	}

	@XmlElementWrapper(name="issues")
	@XmlElement(name = "issue")
	public void setArcIssues(Set<Issue> arcIssues) {
		this.arcIssues = arcIssues;
	}

	@XmlElement(name = "name")
	public void setArcName(String arcName) {
		this.arcName = arcName;
	}

	@XmlElement(name = "publisher")
	public void setArcPublisher(Publisher arcPublisher) {
		this.arcPublisher = arcPublisher;
	}

	@XmlElement(name = "site_detail_url")
	public void setArcSiteDetailURL(String arcSiteDetailURL) {
		this.arcSiteDetailURL = arcSiteDetailURL;
	}

	public void setArcForceUpdate(boolean arcForceUpdate) {
		this.arcForceUpdate = arcForceUpdate;
	}

	public void setArcForceDownload(boolean arcForceDownload) {
		this.arcForceDownload = arcForceDownload;
	}

	public void setArcPublisherReleased(boolean arcPublisherReleased) {
		this.arcPublisherReleased = arcPublisherReleased;
	}

	public void setArcIssueFilePercent(Integer arcIssueFilePercent) {
		this.arcIssueFilePercent = arcIssueFilePercent;
	}

	// 
	
	@PostLoad
	private void onLoad() {
		if (arcIssues.size() == 0) {
			this.arcIssueFilePercent = 0;
		} else {
			int issuesWithFile = 0;
			for (Issue issue: arcIssues) {
				if (issue.getIssueFile() != null) {
					issuesWithFile++;
				}
			}
			this.arcIssueFilePercent = (issuesWithFile * 100) / arcIssues.size();
		}
	}
	
    @Override
    public int hashCode() {
        return Long.hashCode(arcID);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Arc) {
            if (((Arc) obj).getArcID().equals(getArcID())) {
                return true;
            }
        }
        return false;
    }

}


