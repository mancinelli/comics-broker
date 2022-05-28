package comicsbroker.arc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArcRepository extends JpaRepository <Arc, Long>, JpaSpecificationExecutor<Arc>  {
	
	Page<Arc> findAll(Specification<Arc> spec, Pageable pageable);
	
}
