package language_detection;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

/**
 * @author varh1i
 */
public class Predictor {

	public static void makePredictions(Map<String, Language> languages, Map<String, List<String>> filesByLanguage){
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
    			//Language lang = languages.get(file.getName().substring(0, 2));
        		//int realDistance = lang.getDistance(doc);
    			//System.out.println("Distance: " + distance + " from lang: " + langId + ", but should be: " + lang.getId() +" ("+ realDistance+") DIFF: " + Math.abs(realDistance-distance) );
    		}
    	}
    	System.out.println("correct: " + new Double(correct)/(correct+wrong) + "% (" + correct + "/"+(correct+wrong)+")");
    }
	
	private static void makePredictions(Map<String, Language> languages){
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
    			Language lang = languages.get(file.getName().substring(0, 2));
        		int realDistance = lang.getDistance(doc);
    			System.out.println("Distance: " + distance + " from lang: " + langId + ", but should be: " + lang.getId() +" ("+ realDistance+") DIFF: " + Math.abs(realDistance-distance) );
    		}
    	}
    	System.out.println("correct: " + new Double(correct)/(correct+wrong) + "% (" + correct + "/"+(correct+wrong)+")");
    }
	
}
