package Bean;

public class Region {

	private int id;
	private String listeIdRegion = "";


	public Region(int id, String listeIdRegion){
		this.id = id;
		this.listeIdRegion = listeIdRegion;
	}



	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return this.id;
	}


	public String getListeIdRegion() {
		return this.listeIdRegion;
	}



	public void setId(int id) {
		this.id = id;
	}



	public void setListeIdRegion(String listeIdRegion) {
		this.listeIdRegion = listeIdRegion;
	}
}
