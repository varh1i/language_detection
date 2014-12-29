/**
 * Copyright 2009 Zero Technologies OÜ. All rights reserved.
 * Use is subject to license terms.
 */
package language_detection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author varh1i
 */
public class Document {

	private String text;
	
	public Document(String text){
		this.text = text;
	}
	
	private Map<String, Integer> getFrequencyTable(int maxNGramSize) {
		Map<String, Integer> frequency = new HashMap<String, Integer>();
		for (int beginIndex=0; beginIndex < text.length(); beginIndex++) {
    		for (int actualNGramSize=1; actualNGramSize<=maxNGramSize && beginIndex+actualNGramSize <= text.length();actualNGramSize++){
    			String ngram = text.substring(beginIndex, beginIndex+actualNGramSize);
    			frequency.put(ngram, frequency.getOrDefault(ngram, 0)+1);
    		}
    	}
		return frequency;
	} 
	
	public List<NGram> getMostFrequentTerms(int maxNGramSize, int numOfMostFrequent){
		Map<String, Integer> frequency = getFrequencyTable(maxNGramSize);
		List<NGram> mostFreqList = new ArrayList<NGram>(numOfMostFrequent);
        
        for (String key: frequency.keySet()){
    		int occurence = frequency.get(key);
    		if(mostFreqList.size()==0){
    			mostFreqList.add(new NGram(key, occurence));
    		}
    		else {
    			boolean notAdded = true;
				for(int i=0; i<mostFreqList.size() && notAdded; i++){
    				if (mostFreqList.get(i).getFrequency() < occurence) {
    					mostFreqList.add(i, new NGram(key, occurence));
    					notAdded=false;
    					if(mostFreqList.size()>numOfMostFrequent){
    						mostFreqList.remove(numOfMostFrequent);
    					}
    				} 
    			}
				if(notAdded && mostFreqList.size()<numOfMostFrequent){
					mostFreqList.add(new NGram(key, occurence));
				}
			}
        }
        return mostFreqList;
	}
	
}
