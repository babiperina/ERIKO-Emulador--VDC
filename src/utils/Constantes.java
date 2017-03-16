package utils;

public class Constantes {

	public static String TAG = "Constantes.class";

	public static final String ARQUIVO_DE_TEXTO = "assembly";

	// Params
	/*
	 * a. Tamanho da palavra em bits [16, 32 ou 64]; b. Tamanho da RAM em bytes
	 * [8, 16 ou 32]; c. Tamanho do buffer de entrada/saída em bytes [4, 8 ou
	 * 16]; d. Largura do barramento em bits [8, 16 ou 32];
	 */
	public static int SIZE_word = 16;
	public static int SIZE_ram = 8;
	public static int SIZE_e_s_buffer;
	private static void setSizeBuffer() {
		if (SIZE_ram == 8)
			SIZE_e_s_buffer = 4;
		else if (SIZE_ram == 16)
			SIZE_e_s_buffer = 8;
		else if (SIZE_ram == 32)
			SIZE_e_s_buffer = 16;
	}

	public static int WIDTH_barramento = 8;

	// Instructions
	public static String mov = "mov";
	public static String add = "add";
	public static String inc = "inc";
	public static String imul = "imul";

	// Instructions value
	public static int VALUE_mov = 1;
	public static int VALUE_add = 2;
	public static int VALUE_inc = 3;
	public static int VALUE_imul = 4;

	// Instructions REgex
	public static String RE_add_mov = "^(add|mov)\\s+([a-dA-D]|0x[a-fA-F0-9])\\s*,"
			+ "\\s+([a-dA-D]|0x[a-fA-F0-9]+|\\d)\\s*$";
	public static String RE_inc = "^(inc)\\s+([a-dA-D]|0x[a-fA-F0-9])\\s*$";
	public static String RE_imul = "^(imul)\\s+([a-dA-D]|0x[a-fA-F0-9])\\s*," + "\\s+([a-dA-D]|0x[a-fA-F0-9]+|\\d)\\s*,"
			+ "\\s+([a-dA-D]|0x[a-fA-F0-9]+|\\d)\\s*$";

	// Componentes REgex
	public static String RE_register = "[A-D]";
	public static String RE_memory = "0x[a-fA-F0-9]+";
	public static String RE_immediate = "\\d+";

	static {
		System.out.println(TAG + " criada");
		setSizeBuffer();
	}

}
