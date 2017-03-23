package knearestneighbours;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;


/**
 * Loads up an instance set and creates Vector objects on which operations can be performed.
 */
public class InstanceSet {

	private List<Vector> instances = new ArrayList<Vector>();
	private String filepath = "C:\\Users\\Hagwat\\workspace2\\COMP307-assignment1\\ass1-data\\part1";//needs to be more general...probably delete it by the end.
		
	public InstanceSet(String filename){
		
		//System.out.println(new File(".").getAbsoluteFile());
		//System.out.println(new File(filename).getAbsolutePath());
		
		loadInstanceSet(filename);
		
	}
	
	/**
	 * Populates the instance set.
	 */
	public void loadInstanceSet(String filename){			
		File f;
		try{
			if(filename == null){
				f = readFile();
			}else{
				f = new File(filename);
			}
			
			Scanner sc = new Scanner(f);			
			while(sc.hasNext()){				
				instances.add(new Vector(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.next()));
			}
			sc.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Opens a dialog box and returns a file.
	 */
	public File readFile() throws Exception{
		File file = null;		
		final JFileChooser fc = new JFileChooser(filepath);
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}else{
			throw new Exception("didn't choose file");
		}		
		return file;		
	}
	
	public void printAllVectors(List<Vector> vectors){
		for(Vector v: vectors){
			v.print();
		}
	}
	
	/**
	 * Should the collection and Vectors be immutable?
	 * @return
	 */
	public List<Vector> getInstances(){
		return instances;
	}	
	
}
