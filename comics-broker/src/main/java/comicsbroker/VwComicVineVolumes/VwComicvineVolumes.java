package comicsbroker.VwComicVineVolumes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity(name = "vw_comicvine_volumes")
public class VwComicvineVolumes {
	
	@Id
	private String uuid;

	private String publisher;

	private String series;
	
	private String volume;
	
	@Column(name = "comicvine_volume")
	private String comicvineVolume;

	private Long qtd;
	
	@Column(name = "comicvine_volumecheck_lastdate")
	private Date comicvineVolumecheckLastdate;
	
	@Column(name = "comicvine_volumecheck_error")
	private Boolean comicvineVolumecheckError;
	
	@Lob 
	@Column(name = "comicvine_volumecheck_message")
	private String comicvineVolumecheckMessage;

	@Column(name = "dsclub_url")
	private String dsclubURL;

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

	public Long getQtd() {
		return qtd;
	}

	public Date getComicvineVolumecheckLastdate() {
		return comicvineVolumecheckLastdate;
	}

	public Boolean getComicvineVolumecheckError() {
		return comicvineVolumecheckError;
	}

	public String getComicvineVolumecheckMessage() {
		return comicvineVolumecheckMessage;
	}

	public String getDsclubURL() {
		return dsclubURL;
	}
	


}
