package comicsbroker.issue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IssueRepository extends JpaRepository <Issue, Long>, JpaSpecificationExecutor<Issue>  {
	
	@Query(value = "SELECT * FROM issue WHERE issue_volume = ?1 ORDER BY CAST(issue_number AS UNSIGNED)", nativeQuery = true)
	public Page<Issue> queryByIssueVolumeVolumeIDOrderByIssueNumber(Long id, Pageable pageable);
	
	public List<Issue> findByIssueVolumeVolumeIDOrderByIssueNumber(Long id);

	public List<Issue> findByIssueNumberAndIssueVolumeVolumeID(String number, Long id);

}
