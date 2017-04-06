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
	public static final int EPOCHS = 1000;

	public static void main(String[] args){
		List<Imgc> images = constructImageArrays(args[0]);
		Feature[] features = new Feature[NUM_FEATURES];
		Random rand = new Random(665);

		//Constructing features
		for(int i = 0; i < NUM_FEATURES; i++){
			boolean[][] array = images.get(0).getArray();
			features[i] = new Feature(array.length, array[0].length, rand);
		}

		//Perceptron using all images as training set
		Perceptron perc = new Perceptron(features, rand);
		boolean converges = false;
		for (int i = 0; i < EPOCHS; i++) {
			if (converges) {
				perc.printFeatures();
				System.out.println("Number of epochs: " + (i + 1));
				break;
			}
			converges = epoch(perc, images);
		}		
		/*
		//Cross validation with same features but new perceptron. Test set takes 10% of instances
		for( int i = 0; i < 5; i++){
			crossValidate(features, images, rand);
		}
		*/
	}

	/**
	 * Takes 10% of instances and uses them as a test set. 
	 * As of now does not take affirmative action to ensure all classes represented.
	 */
	public static void crossValidate(Feature[] features, List<Imgc> images, Random rand){
		Imgc[] allImages = new Imgc[images.size()];
		images.toArray(allImages);
		Imgc[] testImages = new Imgc[allImages.length/10];
		
		Perceptron perc = new Perceptron(features, rand);
		
		
		for(int i = 0; i < testImages.length; i++){
			int imgIndex = rand.nextInt(allImages.length);
			boolean taken = false;
			for(int j = 0; j < i; j++){
				if(allImages[imgIndex] == (testImages[j])){
					taken = true;
				}
			}
			if(taken==false){
				testImages[i] = allImages[imgIndex];
			}else{
				i--;
			}
		}		

		List<Imgc> trainingImages = new ArrayList<Imgc>();
		for(int i = 0; i < allImages.length; i++){
			boolean taken = false;
			for(int j = 0; j< testImages.length; j++){
				if(allImages[i]==testImages[j]){
					taken = true;
					break;
				}
			}
			if(taken == false){
				trainingImages.add(allImages[i]);
			}
		}
		
	
		
		boolean converges = false;
		for (int i = 0; i < EPOCHS; i++) {
			if (converges) {
				break;
			}
			converges = epoch(perc, trainingImages);
		}
		
		int right = 0;
		int total = 0;
		
		for(Imgc image: testImages){
			boolean approved = perc.approve(image);
			if(image.getClassification().equals("Yes") == approved){	//If yes and approved or not yes and not approved
				right++;
			}
			total++;
		}
		System.out.println("Cross validation "+right+"/"+total);
		
	}

	public static boolean epoch(Perceptron perc, List<Imgc> images){

		perc.train(images);
		
		int right = 0;
		int total = 0;

		for(Imgc image: images){
			boolean approved = perc.approve(image);
			if(image.getClassification().equals("Yes") == approved){	//If yes and approved or not yes and not approved
				right++;
			}
			total++;
		}
		System.out.println(right+"/"+total);
	
		if(right==total)
			return true;
		return false;
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
				//System.out.println(cls);

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


