package comicsbroker.arc;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ArcResponse {

	private String error;
	
	private List<Arc> results;

	public String getError() {
		return error;
	}

	public List<Arc> getResults() {
		return results;
	}

	@XmlElement(name = "error")
	public void setError(String error) {
		this.error = error;
	}

	@XmlElement(name = "results")
	public void setResults(List<Arc> results) {
		this.results = results;
	}
}
