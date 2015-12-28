-- phpMyAdmin SQL Dump
-- version 3.4.3.1
-- http://www.phpmyadmin.net
--
-- Client: 127.0.0.1
-- Généré le : Mer 03 Août 2011 à 00:38
-- Version du serveur: 5.5.13
-- Version de PHP: 5.3.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `arnauds`
--

-- --------------------------------------------------------

--
-- Structure de la table `arn_clients`
--

CREATE TABLE IF NOT EXISTS `arn_clients` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `nom_client` varchar(32) NOT NULL,
  `prenom_client` varchar(32) DEFAULT NULL,
  `adresse_client` varchar(32) DEFAULT NULL,
  `cp_client` varchar(32) DEFAULT NULL,
  `ville_client` varchar(32) DEFAULT NULL,
  `infos_client` text,
  `mail_client` varchar(250) DEFAULT NULL,
  `tel_client` varchar(25) DEFAULT NULL,
  `ddn_client` date DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

--
-- Contenu de la table `arn_clients`
--

INSERT INTO `arn_clients` (`id_client`, `nom_client`, `prenom_client`, `adresse_client`, `cp_client`, `ville_client`, `infos_client`, `mail_client`, `tel_client`, `ddn_client`) VALUES
(20, '', '', '', '', '', '', '', '', NULL),
(19, 'rte', 'tert', '', '', '', '', '', '', NULL),
(1, 'Client 1', 'prenom Client 1', 'adresse Client 1', '33500', 'Libourne', 'Infos Client 1', '', '', NULL),
(2, 'Client 2', 'prenom Client 2', 'adresse Client 2', '24000', 'Cercoux', 'Infos Client 2', '', '', NULL),
(3, 'Client 3', 'prenom Client 3', 'adresse Client 3', '69000', 'Lyon', 'Infos Client 3', '', '', NULL),
(4, 'Client 4', 'prenom Client 4', 'adresse Client 4', '33700', 'Mérignac', 'Infos Client 4', '', '', NULL),
(5, 'Client 5', 'prenom Client 5', 'adresse Client 5', '93000', 'Créteil', 'Infos Client 5', '', '', NULL),
(21, 'Aumettre', 'ere', 'FE', '33500', 'fde', '', '', '', NULL),
(22, 'toto', 'fsdfsd', 'fdsfsd', '33500', 'fe', '', '', '', NULL),
(23, 'de', 'de', 'de', '33500', 'de', '', '', '', NULL),
(24, 'toto', 'titi', 'la bas', '33700', 'Merignac', '', '', '', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `arn_conf_appli`
--

CREATE TABLE IF NOT EXISTS `arn_conf_appli` (
  `id_conf` int(11) NOT NULL AUTO_INCREMENT,
  `tva_conf` double NOT NULL,
  `nb_lignes_produit` int(11) NOT NULL,
  PRIMARY KEY (`id_conf`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `arn_conf_appli`
--

INSERT INTO `arn_conf_appli` (`id_conf`, `tva_conf`, `nb_lignes_produit`) VALUES
(1, 19.6, 5);

-- --------------------------------------------------------

--
-- Structure de la table `arn_etat_paiement`
--

CREATE TABLE IF NOT EXISTS `arn_etat_paiement` (
  `id_etat_paiement` int(11) NOT NULL AUTO_INCREMENT,
  `libelle_etat_paiement` varchar(32) NOT NULL,
  PRIMARY KEY (`id_etat_paiement`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `arn_etat_paiement`
--

INSERT INTO `arn_etat_paiement` (`id_etat_paiement`, `libelle_etat_paiement`) VALUES
(1, 'Réglé'),
(2, 'En attente');

-- --------------------------------------------------------

--
-- Structure de la table `arn_facture`
--

CREATE TABLE IF NOT EXISTS `arn_facture` (
  `id_facture` int(11) NOT NULL AUTO_INCREMENT,
  `num_facture` varchar(32) NOT NULL,
  `id_client_fk` int(11) NOT NULL,
  `id_users_fk` int(11) DEFAULT NULL,
  `tva_facture` decimal(10,0) DEFAULT NULL,
  `prix_total_HT` decimal(10,0) DEFAULT NULL,
  `prix_total_TTC` decimal(10,0) DEFAULT NULL,
  `date_facture` date DEFAULT NULL,
  `id_etat_paiement_fk` int(11) DEFAULT NULL,
  `id_moyen_paiement_fk` int(11) DEFAULT NULL,
  `lieu_facture` varchar(128) DEFAULT NULL,
  `port_facture` decimal(10,0) NOT NULL,
  `dest_nom` varchar(32) DEFAULT NULL,
  `dest_prenom` varchar(32) DEFAULT NULL,
  `dest_adresse` varchar(250) DEFAULT NULL,
  `dest_code_postal` varchar(10) DEFAULT NULL,
  `dest_ville` varchar(250) DEFAULT NULL,
  `dest_mail` varchar(250) DEFAULT NULL,
  `dest_telephone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id_facture`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

--
-- Contenu de la table `arn_facture`
--

INSERT INTO `arn_facture` (`id_facture`, `num_facture`, `id_client_fk`, `id_users_fk`, `tva_facture`, `prix_total_HT`, `prix_total_TTC`, `date_facture`, `id_etat_paiement_fk`, `id_moyen_paiement_fk`, `lieu_facture`, `port_facture`, `dest_nom`, `dest_prenom`, `dest_adresse`, `dest_code_postal`, `dest_ville`, `dest_mail`, `dest_telephone`) VALUES
(1, '000001', 1, 2, '20', '40', '48', '2008-05-03', 1, 2, '', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, '000002', 1, 1, '20', '40', '48', '2008-05-06', 1, 3, '', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, '000003', 2, 2, '20', '200', '239', '2008-05-12', 2, 1, '', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, '000004', 3, 3, '20', '350', '419', '2008-06-01', 2, 2, '', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, '000005', 4, 3, '20', '150', '179', '2008-06-13', 1, 1, '', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(17, 'gfdgdfg', 20, NULL, '20', '200', '239', '2011-07-06', 1, 0, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(18, 'gfdgdfg', 20, NULL, '20', '35', '42', '2011-07-21', 1, 0, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(47, '201107003', 24, NULL, '20', '72', '86', '2011-07-22', 1, 1, 'sasa', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(34, '201105008', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(46, '201107002', 24, NULL, '20', '40', '48', '2011-07-22', 1, 1, 'sasa', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(32, '201105006', 20, NULL, '20', '150', '179', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(31, '201105005', 20, NULL, '20', '35', '42', '2010-04-06', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(45, '201107001', 22, NULL, '20', '7', '8', '2011-07-22', 1, 2, 'libourne', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(35, '20110509', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(36, '201105010', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(37, '201105011', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(38, '201105012', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(39, '201105013', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(40, '201105014', 20, NULL, '20', '150', '179', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(41, '201105015', 20, NULL, '20', '35', '42', '2011-05-02', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(42, '201105016', 20, NULL, '20', '35', '42', '2011-05-03', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(43, '201105017', 20, NULL, '20', '35', '42', '2011-05-03', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(44, '201105018', 20, NULL, '20', '35', '42', '2011-05-03', 1, 1, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(48, '201107004', 0, NULL, '20', '24', '28', '2011-07-22', 1, 1, '', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `arn_moyen_paiement`
--

CREATE TABLE IF NOT EXISTS `arn_moyen_paiement` (
  `id_moyen_paiement` int(11) NOT NULL AUTO_INCREMENT,
  `libelle_moyen_paiement` varchar(32) NOT NULL,
  PRIMARY KEY (`id_moyen_paiement`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `arn_moyen_paiement`
--

INSERT INTO `arn_moyen_paiement` (`id_moyen_paiement`, `libelle_moyen_paiement`) VALUES
(1, 'Chèques'),
(2, 'Espèces'),
(3, 'liquide');

-- --------------------------------------------------------

--
-- Structure de la table `arn_produits`
--

CREATE TABLE IF NOT EXISTS `arn_produits` (
  `id_produit` int(11) NOT NULL AUTO_INCREMENT,
  `denomination_produit` varchar(64) NOT NULL,
  `prix_produit` float NOT NULL,
  `id_tva_fk` int(11) NOT NULL,
  PRIMARY KEY (`id_produit`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Contenu de la table `arn_produits`
--

INSERT INTO `arn_produits` (`id_produit`, `denomination_produit`, `prix_produit`, `id_tva_fk`) VALUES
(1, 'Bouteille 2005 (75cl) - LALANDE DE POMEROL', 6.5, 1),
(2, 'Bouteille 2005 (75cl) - BORDEAUX', 3.5, 1),
(3, 'Magnum 2005 - LALANDE DE POMEROL', 20, 1),
(4, 'Bouteille 2005 (75cl) - LALANDE DE POMEROL - Basto', 15, 2),
(5, 'BIB (10L) - BORDEAUX', 21, 0),
(22, 'test2', 10, 0);

-- --------------------------------------------------------

--
-- Structure de la table `arn_relation_facture_produits`
--

CREATE TABLE IF NOT EXISTS `arn_relation_facture_produits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_facture_fk` int(11) NOT NULL,
  `id_produit_fk` int(11) NOT NULL,
  `prix_unitaire` decimal(10,0) NOT NULL,
  `quantite_produit` int(11) NOT NULL,
  `prix_total` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Contenu de la table `arn_relation_facture_produits`
--

INSERT INTO `arn_relation_facture_produits` (`id`, `id_facture_fk`, `id_produit_fk`, `prix_unitaire`, `quantite_produit`, `prix_total`) VALUES
(1, 0, 0, '4', 10, '35'),
(2, 0, 0, '0', 0, '0'),
(3, 0, 0, '0', 0, '0'),
(4, 0, 0, '0', 0, '0'),
(5, 0, 2, '4', 10, '35'),
(6, 0, 0, '0', 0, '0'),
(7, 0, 0, '0', 0, '0'),
(8, 0, 0, '0', 0, '0'),
(9, 0, 2, '4', 10, '35'),
(10, 0, 0, '0', 0, '0'),
(11, 0, 0, '0', 0, '0'),
(12, 0, 0, '0', 0, '0'),
(13, 0, 2, '4', 10, '35'),
(14, 0, 0, '0', 0, '0'),
(15, 0, 0, '0', 0, '0'),
(16, 0, 0, '0', 0, '0'),
(17, 0, 0, '0', 0, '0'),
(18, 0, 2, '4', 10, '35'),
(19, 0, 0, '0', 0, '0'),
(20, 0, 0, '0', 0, '0'),
(21, 0, 2, '4', 10, '35'),
(22, 0, 4, '15', 10, '150'),
(23, 0, 2, '4', 10, '35'),
(24, 0, 2, '4', 10, '35'),
(25, 0, 2, '4', 10, '35'),
(26, 44, 2, '4', 10, '35'),
(27, 45, 2, '4', 2, '7'),
(28, 46, 3, '20', 2, '40'),
(29, 47, 3, '20', 2, '40'),
(30, 47, 24, '32', 1, '32'),
(31, 48, 1, '7', 2, '13'),
(32, 48, 2, '4', 3, '11');

-- --------------------------------------------------------

--
-- Structure de la table `arn_tva`
--

CREATE TABLE IF NOT EXISTS `arn_tva` (
  `id_tva` int(11) NOT NULL AUTO_INCREMENT,
  `taux_tva` float(10,2) NOT NULL,
  PRIMARY KEY (`id_tva`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `arn_tva`
--

INSERT INTO `arn_tva` (`id_tva`, `taux_tva`) VALUES
(1, 19.60),
(2, 5.50);

-- --------------------------------------------------------

--
-- Structure de la table `arn_users`
--

CREATE TABLE IF NOT EXISTS `arn_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `arn_users`
--

INSERT INTO `arn_users` (`id`, `login`, `password`) VALUES
(1, 'antho', 'pass'),
(2, 'jeje', 'jeje'),
(3, 'christian', 'christian'),
(4, 'dede', 'dede'),
(5, 'marie', 'marie');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
