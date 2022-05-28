package comicsbroker.publisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PublisherRepository extends JpaRepository <Publisher, Long>, JpaSpecificationExecutor<Publisher>  {
	
	Page<Publisher> findAll(Specification<Publisher> spec, Pageable pageable);

}
