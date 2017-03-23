package knearestneighbours;

import java.util.List;

/**
 * Has two functions:
 * 
 * 	Calculates range with a method that takes set of vectors and returns or stores the range of each feature.
 *
 *	Calculates adjusted Euclidean distance between two vectors.
 */
public class Calculator {
	
	/**
	 * Returns the normalized Euclidean distance between two vectors.
	 * @param v1
	 * @param v2
	 * @param ranges
	 */
	public static double getDistance(Vector v1, Vector v2, double[] ranges){

		double[] ftrs1 = v1.getFeatures();
		double[] ftrs2 = v2.getFeatures();
		double sumOfSquares = 0;

		//sum the squares of the differences between vector components divided by range of vector components.
		for(int i = 0; i < ftrs1.length; i++){
			double Di = ftrs1[i]-ftrs2[i];
			double Ri = ranges[i];			
			sumOfSquares += (Di*Di)/(Ri*Ri);
		}
		
		return Math.sqrt(sumOfSquares);		
	}
		
	/**
	 * For each instance, check each feature of that instance to see if it is the highest or lowest example of that feature. Then take the highest and lowest examples of each feature to calculate a range.
	 * @param instances
	 */
	public static double[] getRanges(List<Vector> instances){
		int featureNum = instances.get(0).getFeatureNumber();
		double[] highest = new double[featureNum]; //length of array is determined by the number of features in the first instance.
		double[] lowest = new double[featureNum];
		
		double[] theseFeatures = instances.get(0).getFeatures(); //get the first array of features.		
		for( int i = 0; i < featureNum; i++){ 
			highest[i] = theseFeatures[i]; //give range arrays an initial value.
			lowest[i] = theseFeatures[i];
		}		
		
		for(Vector v: instances){
			theseFeatures = v.getFeatures(); //will repeat the first instance
			for( int i = 0; i < featureNum; i++){
				
				if(highest[i] < theseFeatures[i]){
					highest[i] = theseFeatures[i]; //set highest to highest so far.
				}
				
				if(lowest[i] > theseFeatures[i]){
					lowest[i] = theseFeatures[i];
				}
			}
		}		
		
		return new double[]{highest[0]-lowest[0], highest[1]-lowest[1], highest[2]-lowest[2], highest[3]-lowest[3]};
		}
			
}
