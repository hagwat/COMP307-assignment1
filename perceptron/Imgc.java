package perceptron;

/**
 * Image that includes a 2D array of pixels and a classification.
 */
public class Imgc {

	private boolean[][] array;
	private String classification;
	private boolean imageClassificationCorrect = false;	//Might not actually be useful

	public Imgc(boolean[][] array, String classification){
		this.array = array;
		this.classification = classification;
	}

	public boolean[][] getArray() {
		return array;
	}

	public String getClassification() {
		return classification;
	}

	/**
	 * Returns true if the perceptron guessed correctly last time
	 */
	public boolean correct(){
		return imageClassificationCorrect;
	}

	public void setCorrect(boolean correct){
		imageClassificationCorrect = correct;
	}

}
