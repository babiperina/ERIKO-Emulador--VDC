package componentes;

import java.util.Arrays;

import utils.Constantes;

public class ModuloES {


	// o tamanho do buffer está em bits
	private int bufferCI = -1;
	int[] buffer = new int[Constantes.SIZE_e_s_buffer * 8];
	String in;

	public ModuloES() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = -1;
		}
	}
	
	void receivedInstructionsFromEncoder(){
		
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public void incrementBufferCI() {
		bufferCI++;
	}

	public int getBufferCI() {
		return bufferCI;
	}

	public int getBufferSize() {
		return buffer.length;
	}

	@Override
	public String toString() {
		return "ModuloES [BufferSize: " + getBufferSize() + "bits] [buffer=" + Arrays.toString(buffer) + "]";
	}


}
