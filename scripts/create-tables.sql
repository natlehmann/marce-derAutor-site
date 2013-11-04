create table Usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null,
	password varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON Usuario(nombre);

create table Rol(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_rol_nombre ON Rol(nombre);

create table Usuario_Rol(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	usuario_id BIGINT NOT NULL,
	rol_id BIGINT NOT NULL,
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (rol_id) REFERENCES Rol(id)
)  ENGINE=InnoDB;

create table Autor(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

create table Pais(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null,
	codigo varchar(10)
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_Pais_nombre ON Pais(nombre);

create table UnidadesVendidasPorAutor(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	cantidadUnidades BIGINT default 0,
	anio int not null,
	trimestre int not null,
	pais_id BIGINT NOT NULL,
	autor_id BIGINT NOT NULL,
	FOREIGN KEY (pais_id) REFERENCES Pais(id),
	FOREIGN KEY (autor_id) REFERENCES Autor(id)
)  ENGINE=InnoDB;

