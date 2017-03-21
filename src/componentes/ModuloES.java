package componentes;

import java.util.Arrays;

import utils.Constantes;

public class ModuloES {
	
	private static final String TAG = "ModuloES.class";
	
	//o tamanho do buffer está em bits
	private int bufferCI = -1;
	int[] buffer = new int[Constantes.SIZE_e_s_buffer*8];
	
	public int getBufferCI(){
		return bufferCI;
	}
	
	public int getBufferSize(){
		return buffer.length;
	}
	
	@Override
	public String toString() {
		return "ModuloES [BufferSize: " + getBufferSize() + "bits] [buffer=" + Arrays.toString(buffer) + "]";
	}

	static {
		System.out.println(TAG + " criada");
	}

}
