package hu.bme.language_detection.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author varh1i
 */
public class ReaderUtil {

	public static Map<String, List<String>> getFilesByLangFromEurGovMetaFile(){
    	Map<String, List<String>> filesByLanguage = new HashMap<String,List<String>>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File("src/main/resources/naacl2010-langid/EuroGOV.meta")));
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
		} catch (IOException e) {
			System.err.println("Couldn't read file: src/main/resources/naacl2010-langid/EuroGOV.meta");
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return filesByLanguage;
    }
	
	public static String readFile(File file){
    	StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
	        String line;
	        while((line = br.readLine()) != null){
	                buffer.append(line);
	        }
		} catch (IOException e) {
			System.err.println("Couldn't read file: " + file.getAbsolutePath());
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		String result = buffer.toString(); 
        result = result.toLowerCase();
        //result = result.replaceAll("[^a-zA-Z0-9\\s]", "");
        return result;
    }
	
	
	
}
