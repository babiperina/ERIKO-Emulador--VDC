package utils;

public class Constantes {

	public static final String ARQUIVO_DE_TEXTO = "assembly";
	// public static final String ARQUIVO_DE_TEXTO = "assembly_w_error";

	// Params
	/*
	 * 1 byte = 8 bits a. Tamanho da palavra em bits [16, 32 ou 64] em bytes [2,
	 * 4, 8]; b. Tamanho da RAM em bits [512, 1024, 2048] em bytes [64, 128 ou
	 * 256]; c. Tamanho do buffer de entrada/saida em bits [32, 64, 128] em
	 * bytes [4, 8 ou 16]; d. Largura do barramento em bits [8, 16 ou 32] em
	 * bytes [1, 2, 4];
	 */
	public static int SIZE_word = 8;
	public static int SIZE_ram = 128;
	public static int qtdeInstructionAtRAM;
	public static int offset;
	public static int SIZE_e_s_buffer;
	
	private static void setSizeWord() {
		SIZE_word = SIZE_word*8;
	}
	
	private static void setSizeBuffer() {
		SIZE_e_s_buffer = SIZE_word * 4 / 8;
	}

	private static void setQtdeInstructionAtRAM() {
		int z;
		z = (((+SIZE_word * 4) / 8) * 100) / SIZE_ram;
		if (z <= 25) {
			qtdeInstructionAtRAM = 2;
		} else {
			qtdeInstructionAtRAM = 1;
		}
	}

	private static void setOffset() {
		offset = SIZE_word * 4 / 8 * qtdeInstructionAtRAM;
	} 

	public static int WIDTH_barramento = 8;
	public static String limitMemoryDigits;

	private static void setDigitsLimitMemory() {
		if (WIDTH_barramento == 8 || WIDTH_barramento == 16) {
			limitMemoryDigits = "{1}";
		} else if (WIDTH_barramento == 32) {
			limitMemoryDigits = "{1,2}";
		}
	}

	// Register value
	public static int VALUE_register_A = 300;
	public static int VALUE_register_B = 400;
	public static int VALUE_register_C = 500;
	public static int VALUE_register_D = 600;
	public static int VALUE_register_CI = -5;

	// Instructions
	public static String mov = "mov";
	public static String add = "add";
	public static String inc = "inc";
	public static String imul = "imul";

	// Instructions value 3 a 6
	// r - register ; m - memory ; i - immediate
	public static int VALUE_mov_r_from_r = 311;
	public static int VALUE_mov_r_from_i = 313;
	public static int VALUE_mov_m_from_i = 323;
	public static int VALUE_mov_r_from_m = 312;
	public static int VALUE_mov_m_from_r = 321;
	public static int VALUE_mov_m_from_m = 322;
	public static int VALUE_add_r_from_r = 411;
	public static int VALUE_add_r_from_i = 413;
	public static int VALUE_add_m_from_i = 423;
	public static int VALUE_add_r_from_m = 412;
	public static int VALUE_add_m_from_r = 421;
	public static int VALUE_add_m_from_m = 422;
	public static int VALUE_inc_r = 501;
	public static int VALUE_inc_m = 502;
	public static int VALUE_imul_r_r_r = 6111;
	public static int VALUE_imul_r_r_m = 6112;
	public static int VALUE_imul_r_r_i = 6113;
	public static int VALUE_imul_r_m_r = 6121;
	public static int VALUE_imul_r_m_m = 6122;
	public static int VALUE_imul_r_m_i = 6123;
	public static int VALUE_imul_r_i_r = 6131;
	public static int VALUE_imul_r_i_m = 6132;
	public static int VALUE_imul_r_i_i = 6133;
	public static int VALUE_imul_m_r_r = 6211;
	public static int VALUE_imul_m_r_m = 6212;
	public static int VALUE_imul_m_r_i = 6213;
	public static int VALUE_imul_m_m_r = 6221;
	public static int VALUE_imul_m_m_m = 6222;
	public static int VALUE_imul_m_m_i = 6223;
	public static int VALUE_imul_m_i_r = 6231;
	public static int VALUE_imul_m_i_m = 6232;
	public static int VALUE_imul_m_i_i = 6233;

	// Instructions REgex
	public static String RE_add_mov;
	public static String RE_inc;
	public static String RE_imul;

	// Componentes REgex
	public static String RE_register = "[A-D]";
	public static String RE_memory = "0x[a-fA-F0-9]" + "";
	public static String RE_immediate = "\\d+";

	static {
		setDigitsLimitMemory();
		setSizeWord();
		setQtdeInstructionAtRAM();
		setSizeBuffer();
		setOffset();
		RE_add_mov = "^(add|mov)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*,"
				+ "\\s+([a-dA-D]|0x[a-fA-F0-9]+|\\d+)\\s*$";
		RE_inc = "^(inc)\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + ")\\s*$";
		RE_imul = "^(imul)\\s+([a-dA-D]|0x[a-fA-F0-9] " + limitMemoryDigits + ")\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]"
				+ limitMemoryDigits + "|\\d)\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]" + limitMemoryDigits + "|\\d+)\\s*$";
	}

}
