package comicsbroker.VwSeriesCompleteError;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity(name = "vw_seriescomplete_error")
public class VwSeriesCompleteError {
	
	@Id
	private String uuid;

	private String publisher;

	private String series;
	
	private String volume;
	
	private String comicvine_volume;

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




}
