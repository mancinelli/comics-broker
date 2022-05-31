package comicsbroker.VwSeriesCompleteNo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VwSeriesCompleteNoRepository extends JpaRepository <VwSeriesCompleteNo, String>  {

	List<VwSeriesCompleteNo> findByComicvineVolumeCheckLastDateNullOrComicvineVolumeCheckLastDateLessThanOrderByComicvineVolumeCheckLastDateDesc(Date dateLessThan);
	
}
