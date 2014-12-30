package language_detection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

	private static Map<String, Language> languages = new HashMap<String, Language>();
	public static int MAX_NGRAM_SIZE = 3;
	public static int NUM_OF_MOST_FREQ_TERMS = 400;
	
    public static void main(String[] args) throws IOException {
    	long start = System.currentTimeMillis();
    	Map<String, List<String>> filesByLanguage = ReaderUtil.readEurGovMetaFile();
    	languages = ReaderUtil.createLanguageProfilesEurGov(filesByLanguage);
    	//languages = ReaderUtil.createLanguageProfilesWikipedia();
    	long profileTime = System.currentTimeMillis() - start;
    	System.out.println("Created " + languages.size() + " profiles");
    	System.out.println("Time for creating profiles: " + profileTime + " ms");
    	start = System.currentTimeMillis();
    	Predictor.makePredictions(languages, filesByLanguage);
    	//makePredictions(languages);
    	long predictionTime = System.currentTimeMillis() - start;
    	System.out.println("Time for making predictions: " + predictionTime + " ms");
    }
    
    
}
