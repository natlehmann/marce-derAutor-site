create table Usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null,
	password varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON Usuario(nombre);

create table Autor(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

