package comicsbroker.volume;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VolumeRepository extends JpaRepository <Volume, Long>, JpaSpecificationExecutor<Volume>  {

	Page<Volume> findAll(Specification<Volume> spec, Pageable pageable);
	
}
