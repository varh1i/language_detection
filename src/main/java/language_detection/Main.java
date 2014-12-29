package language_detection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        Map<String, Integer> frequency = new HashMap<String, Integer>();
        String text = "Your text goes here, actual mileage may varyry";
    	int maxNGramSize = 3;
    	
    	long start = System.currentTimeMillis();
    	for (int beginIndex=0; beginIndex < text.length(); beginIndex++) {
    		for (int actualNGramSize=1; actualNGramSize<=maxNGramSize && beginIndex+actualNGramSize <= text.length();actualNGramSize++){
    			String ngram = text.substring(beginIndex, beginIndex+actualNGramSize);
    			frequency.put(ngram, frequency.getOrDefault(ngram, 0)+1);
    		}
    	}

    	
    	
        int numOfMostFrequent = 100;
        List<NGram> mostFreqList = new ArrayList<NGram>(numOfMostFrequent);
        
        //System.out.println(mostFreqList.get(0));
        for (String key: frequency.keySet()){
    		int occurence = frequency.get(key);
    		//System.out.println("key: \"" + key + "\", occ: " + occurence);
    		//if(mostFreqList.size()<400){
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
    			//mostFreqList.adfrequency.get(key);
            //}
        }
        
        System.out.println(System.currentTimeMillis() - start);
        for(NGram ngram:mostFreqList){
        	System.out.println("term:" + ngram.getText() + ", occur:" + ngram.getFrequency());
        }
        
    }
    
    
}
