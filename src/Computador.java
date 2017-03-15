import componentes.Cpu;

public class Computador {

	public static String TAG = "Computador.class";

	public static void main(String[] args) {
		Cpu cpu = new Cpu();

		System.out.println(cpu.toString());
	}

	static {
		System.out.println(TAG + " criada");
	}

}
