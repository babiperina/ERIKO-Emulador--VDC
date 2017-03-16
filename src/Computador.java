import componentes.Cpu;
import componentes.ModuloES;
import componentes.Ram;

public class Computador {

	public static String TAG = "Computador.class";

	public static void main(String[] args) {
		Cpu cpu = new Cpu();
		Ram ram = new Ram();
		ModuloES es = new ModuloES();

		System.out.println(cpu.toString());
		System.out.println(ram.toString());
		System.out.println(es.toString());


	}

	static {
		System.out.println(TAG + " criada");
	}

}
