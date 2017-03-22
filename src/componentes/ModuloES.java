package componentes;

import java.util.Arrays;

import principal.Computador;
import utils.Constantes;

public class ModuloES {

	// o tamanho do buffer está em bits
	private int bufferCI = -1;
	int[] buffer = new int[Constantes.SIZE_e_s_buffer * 8];
	// instrução que entra
	long[] code = null;
	private String inInstruction = "";

	public ModuloES() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = -1;
		}
	}

	public void pullInstructionFromEncoder() {
		if (inInstruction == null || inInstruction == "") {
			Computador.encoder.sendInstructionsToESBuffer();
			if (code[0] != 0) {
				System.out.println(code[0] + " TO NA E/S");
				// type = 2; x=3;y=4;z=5
				for (int i = 0; i < code.length; i++) {
					if(inInstruction == null){
						inInstruction = "";
					}
					inInstruction += (i+2) + "" + Long.toBinaryString(code[i]);
				}
			}
		} else {
			// n pode pegar instrução pq ainda tem coisa pra ir pro buffer
		}
		moveInInstructionToBuffer();
	}

	public void moveInInstructionToBuffer() {
		System.out.println("Size:"+inInstruction.length() + " " + inInstruction);
		inInstruction = "";
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
