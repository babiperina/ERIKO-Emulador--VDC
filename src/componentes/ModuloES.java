package componentes;

import java.util.Arrays;

import utils.Constantes;

public class ModuloES {

	// o tamanho do buffer está em bits
	private int bufferCI = -1;
	int[] buffer = new int[Constantes.SIZE_e_s_buffer];
	// instrução que entra
	long[] code = null;
	private String inInstruction = "";

	public ModuloES() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = -1;
		}
	}

	public void pullInstructionFromEncoder() {
	}

	public void moveInInstructionToBuffer() {
	}

	public String getInInstruction() {
		return inInstruction;
	}

	public void setInInstruction(String inInstruction) {
		this.inInstruction = inInstruction;
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
