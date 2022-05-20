CREATE TABLE `broker_comics` (
  `comicbook_id` varchar(256) NOT NULL,
  `file` varchar(1024) DEFAULT NULL,
  `title` varchar(256) DEFAULT NULL,
  `series` varchar(256) DEFAULT NULL,
  `number` varchar(16) DEFAULT NULL,
  `volume` varchar(16) DEFAULT NULL,
  `summary` longtext,
  `notes` longtext,
  `year` varchar(16) DEFAULT NULL,
  `month` varchar(16) DEFAULT NULL,
  `publisher` varchar(128) DEFAULT NULL,
  `web` varchar(2048) DEFAULT NULL,
  `pagecount` varchar(16) DEFAULT NULL,
  `characters` longtext,
  `added` datetime DEFAULT NULL,
  `tags` varchar(256) DEFAULT NULL,
  `seriescomplete` varchar(8) DEFAULT NULL,
  `custom_values_store` longtext,
  `comicvine_issue` varchar(32) DEFAULT NULL,
  `comicvine_volume` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`comicbook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;