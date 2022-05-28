package comicsbroker;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import comicsbroker.volume.Volume;
import comicsbroker.volume.VolumeResponse;

@Configuration
@EnableScheduling
@EnableAsync
public class ApplicationScheduling {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private VwNoComicvineInfoRepository vwNoComicvineInfoRepository;

	@Autowired
	private VwSeriesCompleteErrorRepository vwSeriesCompleteErrorRepository;

	@Autowired
	private VwSeriesCompleteYesRepository vwSeriesCompleteYesRepository;

	@Scheduled(fixedRate = 10000)
	void check() {
		
		logger.info("check() -----------------------------------------------------------------------------");
		logger.info("check() running at : " + new Date());
		
		checkComicsWithNotComicvineInfo();

		checkComicsWithSeriesCompleteError();

		//checkComicsSeriesCompleteOnComicVineApi();
		
	}
	
	
	public boolean checkComicsWithNotComicvineInfo() {
		logger.debug("checkComicsWithNotComicvineInfo() running at : " + new Date());
		List<VwNoComicvineInfo> vwNoComicvineInfoList = vwNoComicvineInfoRepository.findAll();
		if (vwNoComicvineInfoList.size() > 0) {
			logger.warn("checkComicsWithNotComicvineInfo() exists " + vwNoComicvineInfoList.size() + " comics with not comicvine info.");
			for (VwNoComicvineInfo vwNoComicvineInfo : vwNoComicvineInfoList) {
				logger.info("     [Publisher: " + vwNoComicvineInfo.getPublisher() + "]"  
					+ " [Series: " + vwNoComicvineInfo.getSeries() + "]"
					+ " [Volume: " + vwNoComicvineInfo.getVolume() + "]" 
					+ " [Number: " + vwNoComicvineInfo.getNumber() + "]" 
					+ " [ComicvineIssue: " + vwNoComicvineInfo.getComicvine_issue() + "]" 
					+ " [ComicvineVolume: " + vwNoComicvineInfo.getComicvine_volume() + "]" );
			}
			return false;
		} else {
			logger.info("checkComicsWithNotComicvineInfo() ok.");
			return true;
		}
	}

	public boolean checkComicsWithSeriesCompleteError() {
		logger.debug("checkComicsWithSeriesCompleteError() running at : " + new Date());
		List<VwSeriesCompleteError> vwSeriesCompleteErrorList = vwSeriesCompleteErrorRepository.findAll();
		if (vwSeriesCompleteErrorList.size() > 0) {
			logger.warn("checkComicsWithSeriesCompleteError() exists " + vwSeriesCompleteErrorList.size() + " comics with volume complete error.");
			for (VwSeriesCompleteError vwSeriesCompleteError : vwSeriesCompleteErrorList) {
				logger.info("     [Publisher: " + vwSeriesCompleteError.getPublisher() + "]"  
						+ " [Series: " + vwSeriesCompleteError.getSeries() + "]"
						+ " [Volume: " + vwSeriesCompleteError.getVolume() + "]" 
						+ " [ComicvineVolume: " + vwSeriesCompleteError.getComicvine_volume() + "]" );
			}
			return false;
		} else {
			logger.info("checkComicsWithSeriesCompleteError() ok.");
			return true;
		}
	}
	
	public void checkComicsSeriesCompleteOnComicVineApi() {
		logger.debug("checkComicsSeriesCompleteOnComicVineApi() running at : " + new Date());
		
		List<VwSeriesCompleteYes> vwSeriesCompleteYesList = vwSeriesCompleteYesRepository.findAll();
		for (VwSeriesCompleteYes vwSeriesCompleteYes : vwSeriesCompleteYesList) {
			
        	try {
    			String uri = Application.COMICVINE_API_URI
    					.concat("/volume/" + Application.COMIC_TYPE_VOLUME + "-").concat( vwSeriesCompleteYes.getComicvine_volume())
    					.concat("?api_key=").concat(Application.COMICVINE_API_KEY);
    			logger.info("uri: {}", uri);

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
            	try {

            		if (! volumeResponse.getError().equalsIgnoreCase(Application.COMICVINE_API_RESPONSE_ERROR_OK)) throw new Exception("VolumeTask: response error");
                    
                	volume = volumeResponse.getResults().get(0);

                	// Volume check
            		if (volume == null) throw new Exception("volume error. [null]");
            		if (volume.getVolumeID() == null) throw new Exception("volume id error. [null]");

            		logger.info("volume.getVolumeCountOfIssues() = " + volume.getVolumeCountOfIssues());
                	

                	
                	
                	
                	
                	
                	
                	
                	
        			
        		} catch (Exception e) {
        			logger.error(e.getMessage(), e);
            	}
            	logger.debug("save");

    		} catch (Exception e) {
    			logger.error(e.getMessage(), e);
            }			

		}
	}

}
