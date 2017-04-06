package perceptron;

import java.util.Random;

public class Feature {

	private int[][] pixels;
	private boolean[] sign;
	

	public Feature(int rows, int cols, Random rand){
		this.pixels = new int[4][2];
		this.sign = new boolean[4];

		for(int i = 0; i<4; i++){
			int newX = rand.nextInt(cols-1);
			int newY = rand.nextInt(rows-1);
			while(duplicate(newX, newY)){
				newX = rand.nextInt(cols-1);
				newY = rand.nextInt(rows-1);
			}
			pixels[i][0] = newX;
			pixels[i][1] = newY;
			sign[i] = rand.nextBoolean();
		}

	}

	public boolean duplicate(int newX, int newY){

		for(int i = 0; i<pixels.length ; i++){
			if(pixels[i][0] == newX && pixels[i][1] == newY){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if at least 3 of the pixels connected agree with the feature
	 */
	public boolean approve(Imgc image){
		int count = 0;

		for(int i = 0; i<4 ; i++){
			int col = pixels[i][0];
			int row = pixels[i][1];
			if(image.getArray()[row][col]==sign[i]){
				count++;
			}
		}

		if(count>=3){
			return true;
		}
		return false;
	}

	public void print(){
		
		for(int i = 0; i<4 ; i++){
			System.out.print("Column: "+pixels[i][1]+" Row: "+pixels[i][0]);
			if(sign[i]){
				System.out.println(" [1]");
			}else{
				System.out.println(" [0]");
			}
		}		
		System.out.println();
		}










}
