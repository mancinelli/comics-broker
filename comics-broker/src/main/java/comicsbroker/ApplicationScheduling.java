package comicsbroker;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import comicsbroker.VwNoComicvineInfo.VwNoComicvineInfo;
import comicsbroker.VwNoComicvineInfo.VwNoComicvineInfoRepository;
import comicsbroker.VwSeriesCompleteError.VwSeriesCompleteError;
import comicsbroker.VwSeriesCompleteError.VwSeriesCompleteErrorRepository;
import comicsbroker.VwSeriesCompleteYes.VwSeriesCompleteYes;
import comicsbroker.VwSeriesCompleteYes.VwSeriesCompleteYesRepository;
import comicsbroker.comics.BrokerComics;
import comicsbroker.comics.BrokerComicsRepository;
import comicsbroker.issue.Issue;
import comicsbroker.log.Log;
import comicsbroker.log.LogRepository;
import comicsbroker.volume.Volume;
import comicsbroker.volume.VolumeResponse;

@Configuration
@EnableScheduling
@EnableAsync
public class ApplicationScheduling {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private static final String className = "ApplicationScheduling";

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private VwNoComicvineInfoRepository vwNoComicvineInfoRepository;

	@Autowired
	private VwSeriesCompleteErrorRepository vwSeriesCompleteErrorRepository;

	@Autowired
	private VwSeriesCompleteYesRepository vwSeriesCompleteYesRepository;

	@Autowired
	private BrokerComicsRepository brokerComicsRepository;

	@Scheduled(fixedRate = 10000)
	void check() {
		
		logger.info("check() running at : " + new Date());
		
		checkComicsWithNotComicvineInfo();

		checkComicsWithSeriesCompleteError();

		checkComicsSeriesCompleteYesOnComicVineApi();
		
	}
	
	/*
	 * valid with addLog...
	 */
	public boolean checkComicsWithNotComicvineInfo() {
		logger.debug("checkComicsWithNotComicvineInfo() running at : " + new Date());
		List<VwNoComicvineInfo> vwNoComicvineInfoList = vwNoComicvineInfoRepository.findAll();
		if (vwNoComicvineInfoList.size() > 0) {
			for (VwNoComicvineInfo vwNoComicvineInfo : vwNoComicvineInfoList) {
				String sMessage = "Comics volume [" +  vwNoComicvineInfo.getComicvine_volume() + "] with not comicvine info. [vw_seriescomplete_error]";
				Log log = new Log(
						Log.LOG_LEVEL_ERROR, 
						this.getClass().getName(),
						new Object(){}.getClass().getEnclosingMethod().getName(),
						sMessage,
						vwNoComicvineInfo.getPublisher(),
						"",
						vwNoComicvineInfo.getSeries(),
						vwNoComicvineInfo.getVolume(),
						vwNoComicvineInfo.getComicvine_volume(),
						"",
						"");
				addLog(log);
				updateVolumeComics(vwNoComicvineInfo.getComicvine_volume(), true, sMessage);
				
			}
			return false;
		} else {
			logger.info("     [OK]");
			return true;
		}
	}

	/*
	 * valid addLog...
	 */
	public boolean checkComicsWithSeriesCompleteError() {
		logger.debug("checkComicsWithSeriesCompleteError() running at : " + new Date());
		List<VwSeriesCompleteError> vwSeriesCompleteErrorList = vwSeriesCompleteErrorRepository.findAll();
		if (vwSeriesCompleteErrorList.size() > 0) {
			logger.warn("checkComicsWithSeriesCompleteError [found: " + vwSeriesCompleteErrorList.size() +"]");
			for (VwSeriesCompleteError vwSeriesCompleteError : vwSeriesCompleteErrorList) {

				String sMessage = "Comics with series complete error. [vw_seriescomplete_error]";
				Log log = new Log(
						Log.LOG_LEVEL_ERROR, 
						this.getClass().getName(),
						new Object(){}.getClass().getEnclosingMethod().getName(),
						sMessage,
						vwSeriesCompleteError.getPublisher(),
						"",
						vwSeriesCompleteError.getSeries(),
						vwSeriesCompleteError.getVolume(),
						vwSeriesCompleteError.getComicvine_volume(),
						"",
						"");
				addLog(log);
				updateVolumeComics(vwSeriesCompleteError.getComicvine_volume(), true, sMessage);
			
			}
			return false;
		} else {
			logger.info("checkComicsWithSeriesCompleteError [OK]");
			return true;
		}
	}
	
	/*
	 * 
	 */
	public void checkComicsSeriesCompleteYesOnComicVineApi() {
		
		logger.debug("checkComicsSeriesCompleteYesOnComicVineApi() running at : " + new Date());
		
		List<VwSeriesCompleteYes> vwSeriesCompleteYesList = vwSeriesCompleteYesRepository.findAll();
		Integer vwSeriesCompleteYesListCount = vwSeriesCompleteYesList.size();
		Integer vwSeriesCompleteYesCount = 0;
		for (VwSeriesCompleteYes vwSeriesCompleteYes : vwSeriesCompleteYesList) {
			
        	try {

        		vwSeriesCompleteYesCount++;
    			logger.info("Cheking volume [{}] of [{}]. [{}][{}][{}]", vwSeriesCompleteYesCount, vwSeriesCompleteYesListCount, vwSeriesCompleteYes.getPublisher(), vwSeriesCompleteYes.getSeries(), vwSeriesCompleteYes.getVolume());

    			/*
        		 * https://comicvine.gamespot.com/api/
        		 * Rate limiting
				 * We restrict the number of requests made per user/hour. We officially support 200 requests per resource, per hour.
				 * 
				 * 3.600 seconds/hour
				 * 18 seconds/request/hour (3.600/18) = 200 requests/hour 
        		 */

        		logger.info("Waiting sleep time...");
        		Thread.sleep(18*1000);

        		String uri = Application.COMICVINE_API_URI
    					.concat("/volume/" + Application.COMIC_TYPE_VOLUME + "-").concat( vwSeriesCompleteYes.getComicvine_volume())
    					.concat("?api_key=").concat(Application.COMICVINE_API_KEY);
    			logger.info("Opening {}", uri);

        		URL url = new URL(uri);

    			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    			connection.setRequestMethod("GET");
    			connection.setRequestProperty("Accept", "application/xml");
    			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");

    			// URL InputStream
    			InputStream isXml = connection.getInputStream();
    			//
    			
    			// File InputStream
    			//ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    			//InputStream isXml = classloader.getResourceAsStream("volume49197.xml");
    			//
    			
                JAXBContext jaxbContext  = JAXBContext.newInstance(VolumeResponse.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                Volume volume;
                VolumeResponse volumeResponse = (VolumeResponse) jaxbUnmarshaller.unmarshal(isXml);
                
        		if (! volumeResponse.getError().equalsIgnoreCase(Application.COMICVINE_API_RESPONSE_ERROR_OK)) throw new Exception("VolumeTask: response error");
                
            	volume = volumeResponse.getResults().get(0);

            	// Check ComicVine volume null
				if (volume == null) {
					String sMessage =  "ComicVine returns [null] volume.";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							className,
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesCompleteYes.getPublisher(),
							"",
							vwSeriesCompleteYes.getSeries(),
							vwSeriesCompleteYes.getVolume(),
							vwSeriesCompleteYes.getComicvine_volume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesCompleteYes.getComicvine_volume(), true, sMessage);
					throw new Exception(sMessage);
				}

				// Check ComicVine volumeID null
        		if (volume.getVolumeID() == null) {
					String sMessage = "ComicVine returns [null] volumeID.";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							this.getClass().getName(),
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesCompleteYes.getPublisher(),
							"",
							vwSeriesCompleteYes.getSeries(),
							vwSeriesCompleteYes.getVolume(),
							vwSeriesCompleteYes.getComicvine_volume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesCompleteYes.getComicvine_volume(), true, sMessage);
					throw new Exception(sMessage);
        		}

        		// Check count of issues
        		if (vwSeriesCompleteYes.getQtd() != volume.getVolumeCountOfIssues().longValue() ) {
					String sMessage = "ComicVine returns different count of issues [" + volume.getVolumeCountOfIssues().longValue() + "] than ComicRack [" + vwSeriesCompleteYes.getQtd() + "].";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							this.getClass().getName(),
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesCompleteYes.getPublisher(),
							volume.getVolumePublisher().getPublisherID().toString(),
							vwSeriesCompleteYes.getSeries(),
							vwSeriesCompleteYes.getVolume(),
							vwSeriesCompleteYes.getComicvine_volume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesCompleteYes.getComicvine_volume(), true, sMessage);
					throw new Exception(sMessage);
        		}
        		
        		// Check all volume issue number
        		Integer issueCheckCount = 0;
        		Integer issueCheckError = 0;
        		for (Issue issue : volume.getVolumeIssues()) {
        			issueCheckCount++;
        			List<BrokerComics> brokerComicsIssueList = brokerComicsRepository.findAllByComicvineIssue(issue.getIssueID().toString());

        			if (brokerComicsIssueList.isEmpty()) {
        				issueCheckError++;
    					String sMessage = "ComicVine issue [" + issue.getIssueID().toString() + "] not found on ComicRack.";
    					Log log = new Log(
    							Log.LOG_LEVEL_WARN, 
    							this.getClass().getName(),
    							new Object(){}.getClass().getEnclosingMethod().getName(),
    							sMessage,
    							vwSeriesCompleteYes.getPublisher(),
    							volume.getVolumePublisher().getPublisherID().toString(),
    							vwSeriesCompleteYes.getSeries(),
    							vwSeriesCompleteYes.getVolume(),
    							vwSeriesCompleteYes.getComicvine_volume(),
    							issue.getIssueNumber(),
    							issue.getIssueID().toString());
    					addLog(log);
    					updateIssueComics(issue.getIssueID().toString(), true, sMessage);
        			
        			} else if (brokerComicsIssueList.size() > 1) {
        				issueCheckError++;
    					String sMessage = "ComicVine issue [" + issue.getIssueID().toString() + "] found more than 1 ComicRack comic.";
    					Log log = new Log(
    							Log.LOG_LEVEL_WARN, 
    							this.getClass().getName(),
    							new Object(){}.getClass().getEnclosingMethod().getName(),
    							sMessage,
    							vwSeriesCompleteYes.getPublisher(),
    							volume.getVolumePublisher().getPublisherID().toString(),
    							vwSeriesCompleteYes.getSeries(),
    							vwSeriesCompleteYes.getVolume(),
    							vwSeriesCompleteYes.getComicvine_volume(),
    							issue.getIssueNumber(),
    							issue.getIssueID().toString());
    					addLog(log);
    					updateIssueComics(issue.getIssueID().toString(), true, sMessage);
        				
        			} else {
    					
        				BrokerComics brokerComics = brokerComicsIssueList.get(0);
        				
        				if (! brokerComics.getNumber().equalsIgnoreCase(issue.getIssueNumber())) {
        					issueCheckError++;
        					String sMessage = "ComicVine issue number [" + brokerComics.getNumber() +  "] different than ComicRack comic number [" + issue.getIssueNumber() + "].";
        					Log log = new Log(
        							Log.LOG_LEVEL_WARN, 
        							this.getClass().getName(),
        							new Object(){}.getClass().getEnclosingMethod().getName(),
        							sMessage,
        							vwSeriesCompleteYes.getPublisher(),
        							volume.getVolumePublisher().getPublisherID().toString(),
        							vwSeriesCompleteYes.getSeries(),
        							vwSeriesCompleteYes.getVolume(),
        							vwSeriesCompleteYes.getComicvine_volume(),
        							issue.getIssueNumber(),
        							issue.getIssueID().toString());
        					addLog(log);
        					updateIssueComics(issue.getIssueID().toString(), true, sMessage);
        				}
        			}
        			
        		} //for (Issue issue : volume.getVolumeIssues())
        			
        		if (issueCheckError > 0) {
					String sMessage = "ComicVine volume issues check on ComicRack issues found [" + issueCheckError + "] errors.";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							this.getClass().getName(),
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesCompleteYes.getPublisher(),
							volume.getVolumePublisher().getPublisherID().toString(),
							vwSeriesCompleteYes.getSeries(),
							vwSeriesCompleteYes.getVolume(),
							vwSeriesCompleteYes.getComicvine_volume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesCompleteYes.getComicvine_volume(), true, sMessage);
					throw new Exception(sMessage);
        		}
        		
        		// Update ComicVine last check date
				String sMessage = "ComicVine volume check complete with no errors.";
				Log log = new Log(
						Log.LOG_LEVEL_INFO, 
						this.getClass().getName(),
						new Object(){}.getClass().getEnclosingMethod().getName(),
						sMessage,
						vwSeriesCompleteYes.getPublisher(),
						volume.getVolumePublisher().getPublisherID().toString(),
						vwSeriesCompleteYes.getSeries(),
						vwSeriesCompleteYes.getVolume(),
						vwSeriesCompleteYes.getComicvine_volume(),
						"",
						"");
				addLog(log);
				updateVolumeComics(vwSeriesCompleteYes.getComicvine_volume(), false, null);


    		} catch (Exception e) {
    			logger.error(e.getMessage(), e);

            }			

		}
	}

	private void addLog (Log log) {
		if (log.getLogLevel().equalsIgnoreCase(Log.LOG_LEVEL_INFO)) 	logger.info(log.getLogMessage());
		if (log.getLogLevel().equalsIgnoreCase(Log.LOG_LEVEL_WARN)) 	logger.warn(log.getLogMessage());
		if (log.getLogLevel().equalsIgnoreCase(Log.LOG_LEVEL_ERROR)) 	logger.error(log.getLogMessage());
		logRepository.save(log);
	}

	private void updateVolumeComics (String comicvineVolume, Boolean error, String message) {
		List<BrokerComics> brokerComicsVolumeList = brokerComicsRepository.findAllByComicvineVolume(comicvineVolume);
		Date lastCheckDate = new Date();
		for (BrokerComics brokerComics : brokerComicsVolumeList) {
			brokerComics.setComicvineVolumeCheckLastDate(lastCheckDate);
			brokerComics.setComicvineVolumeCheckError(error);
			if (message != null) {
				if (brokerComics.getComicvineVolumeCheckMessage() == null) {
					brokerComics.setComicvineVolumeCheckMessage(message);
				} else {
					brokerComics.setComicvineVolumeCheckMessage(brokerComics.getComicvineVolumeCheckMessage() + "\n" + message);
				}
			}
			brokerComicsRepository.save(brokerComics);
		}
	}

	private void updateIssueComics (String comicvineIssue, Boolean error, String message) {
		List<BrokerComics> brokerComicsVolumeList = brokerComicsRepository.findAllByComicvineIssue(comicvineIssue);
		Date lastCheckDate = new Date();
		for (BrokerComics brokerComics : brokerComicsVolumeList) {
			brokerComics.setComicvineVolumeCheckLastDate(lastCheckDate);
			brokerComics.setComicvineVolumeCheckError(error);
			if (message != null) {
				if (brokerComics.getComicvineVolumeCheckMessage() == null) {
					brokerComics.setComicvineVolumeCheckMessage(message);
				} else {
					brokerComics.setComicvineVolumeCheckMessage(brokerComics.getComicvineVolumeCheckMessage() + "\n" + message);
				}
			}
			brokerComicsRepository.save(brokerComics);
		}
	}

}
