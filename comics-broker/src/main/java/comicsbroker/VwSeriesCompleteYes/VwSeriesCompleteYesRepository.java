package comicsbroker.VwSeriesCompleteYes;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VwSeriesCompleteYesRepository extends JpaRepository <VwSeriesCompleteYes, String>  {

	List<VwSeriesCompleteYes> findByComicvineVolumeCheckLastDateNullOrComicvineVolumeCheckLastDateLessThanOrderByComicvineVolumeCheckLastDateDesc(Date dateLessThan);
	
}
