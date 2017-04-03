package componentes;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import principal.Computador;
import utils.Constantes;

public class Cpu {

	private Registrador A = new Registrador("A", null);
	private Registrador B = new Registrador("B", null);
	private Registrador C = new Registrador("C", null);
	private Registrador D = new Registrador("D", null);
	private Registrador CI = new Registrador("CI", null);

	public Cpu() {

	}

	public void sendEndereco() {
		Computador.bar.sendEndereco(Computador.cpu, Computador.ram, CI.getConteudo());
	}

	public void receiveDado(byte[] dados) {
		byte[] d = dados;
		if (d[0] != -1) {
			System.out.print("TO NA CPU: ");
			for (byte b : d) {
				System.out.print(b + " ");
			}
			decodificar(dados);
		}
		System.out.println();
	}

	public void decodificar(byte[] dados) {
		ByteBuffer bb;
		bb = ByteBuffer.wrap(dados);

		switch (Constantes.SIZE_word) {
		case 16:
			ShortBuffer sb = bb.asShortBuffer();
			short[] shortArray = new short[dados.length / 2];
			sb.get(shortArray);
			executeShort(shortArray);
			break;

		case 32:
			IntBuffer ib = bb.asIntBuffer();
			int[] intArray = new int[dados.length / 4];
			ib.get(intArray);
			executeInt(intArray);
			break;

		case 64:
			LongBuffer lb = bb.asLongBuffer();
			long[] longArray = new long[dados.length / 8];
			lb.get(longArray);
			executeLong(longArray);
			break;
		}

		if (CI != null) {
			if (CI.getConteudo() == Constantes.SIZE_word * 4 / 8) {
				sendEndereco();
			}
		}
	}

	public void executeShort(short[] instrucao) {
		long type = (long) instrucao[0];
		if (type == Constantes.VALUE_mov_m_from_i || type == Constantes.VALUE_mov_m_from_m
				|| type == Constantes.VALUE_mov_m_from_r || type == Constantes.VALUE_mov_r_from_i
				|| type == Constantes.VALUE_mov_r_from_m || type == Constantes.VALUE_mov_r_from_r) {
			executeMov(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_add_m_from_i || type == Constantes.VALUE_add_m_from_m
				|| type == Constantes.VALUE_add_m_from_r || type == Constantes.VALUE_add_r_from_i
				|| type == Constantes.VALUE_add_r_from_m || type == Constantes.VALUE_add_r_from_r) {
			executeAdd(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_inc_m || type == Constantes.VALUE_inc_r) {
			executeInc(type, instrucao[1]);
		} else {
			executeImul(type, instrucao[1], instrucao[2], instrucao[3]);
		}

	}

	public void executeInt(int[] instrucao) {
		long type = (long) instrucao[0];
		if (type == Constantes.VALUE_mov_m_from_i || type == Constantes.VALUE_mov_m_from_m
				|| type == Constantes.VALUE_mov_m_from_r || type == Constantes.VALUE_mov_r_from_i
				|| type == Constantes.VALUE_mov_r_from_m || type == Constantes.VALUE_mov_r_from_r) {
			executeMov(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_add_m_from_i || type == Constantes.VALUE_add_m_from_m
				|| type == Constantes.VALUE_add_m_from_r || type == Constantes.VALUE_add_r_from_i
				|| type == Constantes.VALUE_add_r_from_m || type == Constantes.VALUE_add_r_from_r) {
			executeAdd(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_inc_m || type == Constantes.VALUE_inc_r) {
			executeInc(type, instrucao[1]);
		} else {
			executeImul(type, instrucao[1], instrucao[2], instrucao[3]);
		}
	}

	public void executeLong(long[] instrucao) {
		int type = (int) instrucao[0];
		if (type == Constantes.VALUE_mov_m_from_i || type == Constantes.VALUE_mov_m_from_m
				|| type == Constantes.VALUE_mov_m_from_r || type == Constantes.VALUE_mov_r_from_i
				|| type == Constantes.VALUE_mov_r_from_m || type == Constantes.VALUE_mov_r_from_r) {
			executeMov(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_add_m_from_i || type == Constantes.VALUE_add_m_from_m
				|| type == Constantes.VALUE_add_m_from_r || type == Constantes.VALUE_add_r_from_i
				|| type == Constantes.VALUE_add_r_from_m || type == Constantes.VALUE_add_r_from_r) {
			executeAdd(type, instrucao[1], instrucao[2]);
		} else if (type == Constantes.VALUE_inc_m || type == Constantes.VALUE_inc_r) {
			executeInc(type, instrucao[1]);
		} else {
			executeImul(type, instrucao[1], instrucao[2], instrucao[3]);
		}
	}

	public void executeMov(long type, long x, long y) {

	}

	public void executeAdd(long type, long x, long y) {

	}

	public void executeInc(long type, long x) {

	}

	public void executeImul(long type, long x, long y, long z) {

	}

	public Registrador getA() {
		return A;
	}

	public void setA(Registrador a) {
		A = a;
	}

	public Registrador getB() {
		return B;
	}

	public void setB(Registrador b) {
		B = b;
	}

	public Registrador getC() {
		return C;
	}

	public void setC(Registrador c) {
		C = c;
	}

	public Registrador getD() {
		return D;
	}

	public void setD(Registrador d) {
		D = d;
	}

	public Registrador getCI() {
		return CI;
	}

	public void setCI(Registrador cI) {
		CI = cI;
	}

	@Override
	public String toString() {
		return "Cpu [A=" + A + ", B=" + B + ", C=" + C + ", D=" + D + ", CI=" + CI + "]";
	}

}
