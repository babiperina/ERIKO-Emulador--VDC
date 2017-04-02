package componentes;

import java.util.Arrays;

import utils.Constantes;

public class Ram {
	
	
	//O tamanho da RAM est� em bits
	int[] celulas = new int[Constantes.SIZE_ram];
	
	
	public Ram() {
		for (int i = 0; i < Constantes.offset; i++) {
			celulas[i] = -1;
		}
	}
	
	public int getSize(){
		return celulas.length;
	}
	
	
	@Override
	public String toString() {
		return "Ram [Size: " + getSize() + "bytes] [SizeToInstruction: " + Constantes.offset + "bytes] [WordSize: " + Constantes.SIZE_word + "bits] [celulas=" + Arrays.toString(celulas) + "]";
	}



}
