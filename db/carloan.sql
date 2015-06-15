-- --------------------------------------------------------
-- Host:                         localhost
-- Versione server:              5.5.27 - MySQL Community Server (GPL)
-- S.O. server:                  Win32
-- HeidiSQL Versione:            9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dump della struttura del database carloan
DROP DATABASE IF EXISTS `carloan`;
CREATE DATABASE IF NOT EXISTS `carloan` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `carloan`;


-- Dump della struttura di tabella carloan.auto
DROP TABLE IF EXISTS `auto`;
CREATE TABLE IF NOT EXISTS `auto` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `id_modello` int(50) NOT NULL,
  `targa` varchar(50) NOT NULL,
  `id_filiale` int(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `chilometraggio` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`targa`),
  KEY `FK__filiale` (`id_filiale`),
  KEY `FK_auto_modello` (`id_modello`),
  CONSTRAINT `FK_auto_modello` FOREIGN KEY (`id_modello`) REFERENCES `modelli` (`id`),
  CONSTRAINT `FK__filiale` FOREIGN KEY (`id_filiale`) REFERENCES `filiali` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.auto: ~46 rows (circa)
DELETE FROM `auto`;
/*!40000 ALTER TABLE `auto` DISABLE KEYS */;
INSERT INTO `auto` (`id`, `id_modello`, `targa`, `id_filiale`, `status`, `chilometraggio`) VALUES
	(44, 1, 'targa1', 12, 'DISPONIBILE', 0),
	(45, 11, 'targa2', 9, 'MANUTENZIONE', 0),
	(46, 4, 'targa3', 8, 'DISPONIBILE', 0),
	(47, 7, 'targa4', 11, 'DISPONIBILE', 0),
	(48, 16, 'targa5', 13, 'DISPONIBILE', 0),
	(49, 16, 'targa6', 8, 'DISPONIBILE', 0),
	(50, 13, 'targa7', 8, 'DISPONIBILE', 0),
	(51, 10, 'targa8', 9, 'DISPONIBILE', 0),
	(52, 13, 'targa9', 8, 'DISPONIBILE', 0),
	(53, 14, 'targa10', 12, 'DISPONIBILE', 0),
	(54, 12, 'targa11', 12, 'DISPONIBILE', 0),
	(55, 10, 'targa12', 12, 'DISPONIBILE', 0),
	(56, 15, 'targa13', 12, 'MANUTENZIONE', 0),
	(57, 16, 'targa14', 12, 'DISPONIBILE', 0),
	(58, 6, 'targa15', 11, 'DISPONIBILE', 0),
	(59, 8, 'targa16', 11, 'MANUTENZIONE', 0),
	(60, 5, 'targa17', 11, 'DISPONIBILE', 0),
	(61, 3, 'targa18', 11, 'DISPONIBILE', 0),
	(62, 6, 'targa19', 11, 'DISPONIBILE', 0),
	(63, 7, 'targa20', 9, 'DISPONIBILE', 0),
	(64, 5, 'targa21', 9, 'DISPONIBILE', 0),
	(65, 1, 'targa22', 9, 'DISPONIBILE', 0),
	(66, 11, 'targa23', 9, 'DISPONIBILE', 0),
	(67, 13, 'targa24', 8, 'DISPONIBILE', 0),
	(68, 15, 'targa25', 8, 'DISPONIBILE', 0),
	(69, 16, 'targa26', 8, 'DISPONIBILE', 0),
	(70, 5, 'targa27', 8, 'MANUTENZIONE', 0),
	(71, 6, 'targa28', 8, 'DISPONIBILE', 0),
	(72, 9, 'targa29', 8, 'DISPONIBILE', 0),
	(73, 11, 'targa30', 8, 'DISPONIBILE', 0),
	(74, 1, 'targa31', 13, 'DISPONIBILE', 0),
	(75, 1, 'targa32', 13, 'DISPONIBILE', 0),
	(76, 14, 'targa33', 13, 'DISPONIBILE', 0),
	(77, 6, 'targa34', 13, 'DISPONIBILE', 0),
	(78, 8, 'targa35', 13, 'MANUTENZIONE', 0),
	(79, 12, 'targa36', 13, 'DISPONIBILE', 0),
	(80, 14, 'targa37', 9, 'DISPONIBILE', 0),
	(81, 4, 'targa38', 9, 'DISPONIBILE', 0),
	(82, 4, 'targa39', 9, 'DISPONIBILE', 0),
	(83, 8, 'targa40', 11, 'DISPONIBILE', 0),
	(84, 3, 'targa41', 11, 'DISPONIBILE', 0),
	(85, 9, 'targa42', 12, 'DISPONIBILE', 0),
	(86, 4, 'targa43', 12, 'DISPONIBILE', 0),
	(87, 6, 'targa44', 12, 'DISPONIBILE', 0),
	(88, 8, 'targa45', 13, 'DISPONIBILE', 0),
	(89, 2, 'targa46', 13, 'MANUTENZIONE', 0);
/*!40000 ALTER TABLE `auto` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.clienti
DROP TABLE IF EXISTS `clienti`;
CREATE TABLE IF NOT EXISTS `clienti` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `residenza` varchar(50) DEFAULT NULL,
  `data_di_nascita` date DEFAULT NULL,
  `cod_fiscale` varchar(50) DEFAULT NULL,
  `cod_patente` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.clienti: ~8 rows (circa)
DELETE FROM `clienti`;
/*!40000 ALTER TABLE `clienti` DISABLE KEYS */;
INSERT INTO `clienti` (`id`, `nome`, `cognome`, `mail`, `residenza`, `data_di_nascita`, `cod_fiscale`, `cod_patente`) VALUES
	(1, 'cliente1', 'cliente1', NULL, NULL, NULL, NULL, NULL),
	(2, 'cliente2', 'cliente2', NULL, NULL, NULL, NULL, NULL),
	(3, 'cliente3', 'cliente3', NULL, NULL, NULL, NULL, NULL),
	(4, 'cliente4', 'cliente4', NULL, NULL, NULL, NULL, NULL),
	(5, 'cliente5', 'cliente5', NULL, NULL, NULL, NULL, NULL),
	(6, 'cliente6', 'cliente6', NULL, NULL, NULL, NULL, NULL),
	(7, 'cliente7', 'cliente7', NULL, NULL, NULL, NULL, NULL),
	(8, 'cliente8', 'cliente8', NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `clienti` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.contratti
DROP TABLE IF EXISTS `contratti`;
CREATE TABLE IF NOT EXISTS `contratti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_km` varchar(50) NOT NULL,
  `tariffa` varchar(50) NOT NULL,
  `data_inizio` date NOT NULL,
  `data_limite` date NOT NULL,
  `data_rientro` date DEFAULT NULL,
  `acconto` double(5,2) NOT NULL,
  `stato` varchar(50) NOT NULL,
  `id_cliente` int(50) NOT NULL,
  `id_dipendente` int(50) NOT NULL,
  `id_auto` int(50) NOT NULL,
  `tot_prezzo` double(5,2) DEFAULT NULL,
  `filiale_di_partenza` int(50) NOT NULL,
  `filiale_di_arrivo` int(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_contratti_clienti` (`id_cliente`),
  KEY `FK_contratti_dipendente_di_filiale` (`id_dipendente`),
  KEY `FK_contratti_auto` (`id_auto`),
  KEY `FK_contratti_filiali` (`filiale_di_partenza`),
  KEY `FK_contratti_filiali_2` (`filiale_di_arrivo`),
  CONSTRAINT `FK_contratti_auto` FOREIGN KEY (`id_auto`) REFERENCES `auto` (`id`),
  CONSTRAINT `FK_contratti_clienti` FOREIGN KEY (`id_cliente`) REFERENCES `clienti` (`id`),
  CONSTRAINT `FK_contratti_dipendente_di_filiale` FOREIGN KEY (`id_dipendente`) REFERENCES `dipendente_di_filiale` (`id`),
  CONSTRAINT `FK_contratti_filiali` FOREIGN KEY (`filiale_di_partenza`) REFERENCES `filiali` (`id`),
  CONSTRAINT `FK_contratti_filiali_2` FOREIGN KEY (`filiale_di_arrivo`) REFERENCES `filiali` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.contratti: ~1 rows (circa)
DELETE FROM `contratti`;
/*!40000 ALTER TABLE `contratti` DISABLE KEYS */;
INSERT INTO `contratti` (`id`, `tipo_km`, `tariffa`, `data_inizio`, `data_limite`, `data_rientro`, `acconto`, `stato`, `id_cliente`, `id_dipendente`, `id_auto`, `tot_prezzo`, `filiale_di_partenza`, `filiale_di_arrivo`) VALUES
	(4, 'LIMITATO', 'GIORNALIERA', '2015-06-16', '2015-06-21', NULL, 10.00, 'APERTO', 6, 14, 44, NULL, 9, 9);
/*!40000 ALTER TABLE `contratti` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.dipendente_di_filiale
DROP TABLE IF EXISTS `dipendente_di_filiale`;
CREATE TABLE IF NOT EXISTS `dipendente_di_filiale` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `id_filiale` int(50) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `residenza` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`username`),
  KEY `FK_dipendente_di_filiale_filiali` (`id_filiale`),
  CONSTRAINT `FK_dipendente_di_filiale_filiali` FOREIGN KEY (`id_filiale`) REFERENCES `filiali` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.dipendente_di_filiale: ~20 rows (circa)
DELETE FROM `dipendente_di_filiale`;
/*!40000 ALTER TABLE `dipendente_di_filiale` DISABLE KEYS */;
INSERT INTO `dipendente_di_filiale` (`id`, `username`, `email`, `password`, `id_filiale`, `nome`, `cognome`, `telefono`, `residenza`) VALUES
	(2, 'dipendente1', 'mail1', 'password', 8, NULL, NULL, NULL, NULL),
	(3, 'dipendente2', 'mail2', 'password', 8, NULL, NULL, NULL, NULL),
	(4, 'dipendente3', 'mail3', 'password', 8, NULL, NULL, NULL, NULL),
	(5, 'dipendente4', 'mail4', 'password', 8, NULL, NULL, NULL, NULL),
	(6, 'dipendente5', 'mail5', 'password', 9, NULL, NULL, NULL, NULL),
	(7, 'dipendente6', 'mail6', 'password', 9, NULL, NULL, NULL, NULL),
	(8, 'dipendente7', 'mail7', 'password', 9, NULL, NULL, NULL, NULL),
	(9, 'dipendente8', 'mail8', 'password', 9, NULL, NULL, NULL, NULL),
	(10, 'dipendente9', 'mail9', 'password', 11, NULL, NULL, NULL, NULL),
	(11, 'dipendente10', 'mail10', 'password', 11, NULL, NULL, NULL, NULL),
	(12, 'dipendente11', 'mail11', 'password', 11, NULL, NULL, NULL, NULL),
	(13, 'dipendente12', 'mail12', 'password', 11, NULL, NULL, NULL, NULL),
	(14, 'dipendente13', 'mail13', 'password', 12, NULL, NULL, NULL, NULL),
	(15, 'dipendente14', 'mail14', 'password', 12, NULL, NULL, NULL, NULL),
	(16, 'dipendente15', 'mail15', 'password', 12, NULL, NULL, NULL, NULL),
	(17, 'dipendente16', 'mail16', 'password', 12, NULL, NULL, NULL, NULL),
	(18, 'dipendente17', 'mail17', 'password', 13, NULL, NULL, NULL, NULL),
	(19, 'dipendente18', 'mail18', 'password', 13, NULL, NULL, NULL, NULL),
	(20, 'dipendente19', 'mail19', 'password', 13, NULL, NULL, NULL, NULL),
	(21, 'dipendente20', 'mail20', 'password', 13, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `dipendente_di_filiale` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.fasce
DROP TABLE IF EXISTS `fasce`;
CREATE TABLE IF NOT EXISTS `fasce` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `id_prezzi` int(50) NOT NULL,
  PRIMARY KEY (`id`,`nome`),
  KEY `FK_fascia_prezzi` (`id_prezzi`),
  CONSTRAINT `FK_fascia_prezzi` FOREIGN KEY (`id_prezzi`) REFERENCES `prezzi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.fasce: ~4 rows (circa)
DELETE FROM `fasce`;
/*!40000 ALTER TABLE `fasce` DISABLE KEYS */;
INSERT INTO `fasce` (`id`, `nome`, `id_prezzi`) VALUES
	(4, 'D', 1),
	(3, 'C', 2),
	(2, 'B', 3),
	(1, 'A', 4);
/*!40000 ALTER TABLE `fasce` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.filiali
DROP TABLE IF EXISTS `filiali`;
CREATE TABLE IF NOT EXISTS `filiali` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `luogo` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `id_manager` int(50) NOT NULL,
  PRIMARY KEY (`id`,`nome`),
  KEY `FK_filiali_manager_di_filiale` (`id_manager`),
  CONSTRAINT `FK_filiali_manager_di_filiale` FOREIGN KEY (`id_manager`) REFERENCES `manager_di_filiale` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.filiali: ~5 rows (circa)
DELETE FROM `filiali`;
/*!40000 ALTER TABLE `filiali` DISABLE KEYS */;
INSERT INTO `filiali` (`id`, `nome`, `luogo`, `telefono`, `id_manager`) VALUES
	(8, 'filiale1', NULL, NULL, 5),
	(9, 'filiale2', NULL, NULL, 3),
	(11, 'filiale3', NULL, NULL, 4),
	(12, 'filiale4', NULL, NULL, 6),
	(13, 'filiale5', NULL, NULL, 7);
/*!40000 ALTER TABLE `filiali` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.manager_di_filiale
DROP TABLE IF EXISTS `manager_di_filiale`;
CREATE TABLE IF NOT EXISTS `manager_di_filiale` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `residenza` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.manager_di_filiale: ~5 rows (circa)
DELETE FROM `manager_di_filiale`;
/*!40000 ALTER TABLE `manager_di_filiale` DISABLE KEYS */;
INSERT INTO `manager_di_filiale` (`id`, `username`, `email`, `password`, `nome`, `cognome`, `telefono`, `residenza`) VALUES
	(3, 'utente2', 'mail2', 'password', NULL, NULL, NULL, NULL),
	(4, 'utente3', 'mail3', 'password', NULL, NULL, NULL, NULL),
	(5, 'utente1', 'mail2', 'password', NULL, NULL, NULL, NULL),
	(6, 'utente4', 'mail4', 'password', NULL, NULL, NULL, NULL),
	(7, 'utente5', 'mail5', 'password', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `manager_di_filiale` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.manager_di_sistema
DROP TABLE IF EXISTS `manager_di_sistema`;
CREATE TABLE IF NOT EXISTS `manager_di_sistema` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.manager_di_sistema: ~1 rows (circa)
DELETE FROM `manager_di_sistema`;
/*!40000 ALTER TABLE `manager_di_sistema` DISABLE KEYS */;
INSERT INTO `manager_di_sistema` (`id`, `username`, `password`) VALUES
	(1, 'admin', 'admin');
/*!40000 ALTER TABLE `manager_di_sistema` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.modelli
DROP TABLE IF EXISTS `modelli`;
CREATE TABLE IF NOT EXISTS `modelli` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `id_fascia` int(50) NOT NULL,
  PRIMARY KEY (`id`,`nome`),
  KEY `FK_modello_fascia` (`id_fascia`),
  CONSTRAINT `FK_modello_fascia` FOREIGN KEY (`id_fascia`) REFERENCES `fasce` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.modelli: ~16 rows (circa)
DELETE FROM `modelli`;
/*!40000 ALTER TABLE `modelli` DISABLE KEYS */;
INSERT INTO `modelli` (`id`, `nome`, `id_fascia`) VALUES
	(1, 'modello1', 1),
	(2, 'modello2', 1),
	(3, 'modello3', 1),
	(4, 'modello4', 1),
	(5, 'modello5', 2),
	(6, 'modello6', 2),
	(7, 'modello7', 2),
	(8, 'modello8', 2),
	(9, 'modello9', 3),
	(10, 'modello10', 3),
	(11, 'modello11', 3),
	(12, 'modello12', 3),
	(13, 'modello13', 4),
	(14, 'modello14', 4),
	(15, 'modello15', 4),
	(16, 'modello16', 4);
/*!40000 ALTER TABLE `modelli` ENABLE KEYS */;


-- Dump della struttura di tabella carloan.prezzi
DROP TABLE IF EXISTS `prezzi`;
CREATE TABLE IF NOT EXISTS `prezzi` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `tariffa_base_g` double(5,2) NOT NULL,
  `tariffa_base_s` double(5,2) NOT NULL,
  `costo_chilometrico` double(5,2) NOT NULL,
  `penale_chilometri` double(5,2) NOT NULL,
  `tariffa_illim_g` double(5,2) NOT NULL,
  `tariffa_illim_s` double(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella carloan.prezzi: ~4 rows (circa)
DELETE FROM `prezzi`;
/*!40000 ALTER TABLE `prezzi` DISABLE KEYS */;
INSERT INTO `prezzi` (`id`, `tariffa_base_g`, `tariffa_base_s`, `costo_chilometrico`, `penale_chilometri`, `tariffa_illim_g`, `tariffa_illim_s`) VALUES
	(1, 10.00, 35.00, 0.80, 1.00, 5.00, 17.00),
	(2, 15.00, 45.00, 1.30, 1.50, 8.00, 25.00),
	(3, 20.00, 55.00, 1.80, 2.00, 12.00, 30.00),
	(4, 30.00, 65.00, 2.50, 2.50, 15.00, 36.00);
/*!40000 ALTER TABLE `prezzi` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
