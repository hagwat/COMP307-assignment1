package decisiontrees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Decision tree node.
 */
public class Node {

	private String[] attributes;
	private List<Instance> instances;
	public Node leftChild; //The chosen attribute is true
	public Node rightChild;//The chosen attribute is false
	private int nodeType;
	private double probability;
	private String[] outcomes;
	private String definingAttribute;


	public Node(String[] attributes, List<Instance> instances, String[] outcomes, String definingAttribute){
		System.out.println("Creating node");
		System.out.println("Number of instances in this node: "+instances.size());

		this.attributes = attributes;
		this.instances = instances;
		this.outcomes = outcomes;
		this.definingAttribute = definingAttribute;

		if(pure()){
			System.out.println("This node is pure");
			nodeType = 3;	//Node is pure
			probability = getProbability();
			return;
		}

		split();
	}

	/**
	 * Creates two child nodes by calculating the purest attribute of the node
	 * and sorting the instances by truth value of the given attribute. Returns
	 * 1 for parent, 2 for impure child.
	 */
	public void split(){
		/*
		 * Calculate best purity of remaining attributes.
		 */
		String purestAttr = null;
		double lowestPurity = 2; //higher than any possible real purity value
		int usedCount = 0;

		for(String attr : attributes){
			if(!used(attr)){
				double purity = calculatePurity(attr);
				if(purity < lowestPurity){
					purestAttr = attr;
					lowestPurity = purity;
				}
			}else{
				usedCount++;
			}
		}

		/*
		 * If all attributes are used but the node is not pure then it
		 * should terminate. As is the node will attempt to call getAttrIndex
		 * with a null attribute which will return -1 which will result in left
		 * and right nodes being created with no instances which will then
		 * terminate. Instead we should terminate with an impure node.
		 */

		if(usedCount >= attributes.length){
			probability = getProbability();
			nodeType = 2;	//Node is impure
			return;
		}

		System.out.println("Lowest impurity: "+lowestPurity+" Purest attribute: "+purestAttr);


		// Sort into instances with purestattr true and purestattr false

		List<Instance> trueInsts = new ArrayList<Instance>();
		List<Instance> falseInsts = new ArrayList<Instance>();

		int attrIndex = getAttrIndex(purestAttr);
		for(Instance inst: instances){
			if(inst.getAttrValues()[attrIndex]){
				trueInsts.add(inst);
			}else{
				falseInsts.add(inst);
			}
		}

		leftChild = new Node(attributes, trueInsts, outcomes, purestAttr);
		rightChild = new Node(attributes, falseInsts, outcomes, purestAttr);

		nodeType = 1;	//Node is parent
		return;
	}

	/**
	 * Calculates the proportion of times that one outcome occurred over the
	 * other. If all instances are true or all false for the given attribute
	 * dividing by zero will happen. This is checked for by the used(String attribute) method.
	 *
	 */
	public double calculatePurity(String attribute){

		int attrIndex = getAttrIndex(attribute);

		//counts for various outcomes
		int attrTrueOutcome1 =0;
		int attrTrueOutcome2 =0;
		int attrFalseOutcome1 =0;
		int attrFalseOutcome2 =0;

		for(Instance inst : instances){
			if(inst.getAttrValues()[attrIndex]){		//If the attribute is true
				if(inst.getOutcome().equals(outcomes[0])){	//If outcome1 happened
					attrTrueOutcome1++;
				}else{									//If outcome2 happened (it is inferred)
					attrTrueOutcome2++;
				}
			}else{										//If the attribute is false
				if(inst.getOutcome().equals(outcomes[0])){
					attrFalseOutcome1++;
				}else{
					attrFalseOutcome2++;
				}
			}
		}

		double m = attrTrueOutcome1;
		double n = attrTrueOutcome2;
		double impurityForTrue = (m*n)/((m+n)*(m+n));

		//System.out.print("(Im)purity for true: "+(int)m+"/"+(int)(m+n)+" "+impurityForTrue+" ");

		double o = attrFalseOutcome1;
		double p = attrFalseOutcome2;
		double impurityForFalse = (o*p)/((o+p)*(o+p));

		//System.out.print("(Im)purity for false: "+(int)o+"/"+(int)(o+p)+" "+impurityForFalse+" ");

		double averageImpurity = (impurityForTrue + impurityForFalse)/2;

		//System.out.println(attribute+" Average impurity: "+averageImpurity);

		return averageImpurity;
	}

	/**
	 * Finds the index of the attribute string.
	 */
	public int getAttrIndex(String attr){
		int attrIndex = -1;
		for(int i = 0; i < attributes.length; i++){
			if(attr.equals(attributes[i])){
				attrIndex = i;
			}
		}
		return attrIndex;
	}

	/**
	 * Returns the probability that node is of outcomes[0].
	 */
	public double getProbability(){
		double count1 = 0;
		double count2 = 0;
		for(Instance inst : instances){
			if(inst.getOutcome().equals(outcomes[0])){
				count1++;
			}else{
				count2++;
			}
		}
		double probability = count1/(count1+count2);
		if(probability!=1.0&&probability!=0.0){
			System.out.println("impure");
		}
		//System.out.println("Outcomes are "+count1+" to "+count2+", probability is "+probability);
		return probability;
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

	/**
	 * Is this attribute "used": This is when all instances in the node are true
	 * or all false with regard to this attribute. This can happen in one of two
	 * ways. Either a previous node sorted its children by this attribute, or it
	 * happened incidentally.
	 */
	public boolean used(String attribute){
		int attrIndex = getAttrIndex(attribute);
		int count1 = 0;
		int count2 = 0;
		for(Instance inst: instances){
			if(inst.getAttrValues()[attrIndex]){
				count1++;
			}else{
				count2++;
			}
		}
		if(count1==0||count2==0){
			//System.out.println(attribute+" Used");
			return true;
		}
		return false;
	}

	public void print(int indent){
		if(definingAttribute.equals("root")){
			return;	//dont need to print the root node
		}

		for(int i = 0; i < indent; i++){
			System.out.print("\t"); //Tab as many times as indent
		}
		//Print defining attribute and value eg "cloudy = True:"
		System.out.println(definingAttribute+" = "+instances.get(0).getAttrValues()[getAttrIndex(definingAttribute)]+":");

		if(nodeType == 2||nodeType == 3){	//if a leaf node
			for(int i = 0; i < indent+1; i++){
				System.out.print("\t"); //Tab as many times as indent+1
			}
			String classifier;
			double printedProb;	//The probability of the printed class happening. Therefore printedprob is never less than 0.5. Printedprob currently retired.
			if(probability>=0.5){
				classifier = outcomes[0];
				printedProb = probability;
			}else{
				classifier = outcomes[1];
				printedProb = 1 - probability;
			}

			System.out.println("Class "+classifier+", num instances = "+instances.size()+", prob = "+probability);
		}

	}

	/**
	 * Returns the attributes that have already been decided before this node.
	 */
	public boolean[] getUsedAttrs(){
		boolean[] usedArray = new boolean[attributes.length];
		for(int i = 0; i< attributes.length;i++){
			if(used(attributes[i])){
				usedArray[i] = true;
			}else{
				usedArray[i] = false;
			}
		}
		return usedArray;
	}


	public int getNodeType(){
		return nodeType;
	}

	public int definingAttributeIndex(){
		return getAttrIndex(definingAttribute);
	}

}
