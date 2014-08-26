create table Usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombreApellido varchar(255),
	username varchar(255),
	password varchar(255),
	email varchar(255),
	fechaBaja datetime,
	fechaEliminacion datetime
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON Usuario(username);

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

create table DerechoEditable(
	nombre varchar(255) not null PRIMARY KEY
)  ENGINE=InnoDB;

create table FuenteAuditada(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null
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

create table ItemAuditoria(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null,
	orden int not null,
	puntaje int not null
)  ENGINE=InnoDB;

create table VisitaTecnica(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fuenteAuditada_id BIGINT NOT NULL,
	fecha datetime NOT NULL,
	FOREIGN KEY (fuenteAuditada_id) REFERENCES FuenteAuditada(id)
)  ENGINE=InnoDB;

create table PuntoAuditoria(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	visitaTecnica_id BIGINT NOT NULL,
	itemAuditoria_id BIGINT NOT NULL,
	puntajeAsignado int NOT NULL,
	FOREIGN KEY (visitaTecnica_id) REFERENCES VisitaTecnica(id),
	FOREIGN KEY (itemAuditoria_id) REFERENCES ItemAuditoria(id)
)  ENGINE=InnoDB;

create table EstadoDeTareas(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	asunto varchar(255) NOT NULL,
	autor_id BIGINT,
	fuente_id BIGINT,
	estado varchar(50),
	prioridad varchar(50),
	fecha datetime,
	descripcion varchar(1024) NOT NULL,
	comentario varchar(512) NOT NULL,
	nombreAutor varchar(255),
	nombreFuente varchar(255)
)  ENGINE=InnoDB;

create table ReglamentoDeDistribucion(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fecha datetime not null,
	descripcion varchar(512) not null,
	fuente_id BIGINT NOT NULL,
	derecho_nombre varchar(255) NOT NULL,
	nombreFuente varchar(255)
)  ENGINE=InnoDB;

create table Newsletter(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	subject varchar(255) not null,
	contenido LONGTEXT not null,
	fechaCreacion datetime not null
)  ENGINE=InnoDB;

create table EnvioNewsletter(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fechaEnvio datetime not null,
	newsletter_id BIGINT NOT NULL,
	FOREIGN KEY (newsletter_id) REFERENCES Newsletter(id)
)  ENGINE=InnoDB;

create table ErrorEnvioNewsletter(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	error varchar(512) NOT NULL,
	envioNewsletter_id BIGINT NOT NULL,
	FOREIGN KEY (envioNewsletter_id) REFERENCES EnvioNewsletter(id)
)  ENGINE=InnoDB;

create table ReceptorNewsletter(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fechaApertura datetime null,
	usuario_id BIGINT NOT NULL,
	envioNewsletter_id BIGINT NOT NULL,
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (envioNewsletter_id) REFERENCES EnvioNewsletter(id)
)  ENGINE=InnoDB;

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
	nombrePais varchar(255) null,
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
	nombrePais varchar(255) null,
	trimestre int,
	anio int,
	autor_id BIGINT NULL,
	nombreAutor varchar(255) null,
	cantidadUnidades BIGINT default 0,
	montoPercibido DECIMAL(10,2) default 0
)  ENGINE=InnoDB;
