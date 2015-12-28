package Bean;

public class Produit {

	private int id;
	private String libelleProduit = "";
	private Double prixProduit = 0.00;
	private Tva tva;
	private boolean actif;
	private int nbBouteillesVendu;
	private int nbStock;

	public Produit(int id, String libelleProduit, Double prixProduit, Tva tva,
			boolean actif,int nbStock) {
		this.id = id;
		this.libelleProduit = libelleProduit;
		this.setPrixProduit(prixProduit);
		this.tva = tva;
		this.actif = actif;
		this.nbStock = nbStock;
	}

	public Produit(String libelleProduit, int nbBouteillesVendu) {
		this.libelleProduit = libelleProduit;
		this.nbBouteillesVendu = nbBouteillesVendu;
	}

	public Produit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}

	public Produit() {
	};

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}

	public String getLibelleProduit() {
		return this.libelleProduit;
	}

	public void setPrixProduit(Double prixProduit) {
		this.prixProduit = prixProduit;
	}

	public Double getPrixProduit() {
		return this.prixProduit;
	}

	public Tva getTva() {
		return this.tva;
	}

	public void setTva(Tva tva) {
		this.tva = tva;
	}

	public boolean getActif() {
		return this.actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public int getNbBouteillesVendu() {
		return this.nbBouteillesVendu;
	}

	public void setNbBouteillesVendu(int nbBouteillesVendu) {
		this.nbBouteillesVendu = nbBouteillesVendu;
	}

	public int getNbStock() {
		return this.nbStock;
	}

	public void setNbStock(int nbStock) {
		this.nbStock = nbStock;
	}
}
