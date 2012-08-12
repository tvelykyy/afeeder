-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.63-0ubuntu0.11.10.1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema afeeder_test
--

CREATE DATABASE IF NOT EXISTS afeeder_test;
USE afeeder_test;

--
-- Definition of table `afeeder_test`.`activity`
--

DROP TABLE IF EXISTS `afeeder_test`.`activity`;
CREATE TABLE  `afeeder_test`.`activity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `text` varchar(200) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `group_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `afeeder_test`.`activity`
--
INSERT INTO `afeeder_test`.`activity` VALUES   (1,'UERO 2012 in Poland-Ukraine starts',1,1);
INSERT INTO `afeeder_test`.`activity` VALUES   (2,'Ukraine - Sweden',1,1);
INSERT INTO `afeeder_test`.`activity` VALUES   (3,'France - Ukraine',1,3);

--
-- Definition of table `afeeder_test`.`group`
--

DROP TABLE IF EXISTS `afeeder_test`.`group`;
CREATE TABLE  `afeeder_test`.`group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT  CHARSET=latin1;

--
-- Dumping data for table `afeeder_test`.`group`
--
INSERT INTO `afeeder_test`.`group` VALUES   (1,'Sport');
INSERT INTO `afeeder_test`.`group` VALUES   (2,'Politics');
INSERT INTO `afeeder_test`.`group` VALUES   (3,'Healtcare');


--
-- Definition of table `afeeder_test`.`role`
--

DROP TABLE IF EXISTS `afeeder_test`.`role`;
CREATE TABLE  `afeeder_test`.`role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `afeeder_test`.`role`
--
INSERT INTO `afeeder_test`.`role` VALUES   (1,'ROLE_ADMIN');
INSERT INTO `afeeder_test`.`role` VALUES   (2,'ROLE_USER');

--
-- Definition of table `afeeder_test`.`user`
--

DROP TABLE IF EXISTS `afeeder_test`.`user`;
CREATE TABLE  `afeeder_test`.`user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(8) NOT NULL,
  `password` char(32) NOT NULL,
  `name` varchar(20) NOT NULL,
  `token` char(32) DEFAULT NULL,
  `last_token_usage` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `afeeder_test`.`user`
--
INSERT INTO `afeeder_test`.`user` VALUES   (1,'admin','5f4dcc3b5aa765d61d8327deb882cf99','Admin', null, null);
INSERT INTO `afeeder_test`.`user` VALUES   (2,'user1','5f4dcc3b5aa765d61d8327deb882cf99','User1', null, null);

--
-- Definition of table `afeeder_test`.`user_role`
--

DROP TABLE IF EXISTS `afeeder_test`.`user_role`;
CREATE TABLE  `afeeder_test`.`user_role` (
  `user_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `afeeder_test`.`user_role`
--
INSERT INTO `afeeder_test`.`user_role` VALUES   (1,1);
INSERT INTO `afeeder_test`.`user_role` VALUES   (1,2);
INSERT INTO `afeeder_test`.`user_role` VALUES   (2,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
