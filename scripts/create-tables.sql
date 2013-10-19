create table usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null,
	password varchar(255) not null,
	version bigint
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON usuario(nombre);

create table autor(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null,
	version bigint
)  ENGINE=InnoDB;
