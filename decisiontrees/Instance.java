package decisiontrees;

public class Instance {

	private String outcome;
	private boolean[] attributeValues;
	
	public Instance(String outcome, boolean[] attributeValues){
		this.outcome = outcome;
		this.attributeValues = attributeValues;
	}
	
	public void print(){
		System.out.print(outcome+" ");
		
		for(int i = 0; i < attributeValues.length; i++){
			System.out.print(attributeValues[i]+" ");
		}
		System.out.println();
	}
	
	public boolean[] getAttrValues(){
		return attributeValues;
	}
	
	public String getOutcome(){
		return outcome;
	}
}
