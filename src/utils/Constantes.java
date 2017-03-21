package utils;

public class Constantes {

	public static String TAG = "Constantes.class";

	public static final String ARQUIVO_DE_TEXTO = "assembly";

	// Params
	/* 1 byte = 8 bits
	 * a. Tamanho da palavra em bits [16, 32 ou 64] em bytes [2, 4, 8]; 
	 * b. Tamanho da RAM em bits [64, 128, 256] em bytes [8, 16 ou 32]; 
	 * c. Tamanho do buffer de entrada/saida em bits [32, 64, 128] em bytes [4, 8 ou 16]; 
	 * d. Largura do barramento em bits [8, 16 ou 32] em bytes [1, 2, 4];
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

	//Register value
	public static int VALUE_register_A = -1;
	public static int VALUE_register_B = -2;
	public static int VALUE_register_C = -3;
	public static int VALUE_register_D = -4;
	public static int VALUE_register_CI = -5;
	
	// Instructions
	public static String mov = "mov";
	public static String add = "add";
	public static String inc = "inc";
	public static String imul = "imul";

	// Instructions value 3 a 6 
	// r - register ; m - memory ; i - immediate
	public static int VALUE_mov_r_from_r = 301;
	public static int VALUE_mov_r_from_i = 302;
	public static int VALUE_mov_m_from_i = 303;
	public static int VALUE_mov_r_from_m = 304;
	public static int VALUE_mov_m_from_r = 305;
	public static int VALUE_mov_m_from_m = 306;
	public static int VALUE_add_r_from_r = 401;
	public static int VALUE_add_r_from_i = 402;
	public static int VALUE_add_m_from_i = 403;
	public static int VALUE_add_r_from_m = 404;
	public static int VALUE_add_m_from_r = 405;
	public static int VALUE_add_m_from_m = 406;
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
	public static String RE_add_mov = "^(add|mov)\\s+([a-dA-D]|0x[a-fA-F0-9]+)\\s*,"
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
