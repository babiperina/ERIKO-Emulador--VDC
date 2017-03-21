package componentes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import principal.Computador;
import utils.Constantes;

public class Encoder {

	private String instrucao;

	public void pullInstructionsFromParser() {
		// só pode pegar essa instrução se o CI do buffer for -1
		// ou a informação que tiver no espaço buffer[BufferCI] for diferente de
		// 0 e 1
		instrucao = Computador.parser.instrucoes.get(Computador.parser.instrucaoAtual);
		printInstrucao();
		Computador.parser.sendDataToEncoder();
	}

	public void encoderInstrucao() {
		Pattern p1 = Pattern.compile(Constantes.RE_add_mov);
		Pattern p2 = Pattern.compile(Constantes.RE_inc);
		Pattern p3 = Pattern.compile(Constantes.RE_imul);
		long code[];
		Matcher type1, type2, type3;

		type1 = p1.matcher(instrucao);
		type2 = p2.matcher(instrucao);
		type3 = p3.matcher(instrucao);

		if (type1.matches() || type2.matches() || type3.matches()) {
			Pattern r = Pattern.compile(Constantes.RE_register);
			Pattern m = Pattern.compile(Constantes.RE_memory);

			if (type1.matches()) {
				String type, x, y;
				type = type1.group(1);
				x = type1.group(2);
				y = type1.group(3);

				if (type.equalsIgnoreCase("mov")) {
					code = encoderMovInstruction(r, m, x, y);
					System.out.println("Instrução codificada em longs: " + code[0] + " " + code[1] + " " + code[2]);
				} else {
					System.out.println("add");
				}
			} else if (type2.matches()) {
				System.out.println("inc");
			} else {
				System.out.println("imul");
			}
		} else {
			System.out.println("Programa encerrado. Error line: " + Computador.parser.instrucaoAtual);
		}
	}

	long[] encoderMovInstruction(Pattern r, Pattern m, String x, String y) {
		long code[] = new long[3];
		String msg = "mov ";
		Matcher matcher;

		matcher = r.matcher(x);
		if (matcher.matches()) {
			// mov register
			code[1] = encoderRegister(x);
			msg += "register, ";
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// mov register, register
				msg += "register";
				code[0] = Constantes.VALUE_mov_r_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (matcher.matches()) {
					// mov register, memory
					msg += "memory";
					code[0] = Constantes.VALUE_mov_r_from_m;

				} else {
					// mov register, immediate
					msg += "immediate";
					code[0] = Constantes.VALUE_mov_r_from_i;

				}
			}

		} else {
			// mov memory
			msg += "memory, ";

			matcher = r.matcher(y);
			if (matcher.matches()) {
				// mov memory, register
				msg += "register";
				code[0] = Constantes.VALUE_mov_m_from_r;
				code[2] = encoderRegister(y);
			} else {

				// mov memory, immediate
				msg += "immediate";
				code[0] = Constantes.VALUE_mov_m_from_i;

			}
		}
		System.out.println(msg);
		return code;
	}

	long encoderRegister(String register) {
		if (register.equalsIgnoreCase("a")) {
			return Constantes.VALUE_register_A;
		} else if (register.equalsIgnoreCase("b")) {
			return Constantes.VALUE_register_B;
		} else if (register.equalsIgnoreCase("c")) {
			return Constantes.VALUE_register_C;
		} else {
			return Constantes.VALUE_register_D;
		}

	}

	public void sendInstructionsToESBuffer() {

	}

	public void printInstrucao() {
		System.out.println("Instrução atual: " + instrucao);
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

}
