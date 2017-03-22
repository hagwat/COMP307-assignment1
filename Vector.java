package knearestneighbours;


/**
 * Instance vector with 4 features specifically for iris plants.
 */
public class Vector {

	private final double[] features;
	private String actualLabel;
	private String classifiedLabel = null; //The label we will be generating with the k nearest neighbours algorithm.
	
	public Vector(double feature1, double feature2, double feature3, double feature4, String label){
		this.features = new double[]{ feature1, feature2, feature3, feature4 };		
		this.actualLabel = label;
	}

	public void print(){
		System.out.println(features[0]+", "+features[1]+", "+features[2]+", "+features[3]+", "+actualLabel);
	}
	
	/**
	 * Returns number of features in vector. If for any reason vectors have variable numbers of features, this 
	 * method will actually be useful, but most methods that make use of it will need a drastic rethink.	
	 * @returncd45
	 */
	public int getFeatureNumber(){
		return features.length;
	}
	
	public double[] getFeatures(){
		double[] newArray = new double[features.length];
		for(int i = 0; i < features.length; i++){
			newArray[i] = features[i];
		}
		return newArray;
	}
	
}
