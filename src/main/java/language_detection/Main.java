package language_detection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class Main {

	private static HashMap<String, Language> languages = new HashMap<String, Language>();
	public static int MAX_NGRAM_SIZE = 3;
	public static int NUM_OF_MOST_FREQ_TERMS = 400;
	
    public static void main(String[] args) throws IOException {
    	long start = System.currentTimeMillis();
    	createLanguageProfiles();
    	makePredictions();
    	
    	System.out.println(System.currentTimeMillis() - start);
    }
    
    private static void makePredictions(){
    	int correct = 0;
    	int wrong = 0;
    	File directory = new File("src/main/resources/naacl2010-langid/Wikipedia");
    	File[] files = directory.listFiles();
    	for(File file:files){
    		int distance = Integer.MAX_VALUE;
        	String langId = null;
    		Document doc = new Document(readFile(file));
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
    	System.out.println("correct: " + correct + ", wrong:" + wrong);
    }
    
    private static void createLanguageProfiles(){
    	File directory = new File("src/main/resources/naacl2010-langid/Wikipedia");
    	File[] files = directory.listFiles();
    	String id = files[0].getName().substring(0, 2);
    	String text = "";
    	for(File file : files){
    		if(file.getName().substring(0, 2).equals(id)){
    	        text += readFile(file);
    	        
    		}else{
    			createLanguage(id, text);
    			text = "";
    			id = file.getName().substring(0, 2); 
    			readFile(file);
    		}
    		
    	}
    }
    
    private static String readFile(File file){
    	StringBuffer buffer = new StringBuffer();
    	FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
	        String line;
	        
	        while((line = br.readLine()) != null){
	                buffer.append(line);
	        }
	        br.close();
	        fr.close();
		} catch (IOException e) {
			System.err.println("Couldn't read files");
			e.printStackTrace();
		}
        
        return buffer.toString();
    }
    
    public static void createLanguage(String id, String text){
    	Document doc = new Document(text);
        List<NGram> mostFreqList = doc.getMostFrequentTerms(MAX_NGRAM_SIZE, NUM_OF_MOST_FREQ_TERMS);
        System.out.println("\nID: " + id);
        for(NGram ngram:mostFreqList){
        	System.out.println("term:\"" + ngram.getText() + "\", occur:" + ngram.getFrequency());
        }
        
        languages.put(id, new Language(id, mostFreqList));
        
    }
}
