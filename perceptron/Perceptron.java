package perceptron;

import java.util.*;

public class Perceptron {

	Feature[] features;
	float[] weights;

	public Perceptron(Feature[] features, Random rand){
		this.features = features;
		generateWeights(rand);
	}

	/**
	 * Returns true iff the sum of weights x outputs plus the threshold which is a negative number is >= than 0.
	 */
	public boolean approve(Imgc image){
		float sum = 0;
		float threshold = -10; //Todo find out how big threshold will be

		for(int i = 0; i< features.length; i++){
			if(features[i].approve(image)){
				sum += weights[i];
			}
		}

		if(sum+threshold>=0){
			return true;
		}
		return false;
	}

	public void generateWeights(Random rand){
		weights = new float[features.length];
		for(int i = 0; i<weights.length; i++){
			weights[i] = rand.nextFloat();
		}
	}

	/**
	 * For each weight increment or decrement based on if feature gets classification right
	 */
	public void train(List<Imgc> images){

		for(Imgc img:images){
			for(Feature ft :features){
				if(img.getClassification().equals("Yes") != ft.approve(img)){	//If yes and approved or not yes and not approved
					//Change weight
					//May have to change differently if negative change Todo



				}




			}
		}









	}

}














