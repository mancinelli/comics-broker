package comicsbroker;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {
	
	//public static final String COMICVINE_API_KEY = "a79466341798962f8b0d16b485bf74cc96dff166"; //marciomancinelli
	public static final String COMICVINE_API_KEY = "4c8be87298ca9af524e8acd374c2608446402e23"; //comicsmarcio
	

	public static final String COMIC_TYPE_ISSUE = "4000";
	public static final String COMIC_TYPE_CHARACTER = "4005";
	public static final String COMIC_TYPE_PUBLISHER = "4010";
	public static final String COMIC_TYPE_LOCATION = "4020";
	public static final String COMIC_TYPE_ARC = "4045";
	public static final String COMIC_TYPE_VOLUME = "4050";
	
	public static final String COMICVINE_URI = "https://comicvine.gamespot.com";
	public static final String COMICVINE_API_URI = "https://comicvine.gamespot.com/api";
	
	public static final String COMICVINE_API_RESPONSE_ERROR_OK = "OK";

	public static final String BROKER_RESULT_OK = "OK";
	public static final String BROKER_RESULT_NOK = "NOK";

	
	//public static final String PATTERN_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	//public static final String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	 
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		//application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
        logger.info("Spring boot application start at : " + new Date());
	}

	/*
	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   
        logger.info("Spring boot application running in UTC timezone : " + new Date());
    }
    */

}
