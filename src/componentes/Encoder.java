package componentes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import principal.Computador;
import utils.Constantes;

public class Encoder {

	private String instrucao;
	private long code[];

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
			} else if (type2.matches()) {
				String x = type2.group(2);

				code = encoderIncInstruction(r, x);
			} else {
				String x, y, z;
				x = type3.group(2);
				y = type3.group(3);
				z = type3.group(4);
				code = encoderImulInstruction(r, m, x, y, z);
			}
		} else {
			System.out.println("Programa encerrado. Error line: " + Computador.parser.instrucaoAtual);
		}
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
			code[1] = encoderMemory(x);
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
				code[2] = Long.parseLong(y);
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
				code[3] = encoderMemory(z);
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

	public String seeInstrucaoCode() {
		String code = "";

		if (!instrucao.equalsIgnoreCase("error")) {

			for (long l : this.code) {
				code += l + " ";
			}

		}
		return code;
	}

	public void sendInstructionsToESBuffer() {
		// enviar a instrução para o modulo E/S
		// isto somente será possível se CI = -1 ou value on buffer[CI] for -1
		if (Computador.es.getBufferCI() == -1 || Computador.es.buffer[Computador.es.getBufferCI()] == -1) {
			Computador.es.setIn("");
			String msg = "";
			for (int i = 0; i < code.length; i++) {
				msg+=Long.toBinaryString(code[i])+" ";
			}
//			System.out.println(msg);
//			Computador.es.receivedInstructionsFromEncoder();
		} else {
			System.out.println("Buffer lotado.");
		}
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
