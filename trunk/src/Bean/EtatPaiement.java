package Bean;

public class EtatPaiement {

	private int id;
	private String libelleEtatPaiement = "";
	private boolean actif;
	
	public EtatPaiement(int id, String libelleEtatPaiement, boolean actif){
		this.id = id;
		this.libelleEtatPaiement = libelleEtatPaiement;
		this.actif = actif;
	}
	
	public EtatPaiement(String libelleEtatPaiement){
		this.libelleEtatPaiement = libelleEtatPaiement;
	}
	
	public EtatPaiement(){};
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setLibelleEtatPaiement(String libelleEtatPaiement) {
		this.libelleEtatPaiement = libelleEtatPaiement;
	}
	public String getLibelleEtatPaiement() {
		return libelleEtatPaiement;
	}

	public boolean getActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}
	
}
