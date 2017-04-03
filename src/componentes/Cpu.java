package componentes;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import principal.Computador;
import utils.Constantes;

public class Cpu {

	private Registrador A = new Registrador(Constantes.VALUE_register_A, "A", null);
	private Registrador B = new Registrador(Constantes.VALUE_register_B, "B", null);
	private Registrador C = new Registrador(Constantes.VALUE_register_C, "C", null);
	private Registrador D = new Registrador(Constantes.VALUE_register_D, "D", null);
	private Registrador CI = new Registrador(Constantes.VALUE_register_CI, "CI", null);

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
		int a = (int) x;
		int b = (int) y;
		if (type == Constantes.VALUE_mov_m_from_i) {
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_mov_m_from_m) {
			b = pegarValorDaMemoria(a);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_mov_m_from_r) {
			b = pegarValorDoRegistrador(b);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_mov_r_from_r) {
			b = pegarValorDoRegistrador(b);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_mov_r_from_m) {
			b = pegarValorDaMemoria(b);
			insertValorRegistrador(a, b);
		} else {
			// Constantes.VALUE_mov_r_from_i
			insertValorRegistrador(a, b);
		}

	}

	public void executeAdd(long type, long x, long y) {
		int a = (int) x;
		int b = (int) y;
		if (type == Constantes.VALUE_add_m_from_i) {
			b = pegarValorDaMemoria(a) + b;
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_add_m_from_m) {
			b = pegarValorDaMemoria(a) + pegarValorDaMemoria(b);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_add_m_from_r) {
			b = pegarValorDaMemoria(a) + pegarValorDoRegistrador(b);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_add_r_from_r) {
			b = pegarValorDoRegistrador(a) + pegarValorDoRegistrador(b);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_add_r_from_m) {
			b = pegarValorDoRegistrador(a) + pegarValorDaMemoria(b);
			insertValorRegistrador(a, b);
		} else {
			// Constantes.VALUE_add_r_from_i
			b = pegarValorDoRegistrador(a) + b;
			insertValorRegistrador(a, b);
		}
	}

	public void executeInc(long type, long x) {
		int a = (int) x;
		if (type == Constantes.VALUE_inc_m) {
			a = pegarValorDaMemoria(a);
			insertValorMemoria(a, a + 1);
		} else {
			// Constantes.VALUE_inc_r
			a = pegarValorDoRegistrador(a);
			insertValorRegistrador(a, a + 1);
		}
	}

	public void executeImul(long type, long x, long y, long z) {
		int a = (int) x;
		int b = (int) y;
		int c = (int) z;

		if (type == Constantes.VALUE_imul_r_r_r) {
			b = pegarValorDoRegistrador(b) * pegarValorDoRegistrador(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_r_m) {
			b = pegarValorDoRegistrador(b) * pegarValorDaMemoria(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_r_i) {
			b = pegarValorDoRegistrador(b) + c;
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_m_r) {
			b = pegarValorDaMemoria(b) * pegarValorDoRegistrador(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_m_m) {
			b = pegarValorDaMemoria(b) * pegarValorDaMemoria(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_m_i) {
			b = pegarValorDaMemoria(b) * c;
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_i_r) {
			b = b * pegarValorDoRegistrador(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_i_m) {
			b = b * pegarValorDaMemoria(c);
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_r_i_i) {
			b = b * c;
			insertValorRegistrador(a, b);
		} else if (type == Constantes.VALUE_imul_m_r_r) {
			b = pegarValorDoRegistrador(b) * pegarValorDoRegistrador(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_r_m) {
			b = pegarValorDoRegistrador(b) * pegarValorDaMemoria(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_r_i) {
			b = pegarValorDoRegistrador(b) * c;
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_m_r) {
			b = pegarValorDaMemoria(b) * pegarValorDoRegistrador(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_m_m) {
			b = pegarValorDaMemoria(b) * pegarValorDaMemoria(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_m_i) {
			b = pegarValorDaMemoria(b) * c;
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_i_r) {
			b = b * pegarValorDoRegistrador(c);
			insertValorMemoria(a, b);
		} else if (type == Constantes.VALUE_imul_m_i_m) {
			b = b * pegarValorDaMemoria(c);
			insertValorMemoria(a, b);
		} else {
			// Constantes.VALUE_imul_m_i_i
			b = b * c;
			insertValorMemoria(a, b);
		}
	}

	public void insertValorMemoria(int endereco, int valor) {

	}

	public int pegarValorDaMemoria(int endereco) {

		return 0;
	}

	public int pegarValorDoRegistrador(int registrador) {
		if (registrador == A.getId()) {
			if (!A.isEmpty())
				return A.getConteudo();
			else
				return 0;
		} else if (registrador == B.getId()) {
			if (!B.isEmpty())
				return B.getConteudo();
			else
				return 0;
		} else if (registrador == C.getId()) {
			if (!C.isEmpty())
				return C.getConteudo();
			else
				return 0;
		} else {
			if (!D.isEmpty())
				return D.getConteudo();
			else
				return 0;
		}
	}

	public void insertValorRegistrador(int registrador, int valor) {
		if (registrador == A.getId()) {
			A.setConteudo(valor);
		} else if (registrador == B.getId()) {
			B.setConteudo(valor);
		} else if (registrador == C.getId()) {
			C.setConteudo(valor);
		} else {
			D.setConteudo(valor);
		}
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
