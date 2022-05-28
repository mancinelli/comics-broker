package comicsbroker.comics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrokerComicsRepository extends JpaRepository <BrokerComics, String>, JpaSpecificationExecutor<BrokerComics>  {
		
}
