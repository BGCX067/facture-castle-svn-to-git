package Bean;

import java.util.Calendar;

public class Bilan {

	private String nomSociete;
	private String nomGerantUn;
	private String nomGerantDeux;
	private String nomGerantTrois;
	private String titreBilan;
	private String titrePremierePartie;
	private String contenuPremierePartie;
	private String titreDeuxiemePartie;
	private String contenuDeuxiemePartie;
	private String titreTroisiemePartie;
	private String contenuTroisiemePartie;
	private String titreQuatriemePartie;
	private String contenuQuatriemePartie;
	private Calendar dateGeneration;



	public Bilan() {
	}

	/**
	 * @param nomSociete
	 * @param nomGerantUn
	 * @param nomGerantDeux
	 * @param nomGerantTrois
	 * @param titreBilan
	 * @param titrePremierePartie
	 * @param contenuPremierePartie
	 * @param titreDeuxiemePartie
	 * @param contenuDeuxiemePartie
	 * @param titreTroisiemePartie
	 * @param contenuTroisiemePartie
	 * @param titreQuatriemePartie
	 * @param contenuQuatriemePartie
	 * @param dateGeneration
	 */
	public Bilan(String nomSociete, String nomGerantUn, String nomGerantDeux,
			String nomGerantTrois, String titreBilan,
			String titrePremierePartie, String contenuPremierePartie,
			String titreDeuxiemePartie, String contenuDeuxiemePartie,
			String titreTroisiemePartie, String contenuTroisiemePartie,
			String titreQuatriemePartie, String contenuQuatriemePartie,
			Calendar dateGeneration) {
		super();
		this.nomSociete = nomSociete;
		this.nomGerantUn = nomGerantUn;
		this.nomGerantDeux = nomGerantDeux;
		this.nomGerantTrois = nomGerantTrois;
		this.titreBilan = titreBilan;
		this.titrePremierePartie = titrePremierePartie;
		this.contenuPremierePartie = contenuPremierePartie;
		this.titreDeuxiemePartie = titreDeuxiemePartie;
		this.contenuDeuxiemePartie = contenuDeuxiemePartie;
		this.titreTroisiemePartie = titreTroisiemePartie;
		this.contenuTroisiemePartie = contenuTroisiemePartie;
		this.titreQuatriemePartie = titreQuatriemePartie;
		this.contenuQuatriemePartie = contenuQuatriemePartie;
		this.dateGeneration = dateGeneration;
	}

	public String getNomSociete() {
		return this.nomSociete;
	}

	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}

	public String getNomGerantUn() {
		return this.nomGerantUn;
	}

	public void setNomGerantUn(String nomGerantUn) {
		this.nomGerantUn = nomGerantUn;
	}

	public String getNomGerantDeux() {
		return this.nomGerantDeux;
	}

	public void setNomGerantDeux(String nomGerantDeux) {
		this.nomGerantDeux = nomGerantDeux;
	}

	public String getNomGerantTrois() {
		return this.nomGerantTrois;
	}

	public void setNomGerantTrois(String nomGerantTrois) {
		this.nomGerantTrois = nomGerantTrois;
	}

	public String getTitreBilan() {
		return this.titreBilan;
	}

	public void setTitreBilan(String titreBilan) {
		this.titreBilan = titreBilan;
	}

	public String getTitrePremierePartie() {
		return this.titrePremierePartie;
	}

	public void setTitrePremierePartie(String titrePremierePartie) {
		this.titrePremierePartie = titrePremierePartie;
	}

	public String getContenuPremierePartie() {
		return this.contenuPremierePartie;
	}

	public void setContenuPremierePartie(String contenuPremierePartie) {
		this.contenuPremierePartie = contenuPremierePartie;
	}

	public String getTitreDeuxiemePartie() {
		return this.titreDeuxiemePartie;
	}

	public void setTitreDeuxiemePartie(String titreDeuxiemePartie) {
		this.titreDeuxiemePartie = titreDeuxiemePartie;
	}

	public String getContenuDeuxiemePartie() {
		return this.contenuDeuxiemePartie;
	}

	public void setContenuDeuxiemePartie(String contenuDeuxiemePartie) {
		this.contenuDeuxiemePartie = contenuDeuxiemePartie;
	}

	public String getTitreTroisiemePartie() {
		return this.titreTroisiemePartie;
	}

	public void setTitreTroisiemePartie(String titreTroisiemePartie) {
		this.titreTroisiemePartie = titreTroisiemePartie;
	}

	public String getContenuTroisiemePartie() {
		return this.contenuTroisiemePartie;
	}

	public void setContenuTroisiemePartie(String contenuTroisiemePartie) {
		this.contenuTroisiemePartie = contenuTroisiemePartie;
	}

	public String getTitreQuatriemePartie() {
		return this.titreQuatriemePartie;
	}

	public void setTitreQuatriemePartie(String titreQuatriemePartie) {
		this.titreQuatriemePartie = titreQuatriemePartie;
	}

	public String getContenuQuatriemePartie() {
		return this.contenuQuatriemePartie;
	}

	public void setContenuQuatriemePartie(String contenuQuatriemePartie) {
		this.contenuQuatriemePartie = contenuQuatriemePartie;
	}

	public Calendar getDateGeneration() {
		return this.dateGeneration;
	}

	public void setDateGeneration(Calendar dateGeneration) {
		this.dateGeneration = dateGeneration;
	}



}
