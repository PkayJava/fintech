DROP TABLE IF EXISTS `tbl_audit`;
CREATE TABLE `tbl_audit` (
  `id`         VARCHAR(100) NOT NULL,
  `log_date`   DATETIME     NOT NULL,
  `log_event`  VARCHAR(20)  NOT NULL,
  `log_table`  VARCHAR(100) NOT NULL,
  `log_script` TEXT,
  PRIMARY KEY (`id`),
  KEY `tbl_audit_001` (`log_date`),
  KEY `tbl_audit_002` (`log_event`),
  KEY `tbl_audit_003` (`log_table`)
);

DROP TABLE IF EXISTS `tbl_audit_value`;
CREATE TABLE `tbl_audit_value` (
  `id`           VARCHAR(100) NOT NULL,
  `audit_id`     VARCHAR(100) NULL,
  `field_name`   VARCHAR(255) NULL,
  `before_value` TEXT,
  `after_value`  TEXT,
  PRIMARY KEY (`id`),
  KEY `tbl_audit_value_001` (`audit_id`),
  KEY `tbl_audit_value_002` (`field_name`)
);

DROP TABLE IF EXISTS `tbl_proxy`;
CREATE TABLE `tbl_proxy` (
  `id`                    VARCHAR(100)  NOT NULL,
  `path`                  VARCHAR(1000) NULL,
  `method`                VARCHAR(30)   NULL,
  `accept`                VARCHAR(255)  NULL,
  `request_content_type`  VARCHAR(100)  NULL,
  `request_body`          TEXT          NULL,
  `response_content_type` VARCHAR(100)  NULL,
  `response_body`         TEXT          NULL,
  `response_status`       INT(3)        NULL,
  `response_status_text`  VARCHAR(200)  NULL,
  `created_datetime`      DATETIME      NOT NULL,
  PRIMARY KEY (`id`),
  KEY `001` (`created_datetime`)
);

DROP TABLE IF EXISTS `tbl_proxy_request_header`;
CREATE TABLE `tbl_proxy_request_header` (
  `id`       VARCHAR(100) NOT NULL,
  `proxy_id` VARCHAR(100) NOT NULL,
  `h_name`   VARCHAR(255) NULL,
  `h_value`  VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  KEY `001` (`proxy_id`)
);

DROP TABLE IF EXISTS `tbl_proxy_response_header`;
CREATE TABLE `tbl_proxy_response_header` (
  `id`       VARCHAR(100) NOT NULL,
  `proxy_id` VARCHAR(100) NOT NULL,
  `h_name`   VARCHAR(255) NULL,
  `h_value`  VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  KEY `001` (`proxy_id`)
);
