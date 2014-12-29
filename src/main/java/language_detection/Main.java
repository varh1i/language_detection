package language_detection;


public class Main {

    public static void main(String[] args) {
       // Map<String, Integer> frequency = new HashMap<String, Integer>();
        String text = "Your text goes here, actual mileage may vary";
        
    	int nGramSize = 1;
    	for(int beginIndex=0; beginIndex + nGramSize < text.length()+1; beginIndex++){
    		System.out.println(text.substring(beginIndex, beginIndex+nGramSize));
    	}
       
    	long start = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - start);
    }
	
}
