
public abstract class Victor {

	
	public static void main(String[] args){
		char[] conjunto = new char[]{'a','b','c'};
		int p = 1;
		int[] marcadores = new int[p];
		
		for (int k=0; k<marcadores.length; k++) marcadores[k] = k; //resetando primeira vez.
		
		boolean terminou = false, checar = false; //só pra sair do loop
		while (!terminou){
			checar = true;
			for (int k=0; k<marcadores.length; k++){ //encerrar o while
				checar &= (marcadores[k] == conjunto.length-(marcadores.length-k)); //checar se os marcadores estão o máximo possível para a direita
			}
			if (checar) terminou = true;
			mover(conjunto, marcadores);
		}
	}
	
	public static int contador = 0;
	public static void mover(char[] conjunto, int[] marcadores){
		for (int k=marcadores.length-1; k>=0; k--){//iterar ao contrário o vetor marcadores
			if (marcadores[k] < conjunto.length-(marcadores.length-k)){ //se não for pra resetar
				for (int l=0; l<marcadores.length; l++){
					System.out.print(conjunto[marcadores[l]] + ",");
				}
				marcadores[k] ++;
				System.out.println();
				return;
			}
			//se for pra resetar \/
			if (marcadores[k] == conjunto.length-(marcadores.length-k) //se 3 = 3
					&& (contador) + marcadores.length <= conjunto.length){
				contador ++;
				for (int l=0; l<marcadores.length; l++){//printando a última combinação antes de resetar
					System.out.print(conjunto[marcadores[l]] + ",");
				}
				for (int l=0; l<marcadores.length; l++) marcadores[l] = l + contador; //resetando o vetor marcadores de novo
				System.out.println();
				return;
			}
		}
		return;
	}
	
}
