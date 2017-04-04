package perceptron;

import java.io.File;
import java.util.*;

/**
 * Construct an image array there is a line for dimensions then a long string of
 * characters with possible newline characters (ignore those) just go by the
 * dimensions to decide a new line.
 */
public class Main {

	public static final int NUM_FEATURES = 60;

	public static void main(String[] args){
		List<boolean[][]> images = constructImageArrays(args[0]);	//TODO include image classification. Check what it is that gives an image its classification at home where can view
		Feature[] features = new Feature[NUM_FEATURES];
		Random rand = new Random(666);


		for(int i = 0; i < NUM_FEATURES; i++){
			features[i] = new Feature(images.get(0).length, images.get(0)[0].length, rand);
		}

		for(Feature f: features){
			f.print();
		}

		Perceptron perc = new Perceptron(features, rand);

		for(boolean[][] image: images){
			perc.approve(image);
		}

		/*
		 * TODO training method on perceptron.
		 *
		 * If -ve example and wrong (ie,
		 * weights on active features are too high) Subtract feature vector from
		 * weight vector.
		 *
		 * If +ve example and wrong (ie, weights on active
		 * features are too low) Add feature vector to weight vector
		 *
		 * What is feature vector???
		 */
	}




	public static List<boolean[][]> constructImageArrays(String filename) {
		List<boolean[][]> arrays = new ArrayList<boolean[][]>();
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			//sc.useDelimiter("\\s+");
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");

			while(sc.hasNext()){
				sc.next();//filetype
				sc.next();//classification

				int rows = sc.nextInt();
			    int cols = sc.nextInt();
			    boolean[][] newimage = new boolean[rows][cols];
			    for (int r=0; r<rows; r++){
			    	for (int c=0; c<cols; c++){
			    		newimage[r][c] = (sc.findWithinHorizon(bit,0).equals("1"));
			    		//System.out.print(newimage[r][c]);
			    	}
			    	//System.out.println();
			    }

			    arrays.add(newimage);
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arrays;
	}



}


