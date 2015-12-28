package Bean;

public class MoyenPaiement {

	private int id;
	private String libelleMoyenPaiement = "";
	private Boolean Actif;
	
	

	public MoyenPaiement(int id, String libelleMoyenPaiement, boolean actif){
		this.id = id;
		this.libelleMoyenPaiement = libelleMoyenPaiement;
		this.Actif = actif;
	}
	
	public MoyenPaiement(String libelleMoyenPaiement){
		this.libelleMoyenPaiement = libelleMoyenPaiement;
	}
	
	public MoyenPaiement(){};
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setLibelleMoyenPaiement(String libelleMoyenPaiement) {
		this.libelleMoyenPaiement = libelleMoyenPaiement;
	}
	public String getLibelleMoyenPaiement() {
		return libelleMoyenPaiement;
	}
	
	
	public Boolean getActif() {
		return Actif;
	}

	public void setActif(Boolean actif) {
		Actif = actif;
	}
}
