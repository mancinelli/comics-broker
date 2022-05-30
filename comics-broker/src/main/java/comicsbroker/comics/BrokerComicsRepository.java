package comicsbroker.comics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrokerComicsRepository extends JpaRepository <BrokerComics, String>, JpaSpecificationExecutor<BrokerComics>  {
		
	public List<BrokerComics> findAllByComicvineVolume(String volume);
	
	public List<BrokerComics> findAllByComicvineIssue(String issue);

}
