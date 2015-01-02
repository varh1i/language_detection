package hu.bme.language_detection.model;

import hu.bme.language_detection.util.LanguageAndDocumentProfiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author varh1i
 */
public class Language {

	private String id;
	private List<NGram> mostFreq;
	private Map<String, Integer> positions = new HashMap<String, Integer>();
	
	public Language(String id, List<NGram> mostFreqList){
		this.id = id;
		this.mostFreq = mostFreqList;
		positions = LanguageAndDocumentProfiler.createPositions(mostFreq);
	}
	
	public String getId() {
		return id;
	}
	
	public Map<String, Integer> getPositions() {
		return this.positions;
	}
}
