package comicsbroker.VwSeriesCompleteError;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface VwSeriesCompleteErrorRepository extends JpaRepository <VwSeriesCompleteError, String>  {
		
	@Procedure(procedureName = "sp_broker_comics_replace")
	void spBrokerComicsReplace();

}
