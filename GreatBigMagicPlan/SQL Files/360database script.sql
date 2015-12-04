Create database if not exists `360Project`;

use `360Project`;

DROP TABLE IF EXISTS `data`;

CREATE TABLE `data` (
  `Title` varChar (50) NOT NULL UNIQUE,
  `Type` varchar(20) NOT NULL,
  `Description` varchar(100) NOT NULL,
  `Content` varchar(10000) NOT NULL,
  PRIMARY KEY (`Title`)
);

LOCK TABLES `data` WRITE;

insert  into `data`(`Title`,`Type`,`Description`,`Content`) values ('The sample data', 'Sample', 'A simple sample of a text block', 'This is the sample text. Woot woot, its working! blah blah blah, more stuff and yada yada yada.');

UNLOCK TABLES;