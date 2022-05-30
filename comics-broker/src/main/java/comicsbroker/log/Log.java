package comicsbroker.log;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "broker_log")
public class Log {
	
	public static final String LOG_LEVEL_INFO = "INFO";
	public static final String LOG_LEVEL_WARN = "WARN";
	public static final String LOG_LEVEL_ERROR = "ERROR";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Long logID;

	@Column(name = "log_date")
	private Date logDate;
	
	@Column(name = "log_level")
	private String logLevel;
	
	@Column(name = "log_class")
	private String logclass;
	
	@Column(name = "log_method")
	private String logMethod;

	@Column(name = "log_publisher")
	private String logPublisher;

	@Column(name = "log_publisher_comicvine")
	private String logPublisherComicVine;

	@Column(name = "log_series")
	private String logSeries;

	@Column(name = "log_volume")
	private String logVolume;

	@Column(name = "log_volume_comicvine")
	private String logVolumeComicVine;

	@Column(name = "log_issue_number")
	private String logIssueNumber;

	@Column(name = "log_issue_comicvine")
	private String logIssueComicVine;

	@Lob
	@Column(name = "log_message")
	private String logMessage;

	public Log () {
		logDate	= new Date();
	}
			
	public Log (String sLevel, 
				String sClass, 
				String sMethod, 
				String sMessage,
				String sPublisher, 
				String sPublisherComicVine, 
				String sSeries, 
				String sVolume, 
				String sVolumeComicVine, 
				String sIssueNumber, 
				String sIssueComicVine) {
		logDate					= new Date();
		logLevel 				= sLevel;
		logclass 				= sClass;
		logMethod 				= sMethod;
		logMessage 				= sMessage;
		logPublisher 			= sPublisher;
		logPublisherComicVine 	= sPublisherComicVine;
		logSeries		 		= sSeries;
		logVolume 				= sVolume;
		logVolumeComicVine 		= sVolumeComicVine;
		logIssueNumber	 		= sIssueNumber;
		logIssueComicVine 		= sIssueComicVine;
	}
	
	public Long getLogID() {
		return logID;
	}

	public void setLogID(Long logID) {
		this.logID = logID;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogclass() {
		return logclass;
	}

	public void setLogclass(String logclass) {
		this.logclass = logclass;
	}

	public String getLogMethod() {
		return logMethod;
	}

	public void setLogMethod(String logMethod) {
		this.logMethod = logMethod;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	
	public String getLogPublisher() {
		return logPublisher;
	}

	public void setLogPublisher(String logPublisher) {
		this.logPublisher = logPublisher;
	}

	public String getLogSeries() {
		return logSeries;
	}

	public void setLogSeries(String logSeries) {
		this.logSeries = logSeries;
	}
	
	public String getLogVolume() {
		return logVolume;
	}

	public void setLogVolume(String logVolume) {
		this.logVolume = logVolume;
	}

	public String getLogPublisherComicVine() {
		return logPublisherComicVine;
	}

	public void setLogPublisherComicVine(String logPublisherComicVine) {
		this.logPublisherComicVine = logPublisherComicVine;
	}

	public String getLogVolumeComicVine() {
		return logVolumeComicVine;
	}

	public void setLogVolumeComicVine(String logVolumeComicVine) {
		this.logVolumeComicVine = logVolumeComicVine;
	}

	public String getLogIssueComicVine() {
		return logIssueComicVine;
	}

	public void setLogIssueComicVine(String logIssueComicVine) {
		this.logIssueComicVine = logIssueComicVine;
	}

	public String getLogIssueNumber() {
		return logIssueNumber;
	}

	public void setLogIssueNumber(String logIssueNumber) {
		this.logIssueNumber = logIssueNumber;
	}

}


