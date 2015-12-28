package Bean;


public class Client {

	private int id;
	private String nom = "";
	private String prenom = "";
	private String adresse = "";
	private String cp = "";
	private String ville = "";
	private String infos = "";
	private String mail="";
	private String dateNaissance = null;
	private String telephone = "";


	public Client(){};

	public Client(int id, String nom, String prenom, String adresse, String cp, String ville,  String infos,String mail,String telephone,String dateNaissance){
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.infos = infos;
		this.telephone = telephone;
		this.mail = mail;
		this.dateNaissance = dateNaissance;
	};

	public Client(int id, String ville){
		this.id = id;
		this.ville = ville;
	};

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNom() {
		return this.nom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPrenom() {
		return this.prenom;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getAdresse() {
		return this.adresse;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getCp() {
		return this.cp;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getVille() {
		return this.ville;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public String getInfos() {
		return this.infos;
	}


}
