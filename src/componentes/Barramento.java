package componentes;

import principal.Computador;

public class Barramento {
	
	public void sendValor(Cpu cpu, Ram ram, int endereco, int valor){
		Computador.ram.celulas_valores[endereco] = valor;
	}
	public int pullValor(Ram ram, Cpu cpu, int endereco){
		return Computador.ram.celulas_valores[endereco];
	}

	public void sendDados(Object de, Object para, byte[] dados) {

		if (de instanceof ModuloES && para instanceof Ram) {
			Computador.ram.receiveDados(dados);
		} else if (de instanceof Ram && para instanceof Cpu) {
			Computador.cpu.receiveDado(dados);
		}
	}

	public void sendEndereco(Object de, Object para, int endereco) {

		if (de instanceof Cpu && para instanceof Ram) {
			Computador.ram.sendDadosToCpu(endereco);
		}

	}

}
