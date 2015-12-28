package Bean;

public class LigneDeProduit {

	private int id;
	private int id_produit_fk;
	private int id_facture_fk;
	private Produit produit;
	private int quantite;
	private Double prixUnitaire = 0.00;
	private Double prixTotal = 0.00;
	
	public LigneDeProduit(Produit produit,int quantite,Double prixUnitaire,Double prixTotal){
		this.produit = produit;
		this.quantite = quantite;
		this.prixUnitaire = prixUnitaire;
		this.prixTotal = prixTotal;
	}
	
	public LigneDeProduit(int id, int id_produit_fk, int id_facture_fk, int quantite,Double prixUnitaire,Double prixTotal){
		this.id = id;
		this.id_produit_fk = id_produit_fk;
		this.id_facture_fk = id_facture_fk;
		this.quantite = quantite;
		this.prixUnitaire = prixUnitaire;
		this.prixTotal = prixTotal;
	}
	
	public LigneDeProduit(){};
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setPrixUnitairet(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	public Double getPrixUnitaire() {
		return prixUnitaire;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setPrixTotal(Double prixTotal) {
		this.prixTotal = prixTotal;
	}
	public Double getPrixTotal() {
		return prixTotal;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setId_produit_fk(int id_produit_fk) {
		this.id_produit_fk = id_produit_fk;
	}

	public int getId_produit_fk() {
		return id_produit_fk;
	}

	public void setId_facture_fk(int id_facture_fk) {
		this.id_facture_fk = id_facture_fk;
	}

	public int getId_facture_fk() {
		return id_facture_fk;
	}


}
