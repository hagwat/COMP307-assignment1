package knearestneighbours;

import java.util.*;

public class KNrstNghbrAlgrthm {

	public static void findLabels(InstanceSet testSet, InstanceSet trainingSet, int K){
		
		for(Vector v : testSet.getInstances()){ 
			String foundLabel = findLabel(v,  trainingSet, K); // For each instance in the test set, find its label.
			v.setClassifier(foundLabel);
		}		
	}

	/**
	 * Determines the label of a test instance by finding the K nearest
	 * neighbours and using their labels to determine a majority neighbour.
	 */
	public static String findLabel(Vector test, InstanceSet trainingSet, int K){
		
		HashMap<Vector, Double> leaderboard = new HashMap<Vector, Double>(); //the current nearest neighbours.		
		double[] ranges = Calculator.getRanges(trainingSet.getInstances());
		
		for(Vector trainer: trainingSet.getInstances()){ //for each Vector in the training set find how close it is and update the leaderboard.
			double distance = Calculator.getDistance(test, trainer, ranges);
			
			if(leaderboard.size()<K){ 				//if nearest neighbours number less than K
				leaderboard.put(trainer, distance);	//simply add the new value to nearest neighbours
			}else{
				updateLeaderboard(trainer, distance, leaderboard); //otherwise check to see that it is nearer than the current nearest neighbours
			}
		}
		String label = countNearbyLabels(leaderboard);
		return label;		
	}
	
	/**
	 * Updates a map of the nearest neighbours. 
	 */
	public static void updateLeaderboard(Vector contender, double score, HashMap<Vector, Double> leaderboard){
		
		Vector demoted = contender;
		double worst = score;				//the contender starts as the worst score
		
		for(Vector v: leaderboard.keySet()){//for each vector in the leaderboard
			if(worst<leaderboard.get(v)){	//if the current worst scoring vector has a lower (better) score than this element
				demoted = v;
				worst = leaderboard.get(v);
			}
		}
		
		if(demoted == contender){
			return;
		}else{
			leaderboard.remove(demoted);
			leaderboard.put(contender, score);
		}		
	}
	
	/**
	 * Counts each different label present in the nearest neighbours to determine the classification of the instance.
	 */
	public static String countNearbyLabels(HashMap<Vector, Double> neighbours){
		
		Map<String, Integer> labels = new HashMap<String, Integer>();
		
		for(Vector v : neighbours.keySet()){
			String label = v.getActualLabel();
			if(!labels.containsKey(label)){				//If no example of the label has been counted yet
				labels.put(label, 1);					//Record it and set the count to 1
			}else{		
				Integer count = labels.get(label) + 1;	//Get the count and increment it
				labels.put(label, count);				
			}
		}
		
		String contender = null;
		Integer score = 0;
		
		for(String label : labels.keySet()){
			if(labels.get(label) > score){
				contender = label;
				score = labels.get(label);
			}
		}
		
		return contender;
	}
	
}
