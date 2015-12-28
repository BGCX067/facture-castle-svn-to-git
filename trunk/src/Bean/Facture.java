package Bean;



public class Facture {

	private int id;
	private String numeroFacture = "";
	private String lieuTransaction = "";
	private Client client;
	private Double remise = 0.00;
	private Double port = 0.00;
	private Double txTVA = 0.00;
	private Double prixTotalHT = 0.00;
	private Double prixTotalTTC = 0.00;
	private String dateFacture;
	private int idEtatPaiement;
	private int idMoyenPaiement;
	private LigneDeProduit[] ligneDeProduit;

	private String destNom;
	private String destPrenom;
	private String destAdresse;
	private String destCodePostal;
	private String destVille;
	private String destMail;
	private String destTelephone;


	public Facture(int id, String numeroFacture,String lieuTransaction, Client client, Double prixTotalHT, Double prixTotalTTC, String dateFacture, int idEtatPaiement, int idMoyenPaiement, LigneDeProduit[] ligneDeProduit){
		this.id=id;
		this.numeroFacture=numeroFacture;
		this.lieuTransaction=lieuTransaction;
		this.client=client;
		this.prixTotalHT=prixTotalHT;
		this.prixTotalTTC=prixTotalTTC;
		this.dateFacture=dateFacture;
		this.idEtatPaiement=idEtatPaiement;
		this.idMoyenPaiement=idMoyenPaiement;
		this.ligneDeProduit=ligneDeProduit;
	};

	public Facture(int id, String numeroFacture,String lieuTransaction, Client client, Double prixTotalHT, Double prixTotalTTC, String dateFacture, int idEtatPaiement, int idMoyenPaiement){
		this.id=id;
		this.numeroFacture=numeroFacture;
		this.lieuTransaction=lieuTransaction;
		this.client=client;
		this.prixTotalHT=prixTotalHT;
		this.prixTotalTTC=prixTotalTTC;
		this.dateFacture=dateFacture;
		this.idEtatPaiement=idEtatPaiement;
		this.idMoyenPaiement=idMoyenPaiement;
	};




	public Facture(int id, String numeroFacture, String lieuTransaction,
			Client client,Double remise, Double port, Double txTVA, Double prixTotalHT,
			Double prixTotalTTC, String dateFacture, int idEtatPaiement,
			int idMoyenPaiement,
			String destNom, String destPrenom, String destAdresse,
			String destCodePostal, String destVille, String destMail,
			String destTelephone) {
		super();
		this.id = id;
		this.numeroFacture = numeroFacture;
		this.lieuTransaction = lieuTransaction;
		this.client = client;
		this.remise = remise;
		this.port = port;
		this.txTVA = txTVA;
		this.prixTotalHT = prixTotalHT;
		this.prixTotalTTC = prixTotalTTC;
		this.dateFacture = dateFacture;
		this.idEtatPaiement = idEtatPaiement;
		this.idMoyenPaiement = idMoyenPaiement;
		this.destNom = destNom;
		this.destPrenom = destPrenom;
		this.destAdresse = destAdresse;
		this.destCodePostal = destCodePostal;
		this.destVille = destVille;
		this.destMail = destMail;
		this.destTelephone = destTelephone;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public void setNumeroFacture(String numeroFacture) {
		this.numeroFacture = numeroFacture;
	}
	public String getNumeroFacture() {
		return this.numeroFacture;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Client getClient() {
		return this.client;
	}
	public void setPrixTotalTTC(Double prixTotalTTC) {
		this.prixTotalTTC = prixTotalTTC;
	}
	public Double getPrixTotalTTC() {
		return this.prixTotalTTC;
	}
	public void setPrixTotalHT(Double prixTotalHT) {
		this.prixTotalHT = prixTotalHT;
	}
	public Double getPrixTotalHT() {
		return this.prixTotalHT;
	}
	public void setDateFacture(String dateFacture) {
		this.dateFacture = dateFacture;
	}
	public String getDateFacture() {
		return this.dateFacture;
	}
	public void setIdEtatPaiement(int idEtatPaiement) {
		this.idEtatPaiement = idEtatPaiement;
	}
	public int getIdEtatPaiement() {
		return this.idEtatPaiement;
	}
	public void setIdMoyenPaiement(int idMoyenPaiement) {
		this.idMoyenPaiement = idMoyenPaiement;
	}
	public int getIdMoyenPaiement() {
		return this.idMoyenPaiement;
	}

	public void setLieuTransaction(String lieuTransaction) {
		this.lieuTransaction = lieuTransaction;
	}

	public String getLieuTransaction() {
		return this.lieuTransaction;
	}

	public void setLigneDeProduit(LigneDeProduit[] ligneDeProduit) {
		this.ligneDeProduit = ligneDeProduit;
	}

	public LigneDeProduit listeLigneDeProduit(int i) {
		return this.ligneDeProduit[i];
	}

	public LigneDeProduit[] getLigneDeProduit() {
		return this.ligneDeProduit;
	}

	public void setPort(Double port) {
		this.port = port;
	}

	public Double getPort() {
		return this.port;
	}

	public void setTxTVA(Double txTVA) {
		this.txTVA = txTVA;
	}

	public Double getTxTVA() {
		return this.txTVA;
	}

	public String getDateFactureFormate() {
		return this.dateFacture.substring(8,10)+"/"+this.dateFacture.substring(5,7)+"/"+this.dateFacture.substring(0,4);
	}





	public final String getDestNom() {
		return this.destNom;
	}

	public final void setDestNom(String destNom) {
		this.destNom = destNom;
	}

	public final String getDestPrenom() {
		return this.destPrenom;
	}

	public final void setDestPrenom(String destPrenom) {
		this.destPrenom = destPrenom;
	}

	public final String getDestAdresse() {
		return this.destAdresse;
	}

	public final void setDestAdresse(String destAdresse) {
		this.destAdresse = destAdresse;
	}

	public final String getDestCodePostal() {
		return this.destCodePostal;
	}

	public final void setDestCodePostal(String destCodePostal) {
		this.destCodePostal = destCodePostal;
	}

	public final String getDestVille() {
		return this.destVille;
	}

	public final void setDestVille(String destVille) {
		this.destVille = destVille;
	}

	public final String getDestMail() {
		return this.destMail;
	}

	public final void setDestMail(String destMail) {
		this.destMail = destMail;
	}

	public final String getDestTelephone() {
		return this.destTelephone;
	}

	public final void setDestTelephone(String destTelephone) {
		this.destTelephone = destTelephone;
	}

	public Facture(){
		this.numeroFacture = "20110101";
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	public Double getRemise() {
		return this.remise;
	}



}
