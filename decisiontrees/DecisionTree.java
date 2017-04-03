package decisiontrees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Holder class for decision tree nodes. This class might just get absorbed back
 * into the InstanceSet or it might not.
 */
public class DecisionTree {

	private String[] outcomes;
	private String[] attributes;
	private List<Instance> instances;
	private Node root;

	/**
	 * Holds the information from the InstanceSet
	 */
	public DecisionTree(String[] outcomes, String[] attributes, List<Instance> instances){
		this.outcomes = outcomes;
		this.attributes = attributes;
		this.instances = instances;

		this.root = new Node(attributes, instances, outcomes, "root");

		traverse();
	}

	public void traverse(){
		traverse(root, 0);
	}

	public void traverse(Node node, int indent){
		node.print(indent);

		if(node.getNodeType() == 1){
			traverse(node.leftChild, indent+1);
			traverse(node.rightChild, indent+1);
		}
	}

	/**
	 * Tests the efficacy of the decision tree by comparing each test outcome with the predicted outcome for that instance.
	 * Or, remember the proportion of instances in the test set who had the same attributes as a leaf node and compare it to the predicted proportion.
	 */
	public void test(InstanceSet testSet){
		System.out.println("Testing test set");

		List<InstHolder> similarCases = new ArrayList<InstHolder>();

		for(Instance inst: testSet.getInstances()){

			Node current = root;	//Start with the root node

			//recurse down children until find a leaf
			while(current.getNodeType()==1){	//Node is parent
				int attrIndex = current.leftChild.definingAttributeIndex();
				if(inst.getAttrValues()[attrIndex]==true){	//Instance has true value for defining attribute of children
					current = current.leftChild;
				}else{
					current = current.rightChild;
				}
			}

			//check if node is empty. shouldn't happen. I think
			if(current.getNodeType()==2){
				System.out.println("Empty node this is awkward");
			}

			//add node to holder class
			boolean[] usedArray = current.getUsedAttrs();
			boolean hasExistingHolder = false;

			for(InstHolder holder: similarCases ){	//for each group of instances
				if(holder.hasSimilar(inst, usedArray)){	//if instance fits the holder
					holder.add(inst);
					hasExistingHolder = true;
					break;
				}
			}

			if(!hasExistingHolder){	//If no holder exists for instance
				similarCases.add(new InstHolder(inst, usedArray, current.getProbability()));//adds instance to new holder
			}
		}

		//check each holder to see how closely the held instances match the probability



		for(InstHolder holder: similarCases){

			double outcome =0;
			double count =0;
			for(Instance inst :holder.instances){
				System.out.print(inst.getOutcome()+" ");
				if(inst.getOutcome().equals(outcomes[0])){	//outcome1
					outcome++;
				}
				count++;
			}
			System.out.println();
			double outcomeRate = outcome/count;
			double probability = holder.probability;
			System.out.println("Rate "+outcomes[0]+" happened: "+outcomeRate+", Predicted rate: "+probability);
			holder.print();
			System.out.println();
		}

		System.out.println("Mean deviation: "+calculateAccuracy(similarCases));

	}

	/**
	 * calculates accuracy by comparing expected rates of outcomes with test
	 * rates. Tallies them all up and computes a mean.
	 */
	public double calculateAccuracy(List<InstHolder> similarCases){
		double totalDevi = 0;
		double count = 0;

		for(InstHolder holder: similarCases){
			double[] tuple = holder.predictionDeviation();
			totalDevi += (tuple[0]*tuple[1]);	//Add average deviation for each instance
			count += tuple[1];					//increment count for each instance
		}


		return totalDevi/count;
	}

	/**
	 * Has instances which share a leaf node.
	 * Stores attribute combo of the leaf node and can check if belongs to
	 * this InstHolder by comparing to leaf node attributes.
	 * To do this check used status of attributes.
	 * For each used attribute check boolean value in Instance.
	 */
	private class InstHolder{

		boolean[] holderAttrs;
		boolean[] holderUsed;
		double probability;	//expected probability of outcome1 aka outcomes[0] according to training set

		List<Instance> instances = new ArrayList<Instance>();


		public InstHolder(Instance firstInst, boolean[] used, double probability){
			this.holderAttrs = firstInst.getAttrValues();
			this.holderUsed = used;
			instances.add(firstInst);
			this.probability = probability;
		}

		/**
		 * Checks if an instance is from the same node as the instances stored here by
		 * seeing if its attribute values are the same for all used attributes.
		 */
		public boolean hasSimilar(Instance inst, boolean[] used){

			boolean[] attrVals = inst.getAttrValues();

			for(int i = 0; i < attributes.length; i++){	// for each attribute

				if(used[i] != holderUsed[i]){ 	//If holder used is not equal to instance used
					return false;
				}

				if(used[i] == true){
					if(attrVals[i] != holderAttrs[i]){	//or if a used value doesnt match
						return false;
					}
				}

			}

			return true;	//used set and used attribute match up so is similar
		}

		public void add(Instance inst){
			instances.add(inst);
		}

		public void print(){
			System.out.print("Holder: ");
			for(int i = 0; i<holderAttrs.length;i++){
				if(holderUsed[i]){
					System.out.print(" "+ attributes[i]);
					if(holderAttrs[i]){
						System.out.print(" True");
					}else{
						System.out.print(" False");
					}
				}else{
					System.out.print(" -NOT USED-");
				}

			}
			System.out.println(" Num instances: "+instances.size()+", Expected probability: "+probability);
		}

		/**
		 * Take the mean result and obtains the difference from the prediction
		 * and returns it along with the number of instances.
		 */
		public double[] predictionDeviation(){
			double totalOutcome = 0;
			double count = 0;
			double meanOutcome;
			double predictedOutcome = probability;
			double deviation;

			for(Instance inst: instances){
				if(inst.getOutcome().equals(outcomes[0])){
					totalOutcome++;
				}
				count++;
			}
			meanOutcome = totalOutcome/count;

			if(predictedOutcome > meanOutcome){
				deviation = predictedOutcome - meanOutcome;
			}else{
				deviation = meanOutcome - predictedOutcome;
			}

			System.out.println("Num instances: "+instances.size()+", Mean = "+meanOutcome+
					", Predicted = "+predictedOutcome+", Deviation = "+deviation);

			return new double[]{deviation, count};
		}

	}






}
