package componentes;

import java.util.Arrays;

import utils.Constantes;

public class Ram {
	
	public static final String TAG = "Ram.class";
	
	//O tamanho da RAM está em bits
	int[] celulas = new int[Constantes.SIZE_ram];
	
	
	public Ram() {
	}
	
	public int getSize(){
		return celulas.length;
	}
	
	
	@Override
	public String toString() {
		return "Ram [Size: " + getSize() + "bits] [celulas=" + Arrays.toString(celulas) + "]";
	}


	static {
		System.out.println(TAG + " criada");
	}


}
