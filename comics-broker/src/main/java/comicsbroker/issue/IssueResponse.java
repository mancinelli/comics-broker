package comicsbroker.issue;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class IssueResponse {

	private String error;
	
	private List<Issue> results;

	public String getError() {
		return error;
	}

	public List<Issue> getResults() {
		return results;
	}

	@XmlElement(name = "error")
	public void setError(String error) {
		this.error = error;
	}

	@XmlElement(name = "results")
	public void setResults(List<Issue> results) {
		this.results = results;
	}
}
