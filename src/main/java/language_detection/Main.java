package language_detection;

import java.util.List;


public class Main {

    public static void main(String[] args) {
    	long start = System.currentTimeMillis();
        Document doc = new Document("Your text goes here, actual mileage may varyry");
        List<NGram> mostFreqList = doc.getMostFrequentTerms(3, 10);
        System.out.println(System.currentTimeMillis() - start);
        for(NGram ngram:mostFreqList){
        	System.out.println("term:" + ngram.getText() + ", occur:" + ngram.getFrequency());
        }
    }
    
    
}
