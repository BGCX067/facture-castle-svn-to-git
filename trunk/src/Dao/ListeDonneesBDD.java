package Dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import Bean.Client;
import Bean.EtatPaiement;
import Bean.Facture;
import Bean.FraisPort;
import Bean.MoyenPaiement;
import Bean.Produit;
import Bean.Region;
import Bean.Tranche;
import Bean.Tva;
import bdd.Connect;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;

public class ListeDonneesBDD {

	static ResultSet result;
	static ResultSet resultTranche;

	public ListeDonneesBDD(Connection conn) {

	}

	/* Liste des moyens de paiement */
	public static List<MoyenPaiement> recupMoyenPaiement() {

		List<MoyenPaiement> moyenPaiement = new ArrayList<MoyenPaiement>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_moyen_paiement");
			while (result.next()) {
				moyenPaiement.add(new MoyenPaiement(result
						.getInt("id_moyen_paiement"), result
						.getString("libelle_moyen_paiement"), result
						.getBoolean("actif")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moyenPaiement;
	}

	/* Récupérer id des moyens de paiement */
	public static int recupIdMoyenPaiement(String moyenPaiement) {

		System.out.println("Récupérer id des moyens de paiement");
		int idMoyenPaiement = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_moyen_paiement where libelle_moyen_paiement = '"
											+ moyenPaiement + "'");
			if (result.first()) {
				idMoyenPaiement = result.getInt("id_moyen_paiement");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idMoyenPaiement;
	}

	/* Liste des etats de paiement */
	public static List<EtatPaiement> recupEtatPaiement() {

		List<EtatPaiement> etatPaiement = new ArrayList<EtatPaiement>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_etat_paiement");
			while (result.next()) {
				etatPaiement.add(new EtatPaiement(result
						.getInt("id_etat_paiement"), result
						.getString("libelle_etat_paiement"), result
						.getBoolean("actif")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return etatPaiement;
	}

	/* Récupérer id des etats de paiement */
	public static int recupIdEtatPaiement(String etatPaiement) {

		System.out.println("Récupérer id des etats de paiement");
		int idEtatPaiement = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_etat_paiement where libelle_etat_paiement = '"
											+ etatPaiement + "'");
			if (result.first()) {
				idEtatPaiement = result.getInt("id_etat_paiement");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idEtatPaiement;
	}

	/* Liste des produits Actif */
	public static List<Produit> recupProduitActif() {

		List<Produit> produit = new ArrayList<Produit>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_produits WHERE actif=1");
			DAO<Tva> tvaDao = new TvaDAO(Connect.getInstance());
			while (result.next()) {
				produit.add(new Produit(result.getInt("id_produit"), result
						.getString("denomination_produit"), result
						.getDouble("prix_produit"), tvaDao.find(result
								.getInt("id_tva_fk")), result.getBoolean("actif"), result.getInt("stock")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produit;
	}

	/* Liste des produits */
	public static List<Produit> recupProduit() {

		List<Produit> produit = new ArrayList<Produit>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_produits");
			DAO<Tva> tvaDao = new TvaDAO(Connect.getInstance());
			while (result.next()) {
				produit.add(new Produit(result.getInt("id_produit"), result
						.getString("denomination_produit"), result
						.getDouble("prix_produit"), tvaDao.find(result
								.getInt("id_tva_fk")), result.getBoolean("actif"), result.getInt("stock")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produit;
	}

	/* Prix d un produit */
	public static Double getPrixProduit(String libelleProduit) {

		Double prixProduit = 0.00;
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_produits WHERE denomination_produit = '"
											+ libelleProduit + "'");
			while (result.next()) {
				prixProduit = result.getDouble("prix_produit");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prixProduit;
	}

	/* Numéro de facture */
	public static String getNumeroFacutre() {

		String numeroFacture = "";
		String nombreNumeroFacture = "";
		int tmpNumFacture = 001;
		DateFormat format = new SimpleDateFormat("yyyyMM");
		numeroFacture = format.format(new Date());
		System.out.println(numeroFacture);
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_facture WHERE num_facture like '%"
											+ numeroFacture + "%'");
			while (result.next()) {
				tmpNumFacture++;
			}
			if (tmpNumFacture < 9) {
				nombreNumeroFacture = "00";
			} else if (tmpNumFacture < 99) {
				nombreNumeroFacture = "0";
			}
			numeroFacture = numeroFacture + nombreNumeroFacture + tmpNumFacture;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(numeroFacture);
		return numeroFacture;
	}

	/* Récupérer id d un produit */
	public static int findIdProduit(Produit produit) {

		int idProduit = 0;
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_produits WHERE denomination_produit = '"
											+ produit.getLibelleProduit() + "'");
			if (result.first()) {
				idProduit = result.getInt("id_produit");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idProduit;
	}

	/* Récupérer le libellé d un produit */
	public static String findLibelleProduit(int idProduit) {

		String libelleProduit = "";
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT denomination_produit FROM arn_produits WHERE id_produit = '"
											+ idProduit + "'");
			if (result.first()) {
				libelleProduit = result.getString("denomination_produit");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return libelleProduit;
	}

	/* Récupérer id d une facture */
	public static int findIdFacture(Facture facture) {

		int idfacture = 0;
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_facture WHERE num_facture = '"
											+ facture.getNumeroFacture() + "'");
			if (result.first()) {
				idfacture = result.getInt("id_facture");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idfacture;
	}

	/* Récupérer le nombre de facture */
	public static int getNbrFacture() {

		int nbrfacture = 0;
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT count(*) FROM arn_facture");
			if (result.first()) {
				nbrfacture = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbrfacture;
	}

	/* Retourne toutes les factures */
	public static List<Facture> recupAllFacture() {

		List<Facture> allFacture = new ArrayList<Facture>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_facture order by date_facture desc LIMIT 0, 30");
			DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
			while (result.next()) {
				allFacture.add(new Facture(result.getInt("id_facture"), result
						.getString("num_facture"), result
						.getString("lieu_facture"), clientDao.find(result
								.getInt("id_client_fk")), result
								.getDouble("prix_total_HT"), result
								.getDouble("prix_total_TTC"), result
								.getString("date_facture"), result
								.getInt("id_etat_paiement_fk"), result
								.getInt("id_moyen_paiement_fk")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allFacture;
	}

	/* Retourne toutes les factures en fonction des critères de recherche */
	public static List<Facture> recupAllFactureCriteres(String numFacture,
			String nomClient, String dateFacture, String ville) {

		List<Facture> allFacture = new ArrayList<Facture>();
		List<Client> allClient = new ArrayList<Client>();
		String clause ="";
		DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
		allClient = ClientDAO.recupAllIdClientByNom(nomClient, ville);
		clientDao.findIdByNom(nomClient);

		if (!nomClient.equals("")) {
			Iterator<Client> itClient = allClient.iterator();

			while(itClient.hasNext())
			{
				Client clientTmp = itClient.next();
				if (numFacture.equals("") && nomClient.equals("")
						&& dateFacture.equals("") && ville.equals("")) {
					clause = "";
				} else {
					clause = " where ";
					if (!numFacture.equals("")) {
						clause += "num_facture = '" + numFacture + "'";
						if (!nomClient.equals("")) {
							clause += " and id_client_fk = "
									+ clientTmp.getId();
						}
						if (!dateFacture.equals("")) {
							clause += " and date_facture = '" + dateFacture + "'";
						}
					} else if (!nomClient.equals("")) {
						clause += "id_client_fk = " + clientTmp.getId();
						if (!dateFacture.equals("")) {
							clause += " and date_facture = '" + dateFacture + "'";
						}
					} else if (!dateFacture.equals("")) {
						clause += " date_facture = '" + dateFacture + "'";
					} else if (!ville.equals("")) {
						clause += " ville_client like '%" + clientTmp.getVille() + "%'";
					}
				}

				try {
					ResultSet result = (ResultSet) Connect
							.getInstance()
							.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
									java.sql.ResultSet.CONCUR_READ_ONLY)
									.executeQuery("SELECT * FROM arn_facture " + clause);
					System.out.println("SELECT * FROM arn_facture " + clause);


					while (result.next()) {
						allFacture.add(new Facture(result.getInt("id_facture"), result
								.getString("num_facture"), result
								.getString("lieu_facture"), clientDao.find(result
										.getInt("id_client_fk")), result
										.getDouble("prix_total_HT"), result
										.getDouble("prix_total_TTC"), result
										.getString("date_facture"), result
										.getInt("id_etat_paiement_fk"), result
										.getInt("id_moyen_paiement_fk")));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else{
			if (numFacture.equals("") && nomClient.equals("")
					&& dateFacture.equals("") && ville.equals("")) {
				clause = "";
			} else {
				clause = " where ";
				if (!numFacture.equals("")) {
					clause += "num_facture = '" + numFacture + "'";
					if (!nomClient.equals("")) {
						clause += " and id_client_fk = "
								+ clientDao.findIdByNom(nomClient);
					}
					if (!dateFacture.equals("")) {
						clause += " and date_facture = '" + dateFacture + "'";
					}
				} else if (!nomClient.equals("")) {
					clause += "id_client_fk = " + clientDao.findIdByNom(nomClient);
					if (!dateFacture.equals("")) {
						clause += " and date_facture = '" + dateFacture + "'";
					}
				} else if (!dateFacture.equals("")) {
					clause += " date_facture = '" + dateFacture + "'";
				} else if (!ville.equals("")) {
					clause += " ville_client like '%" + ville + "%'";
				}
			}

			try {
				ResultSet result;
				if (!ville.equals("")) {
					System.out
					.println("SELECT * FROM arn_facture, arn_clients "
							+ clause
							+ " AND arn_facture.id_client_fk = arn_clients.id_client ");

					result = (ResultSet) Connect
							.getInstance()
							.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
									java.sql.ResultSet.CONCUR_READ_ONLY)
									.executeQuery(
											"SELECT * FROM arn_facture, arn_clients "
													+ clause
													+ " AND arn_facture.id_client_fk = arn_clients.id_client ");

				} else {
					result = (ResultSet) Connect
							.getInstance()
							.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
									java.sql.ResultSet.CONCUR_READ_ONLY)
									.executeQuery("SELECT * FROM arn_facture " + clause);
					System.out.println("SELECT * FROM arn_facture " + clause);
				}

				while (result.next()) {
					allFacture.add(new Facture(result.getInt("id_facture"), result
							.getString("num_facture"), result
							.getString("lieu_facture"), clientDao.find(result
									.getInt("id_client_fk")), result
									.getDouble("prix_total_HT"), result
									.getDouble("prix_total_TTC"), result
									.getString("date_facture"), result
									.getInt("id_etat_paiement_fk"), result
									.getInt("id_moyen_paiement_fk")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allFacture;
	}

	/* Récupérer le nombre de facture */
	public static int getNbrFactureCriteres(String numFacture,
			String nomClient, String dateFacture, String ville) {

		int nbrfacture = 0;
		List<Client> allClient = new ArrayList<Client>();
		String clause ="";
		DAO<Client> clientDao = new ClientDAO(Connect.getInstance());
		allClient = ClientDAO.recupAllIdClientByNom(nomClient, ville);

		if (!nomClient.equals("")) {
			Iterator<Client> itClient = allClient.iterator();
			while(itClient.hasNext())
			{
				Client clientTmp = itClient.next();
				if (numFacture.equals("") && nomClient.equals("")
						&& dateFacture.equals("") && ville.equals("")) {
					clause = "";
				} else {
					clause = " where ";
					if (!numFacture.equals("")) {
						clause += "num_facture = '" + numFacture + "'";
						if (!nomClient.equals("")) {
							clause += " and id_client_fk = "
									+ clientTmp.getId();
						}
						if (!dateFacture.equals("")) {
							clause += " and date_facture = '" + dateFacture + "'";
						}
					} else if (!nomClient.equals("")) {
						clause += "id_client_fk = " + clientTmp.getId();
						if (!dateFacture.equals("")) {
							clause += " and date_facture = '" + dateFacture + "'";
						}
					} else if (!dateFacture.equals("")) {
						clause += " date_facture = '" + dateFacture + "'";
					} else if (!ville.equals("")) {
						clause += " ville_client like '%" + clientTmp.getVille() + "%'";
					}
				}
				try {
					ResultSet result = (ResultSet) Connect
							.getInstance()
							.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
									java.sql.ResultSet.CONCUR_READ_ONLY)
									.executeQuery(
											"SELECT count(*) FROM arn_facture " + clause);

					if (result.first()) {
						nbrfacture += result.getInt(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else{

			if (numFacture.equals("") && nomClient.equals("")
					&& dateFacture.equals("") && ville.equals("")) {
				clause = "";
			} else {
				clause = " where ";
				if (!numFacture.equals("")) {
					clause += "num_facture = '" + numFacture + "'";
					if (!nomClient.equals("")) {
						clause += " and id_client_fk = "
								+ clientDao.findIdByNom(nomClient);
					}
					if (!dateFacture.equals("")) {
						clause += " and date_facture = '" + dateFacture + "'";
					}
				} else if (!nomClient.equals("")) {
					clause += "id_client_fk = " + clientDao.findIdByNom(nomClient);
					if (!dateFacture.equals("")) {
						clause += " and date_facture = '" + dateFacture + "'";
					}
				} else if (!dateFacture.equals("")) {
					clause += " date_facture = '" + dateFacture + "'";
				} else if (!ville.equals("")) {
					clause += " ville_client like '%" + ville + "%'";
				}
			}
			try {
				ResultSet result;
				if (!ville.equals("")) {
					System.out
					.println("SELECT count(*) FROM arn_facture, arn_clients "
							+ clause
							+ "AND arn_facture.id_client_fk = arn_clients.id_client ");

					result = (ResultSet) Connect
							.getInstance()
							.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
									java.sql.ResultSet.CONCUR_READ_ONLY)
									.executeQuery(
											"SELECT count(*) FROM arn_facture, arn_clients "
													+ clause
													+ " AND arn_facture.id_client_fk = arn_clients.id_client ");

				} else {
					result = (ResultSet) Connect
							.getInstance()
							.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
									java.sql.ResultSet.CONCUR_READ_ONLY)
									.executeQuery(
											"SELECT count(*) FROM arn_facture " + clause);
				}
				if (result.first()) {
					nbrfacture = result.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nbrfacture;
	}

	/* Liste des villes des clients */
	public static List<String> recupVilleClient() {

		List<String> ville = new ArrayList<String>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT ville_client FROM arn_clients");
			while (result.next()) {
				ville.add((result.getString("ville_client")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ville;
	}

	/* Liste des noms des clients */
	public static List<String> recupNomClient() {

		List<String> nomClient = new ArrayList<String>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT nom_client FROM arn_clients");
			while (result.next()) {
				nomClient.add((result.getString("nom_client")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nomClient;
	}

	/* Liste des prenoms des clients */
	public static List<String> recupPrenomClient(String nomClient) {

		List<String> prenomClient = new ArrayList<String>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT prenom_client FROM arn_clients where nom_client ='"
											+ nomClient + "'");
			while (result.next()) {
				prenomClient.add((result.getString("prenom_client")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prenomClient;
	}

	/* Liste des produits disponibles dans la table de relation facture produits */
	public static List<String> recupListeProduitsRelation() {

		List<String> listeProduit = new ArrayList<String>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT distinct(id_produit_fk) FROM arn_relation_facture_produits");
			while (result.next()) {
				listeProduit.add((result.getString("id_produit_fk")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeProduit;
	}

	/**
	 * Compter le nombre de bouteille vendus dans la table de relation facture
	 * produits
	 **/
	public static int recupNbrBouteilleVendu(int idProduit) {

		int nbrBouteille = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT count(quantite_produit) as nbrProduit FROM arn_relation_facture_produits where id_produit_fk = "
											+ idProduit);
			while (result.next()) {
				nbrBouteille = result.getInt("nbrProduit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(nbrBouteille);
		return nbrBouteille;
	}

	/**
	 * Compter le nombre de bouteille vendus dans la table de relation facture
	 * produits avec criteres
	 **/
	public static int recupNbrBouteilleVenduWithCriteres(int idProduit,
			String mois, String annees) {

		int nbrBouteille = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT sum(quantite_produit) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where id_produit_fk = '"
											+ idProduit
											+ "' and MONTH(date_facture) = '"
											+ mois
											+ "' and YEAR(date_facture) = '"
											+ annees
											+ "'");
			while (result.next()) {
				nbrBouteille = result.getInt("nbrProduit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out
		.println("SELECT sum(quantite_produit) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where id_produit_fk = '"
				+ idProduit
				+ "' and MONTH(date_facture) = '"
				+ mois
				+ "' and YEAR(date_facture) = '" + annees + "'");
		return nbrBouteille;
	}

	/**
	 * Compter le nombre de bouteille vendus dans la table de relation facture
	 * produits avec criteres de date (periode)
	 **/
	public static int recupNbrBouteilleVenduWithCriteresDate(int idProduit,
			Date dateMinimum, Date dateMaximum) {

		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String dateMinimumFormate = formatDate.format(dateMinimum.getTime());
		String dateMaximumFormate = formatDate.format(dateMaximum.getTime());

		int nbrBouteille = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT sum(quantite_produit) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where id_produit_fk = '"
											+ idProduit
											+ "' and date_facture > '"
											+ dateMinimumFormate
											+ "' and date_facture < '"
											+ dateMaximumFormate + "'");
			while (result.next()) {
				nbrBouteille = result.getInt("nbrProduit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out
		.println("SELECT sum(quantite_produit) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where id_produit_fk = '"
				+ idProduit
				+ "' and date_facture > '"
				+ dateMinimumFormate
				+ "' and date_facture < '"
				+ dateMaximumFormate + "'");
		return nbrBouteille;
	}

	/**
	 * Compter le nombre de bouteille vendus dans la table de relation facture
	 * produits avec criteres
	 **/
	public static int recupPrixByBouteilleVenduWithCriteres(int idProduit,
			String mois, String annees) {

		int prixByBouteille = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT sum(prix_total) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where id_produit_fk = '"
											+ idProduit
											+ "' and MONTH(date_facture) = '"
											+ mois
											+ "' and YEAR(date_facture) = '"
											+ annees
											+ "'");
			while (result.next()) {
				prixByBouteille = result.getInt("nbrProduit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prixByBouteille;
	}

	/**
	 * Compter le nombre de bouteille vendus dans la table de relation facture
	 * produits avec criteres de date (periode)
	 **/
	public static int recupPrixByBouteilleVenduWithCriteresDate(int idProduit,
			Date dateMinimum, Date dateMaximum) {

		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String dateMinimumFormate = formatDate.format(dateMinimum.getTime());
		String dateMaximumFormate = formatDate.format(dateMaximum.getTime());

		int prixByBouteille = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT sum(prix_total) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where id_produit_fk = '"
											+ idProduit
											+ "' and date_facture > '"
											+ dateMinimumFormate
											+ "' and date_facture < '"
											+ dateMaximumFormate + "'");
			while (result.next()) {
				prixByBouteille = result.getInt("nbrProduit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prixByBouteille;
	}

	/**
	 * Liste des taux de tva
	 **/
	public static List<Tva> recupTauxTva() {

		List<Tva> tauxTva = new ArrayList<Tva>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_tva");
			while (result.next()) {
				tauxTva.add(new Tva(result.getInt("id_tva"), result
						.getDouble("taux_tva")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tauxTva;
	}

	/**
	 * Récupérer id des taux de tva
	 **/
	public static int recupIdTauxTva(String valeurTaux) {

		System.out.println("Récupérer id du taux de tva");
		System.out.println("SELECT * FROM arn_tva where taux_tva = "
				+ valeurTaux);
		int idTauxTva = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_tva where taux_tva = "
											+ valeurTaux + "");
			if (result.first()) {
				idTauxTva = result.getInt("id_tva");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idTauxTva;
	}

	/* Récupérer le nombre de produit */
	public static int getNbrProduit() {

		int nbrfacture = 0;
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT count(*) FROM arn_produits");
			if (result.first()) {
				nbrfacture = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbrfacture;
	}

	/* retourne un produit */
	public static Produit recupUnProduit(String idProduit) {

		Produit produit = new Produit();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_produits WHERE id_produit ='"
											+ idProduit + "'");
			DAO<Tva> tvaDao = new TvaDAO(Connect.getInstance());
			while (result.next()) {
				produit = new Produit(result.getInt("id_produit"),
						result.getString("denomination_produit"),
						result.getDouble("prix_produit"), tvaDao.find(result
								.getInt("id_tva_fk")),
								result.getBoolean("actif"), result.getInt("stock"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produit;
	}

	public static boolean verificationUtilisationProduit(Produit produit) {

		boolean exist = false;
		int nbrFacture = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT count(id) as nbrFacture FROM arn_relation_facture_produits where id_produit_fk = "
											+ produit.getId());

			System.out
			.println("SELECT count(id) as nbrFacture FROM arn_relation_facture_produits where id_produit_fk = "
					+ produit.getId());
			while (result.next()) {
				nbrFacture = result.getInt("nbrFacture");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (nbrFacture > 0) {
			exist = true;
		}
		return exist;

	}

	/* Récupérer le nombre de produit */
	public static int getNbrClient() {

		int nbrclient = 0;
		try {
			ResultSet result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT count(*) FROM arn_clients");
			if (result.first()) {
				nbrclient = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbrclient;
	}

	/* Liste des produits */
	public static List<Client> recupClient() {

		List<Client> client = new ArrayList<Client>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_clients");

			while (result.next()) {
				client.add(new Client(result.getInt("id_client"), result
						.getString("nom_client"), result
						.getString("prenom_client"), result
						.getString("adresse_client"), result
						.getString("cp_client"), result
						.getString("ville_client"), result
						.getString("infos_client"), result
						.getString("mail_client"), result
						.getString("tel_client"), result
						.getString("ddn_client")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}

	/* Liste des produits */
	public static Client recupUnClient(String idClient) {

		Client client = new Client();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_clients WHERE id_client ='"
											+ idClient + "'");

			while (result.next()) {
				client = new Client(result.getInt("id_client"),
						result.getString("nom_client"),
						result.getString("prenom_client"),
						result.getString("adresse_client"),
						result.getString("cp_client"),
						result.getString("ville_client"),
						result.getString("infos_client"),
						result.getString("mail_client"),
						result.getString("tel_client"),
						result.getString("ddn_client"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * Compter le nombre de bouteille vendus dans la table de relation facture
	 * produits avec criteres
	 **/
	public static int recupNbrBouteilleVenduByMonthAndYear(String mois,
			String annees) {

		int nbrBouteille = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT sum(quantite_produit) as nbrProduit FROM arn_relation_facture_produits inner join arn_facture on id_facture=id_facture_fk where MONTH(date_facture) = '"
											+ mois
											+ "' and YEAR(date_facture) = '"
											+ annees + "'");
			while (result.next()) {
				nbrBouteille = result.getInt("nbrProduit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbrBouteille;
	}

	public static boolean verificationUtilisationClient(Client client) {

		boolean exist = false;
		int nbrClientUsed = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT count(arnRelFacProd.id) as nbrClientUsed FROM arn_relation_facture_produits arnRelFacProd, arn_facture arnFac where arnFac.id_facture = arnRelFacProd.id_facture_fk AND id_client_fk = "
											+ client.getId());

			System.out
			.println("SELECT count(arnRelFacProd.id) as nbrClientUsed FROM arn_relation_facture_produits arnRelFacProd, arn_facture arnFac where arnFac.id_facture = arnRelFacProd.id_facture_fk AND id_client_fk = "
					+ client.getId());
			while (result.next()) {
				nbrClientUsed = result.getInt("nbrClientUsed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (nbrClientUsed > 0) {
			exist = true;
		}
		return exist;

	}

	/* Liste des moyens de paiement */
	public static MoyenPaiement recupUnMoyenPaiement(String idMoyenPaiement) {

		MoyenPaiement moyenPaiement = new MoyenPaiement();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_moyen_paiement WHERE id_moyen_paiement = "
											+ idMoyenPaiement);
			while (result.next()) {
				moyenPaiement = new MoyenPaiement(
						result.getInt("id_moyen_paiement"),
						result.getString("libelle_moyen_paiement"),
						result.getBoolean("actif"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moyenPaiement;
	}

	/* Liste des moyens de paiement */
	public static List<MoyenPaiement> recupMoyenPaiementActif() {

		List<MoyenPaiement> moyenPaiement = new ArrayList<MoyenPaiement>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_moyen_paiement where actif=1");
			while (result.next()) {
				moyenPaiement.add(new MoyenPaiement(result
						.getInt("id_moyen_paiement"), result
						.getString("libelle_moyen_paiement"), result
						.getBoolean("actif")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moyenPaiement;
	}

	public static boolean verificationUtilisationMoyenPaiement(
			MoyenPaiement moyenPaiement) {

		boolean exist = false;
		int nbrFacture = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT count(id) as nbrFacture FROM arn_relation_facture_produits where id_produit_fk = "
											+ moyenPaiement.getId());

			System.out
			.println("SELECT count(id) as nbrFacture FROM arn_facture where id_moyen_paiement_fk  = "
					+ moyenPaiement.getId());
			while (result.next()) {
				nbrFacture = result.getInt("nbrFacture");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (nbrFacture > 0) {
			exist = true;
		}
		return exist;

	}

	/* retourne un etat de paiement */
	public static EtatPaiement recupUnEtatPaiement(String idEtatPaiement) {

		EtatPaiement etatPaiement = new EtatPaiement();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_etat_paiement WHERE id_etat_paiement = "
											+ idEtatPaiement);
			while (result.next()) {
				etatPaiement = new EtatPaiement(
						result.getInt("id_etat_paiement"),
						result.getString("libelle_etat_paiement"),
						result.getBoolean("actif"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return etatPaiement;
	}

	public static boolean verificationUtilisationEtatPaiement(
			EtatPaiement etatPaiement) {

		boolean exist = false;
		int nbrFacture = 0;
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT count(id_facture) as nbrFacture FROM arn_facture where id_etat_paiement_fk  = "
											+ etatPaiement.getId());

			System.out
			.println("SELECT count(id_facture) as nbrFacture FROM arn_facture where id_etat_paiement_fk   = "
					+ etatPaiement.getId());
			while (result.next()) {
				nbrFacture = result.getInt("nbrFacture");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (nbrFacture > 0) {
			exist = true;
		}
		return exist;

	}

	/* Liste des etats de paiement */
	public static List<EtatPaiement> recupEtatPaiementActif() {

		List<EtatPaiement> etatPaiement = new ArrayList<EtatPaiement>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_etat_paiement WHERE actif=1");
			while (result.next()) {
				etatPaiement.add(new EtatPaiement(result
						.getInt("id_etat_paiement"), result
						.getString("libelle_etat_paiement"), result
						.getBoolean("actif")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return etatPaiement;
	}

	/* Liste des etats de paiement */
	public static List<Region> recupRegion() {

		List<Region> region = new ArrayList<Region>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_region");
			while (result.next()) {
				region.add(new Region(result.getInt("id_region"), result
						.getString("regions")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return region;
	}

	/* Liste des etats de paiement */
	public static Region recupUneRegion(String idRegion) {

		Region region = new Region();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_region WHERE id_region = "
											+ idRegion);
			while (result.next()) {
				region = new Region(result.getInt("id_region"),
						result.getString("regions"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return region;
	}

	/* Liste des etats de paiement */
	public static List<Tranche> recupTranche() {

		List<Tranche> tranche = new ArrayList<Tranche>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_tranche_frais_port");
			while (result.next()) {
				tranche.add(new Tranche(result.getInt("id_tranche_frais_port"),
						result.getString("nom_tranche"), result
						.getInt("tranche_min"), result
						.getInt("tranche_max")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranche;
	}

	/* Liste des etats de paiement */
	public static List<FraisPort> recupFraisPort() {

		List<FraisPort> fraisPort = new ArrayList<FraisPort>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_frais_port");
			while (result.next()) {
				fraisPort.add(new FraisPort(result.getInt("id_frais_port"),
						result.getString("tranche1"), result
						.getString("tranche2"), result
						.getString("tranche3"), result
						.getString("tranche4"), result
						.getString("tranche5"), result
						.getString("tranche6"), result
						.getString("tranche7"), result
						.getString("tranche8"), result
						.getString("tranche9"), result
						.getString("tranche10"), result
						.getString("tranche11"), result
						.getString("tranche12"), result
						.getInt("id_region_fk")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fraisPort;
	}

	/* Liste des etats de paiement */
	public static double recupeFraisPortQantiteCodePostal(int nbBouteille,
			int Region) {

		double port = 0.0;
		String nomTranche = null;
		try {

			// On récupère le nom de la tranche
			System.out
			.println("SELECT * FROM arn_tranche_frais_port WHERE tranche_min < "
					+ nbBouteille + " AND tranche_max > " + nbBouteille);
			resultTranche = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT * FROM arn_tranche_frais_port WHERE tranche_min <= "
											+ nbBouteille + " AND tranche_max >= "
											+ nbBouteille);
			if (resultTranche.first()) {
				nomTranche = resultTranche.getString("nom_tranche");
			}

			if(nomTranche != null){
				// On recupère valeur du frais de port en fonction de la tranche et
				// de la region
				result = (ResultSet) Connect
						.getInstance()
						.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
								java.sql.ResultSet.CONCUR_READ_ONLY)
								.executeQuery(
										"SELECT "
												+ nomTranche
												+ " FROM arn_frais_port WHERE id_region_fk = "
												+ Region);
				if (result.first()) {

					port = result.getDouble(nomTranche);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return port;
	}

	/* Liste des etats de paiement */
	public static int recupzone(String region) {

		int zone = 0;
		String[] regions = null;

		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery("SELECT * FROM arn_region");
			while (result.next()) {
				if(result.getString("regions") != null){
					regions = Pattern.compile(",").split(
							result.getString("regions"));

					for (int i = 0; i < regions.length; i++) {
						if (region.equals(regions[i])) {
							zone = result.getInt("id_region");
							break;
						}
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return zone;
	}

	/* Liste des produits vendu le mois en cours */
	public static List<Produit> recupProduitVendusMoisCourant(int mois) {

		System.out
		.println("SELECT DISTINCT(denomination_produit), SUM(quantite_produit) AS total FROM arn_produits p, arn_relation_facture_produits fp, arn_facture f WHERE p.id_produit = fp.id_produit_fk AND fp.id_facture_fk = f.id_facture AND MONTH(f.date_facture)='"
				+ mois + "' GROUP BY denomination_produit");
		List<Produit> produit = new ArrayList<Produit>();
		try {
			result = (ResultSet) Connect
					.getInstance()
					.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY)
							.executeQuery(
									"SELECT DISTINCT(denomination_produit), SUM(quantite_produit) AS total FROM arn_produits p, arn_relation_facture_produits fp, arn_facture f WHERE p.id_produit = fp.id_produit_fk AND fp.id_facture_fk = f.id_facture AND MONTH(f.date_facture)='"
											+ mois + "' GROUP BY denomination_produit");
			while (result.next()) {
				produit.add(new Produit (result.getString("denomination_produit"), result.getInt("total")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produit;
	}

}
