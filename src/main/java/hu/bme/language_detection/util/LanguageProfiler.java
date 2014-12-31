package hu.bme.language_detection.util;

import hu.bme.language_detection.Main;
import hu.bme.language_detection.model.Document;
import hu.bme.language_detection.model.Language;
import hu.bme.language_detection.model.NGram;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

/**
 * @author varh1i
 */
public class LanguageProfiler {
	
	private static HashMap<String, Language> languages = new HashMap<String, Language>();
	
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
	
	private static void createLanguage(String id, String text){
    	Document doc = new Document(text);
        List<NGram> mostFreqList = doc.getMostFrequentTerms(Main.MAX_NGRAM_SIZE, Main.NUM_OF_MOST_FREQ_TERMS);
        languages.put(id, new Language(id, mostFreqList));
    }
}
