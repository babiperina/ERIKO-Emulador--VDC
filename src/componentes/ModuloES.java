package componentes;

import java.util.Arrays;

import utils.Constantes;

public class ModuloES {
	
	private static final String TAG = "ModuloES.class";
	
	int[] buffer = new int[Constantes.SIZE_e_s_buffer];
	
	
	public int getBufferSize(){
		return buffer.length;
	}
	
	@Override
	public String toString() {
		return "ModuloES [BufferSize: " + getBufferSize() + "bytes] [buffer=" + Arrays.toString(buffer) + "]";
	}

	static {
		System.out.println(TAG + " criada");
	}

}
