package componentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import utils.Constantes;

public class Parser {

	public static int instrucaoAtual = -1;
	public static final String TAG = "Parser.class";
	public static ArrayList<String> instrucoes = new ArrayList<>();

	
	public void sendDataToEncoder() {


	}

	public static void extractData() {
		File f = new File(Constantes.ARQUIVO_DE_TEXTO);

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String linha;
			while ((linha = br.readLine()) != null) {
				instrucoes.add(linha);
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
