package knearestneighbours;

import java.io.IOException;

public class Main {

	/**
	 * Load the training set. Then load the test set and for each test instance
	 * compare with training set to decide on label. Then for each label compare
	 * with actual label to find accuracy.
	 */
	public static void main(String[] args) {
				
		//InstanceSet trainingSet = new InstanceSet(args[0]); 	TODO
		//InstanceSet testSet = new InstanceSet(args[1]);

		InstanceSet trainingSet = new InstanceSet(null);
		InstanceSet testSet = new InstanceSet(null);
		
		int K = 5;		
		System.out.print("Choose a value for K:");
		
		try {
			K = System.in.read();
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		KNrstNghbrAlgrthm.findLabels(testSet, trainingSet, K);
		testSet.printAllVectors(testSet.getInstances());
	}

}
