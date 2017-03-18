package componentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import principal.Computador;
import utils.Constantes;

public class Parser {

	public static int instrucaoAtual = -1;
	public static final String TAG = "Parser.class";
	public static ArrayList<String> instrucoes = new ArrayList<>();

	public void sendDataToEncoder() {
		instrucaoAtual++;
		Computador.encoder.pullInstructionsFromParser();;
	}

	public void printInstructions() {
		for (String instrucao : instrucoes) {
			System.out.println(instrucao);
		}
	}

	public static void extractData() {
		File f = new File(Constantes.ARQUIVO_DE_TEXTO);

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			Pattern p1 = Pattern.compile(Constantes.RE_add_mov);
			Pattern p2 = Pattern.compile(Constantes.RE_inc);
			Pattern p3 = Pattern.compile(Constantes.RE_imul);
			Matcher m1, m2, m3;

			String linha;
			while ((linha = br.readLine()) != null) {
				m1 = p1.matcher(linha);
				m2 = p2.matcher(linha);
				m3 = p3.matcher(linha);
				if (m1.matches() || m2.matches() || m3.matches()) {
					instrucoes.add(linha);
				} else {
					instrucoes.add("error");
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	static {
		System.out.println(TAG + " criada");
		extractData();
	}

}
