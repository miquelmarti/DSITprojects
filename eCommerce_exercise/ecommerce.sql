/*
SQLyog Community Edition- MySQL GUI v6.16
MySQL - 5.0.51a-community-nt : Database - ecommerce
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `ecommerce`;

USE `ecommerce`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL default '-1',
  `name` varchar(45) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `category` */

insert  into `category`(`id`,`name`) values (0,'bakery'),(1,'dairy'),(2,'fruitVeg'),(3,'meats');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(45) default NULL,
  `price` decimal(5,2) default NULL,
  `description` varchar(100) default NULL,
  `lastupdate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `categoryid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`id`,`name`,`price`,`description`,`lastupdate`,`categoryid`) values (0,'milk','1.70','semi skimmed (1L)','2010-07-12 12:41:24',1),(1,'cheese','2.39','mild cheddar (330g) ','2010-07-12 12:41:33',1),(2,'butter','1.09','unsalted (250g) ','2010-07-12 12:41:37',1),(3,'eggs','1.76','medium-sized (6 eggs) ','2010-07-12 12:41:43',1),(4,'meatPatties','2.29','rolled in fresh herbs<br>2 patties (250g)','2010-07-12 17:44:16',3),(5,'parmaHam','3.49','matured, organic (70g)','2010-07-12 17:44:48',3),(6,'chicken','2.59','free range (250g)','2010-07-12 17:45:19',3),(7,'sausages','3.55','reduced fat, pork<br>3 sausages (350g)','2010-07-12 17:46:50',3),(8,'loaf','1.89','600g','2010-07-12 17:48:12',0),(9,'bagel','1.19','4 bagels','2010-07-12 17:48:49',0),(10,'bun','1.15','4 buns','2010-07-12 17:49:47',0),(11,'cookie','2.39','contain peanuts<br>(3 cookies)','2010-07-12 17:50:12',0),(12,'corn','1.59','2 pieces','2010-07-12 17:51:10',2),(13,'berries','2.49','150g','2010-07-12 17:51:36',2),(14,'broccoli','1.29','500g','2010-07-12 17:52:21',2),(15,'watermelon','1.49','250g','2010-07-12 17:52:18',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
