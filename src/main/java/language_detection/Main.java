package language_detection;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Main {

	private static Map<String, Language> languages = new HashMap<String, Language>();
	public static int MAX_NGRAM_SIZE = 3;
	public static int NUM_OF_MOST_FREQ_TERMS = 400;
	
    public static void main(String[] args) throws IOException {
    	long start = System.currentTimeMillis();
    	languages = ReaderUtil.createLanguageProfilesWikipedia();
    	long profileTime = System.currentTimeMillis() - start;
    	System.out.println("Created " + languages.size() + " profiles");
    	System.out.println("Time for creating profiles: " + profileTime + " ms");
    	start = System.currentTimeMillis();
    	makePredictions();
    	long predictionTime = System.currentTimeMillis() - start;
    	System.out.println("Time for making predictions: " + predictionTime + " ms");
    }
    
    private static void makePredictions(){
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
