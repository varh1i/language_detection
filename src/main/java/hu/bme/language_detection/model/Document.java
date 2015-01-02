package hu.bme.language_detection.model;

import hu.bme.language_detection.Main;
import hu.bme.language_detection.util.LanguageAndDocumentProfiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author varh1i
 */
public class Document {

	private Map<String, Integer> positions = new HashMap<String, Integer>();
	
	public Document(String text){
		List<NGram> mostFreq = LanguageAndDocumentProfiler.getMostFrequentTerms(Main.MAX_NGRAM_SIZE, Main.NUM_OF_MOST_FREQ_TERMS, text);
		this.positions = LanguageAndDocumentProfiler.createPositions(mostFreq);
	}
	
	public Map<String, Integer> getPositions(){
		return this.positions;
	}
}
