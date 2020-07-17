-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 17, 2020 at 02:36 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bd`
--

-- --------------------------------------------------------

--
-- Table structure for table `appartement`
--

CREATE TABLE IF NOT EXISTS `appartement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `type` varchar(50) NOT NULL,
  `lat` varchar(200) NOT NULL,
  `lng` varchar(100) NOT NULL,
  `adress` varchar(200) CHARACTER SET utf8 NOT NULL,
  `prix` varchar(100) NOT NULL,
  `image` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `appartement`
--

INSERT INTO `appartement` (`id`, `name`, `type`, `lat`, `lng`, `adress`, `prix`, `image`) VALUES
(6, 'namekjjfdssh', 'allocation', '36.7146746', '3.194601', 'donosomrthinf, hfj', '63737', 'uploads/namekjjfdssh.jpg'),
(7, 'EmRIuxhJgB', 'allocation', '36.7146758', '3.1946027', 'gfjsd', '5637', 'uploads/EmRIuxhJgB.jpg'),
(8, 'KavDfSQ', 'allocation', '36.7146758', '3.1946027', 'gfjsd', '5637', 'uploads/KavDfSQ.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `etablissements`
--

CREATE TABLE IF NOT EXISTS `etablissements` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `code_etab` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `etablissement` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=623 ;

--
-- Dumping data for table `etablissements`
--

INSERT INTO `etablissements` (`id`, `code_etab`, `etablissement`) VALUES
(270, 'OU-EPH-14339', 'EPH Azazga (Azazga)'),
(271, 'OU-EPH-15274', 'EPH Azzaba (Azzaba)'),
(272, 'OU-EPH-14439', 'EPH Azeffoun (Azeffoun)'),
(273, 'OU-EPH-17003', 'EPH Arzew ( El Mohgoun) (Arzew)'),
(274, 'OU-EPH-12485', 'EPH Arris 2 (Arris)'),
(275, 'OU-EPH-12484', 'EPH Arris 1 (Arris)'),
(276, 'OU-EPH-12807', 'EPH Aokas (Aokas)'),
(277, 'OU-EPH-12725', 'EPH Amizour (Amizour)'),
(278, 'OU-EPH-12822', 'EPH Akbou (Akbou)'),
(279, 'OU-EPH-17021', 'EPH Ain Turk (Akid Othmane) (Ain Turk)'),
(280, 'OU-EPH-12633', 'EPH Ain Touta (Ain Touta)'),
(281, 'OU-EPH-18720', 'EPH Ain Temouchent (Ain Timouchent)'),
(282, 'OU-EPH-16233', 'EPH Ain Tedles (Ain Tadles)'),
(283, 'OU-EPH-2386', 'EPH Ain Taya'),
(284, 'OU-EPH-18670', 'EPH Ain Sefra (Ain Sefra)'),
(285, 'OU-EPH-13587', 'EPH Ain Salah (In Salah)'),
(286, 'OU-EPH-14702', 'EPH Ain Oussera (Ain Oussera)'),
(287, 'OU-EPH-15019', 'EPH Ain Oulmane (Ain Oulmene)'),
(288, 'OU-EPH-15813', 'EPH Ain Larbi (Ain Larbi)'),
(289, 'OU-EPH-12373', 'EPH Hamouda Amor - Ain Fakroun (Oum El Bouaghi)'),
(290, 'OU-EPH-16567', 'EPH Ain El Melh (Ain El Melh)'),
(291, 'OU-EPH-14882', 'EPH Ain El Kebira (Ain El Kebira)'),
(292, 'OU-EPH-14260', 'EPH Ain El Hammam (Ain El Hammam)'),
(293, 'OU-EPH-18498', 'EPH Ain Defla (Ain Defla)'),
(294, 'OU-EPH-15994', 'EPH Ain Boucif Aissa Kirouan (Ain Boucif)');

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `image_path` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
