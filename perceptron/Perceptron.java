package perceptron;

import java.util.*;

public class Perceptron {

	Feature[] features;
	float[] weights;
	float learnRate = 0.15f;
	float threshold = -10;

	public Perceptron(Feature[] features, Random rand){
		this.features = features;
		generateWeights(rand);
	}

	/**
	 * Returns true iff the sum of weights x outputs plus the threshold which is a negative number is >= 0.
	 */
	public boolean approve(Imgc image){
		float sum = 0;

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
			boolean classify = approve(img);
			if(classify==false && img.getClassification().equals("Yes")){	//Positive example and wrong
				for(int i = 0; i<features.length; i++){
					if(features[i].approve(img)){	//Active feature								
						weights[i]+=learnRate;
					}
				}
				//Change threshold
				threshold+=learnRate;//Todo Try disabling threshold change
				
			}else if(classify==true && !img.getClassification().equals("Yes")){	//Negative example and wrong
				for(int i = 0; i<features.length; i++){
					if(features[i].approve(img)){	//Active feature						
						weights[i]-=learnRate;
					}
				}
				//Change threshold
				threshold-=learnRate;
			}			
		}
	}

}














