package comicsbroker.VwSeriesComplete;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity(name = "vw_seriescomplete")
public class VwSeriesComplete {
	
	@Id
	private String uuid;

	private String publisher;

	private String series;
	
	private String volume;
	
	private String comicvineVolume;

	private String tags;

	private Long qtd;

	private Long qtd_yes;
	
	@Column(name = "comicvine_volumecheck_lastdate")
	private Date comicvineVolumeCheckLastDate;
	
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

	public String getComicvineVolume() {
		return comicvineVolume;
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

	public Date getComicvineVolumeCheckLastDate() {
		return comicvineVolumeCheckLastDate;
	}


}
