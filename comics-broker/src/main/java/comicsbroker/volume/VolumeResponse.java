package comicsbroker.volume;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class VolumeResponse {

	private String error;
	
	private List<Volume> results;

	public String getError() {
		return error;
	}

	public List<Volume> getResults() {
		return results;
	}

	@XmlElement(name = "error")
	public void setError(String error) {
		this.error = error;
	}

	@XmlElement(name = "results")
	public void setResults(List<Volume> results) {
		this.results = results;
	}

}
