package language_detection;

/**
 * @author varh1i
 */
public class NGram {

	String text;
	Integer frequency;

	public NGram(String text, Integer frequency){
		this.text = text;
		this.frequency = frequency;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
}
