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

		switch(node.getNodeType()){		//does this work?
		case 1:		//Parent
			traverse(node.leftChild, indent+1);
			traverse(node.rightChild, indent+1);
			break;
		case 2:		//Impure

			break;
		case 3:		//Pure

			break;
		}




	}







}
