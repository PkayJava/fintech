CREATE TABLE `tbl_audit` (
  `id` varchar(100) NOT NULL,
  `log_date` datetime DEFAULT NULL,
  `log_event` varchar(20) DEFAULT NULL,
  `log_table` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tbl_audit_001` (`log_date`),
  KEY `tbl_audit_002` (`log_event`),
  KEY `tbl_audit_003` (`log_table`)
);

CREATE TABLE `tbl_audit_value` (
  `id` varchar(100) NOT NULL,
  `audit_id` varchar(100) DEFAULT NULL,
  `field_name` varchar(255) DEFAULT NULL,
  `before_value` text,
  `after_value` text,
  PRIMARY KEY (`id`),
  KEY `tbl_audit_value_001` (`audit_id`),
  KEY `tbl_audit_value_002` (`field_name`)
);