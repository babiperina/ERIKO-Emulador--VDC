package principal;

import componentes.Cpu;
import componentes.Encoder;
import componentes.ModuloES;
import componentes.Parser;
import componentes.Ram;


public class Computador {

	public static final String TAG = "Computador.class";
	
	public static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();
	public static Cpu cpu = new Cpu();
	public static Ram ram = new Ram();
	public static ModuloES es = new ModuloES();

	public static void main(String[] args) {
	
		System.out.println(cpu.toString());
		System.out.println(ram.toString());
		System.out.println(es.toString());
		System.out.println("Instruções:");
		parser.printInstructions();
		parser.sendDataToEncoder();


	}

	static {
		System.out.println(TAG + " criada");
	}

}
