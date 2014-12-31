package hu.bme.language_detection.util;

import hu.bme.language_detection.model.Document;
import hu.bme.language_detection.model.Language;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

/**
 * @author varh1i
 */
public class LanguagePredictor {

	public static void predictEuroGov(Map<String, Language> languages, Map<String, List<String>> filesByLanguage){
    	int correct = 0;
    	int wrong = 0;
    	File directory = new File("src/main/resources/naacl2010-langid/EuroGov");
    	File[] files = directory.listFiles();
    	for(File file:files){
    		int distance = Integer.MAX_VALUE;
        	String langId = null;
        	String text = ReaderUtil.readFile(file);
    		text = Jsoup.parse(text).text();
    		Document doc = new Document(text);
    		for(String languageId: languages.keySet()){
        		Language lang = languages.get(languageId);
        		int tmpDistance = lang.getDistance(doc);
        		if(tmpDistance < distance){
        			distance = tmpDistance;
        			langId = lang.getId();
        		}
        	}
    		if(filesByLanguage.get(langId).contains(file.getName())){
    			correct++;
    		} else {
    			wrong++;
    		}
    	}
    	System.out.println("correct: " + new Double(correct)/(correct+wrong)*100 + "% (" + correct + "/"+(correct+wrong)+")");
    }
	
	public static void predictWikipedia(Map<String, Language> languages){
    	int correct = 0;
    	int wrong = 0;
    	File directory = new File("src/main/resources/naacl2010-langid/Wikipedia");
    	File[] files = directory.listFiles();
    	for(File file:files){
    		int distance = Integer.MAX_VALUE;
        	String langId = null;
    		Document doc = new Document(ReaderUtil.readFile(file));
    		for(String languageId: languages.keySet()){
        		Language lang = languages.get(languageId);
        		int tmpDistance = lang.getDistance(doc);
        		if(tmpDistance < distance){
        			distance = tmpDistance;
        			langId = lang.getId();
        		}
        	}
    		if(file.getName().substring(0, 2).equals(langId)){
    			correct++;
    		} else {
    			wrong++;
    		}
    	}
    	System.out.println("correct: " + new Double(correct)/(correct+wrong)*100 + "% (" + correct + "/"+(correct+wrong)+")");
    }
	
}
