create database demo;
use demo;

DROP TABLE IF EXISTS `demorole`;
CREATE TABLE demorole (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

LOCK TABLES `demorole` WRITE;
INSERT INTO demorole VALUES (1,'ROLE_USER');
UNLOCK TABLES;
select * from demorole;


DROP TABLE IF EXISTS `demouser`;
CREATE TABLE demouser (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
); 


DROP TABLE IF EXISTS demo_user_role;
CREATE TABLE demo_user_role (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  PRIMARY KEY (user_id,role_id),
  KEY `fk_user_role_roleid_idx` (`role_id`),
  CONSTRAINT `fk_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `demorole` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `demouser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
); 
INSERT INTO demouser VALUES (1,'demo','password');
Insert into demo_user_role VALUES(1,1);

select * from demouser u join demorole r join demo_user_role c on c.user_id = u.id and c.role_id = r.id;