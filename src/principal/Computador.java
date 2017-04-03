package principal;

import componentes.Barramento;
import componentes.Cpu;
import componentes.Encoder;
import componentes.ModuloES;
import componentes.Parser;
import componentes.Ram;
import utils.Constantes;

public class Computador {

	public static final String TAG = "Computador.class";

	public static Parser parser = new Parser();
	public static Encoder encoder = new Encoder();
	public static ModuloES es = new ModuloES();
	public static Cpu cpu = new Cpu();
	public static Ram ram = new Ram();
	public static Barramento bar = new Barramento();

	public static void main(String[] args) {

		printToHelp();
		while (parser.instrucaoAtual < parser.instrucoes.size()) {
			System.out.println("+-------------------------------------------------------------------------+");
			System.out.println("+-------------------------------------------------------------------------+");
			System.out.println("Instrução atual: " + (parser.instrucaoAtual + 1) + " QTDE Instruções: "
					+ parser.instrucoes.size());

			System.out.println("+-------------------------------------------------------------------------+");

			System.out.println(ram.toString());
			encoder.pullInstructionsFromParser();
			es.pullInstructionFromEncoder();
			if (Constantes.qtdeInstructionAtRAM == 2 && parser.instrucaoAtual != parser.instrucoes.size())
				es.sendInstructionToRAM(true);
			else
				es.sendInstructionToRAM(false);
			cpu.sendEndereco();

			
			System.out.println("\n \n");
		}

	}

	private static void printToHelp() {
		System.out.println(cpu.toString());
		System.out.println(ram.toString());
		System.out.println(es.toString());
		System.out.println("Instruções:");
		parser.printInstructions();
	}

}
