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
	private List<NGram> mostFreq;
	private Map<String, Integer> positions = new HashMap<String, Integer>();
	
	public Language(String id, List<NGram> mostFreqList){
		this.id = id;
		this.mostFreq = mostFreqList;
		positions = LanguageAndDocumentProfiler.createPositions(mostFreq);
	}
	
	public int getDistance(Document document){
		Map<String, Integer> positions = document.getPositions();
		int distance = 0;
		for(String ngram: positions.keySet()){
			Integer position;
			if((position = this.positions.get(ngram))!=null){
				distance += Math.abs(position-positions.get(ngram));
			}else{
				distance += Main.NUM_OF_MOST_FREQ_TERMS;
			}
			
		}
		return distance;
	}
	
	public String getId() {
		return id;
	}
	
}
