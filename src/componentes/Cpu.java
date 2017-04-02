package componentes;

public class Cpu {


	private Registrador A = new Registrador("A", null);
	private Registrador B = new Registrador("B", null);
	private Registrador C = new Registrador("C", null);
	private Registrador D = new Registrador("D", null);
	private Registrador CI = new Registrador("CI", null);
	
	
	public Cpu() {
	

	}

	@Override
	public String toString() {
		return "Cpu [A=" + A + ", B=" + B + ", C=" + C + ", D=" + D + ", CI=" + CI + "]";
	}
	


}
