
/**
 * Non-recursive combination function.
 * @author Érick Oliveira Rodrigues (erickr@id.uff.br)
 */
public class Combination {

	Combination(){
		
	}
	
	
	/**
	 * Returns the results, or the combinations
	 * @param chars - chars to be combined
	 * @param k - number of items per combination
	 * @return
	 * @author Érick Oliveira Rodrigues (erickr@id.uff.br)
	 */
	public int[][] get(final int[] chars, final int k){
		
		final int numCombinations = factorial(chars.length)/(factorial(k)*factorial(chars.length - k));
		
		int[][] result = new int[numCombinations][k];
		
		int[] markers = new int[k];
		
		for (int l=0; l<markers.length; l++){
			markers[l] = l;
		}
		
		int itCounter = 0;
		final int incIndex = markers.length - 1;
		
		while(markers[0] <= chars.length - (markers.length)){
				
			while(markers[incIndex] < chars.length - (markers.length - 1 - incIndex)){
				
				for (int l=0; l<markers.length; l++){
					result[itCounter][l] = chars[markers[l]];
				}
				markers[incIndex]++;
				itCounter++;
				
			}
			for (int l=incIndex; l>=0; l--){
				if (markers[l] < chars.length - (markers.length - 1 - l)){
					markers[l]++;
					
					for (int l2=l; l2+1<markers.length; l2++){
						markers[l2 + 1] = markers[l2] + 1;
					}
					break;
				}
			}
			
		}
		
		return result;
	}
	
	private int factorial(final int number){
		int n = number - 1;
		int result = number;
		while (n > 1){
			result *= n;
			n--;
		}
		return result;
	}
	
}
