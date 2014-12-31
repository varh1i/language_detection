package hu.bme.language_detection;

import hu.bme.language_detection.model.Language;
import hu.bme.language_detection.util.LanguagePredictor;
import hu.bme.language_detection.util.LanguageAndDocumentProfiler;
import hu.bme.language_detection.util.ReaderUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

	private static Map<String, Language> languageProfiles = new HashMap<String, Language>();
	public static int MAX_NGRAM_SIZE = 3;
	public static int NUM_OF_MOST_FREQ_TERMS = 400;
	
    public static void main(String[] args) throws IOException {
    	System.out.println("Calculation started for EuroGov dataset...");
    	analyseAndPredictEuroGov();
    	System.out.println("Calculation started for Wikipedia dataset...");
    	analyseAndPredictWikipedia();
    }
    
    private static void analyseAndPredictWikipedia(){
    	long start = System.currentTimeMillis();
    	languageProfiles = LanguageAndDocumentProfiler.createLanguageProfilesWikipedia();
    	long profileTime = System.currentTimeMillis() - start;
    	System.out.println("Created profile for " + languageProfiles.size() + " languages in: " +profileTime+ " ms.");
    	start = System.currentTimeMillis();
    	LanguagePredictor.predictWikipedia(languageProfiles);
    	long predictionTime = System.currentTimeMillis() - start;
    	System.out.println("Time for making predictions: " + predictionTime + " ms");
    }
    
    private static void analyseAndPredictEuroGov(){
    	long start = System.currentTimeMillis();
    	Map<String, List<String>> filesByLanguage = ReaderUtil.getFilesByLangFromEurGovMetaFile();
    	languageProfiles = LanguageAndDocumentProfiler.createLanguageProfilesEurGov(filesByLanguage);
    	long profileTime = System.currentTimeMillis() - start;
    	System.out.println("Created profile for " + languageProfiles.size() + " languages in: "+ profileTime + " ms.");
    	start = System.currentTimeMillis();
    	LanguagePredictor.predictEuroGov(languageProfiles, filesByLanguage);
    	long predictionTime = System.currentTimeMillis() - start;
    	System.out.println("Time for making predictions: " + predictionTime + " ms");
    }
}
