package hu.bme.language_detection.util;

import hu.bme.language_detection.Main;
import hu.bme.language_detection.model.Language;
import hu.bme.language_detection.model.NGram;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

/**
 * @author varh1i
 */
public class LanguageAndDocumentProfiler {
	
	private static HashMap<String, Language> languages = new HashMap<String, Language>();

	public static Map<String, Language> createLanguageProfilesEurGov(Map<String, List<String>> filesByLanguage){
    	
    	for(String lang: filesByLanguage.keySet()){
    		List<String> files = filesByLanguage.get(lang);
    		String text = "";
    		for(String file: files){
    			File tmpFile = new File("src/main/resources/naacl2010-langid/EuroGOV/"+file);
    			text += ReaderUtil.readFile(tmpFile);
    		}
    		text = Jsoup.parse(text).text();
    		createLanguage(lang, text);
    	}
    	return languages;
    }
	
	public static Map<String, Language> createLanguageProfilesWikipedia(){
    	File directory = new File("src/main/resources/naacl2010-langid/Wikipedia");
    	File[] files = directory.listFiles();
    	String text = "";
    	String id = getLanguageIdFromFileName(files[0]);

    	for(int i=0; i<files.length; i++){
    		if(getLanguageIdFromFileName(files[i]).equals(id)){
    	        i++;
    			text += ReaderUtil.readFile(files[i]);
    		}else{
    			createLanguage(id, text);
    			text = "";
    			id=getLanguageIdFromFileName(files[i]);
    		}
    	}
    	createLanguage(id, text);
    	return languages;
    }
	
	private static String getLanguageIdFromFileName(File file){
		return file.getName().substring(0, 2);
	}
	
	private static void createLanguage(String id, String text){
        List<NGram> mostFreqList = getMostFrequentTerms(Main.MAX_NGRAM_SIZE, Main.NUM_OF_MOST_FREQ_TERMS, text);
        languages.put(id, new Language(id, mostFreqList));
    }
	
	public static List<NGram> getMostFrequentTerms(int maxNGramSize, int numOfMostFrequent, String text){
		Map<String, Integer> frequency = LanguageAndDocumentProfiler.getFrequencyTable(maxNGramSize, text);
		List<NGram> mostFreqList = new ArrayList<NGram>(numOfMostFrequent);
        
        for (String key: frequency.keySet()){
    		int occurence = frequency.get(key);
    		if(mostFreqList.size()==0){
    			mostFreqList.add(new NGram(key, occurence));
    		} else {
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
	
	private static Map<String, Integer> getFrequencyTable(int maxNGramSize, String text) {
		Map<String, Integer> frequency = new HashMap<String, Integer>();
		for (int beginIndex=0; beginIndex < text.length(); beginIndex++) {
    		for (int actualNGramSize=1; actualNGramSize<=maxNGramSize && beginIndex+actualNGramSize <= text.length();actualNGramSize++){
    			String ngram = text.substring(beginIndex, beginIndex+actualNGramSize);
    			frequency.put(ngram, frequency.getOrDefault(ngram, 0)+1);
    		}
    	}
		return frequency;
	}
	
	public static Map<String, Integer> createPositions(List<NGram> mostFreqList){
		Map<String, Integer> positions = new HashMap<String, Integer>();
		for(int i = 0; i<mostFreqList.size(); i++){
			NGram ngram = mostFreqList.get(i);
			positions.put(ngram.getText(), i);
		}
		return positions;
	}
}
