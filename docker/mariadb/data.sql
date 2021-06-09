SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE IF NOT EXISTS `batch_executor` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `batch_executor`;

CREATE TABLE `parameter` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `value` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `task` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `jms_message_id` varchar(255) NOT NULL,
   `app_instance_id` varchar(255) NOT NULL,
   `job_name` varchar(255) NOT NULL,
   `status` varchar(25) NOT NULL,
   `message_created` datetime NOT NULL,
   `result` varchar(255),
   `task_ref` bigint(20) NOT NULL,
   `task_type` varchar(255) NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE `task_parameter` (
   `task_id` bigint(20) NOT NULL,
   `parameter_id` bigint(20) NOT NULL,
   PRIMARY KEY (`task_id`, `parameter_id`)
) ENGINE=InnoDB;
    
ALTER TABLE `task` 
   ADD CONSTRAINT `UK1_task` unique (`task_ref`);

ALTER TABLE `task_parameter` 
   ADD CONSTRAINT `FK1_task_parameter` 
   FOREIGN KEY (`parameter_id`) 
   REFERENCES `parameter`(`id`);

ALTER TABLE `task_parameter` 
   ADD CONSTRAINT `FK2_task_parameter` 
   FOREIGN KEY (`task_id`) 
   REFERENCES `task`(`id`);


CREATE DATABASE IF NOT EXISTS `batch_api` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `batch_api`;

CREATE TABLE `parameter` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `value` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `result` varchar(255) DEFAULT NULL,
  `status` varchar(25) NOT NULL,
  `task_type` int(11) NOT NULL,
   PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE `task_parameter` (
  `task_id` bigint(20) NOT NULL,
  `parameter_id` bigint(20) NOT NULL,
   PRIMARY KEY (task_id, parameter_id),
   KEY `PRK_task_parameter` (`parameter_id`)
) ENGINE=InnoDB;

CREATE TABLE `task_task` (
  `task_id` bigint(20) NOT NULL,
  `dependency_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`dependency_id`),
  KEY `PRK_task_task` (`dependency_id`)
) ENGINE=InnoDB;

ALTER TABLE `task_parameter`
  ADD CONSTRAINT `FK1_task_parameter` FOREIGN KEY (`parameter_id`) REFERENCES `parameter` (`id`),
  ADD CONSTRAINT `FK2_task_parameter` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

ALTER TABLE `task_task`
  ADD CONSTRAINT `FK1_task_task` FOREIGN KEY (`dependency_id`) REFERENCES `task` (`id`),
  ADD CONSTRAINT `FK2_task_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

COMMIT;
