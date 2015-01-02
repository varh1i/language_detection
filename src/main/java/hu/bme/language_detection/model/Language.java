package hu.bme.language_detection.model;

import hu.bme.language_detection.Main;
import hu.bme.language_detection.util.LanguageAndDocumentProfiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author varh1i
 */
public class Language {

	private String id;
	private Map<String, Integer> positions = new HashMap<String, Integer>();
	
	public Language(String id, String text){
		this.id = id;
		List<NGram> mostFreqList = LanguageAndDocumentProfiler.getMostFrequentTerms(Main.MAX_NGRAM_SIZE, Main.NUM_OF_MOST_FREQ_TERMS, text);
		positions = LanguageAndDocumentProfiler.createPositions(mostFreqList);
	}
	
	public String getId() {
		return id;
	}
	
	public Map<String, Integer> getPositions() {
		return this.positions;
	}
}
