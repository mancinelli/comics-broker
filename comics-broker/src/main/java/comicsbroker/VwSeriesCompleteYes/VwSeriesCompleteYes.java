package comicsbroker.VwSeriesCompleteYes;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity(name = "vw_seriescomplete_yes")
public class VwSeriesCompleteYes {
	
	@Id
	private String uuid;

	private String publisher;

	private String series;
	
	private String volume;
	
	private String comicvine_volume;

	private String tags;

	private Long qtd;

	private Long qtd_yes;

	public String getUuid() {
		return uuid;
	}
	
	public String getPublisher() {
		return publisher;
	}

	public String getSeries() {
		return series;
	}

	public String getVolume() {
		return volume;
	}

	public String getComicvine_volume() {
		return comicvine_volume;
	}

	public String getTags() {
		return tags;
	}

	public Long getQtd() {
		return qtd;
	}

	public Long getQtd_yes() {
		return qtd_yes;
	}




}
