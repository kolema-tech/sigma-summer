CREATE TABLE `message_publisher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `business_type` VARCHAR(20) NULL,
  `payload` VARCHAR(2000) NULL,
  `message_status` VARCHAR(10) NULL,
  `lock_version` INT NULL,
  `create_time` date DEFAULT NULL,
  `create_user` varchar(45) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `update_user` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
