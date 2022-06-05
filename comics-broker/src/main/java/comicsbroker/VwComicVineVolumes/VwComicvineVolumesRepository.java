package comicsbroker.VwComicVineVolumes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VwComicvineVolumesRepository extends JpaRepository <VwComicvineVolumes, String>  {
		
	List<VwComicvineVolumes> findByComicvineVolume(String comicvineVolume);
	
}
