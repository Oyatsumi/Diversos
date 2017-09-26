import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a non-recursive permutation algorithm
 * @author Érick Oliveira Rodrigues (erickr@id.uff.br)
 */
public class Permutation {

	private HashMap<Integer, int[][]> cache = new HashMap<Integer, int[][]>();
	
	private int getHash(final int[] chars, final int k){
		return chars.hashCode() + Integer.hashCode(k);
	}
	private int getHash(final ArrayList<Integer> chars, final int k){
		return chars.hashCode() + Integer.hashCode(k);
	}
	
	
	
	public void print(int[][] out){
		for (int k=0; k<out.length; k++){
			for (int l=0; l<out[0].length; l++){
				System.out.print(out[k][l] + ",");
			}
			System.out.print("\n");
		}
	}
	
	public int[][] get(final ArrayList<Integer> chars, final int k){
		if (cache.containsKey(getHash(chars, k))) return cache.get(getHash(chars,k));
		
		int[][] out = new int[(int) Math.pow(chars.size(), k)][k];
		
		int[] marker = new int[k];
		
		int counter = 0;
		
		boolean finished = false;
		do{
			finished = true;
			for (int m=0; m<marker.length; m++) finished &= marker[m] == chars.size() - 1;
			
			for (int m=0; m<marker.length; m++){
				out[counter][m] = chars.get(marker[m]);
			}
			//increment the marker
			for (int m=marker.length - 1; m>= 0; m--){
				if (marker[m] == chars.size() - 1) continue;
				else{
					marker[m] ++;
					for (int n=m + 1; n<marker.length; n++) marker[n] = 0;
					break;
				}
			}
			
			counter++;
			
			
		}while (!finished);
		
		cache.put(getHash(chars, k), out);
		
		return out;
	}
	
	
	public int[][] get(final int[] chars, final int k){
		if (cache.containsKey(getHash(chars, k))) return cache.get(getHash(chars,k));
		
		int[][] out = new int[(int) Math.pow(chars.length, k)][k];
		
		int[] marker = new int[k];
		
		int counter = 0;
		
		boolean finished = false;
		do{
			finished = true;
			for (int m=0; m<marker.length; m++) finished &= marker[m] == chars.length - 1;
			
			for (int m=0; m<marker.length; m++){
				out[counter][m] = chars[marker[m]];
			}
			//increment the marker
			for (int m=marker.length - 1; m>= 0; m--){
				if (marker[m] == chars.length - 1) continue;
				else{
					marker[m] ++;
					for (int n=m + 1; n<marker.length; n++) marker[n] = 0;
					break;
				}
			}
			
			counter++;
			
			
		}while (!finished);
		
		cache.put(getHash(chars, k), out);
		
		return out;
	}
	
	
}
