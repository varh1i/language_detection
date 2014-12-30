package language_detection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

/**
 * @author varh1i
 */
public class ReaderUtil {

	private static HashMap<String, Language> languages = new HashMap<String, Language>();
	
	public static Map<String, Language> createLanguageProfilesWikipedia(){
    	File directory = new File("src/main/resources/naacl2010-langid/Wikipedia");
    	File[] files = directory.listFiles();
    	String id = files[0].getName().substring(0, 2);
    	String text = "";
    	for(int i=0; i<files.length; i++){
    		if(files[i].getName().substring(0, 2).equals(id)){
    	        //System.out.println("prefix: " + files[i].getName().substring(0, 2) + "name:" + files[i].getName());
    	        i++;
    			text += readFile(files[i]);
    		}else{
    			createLanguage(id, text);
    			text = "";
    			id=files[i].getName().substring(0, 2);
    		}
    		
    	}
    	createLanguage(id, text);
    	return languages;
    }
	
	public static Map<String, Language> createLanguageProfilesEurGov(Map<String, List<String>> filesByLanguage){
    	
    	for(String lang: filesByLanguage.keySet()){
    		List<String> files = filesByLanguage.get(lang);
    		String text = "";
    		for(String file: files){
    			File tmpFile = new File("src/main/resources/naacl2010-langid/EuroGOV/"+file);
    			text += readFile(tmpFile);
    		}
    		text = Jsoup.parse(text).text();
    		createLanguage(lang, text);
    	}
    	return languages;
    }
	
	public static Map<String, List<String>> readEurGovMetaFile(){
    	Map<String, List<String>> filesByLanguage = new HashMap<String,List<String> >();
    	
    	FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(new File("src/main/resources/naacl2010-langid/EuroGOV.meta"));
			br = new BufferedReader(fr);
	        String line;
	        
	        while((line = br.readLine()) != null){
	        		String[] tokens = line.split("\t");
	        		String lang = tokens[2];
	        		String filename = tokens[0];
	        		List<String> list = filesByLanguage.get(lang);
	        		if(list == null){
	        			list = new ArrayList<String>();
	        		}
	        		list.add(filename);
	        		filesByLanguage.put(lang, list);
	        }
	        br.close();
	        fr.close();
		} catch (IOException e) {
			System.err.println("Couldn't read files");
			e.printStackTrace();
		}
        
        return filesByLanguage;
    }
	
	public static String readFile(File file){
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
        
		String result = buffer.toString(); 
        result = result.toLowerCase();
        //result = result.replaceAll("[^a-zA-Z0-9\\s]", "");
        return result;
    }
	
	public static void createLanguage(String id, String text){
    	Document doc = new Document(text);
        List<NGram> mostFreqList = doc.getMostFrequentTerms(Main.MAX_NGRAM_SIZE, Main.NUM_OF_MOST_FREQ_TERMS);
        //System.out.println("\nID: " + id);
        /*for(NGram ngram:mostFreqList){
        	System.out.println("term:\"" + ngram.getText() + "\", occur:" + ngram.getFrequency());
        }*/
        
        languages.put(id, new Language(id, mostFreqList));
        
    }
	
}
