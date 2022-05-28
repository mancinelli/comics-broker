package comicsbroker.comics;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name = "broker_comic")
public class BrokerComics {
	
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

	public void setComicbookID(String comicbookID) {
		this.comicbookID = comicbookID;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getPagecount() {
		return pagecount;
	}

	public void setPagecount(String pagecount) {
		this.pagecount = pagecount;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSeriescomplete() {
		return seriescomplete;
	}

	public void setSeriescomplete(String seriescomplete) {
		this.seriescomplete = seriescomplete;
	}

	public String getCustom_values_store() {
		return custom_values_store;
	}

	public void setCustom_values_store(String custom_values_store) {
		this.custom_values_store = custom_values_store;
	}

	public String getComicvine_issue() {
		return comicvine_issue;
	}

	public void setComicvine_issue(String comicvine_issue) {
		this.comicvine_issue = comicvine_issue;
	}

	public String getComicvine_volume() {
		return comicvine_volume;
	}

	public void setComicvine_volume(String comicvine_volume) {
		this.comicvine_volume = comicvine_volume;
	}

}
