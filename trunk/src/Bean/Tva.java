package Bean;

public class Tva {

	private int id;
	private double tauxTva;
	
	public Tva(int id, double tauxTva){
		this.id = id;
		this.tauxTva = tauxTva;
	}
	
	public Tva(){};
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setTauxTva(double tauxTva) {
		this.tauxTva = tauxTva;
	}
	public double getTauxTva() {
		return tauxTva;
	}

}
