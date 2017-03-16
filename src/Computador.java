import componentes.Cpu;
import componentes.ModuloES;
import componentes.Parser;
import componentes.Ram;

public class Computador {

	public static final String TAG = "Computador.class";

	public static void main(String[] args) {
		Parser parser = new Parser();
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
