package componentes;

import java.util.Arrays;

import principal.Computador;
import utils.Constantes;

public class ModuloES {

	// o tamanho do buffer está em bits
	private int bufferCI = -1;
	int[] buffer = new int[Constantes.SIZE_e_s_buffer];
	// instrução que entra
	long[] code = null;
	private Object instructionIn[] = null;

	public ModuloES() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = -1;
		}
	}

	public Object[] getInstructionIn() {
		return instructionIn;
	}

	public boolean setInstructionIn(Object[] instructionIn) {
		if (this.instructionIn == null) {
			this.instructionIn = instructionIn;
			return true;
		}
		return false;
	}

	public void pullInstructionFromEncoder() {
		Computador.encoder.sendInstructionsToESBuffer();

		// ações
		System.out.print("TO NA ES: ");
		for (int i = 0; i < instructionIn.length; i++) {
			System.out.print(instructionIn[i] + " ");
		}
		System.out.println();
		moveInInstructionToBuffer();
		printBuffer();

		instructionIn = null;
	}

	public void printBuffer() {
		System.out.println(
				"ModuloES [BufferSize: " + getBufferSize() + "bytes] [buffer=" + Arrays.toString(buffer) + "]");
	}

	public void sendInstructionToRAM(){
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = -1;
		}
		System.out.println("Enviando instrução pra RAM");
	}
	
	public void moveInInstructionToBuffer() {
		if (buffer[0] == -1) {
			System.out.println("Movendo pro Buffer");
			byte[] b;
			if (instructionIn[0] instanceof Long) {
				long[] l = new long[instructionIn.length];
				for (int i = 0; i < l.length; i++) {
					l[i] = (long) instructionIn[i];
				}
				b = divideBytes(l);
			} else if (instructionIn[0] instanceof Integer) {
				int[] inti = new int[instructionIn.length];
				for (int i = 0; i < inti.length; i++) {
					inti[i] = (int) instructionIn[i];
				}
				b = divideBytes(inti);
			} else {
				short[] s = new short[instructionIn.length];
				for (int i = 0; i < s.length; i++) {
					s[i] = (short) instructionIn[i];
				}
				b = divideBytes(s);
			}

			for (int i = 0; i < b.length; i++) {
				buffer[i] = b[i];
			}
		}
	}

	public int getBufferCI() {
		return bufferCI;
	}

	public int getBufferSize() {
		return buffer.length;
	}

	public static byte[] divideBytes(long[] arr) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(arr.length * 8);
		bb.asLongBuffer().put(arr);
		return bb.array(); // this returns the "raw" array, it's shared and not
							// copied!
	}

	public static byte[] divideBytes(int[] arr) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(arr.length * 4);
		bb.asIntBuffer().put(arr);
		return bb.array(); // this returns the "raw" array, it's shared and not
							// copied!
	}

	public static byte[] divideBytes(short[] arr) {
		java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(arr.length * 2);
		bb.asShortBuffer().put(arr);
		return bb.array(); // this returns the "raw" array, it's shared and not
							// copied!
	}

	@Override
	public String toString() {
		return "ModuloES [BufferSize: " + getBufferSize() + "bytes] [buffer=" + Arrays.toString(buffer) + "]";
	}

}
