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

create table Cancion(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

create table Fuente(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

create table Pais(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255) not null,
	codigo varchar(10)
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_Pais_nombre ON Pais(nombre);

create table DatosCancion(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	companyId BIGINT,
	pais_id BIGINT,
	trimestre int not null,
	anio int not null,
	formatId int,
	autor_id BIGINT NULL,
	cancion_id BIGINT NULL,
	fuente_id BIGINT NULL,
	cantidadUnidades BIGINT default 0,
	montoPercibido DECIMAL(10,2) default 0,
	FOREIGN KEY (pais_id) REFERENCES Pais(id),
	FOREIGN KEY (autor_id) REFERENCES Autor(id),
	FOREIGN KEY (cancion_id) REFERENCES Cancion(id),
	FOREIGN KEY (fuente_id) REFERENCES Fuente(id)
)  ENGINE=InnoDB;


create table HistorialImportacion(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombreArchivo varchar(255),
	inicio datetime,
	fin datetime,
	duracion BIGINT,	
	tamanioArchivo BIGINT,
	duracionEstimada1024bytes BIGINT,
	duracionEstimada BIGINT
)  ENGINE=InnoDB;

create table FechaDestacada(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fecha datetime not null,
	descripcion varchar(255) not null
)  ENGINE=InnoDB;

create table RankingArtistasMasEjecutados(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ranking BIGINT,
	pais_id BIGINT,
	trimestre int,
	anio int,
	autor_id BIGINT NULL,
	cantidadUnidades BIGINT default 0,
	montoPercibido DECIMAL(10,2) default 0,
	FOREIGN KEY (pais_id) REFERENCES Pais(id),
	FOREIGN KEY (autor_id) REFERENCES Autor(id)
)  ENGINE=InnoDB;