
public class Main {

	//permutation
	public static void main(String[] args){
		Combination c = new Combination();
		
		int[] chars = new int[]{0,1,2,3,4,5,6,7,8,9};
		
		int[][] out = c.get(chars, 2);
		
		for (int k=0; k<out.length; k++){
			for (int l=0; l<out[0].length; l++){
				System.out.print(out[k][l] + ",");
			}
			System.out.print("\n");
		}
		
		System.exit(0);
		//
		
		Permutation p = new Permutation();
		
		chars = new int[]{-2,-1,0,1,2};
		
		out = p.get(chars, 10);
		
		for (int k=0; k<out.length; k++){
			for (int l=0; l<out[0].length; l++){
				System.out.print(out[k][l] + ",");
			}
			System.out.print("\n");
		}
	}
	
	//combination
	
	
	
	
}
