package Bean;

public class Tranche {

	private int id_tranche;
	private String nomTranche;
	private int minimum;
	private int maximum;


	/**
	 * @param id_tranche
	 * @param nomTranche
	 * @param minimum
	 * @param maximum
	 */
	public Tranche(int id_tranche, String nomTranche, int minimum, int maximum) {
		super();
		this.id_tranche = id_tranche;
		this.nomTranche = nomTranche;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public Tranche() {}

	public String getNomTranche() {
		return this.nomTranche;
	}

	public void setNomTranche(String nomTranche) {
		this.nomTranche = nomTranche;
	}

	public int getMinimum() {
		return this.minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return this.maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getId_tranche() {
		return this.id_tranche;
	}

	public void setId_tranche(int id_tranche) {
		this.id_tranche = id_tranche;
	}
}
