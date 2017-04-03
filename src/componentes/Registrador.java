package componentes;

public class Registrador {

	private int id;

	private final String nome;

	private Integer conteudo;

	public Registrador(int id, String nome, Integer conteudo) {
		this.id = id;
		this.nome = nome;
		this.conteudo = conteudo;
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
	
	public boolean isEmpty(){
		return conteudo == null;
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
