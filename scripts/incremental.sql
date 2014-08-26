create table DatosCancion(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	companyId BIGINT,
	pais_id BIGINT,
	nombrePais varchar(255) null,
	trimestre int not null,
	anio int not null,
	autor_id BIGINT NULL,
	nombreAutor varchar(255) null,
	cancion_id BIGINT NULL,
	nombreCancion varchar(255) null,
	fuente_id BIGINT NULL,
	nombreFuente varchar(255) null,
	derecho_nombre varchar(255) NULL,
	cantidadUnidades BIGINT default 0,
	montoPercibido DECIMAL(10,2) default 0
)  ENGINE=InnoDB;

create table RankingArtistasMasEjecutados(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ranking BIGINT,
	pais_id BIGINT,
	trimestre int,
	anio int,
	autor_id BIGINT NULL,
	nombreAutor varchar(255) null,
	cantidadUnidades BIGINT default 0,
	montoPercibido DECIMAL(10,2) default 0
)  ENGINE=InnoDB;

create table RankingArtistasMasCobrados(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ranking BIGINT,
	pais_id BIGINT,
	trimestre int,
	anio int,
	autor_id BIGINT NULL,
	nombreAutor varchar(255) null,
	cantidadUnidades BIGINT default 0,
	montoPercibido DECIMAL(10,2) default 0
)  ENGINE=InnoDB;
