create table usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255),
	password varchar(255),
	version bigint
)  TYPE=innodb;