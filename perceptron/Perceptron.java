package perceptron;

import java.util.*;

public class Perceptron {

	Feature[] features;
	float[] weights;
	float learnRate = 0.15f;
	float threshold = -10;
	float Wmultiplier = 5;

	public Perceptron(Feature[] features, Random rand){
		this.features = features;
		generateWeights(rand);
	}

	/**
	 * Returns true iff the sum of weights x outputs plus the threshold which is a negative number is >= 0.
	 */
	public boolean approve(Imgc image){
		float sum = 0;

		//System.out.println("Commencing approval. Total is "+(sum+threshold)+" Num features: "+features.length);

		for(int i = 0; i< features.length; i++){
			if(features[i].approve(image)){
				sum += weights[i];
			}
			if(i%12==0){
				//System.out.println("Total is "+(sum+threshold));
			}
		}

		
		if(sum+threshold>=0){
			//System.out.println("Approved: "+(sum+threshold)+" Image: "+image.getClassification());
			return true;
		}
		//System.out.println("Not approved: "+(sum+threshold)+" Image: "+image.getClassification());
		return false;
	}

	public void generateWeights(Random rand){
		weights = new float[features.length];
		for(int i = 0; i<weights.length; i++){
			weights[i] = (rand.nextFloat()*Wmultiplier)-(Wmultiplier/2);
		}
	}

	/**
	 * For each weight increment or decrement based on if feature gets classification right
	 */
	public void train(List<Imgc> images){
		float[] newWeights = Arrays.copyOf(weights, weights.length);
		
		for(Imgc img:images){
			boolean classify = approve(img);
			if(classify==false && img.getClassification().equals("Yes")){	//Positive example and wrong
				for(int i = 0; i<features.length; i++){
					if(features[i].approve(img)){	//Active feature
						//System.out.println("Classified as: "+classify+" Image is: "+img.getClassification()+" Feature approval: "+features[i].approve(img)+" increasing feature weight");
						newWeights[i]+=learnRate;
					}
				}
				//Change threshold
				threshold+=learnRate;

			}else if(classify==true && !img.getClassification().equals("Yes")){	//Negative example and wrong
				//System.out.print("negativewrong");
				for(int i = 0; i<features.length; i++){
					if(features[i].approve(img)){	//Active feature
						//System.out.println("Classified as: "+classify+" Image is: "+img.getClassification()+" Feature approval: "+features[i].approve(img)+" decreasing feature weight");
						newWeights[i]-=learnRate;
					}
				}
				//Change threshold
				threshold-=learnRate;
			}
		}
		weights = newWeights;
		//System.out.println();
	}

	public void printFeatures(){
		System.out.println();
		for(int i = 0; i<features.length; i++){
			System.out.println("Weight: "+weights[i]);
			features[i].print();
		}
	}

}














