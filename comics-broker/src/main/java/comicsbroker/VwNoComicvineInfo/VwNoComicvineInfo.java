package comicsbroker.VwNoComicvineInfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity(name = "vw_no_comicvine_info")
public class VwNoComicvineInfo {
	
	@Id
	@Column(name="comicbook_id")
	private String comicbookID; 

	private String file;
	
	private String title;
	
	private String series;
	
	private String number;
	
	private String volume;
	
	@Lob 
	private String summary;
	
	@Lob 
	private String notes;
	
	private String year;
	
	private String month;
	
	private String publisher;
	
	private String web;
	
	private String pagecount;
	
	@Lob 
	private String characters;
	
	private Date added;
	
	private String tags;
	
	private String seriescomplete;
	
	@Lob 
	private String custom_values_store;
	
	private String comicvine_issue;

	private String comicvine_volume;

	public String getComicbookID() {
		return comicbookID;
	}

	public String getFile() {
		return file;
	}

	public String getTitle() {
		return title;
	}

	public String getSeries() {
		return series;
	}

	public String getNumber() {
		return number;
	}

	public String getVolume() {
		return volume;
	}

	public String getSummary() {
		return summary;
	}

	public String getNotes() {
		return notes;
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getWeb() {
		return web;
	}

	public String getPagecount() {
		return pagecount;
	}

	public String getCharacters() {
		return characters;
	}

	public Date getAdded() {
		return added;
	}

	public String getTags() {
		return tags;
	}

	public String getSeriescomplete() {
		return seriescomplete;
	}

	public String getCustom_values_store() {
		return custom_values_store;
	}

	public String getComicvine_issue() {
		return comicvine_issue;
	}

	public String getComicvine_volume() {
		return comicvine_volume;
	}

	

}
