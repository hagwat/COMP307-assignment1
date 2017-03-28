package decisiontrees;

import java.io.File;
import java.util.*;
import javax.swing.JFileChooser;


public class InstanceSet {

	private String[] outcomes = new String[2];
	private String[] attributes;
	private List<Instance> instances = new ArrayList<Instance>();


	public InstanceSet(String filename){
		loadInstanceSet(filename);
	}

	/**
	 * Populates the instance set. Assumes only two outcomes are possible. Also
	 * assumes each attribute can only be true or false.
	 */
	public void loadInstanceSet(String filename){
		File f;
		try{
			if(filename == null){
				f = useDialogBox();
			}else{
				f = new File(filename);
			}

			Scanner sc = new Scanner(f);

			outcomes[0] = sc.next(); outcomes[1] = sc.next();

			ArrayList<String> tempList = new ArrayList<String>();
			while(!(sc.hasNext(outcomes[0])||sc.hasNext(outcomes[1]))){	//while the next token is not an outcome
				tempList.add(sc.next());
			}
			attributes = tempList.toArray(new String[tempList.size()]);	//converts the arraylist to an array

			while(sc.hasNext()){										//for each remaining line
				String outcome = sc.next();
				boolean[] attrVals = new boolean[attributes.length];
				for(int i = 0; i < attrVals.length; i++){				//for each attribute
					if(sc.hasNext("true")){
						attrVals[i] = true;								//get truth value
						sc.next();
					}else if(sc.hasNext("false")){
						attrVals[i] = false;
						sc.next();
					}else{
						throw new Exception("Scanning Error");
					}
				}

				Instance newInst = new Instance(outcome, attrVals);
				instances.add(newInst);
			}

			sc.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void printInstanceSet(){

		System.out.println(outcomes[0]+" "+outcomes[1]);

		for(int i = 0; i < attributes.length; i++){
			System.out.print(attributes[i]+" ");
		}
		System.out.println();

		for(Instance inst : instances){
			inst.print();
		}

	}

	/**
	 * Opens a dialog box and returns a file.
	 */
	public File useDialogBox() throws Exception{
		File file = null;
		final JFileChooser fc = new JFileChooser();
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}else{
			throw new Exception("didn't choose file");
		}
		return file;
	}

	public String[] getOutcomes(){
		return this.outcomes;
	}

	public String[] getAttributes(){
		return this.attributes;
	}

	public List<Instance> getInstances(){
		return this.instances;
	}
}
