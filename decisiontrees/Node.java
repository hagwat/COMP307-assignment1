package decisiontrees;

import java.util.List;
import java.util.Set;

/**
 * Decision tree node.
 */
public class Node {

	private String[] attributes;
	private List<Instance> instances;
	private Node leftChild;
	private Node rightChild;

	public Node(String[] attributes, List<Instance> instances, Set<String> usedAttributes){
		System.out.println("Creating node");

		this.attributes = attributes;
		this.instances = instances;

		if(pure()){
			System.out.println("This node is pure");
			return;
		}

		/*
		 * Calculate best purity of remaining attributes.
		 */
		String purestAttr = null;
		double lowestPurity = 2; //higher than any possible real purity value

		for(String attr : attributes){
			double purity = calculatePurity(attr);
			if(purity < lowestPurity){
				purestAttr = attr;
				lowestPurity = purity;
			}
		}

		System.out.println("Lowest impurity: "+lowestPurity+" Purest attribute: "+purestAttr);

		//TODO Have to make array of remaining attributes as well as sublists of instances for each child node.



	}

	/**
	 * Calculates the proportion of times that one outcome occurred over the other.
	 * @param attribute
	 * @return
	 */
	public double calculatePurity(String attribute){

		int attrIndex = -1;
		for(int i = 0; i < attributes.length; i++){
			if(attribute.equals(attributes[i])){
				attrIndex = i;
			}
		}

		//counts for various outcomes
		int attrTrueOutcome1 =0;
		int attrTrueOutcome2 =0;
		int attrFalseOutcome1 =0;
		int attrFalseOutcome2 =0;

		String outcome1 = instances.get(0).getOutcome(); //Finds one of the two outcomes. It is assumed only two possible outcomes exist.

		for(Instance inst : instances){
			if(inst.getAttrValues()[attrIndex]){		//If the attribute is true
				if(inst.getOutcome().equals(outcome1)){	//If outcome1 happened
					attrTrueOutcome1++;
				}else{									//If outcome2 happened (it is inferred)
					attrTrueOutcome2++;
				}
			}else{										//If the attribute is false
				if(inst.getOutcome().equals(outcome1)){
					attrFalseOutcome1++;
				}else{
					attrFalseOutcome2++;
				}
			}
		}

		double m = attrTrueOutcome1;
		double n = attrTrueOutcome2;
		double impurityForTrue = (m*n)/((m+n)*(m+n));

		System.out.println("Calculating (im)purity for true: "+(int)m+"/"+(int)(m+n)+" "+impurityForTrue);

		double o = attrFalseOutcome1;
		double p = attrFalseOutcome2;
		double impurityForFalse = (o*p)/((o+p)*(o+p));

		System.out.println("Calculating (im)purity for false: "+(int)o+"/"+(int)(o+p)+" "+impurityForFalse);

		double averageImpurity = (impurityForTrue + impurityForFalse)/2;

		System.out.println("Average impurity: "+averageImpurity);

		return averageImpurity;
	}

	/**
	Is this node pure?
	 */
	public boolean pure(){
		String outcome1 = instances.get(0).getOutcome();
		int count1 = 0;
		int count2 = 0;

		for(Instance inst : instances){
			if(inst.getOutcome().equals(outcome1)){	//If outcome1 happened
				count1++;
			}else{									//If outcome2 happened
				count2++;
			}
		}

		if(count1==0||count2==0){
			return true;
		}
		return false;
	}

}
