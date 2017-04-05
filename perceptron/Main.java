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
		List<Imgc> images = constructImageArrays(args[0]);
		Feature[] features = new Feature[NUM_FEATURES];
		Random rand = new Random(666);

		for(int i = 0; i < NUM_FEATURES; i++){
			boolean[][] array = images.get(0).getArray();
			features[i] = new Feature(array.length, array[0].length, rand);
		}

		Perceptron perc = new Perceptron(features, rand);

		epoch(perc, images);	//Do this 1000 times or till convergence

	}


	public static void epoch(Perceptron perc, List<Imgc> images){

		for(Imgc image: images){
			boolean approved = perc.approve(image);
			if(image.getClass().equals("Yes") == approved){	//If yes and approved or not yes and not approved
				System.out.println("Correct");
				image.setCorrect(true);
			}else{
				System.out.println("Incorrect");
				image.setCorrect(false);
			}
		}

		/*
		 * Todo training method on perceptron.
		 *
		 * If -ve example and wrong (ie,
		 * weights on active features are too high) Subtract feature vector from
		 * weight vector.
		 *
		 * If +ve example and wrong (ie, weights on active
		 * features are too low) Add feature vector to weight vector
		 *
		 * What is feature vector???
		 * When do I change weights? After each image? After each epoch? Once or for each image?
		 */

		perc.train(images);	//Changes weights on perceptron depending on which images perc got right






	}

	public static List<Imgc> constructImageArrays(String filename) {
		List<Imgc> arrays = new ArrayList<Imgc>();
		try {
			File f = new File(filename);
			Scanner sc = new Scanner(f);
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");

			while(sc.hasNext()){
				sc.next();	//filetype
				sc.findWithinHorizon("#",0);

				String cls = sc.next();	//classification
				System.out.println(cls);

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

			    Imgc img = new Imgc(newimage, cls);
			    arrays.add(img);
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrays;
	}



}

