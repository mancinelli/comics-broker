package comicsbroker.VwSeriesComplete;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VwSeriesCompleteRepository extends JpaRepository <VwSeriesComplete, String>  {

	List<VwSeriesComplete> findByComicvineVolumeCheckLastDateNullOrComicvineVolumeCheckLastDateLessThanOrderByComicvineVolumeCheckLastDateDesc(Date dateLessThan);
	
	List<VwSeriesComplete> findByComicvineVolume(String volume);
	
}
