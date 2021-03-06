package decisiontrees;

public class Main {



	public static void main(String[] args) {
		InstanceSet trainingSet = new InstanceSet(args[0]);
		DecisionTree tree = new DecisionTree(trainingSet.getOutcomes(), trainingSet.getAttributes(), trainingSet.getInstances());
		InstanceSet testSet = new InstanceSet(args[1]);
		tree.test(testSet);
	}

}
