package comicsbroker.publisher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "broker_publisher")
public class Publisher {
	
	@Id
	@Column(name = "publisher_id")
	private Long publisherID;
	
	@Column(name = "publisher_name")
	private String publisherName;

	@Column(name = "publisher_released")
	private boolean publisherReleased;

	// add other properties
	// add volumes property
	// add arcs property

	// GET
	
	public Long getPublisherID() {
		return publisherID;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public boolean isPublisherReleased() {
		return publisherReleased;
	}

	// SET
	
	@XmlElement(name = "id")
	public void setPublisherID(Long publisherID) {
		this.publisherID = publisherID;
	}

	@XmlElement(name = "name")
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	public void setPublisherReleased(boolean publisherReleased) {
		this.publisherReleased = publisherReleased;
	}

	//
	
    @Override
    public int hashCode() {
        return Long.hashCode(publisherID);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Publisher) {
            if (((Publisher) obj).getPublisherID().equals(getPublisherID())) {
                return true;
            }
        }
        return false;
    }

}


