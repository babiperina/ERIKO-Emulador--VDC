package componentes;

public class Registrador {

	private static int id_inc = 0;

	private int id;

	private final String nome;

	private Integer conteudo;

	public Registrador(String nome, Integer conteudo) {
		id = id_inc + 1;
		this.nome = nome;
		this.conteudo = conteudo;
		id_inc++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public int getConteudo() {
		return conteudo;
	}

	public void setConteudo(int conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "Registrador [id=" + id + ", nome=" + nome + ", conteudo=" + conteudo + "]";
	}
	
	

}
