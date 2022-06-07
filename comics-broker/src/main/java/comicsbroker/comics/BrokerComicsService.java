package comicsbroker.comics;

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
import org.springframework.stereotype.Service;

import comicsbroker.Application;
import comicsbroker.VwSeriesComplete.VwSeriesComplete;
import comicsbroker.VwSeriesComplete.VwSeriesCompleteRepository;
import comicsbroker.issue.Issue;
import comicsbroker.log.Log;
import comicsbroker.log.LogRepository;
import comicsbroker.volume.Volume;
import comicsbroker.volume.VolumeResponse;

@Service
public class BrokerComicsService {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private static final String className = "ApplicationScheduling";

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private VwSeriesCompleteRepository vwSeriesCompleteRepository;

	@Autowired
	private BrokerComicsRepository brokerComicsRepository;
	
	public void callSpBrokerComics() {
		brokerComicsRepository.callSpBrokerComics();
	}

	public void checkComicsVolumeOnComicVineApi(String comicvineVolume) {
		
		logger.debug("checkComicsSeriesCompleteNoOnComicVineApi() running at : " + new Date());
		
		callSpBrokerComics();
		
		List<VwSeriesComplete> vwSeriesCompleteList = vwSeriesCompleteRepository.findByComicvineVolume(comicvineVolume);
		
		Integer vwSeriesCompleteListCount = vwSeriesCompleteList.size();
		Integer vwSeriesCompleteCount = 0;
		for (VwSeriesComplete vwSeriesComplete : vwSeriesCompleteList) {
			
        	try {

        		vwSeriesCompleteCount++;
    			logger.info("Cheking volume [{}] of [{}]. [{}][{}][{}]", vwSeriesCompleteCount, vwSeriesCompleteListCount, vwSeriesComplete.getPublisher(), vwSeriesComplete.getSeries(), vwSeriesComplete.getVolume());

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
    					.concat("/volume/" + Application.COMIC_TYPE_VOLUME + "-").concat( vwSeriesComplete.getComicvineVolume())
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
							vwSeriesComplete.getPublisher(),
							"",
							vwSeriesComplete.getSeries(),
							vwSeriesComplete.getVolume(),
							vwSeriesComplete.getComicvineVolume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesComplete.getComicvineVolume(), true, sMessage);
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
							vwSeriesComplete.getPublisher(),
							"",
							vwSeriesComplete.getSeries(),
							vwSeriesComplete.getVolume(),
							vwSeriesComplete.getComicvineVolume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesComplete.getComicvineVolume(), true, sMessage);
					throw new Exception(sMessage);
        		}

        		// Check count of issues
        		if (vwSeriesComplete.getQtd() != volume.getVolumeCountOfIssues().longValue() ) {
					String sMessage = "ComicVine returns different count of issues [" + volume.getVolumeCountOfIssues().longValue() + "] than ComicRack [" + vwSeriesComplete.getQtd() + "].";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							this.getClass().getName(),
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesComplete.getPublisher(),
							volume.getVolumePublisher().getPublisherID().toString(),
							vwSeriesComplete.getSeries(),
							vwSeriesComplete.getVolume(),
							vwSeriesComplete.getComicvineVolume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesComplete.getComicvineVolume(), true, sMessage);
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
    							vwSeriesComplete.getPublisher(),
    							volume.getVolumePublisher().getPublisherID().toString(),
    							vwSeriesComplete.getSeries(),
    							vwSeriesComplete.getVolume(),
    							vwSeriesComplete.getComicvineVolume(),
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
    							vwSeriesComplete.getPublisher(),
    							volume.getVolumePublisher().getPublisherID().toString(),
    							vwSeriesComplete.getSeries(),
    							vwSeriesComplete.getVolume(),
    							vwSeriesComplete.getComicvineVolume(),
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
        							vwSeriesComplete.getPublisher(),
        							volume.getVolumePublisher().getPublisherID().toString(),
        							vwSeriesComplete.getSeries(),
        							vwSeriesComplete.getVolume(),
        							vwSeriesComplete.getComicvineVolume(),
        							issue.getIssueNumber(),
        							issue.getIssueID().toString());
        					addLog(log);
        					updateIssueComics(issue.getIssueID().toString(), true, sMessage);
        				}
        			}
        		} //for (Issue issue : volume.getVolumeIssues())
        			

        		// Check all volume issues complete series and tags
        		issueCheckCount = 0;
        		issueCheckError = 0;
        		for (Issue issue : volume.getVolumeIssues()) {
        			issueCheckCount++;
        			List<BrokerComics> brokerComicsIssueList = brokerComicsRepository.findAllByComicvineIssue(issue.getIssueID().toString());

        			// more than 1 comic previous checked
    				BrokerComics brokerComics = brokerComicsIssueList.get(0);
    				
    				if (! brokerComics.getSeriescomplete().equalsIgnoreCase("Yes")) {
    					issueCheckError++;
    					String sMessage = "ComicRack comic number [" + issue.getIssueNumber() + "] [seriescomplete] not set to [Yes].";
    					Log log = new Log(
    							Log.LOG_LEVEL_WARN, 
    							this.getClass().getName(),
    							new Object(){}.getClass().getEnclosingMethod().getName(),
    							sMessage,
    							vwSeriesComplete.getPublisher(),
    							volume.getVolumePublisher().getPublisherID().toString(),
    							vwSeriesComplete.getSeries(),
    							vwSeriesComplete.getVolume(),
    							vwSeriesComplete.getComicvineVolume(),
    							issue.getIssueNumber(),
    							issue.getIssueID().toString());
    					addLog(log);
    					updateIssueComics(issue.getIssueID().toString(), true, sMessage);
    				}

    				if (! brokerComics.getTags().equalsIgnoreCase("Complete (completo)") && ! brokerComics.getTags().equalsIgnoreCase("Ongoing (em andamento)")) {
    					issueCheckError++;
    					String sMessage = "ComicRack comic number [" + issue.getIssueNumber() + "] [tags] not set to [Complete (completo)] or [Missing (faltando)].";
    					Log log = new Log(
    							Log.LOG_LEVEL_WARN, 
    							this.getClass().getName(),
    							new Object(){}.getClass().getEnclosingMethod().getName(),
    							sMessage,
    							vwSeriesComplete.getPublisher(),
    							volume.getVolumePublisher().getPublisherID().toString(),
    							vwSeriesComplete.getSeries(),
    							vwSeriesComplete.getVolume(),
    							vwSeriesComplete.getComicvineVolume(),
    							issue.getIssueNumber(),
    							issue.getIssueID().toString());
    					addLog(log);
    					updateIssueComics(issue.getIssueID().toString(), true, sMessage);
    				}
        		}
        		
        		if (issueCheckError > 0) {
					String sMessage = "ComicVine volume issues check on ComicRack issues found [" + issueCheckError + "] errors.";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							this.getClass().getName(),
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesComplete.getPublisher(),
							volume.getVolumePublisher().getPublisherID().toString(),
							vwSeriesComplete.getSeries(),
							vwSeriesComplete.getVolume(),
							vwSeriesComplete.getComicvineVolume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesComplete.getComicvineVolume(), true, sMessage);
					throw new Exception(sMessage);
        		}
        		
        		// Check complete series for volumes with no errors
        		if (vwSeriesComplete.getQtd() != volume.getVolumeCountOfIssues().longValue() ) {
					String sMessage = "ComicVine returns different count of issues [" + volume.getVolumeCountOfIssues().longValue() + "] than ComicRack [" + vwSeriesComplete.getQtd() + "].";
					Log log = new Log(
							Log.LOG_LEVEL_ERROR, 
							this.getClass().getName(),
							new Object(){}.getClass().getEnclosingMethod().getName(),
							sMessage,
							vwSeriesComplete.getPublisher(),
							volume.getVolumePublisher().getPublisherID().toString(),
							vwSeriesComplete.getSeries(),
							vwSeriesComplete.getVolume(),
							vwSeriesComplete.getComicvineVolume(),
							"",
							"");
					addLog(log);
					updateVolumeComics(vwSeriesComplete.getComicvineVolume(), true, sMessage);
					throw new Exception(sMessage);
        		}
        		
        		// Update ComicVine last check date
				String sMessage = "ComicVine volume check complete with no errors.";
				Log log = new Log(
						Log.LOG_LEVEL_INFO, 
						this.getClass().getName(),
						new Object(){}.getClass().getEnclosingMethod().getName(),
						sMessage,
						vwSeriesComplete.getPublisher(),
						volume.getVolumePublisher().getPublisherID().toString(),
						vwSeriesComplete.getSeries(),
						vwSeriesComplete.getVolume(),
						vwSeriesComplete.getComicvineVolume(),
						"",
						"");
				addLog(log);
				updateVolumeComics(vwSeriesComplete.getComicvineVolume(), false, null);


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
			if (error) {
				if (message != null) {
					if (brokerComics.getComicvineVolumeCheckMessage() == null) {
						brokerComics.setComicvineVolumeCheckMessage(message);
					} else {
						brokerComics.setComicvineVolumeCheckMessage(brokerComics.getComicvineVolumeCheckMessage() + "\n" + message);
					}
				}
			} else {
				brokerComics.setComicvineVolumeCheckMessage(null);
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
