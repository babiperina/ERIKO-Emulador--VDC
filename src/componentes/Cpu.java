package componentes;

import principal.Computador;

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
		System.out.print("TO NA CPU: ");
		for (byte b : d) {
			System.out.print(b + " ");
		}
		System.out.println();
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
