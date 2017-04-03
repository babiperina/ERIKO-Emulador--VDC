package componentes;

import java.util.Arrays;

import principal.Computador;
import utils.Constantes;

public class Ram {

	// O tamanho da RAM est� em bits
	byte[] celulas = new byte[Constantes.SIZE_ram];

	public Ram() {
		for (int i = 0; i < Constantes.offset; i++) {
			celulas[i] = -1;
		}
	}

	public boolean receiveDados(byte[] dados) {
		int salto = Constantes.SIZE_word * 4 / 8;
		if (celulas[0] == -1) {
			Computador.cpu.getCI().setConteudo(0);
			for (int i = 0; i < dados.length; i++) {
				celulas[i] = dados[i];
			}
			return true;
		} else if (celulas[salto] == -1) {
			for (int i = salto; i < dados.length + salto; i++) {
				celulas[i] = dados[i - salto];
			}
			return true;
		} else
			return false;
	}

	public void sendDadosToCpu(int endereco) {
		byte[] dados = new byte[Constantes.SIZE_word * 4 / 8];
		System.out.println("Endereço: " + endereco);
		if (endereco == 0) {
			for (int i = 0; i < dados.length; i++) {
				dados[i] = celulas[i];
				celulas[i] = -1;
			}
			Computador.cpu.getCI().setConteudo(dados.length);
			System.out.println(Computador.cpu.getCI().getConteudo());
		} else {
			for (int i = dados.length; i < dados.length + dados.length; i++) {
				dados[i] = celulas[i - dados.length];
				celulas[i - dados.length] = -1;
			}
		}
		Computador.bar.sendDados(Computador.ram, Computador.cpu, dados);

	}

	public int getSize() {
		return celulas.length;
	}

	@Override
	public String toString() {
		return "Ram [Size: " + getSize() + "bytes] [SizeToInstruction: " + Constantes.offset + "bytes] [WordSize: "
				+ Constantes.SIZE_word + "bits] [celulas=" + Arrays.toString(celulas) + "]";
	}

}
