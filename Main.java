package knearestneighbours;

public class Main {

	/**
	 * Load the training set. Then load the test set and for each test instance
	 * compare with training set to decide on label. Then for each label compare
	 * with actual label to find accuracy.
	 */
	public static void main(String[] args) {
		
		
		
		InstanceSet trainingSet = new InstanceSet(args[0]);
		InstanceSet testSet = new InstanceSet(args[1]);

		testSet.printAllVectors(testSet.getInstances());
		KNrstNghbrAlgrthm.findLabels(testSet, trainingSet);
		testSet.printAllVectors(testSet.getInstances());

	}

}
