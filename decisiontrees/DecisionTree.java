package decisiontrees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
		
		this.root = new Node(attributes, instances, new HashSet<String>());
	}
	
	
}
