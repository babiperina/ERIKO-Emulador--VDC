package componentes;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import principal.Computador;
import utils.Constantes;

public class Encoder {

	private String instrucao = null;
	long[] code = new long[3];
	private Object instructionToSend[] = null;

	public void pullInstructionsFromParser() {
		// só pode pegar a instrução se instrucao for "" ou null
		if (instrucao == "" || instrucao == null) {
			instrucao = Computador.parser.instrucoes.get(Computador.parser.instrucaoAtual);
			printInstrucao();
			Computador.parser.sendDataToEncoder();
			code = encoderInstrucaoToLong();
			if (!instrucao.equalsIgnoreCase("error")) {
				toES(code, Constantes.SIZE_word);
				// instructionToSend = toBinary(code, Constantes.SIZE_word);
			}
			instrucao = null;
		} else {
			// caso a instrução anterior ainda não tenha sido passada para o
			// moduloE/S
		}
	}

	public long[] encoderInstrucaoToLong() {
		long code[] = { 0 };
		if (!(instrucao == "" || instrucao == null)) {
			Pattern p1 = Pattern.compile(Constantes.RE_add_mov);
			Pattern p2 = Pattern.compile(Constantes.RE_inc);
			Pattern p3 = Pattern.compile(Constantes.RE_imul);
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
					} else {
						code = encoderAddInstruction(r, m, x, y);
					}
					System.out.println("Instrução codificada em longs: " + code[0] + " " + code[1] + " " + code[2]);
				} else if (type2.matches()) {
					String x = type2.group(2);

					code = encoderIncInstruction(r, x);
					System.out.println("Instruçãoo codificada em longs: " + code[0] + " " + code[1]);
				} else {
					String x, y, z;
					x = type3.group(2);
					y = type3.group(3);
					z = type3.group(4);
					code = encoderImulInstruction(r, m, x, y, z);
					System.out.println("Instrução codificada em longs: " + code[0] + " " + code[1] + " " + code[2] + " "
							+ code[3]);
				}

			} else {
				System.out.println("Programa encerrado. Error line: " + Computador.parser.instrucaoAtual);
			}
		}
		return code;
	}

	public String encoderLongToBinary() {
		return null;
	}

	private long parseLong(String s, int base) {
		return new BigInteger(s, base).longValue();
	}

	public void toES(long code[], int palavraSize) {
		System.out.print("Método toES: ");
		char type = (code[0] + "").charAt(0);
		switch (palavraSize) {
		case 16:
			System.out.println("palavra de 16 bits");
			switch (type) {
			case '3': // mov
				instructionToSend = new Short[3];
				instructionToSend[0] = (short) code[0];
				instructionToSend[1] = (short) code[1];
				instructionToSend[2] = (short) code[2];
				break;
			case '4': // add
				instructionToSend = new Short[3];
				instructionToSend[0] = (short) code[0];
				instructionToSend[1] = (short) code[1];
				instructionToSend[2] = (short) code[2];
				break;
			case '5': // inc
				instructionToSend = new Short[2];
				instructionToSend[0] = (short) code[0];
				instructionToSend[1] = (short) code[1];
				break;
			case '6': // imul
				instructionToSend = new Short[4];
				instructionToSend[0] = (short) code[0];
				instructionToSend[1] = (short) code[1];
				instructionToSend[2] = (short) code[2];
				instructionToSend[3] = (short) code[2];
				break;
			}
			break;
		case 32:
			System.out.println("palavra de 32 bits");
			switch (type) {
			case '3': // mov

				break;
			case '4': // add

				break;
			case '5': // inc

				break;
			case '6': // imul

				break;
			}
			break;
		case 64:
			System.out.println("palavra de 64 bits");
			switch (type) {
			case '3': // mov

				break;
			case '4': // add

				break;
			case '5': // inc

				break;
			case '6': // imul

				break;
			}
			break;

		default:
			System.out.println("error no tamanho da palavra");

			break;
		}
	}

	public long toBinary(long code[], int palavraSize) {
		String n = "";
		int aux = 0;
		for (long l : code) {
			String temp = Long.toBinaryString(l);
			aux = palavraSize - temp.length();
			if (aux < 0) {
				n += temp.substring(0, 16);
				System.out.println(temp.substring(aux * -1, temp.length()));
			} else if (aux < palavraSize) {
				for (int i = 0; i < aux; i++) {
					n += "0";
				}
				n += temp;
			}
		}
		System.out.println("Instrução em binário: " + n);
		return parseLong(n, 2);
	}

	long[] encoderMovInstruction(Pattern r, Pattern m, String x, String y) {
		long code[] = new long[3];
		Matcher matcher;

		matcher = r.matcher(x);
		if (matcher.matches()) {
			// mov register
			code[1] = encoderRegister(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// mov register, register
				code[0] = Constantes.VALUE_mov_r_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (matcher.matches()) {
					// mov register, memory
					code[0] = Constantes.VALUE_mov_r_from_m;
					code[2] = encoderMemory(y);
				} else {
					// mov register, immediate
					code[0] = Constantes.VALUE_mov_r_from_i;
					code[2] = Long.valueOf(y).longValue();
				}
			}

		} else {
			// mov memory
			code[1] = encoderMemory(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// mov memory, register
				code[0] = Constantes.VALUE_mov_m_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (!matcher.matches()) {
					// mov memory, immediate
					code[0] = Constantes.VALUE_mov_m_from_i;
					code[2] = Long.parseLong(y);
				} else {
					// mov memory, memory
					code[0] = Constantes.VALUE_mov_m_from_m;
					code[2] = encoderMemory(y);
				}
			}
		}
		return code;
	}

	long[] encoderAddInstruction(Pattern r, Pattern m, String x, String y) {
		long code[] = new long[3];
		Matcher matcher;

		matcher = r.matcher(x);
		if (matcher.matches()) {
			// add register
			code[1] = encoderRegister(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// add register, register
				code[0] = Constantes.VALUE_add_r_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (matcher.matches()) {
					// add register, memory
					code[0] = Constantes.VALUE_add_r_from_m;
					code[2] = encoderMemory(y);
				} else {
					// add register, immediate
					code[0] = Constantes.VALUE_add_r_from_i;
					code[2] = Long.valueOf(y).longValue();
				}
			}

		} else {
			// add memory
			code[1] = encoderMemory(x);
			matcher = r.matcher(y);
			if (matcher.matches()) {
				// add memory, register
				code[0] = Constantes.VALUE_add_m_from_r;
				code[2] = encoderRegister(y);
			} else {
				matcher = m.matcher(y);
				if (!matcher.matches()) {
					// add memory, immediate
					code[0] = Constantes.VALUE_add_m_from_i;
					code[2] = Long.parseLong(y);
				} else {
					// add memory, memory
					code[0] = Constantes.VALUE_add_m_from_m;
					code[2] = encoderMemory(y);
				}
			}
		}
		return code;
	}

	long[] encoderIncInstruction(Pattern r, String x) {
		long code[] = new long[2];
		Matcher matcher = r.matcher(x);
		if (matcher.matches()) {
			// inc register
			code[0] = Constantes.VALUE_inc_r;
			code[1] = encoderRegister(x);
		} else {
			// inc memory
			code[0] = Constantes.VALUE_inc_m;
			code[1] = encoderMemory(x);
		}
		return code;
	}

	long[] encoderImulInstruction(Pattern r, Pattern m, String x, String y, String z) {
		long[] code = new long[4];
		Matcher matcher;

		String c = "6";
		matcher = r.matcher(x);
		if (matcher.matches()) {
			c += "1";
			code[1] = encoderRegister(x);
		} else {
			c += "2";
			code[1] = encoderMemory(x.substring(2));
		}

		matcher = r.matcher(y);
		if (matcher.matches()) {
			c += "1";
			code[2] = encoderRegister(y);
		} else {
			matcher = m.matcher(y);
			if (matcher.matches()) {
				c += "2";
				code[2] = encoderMemory(y);
			} else {
				c += "3";
				code[2] = Long.parseLong(y.substring(2));
			}
		}

		matcher = r.matcher(z);
		if (matcher.matches()) {
			c += "1";
			code[3] = encoderRegister(z);
		} else {
			matcher = m.matcher(z);
			if (matcher.matches()) {
				c += "2";
				code[3] = encoderMemory(z.substring(2));
			} else {
				c += "3";
				code[3] = Long.parseLong(z);
			}
		}

		code[0] = Long.parseLong(c);
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

	long encoderMemory(String memory) {
		return Long.valueOf(memory.substring(2)).longValue();
	}

	public void sendInstructionsToESBuffer() {
		// mandar instrução pro buffer de ES
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
