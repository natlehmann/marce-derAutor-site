create table Usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null,
	password varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON Usuario(nombre);

create table Autor(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	idExterno BIGINT NOT NULL,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_autor_idExterno ON Autor(idExterno);
