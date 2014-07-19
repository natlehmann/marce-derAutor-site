drop table EstadoDeTareas;

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


drop table ReglamentoDeDistribucion;

create table ReglamentoDeDistribucion(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fecha datetime not null,
	descripcion varchar(512) not null,
	fuente_id BIGINT NOT NULL,
	derecho_nombre varchar(255) NOT NULL,
	nombreFuente varchar(255),
	FOREIGN KEY (derecho_nombre) REFERENCES Derecho(nombre)
)  ENGINE=InnoDB;


drop table Derecho;

create table DerechoEditable(
	nombre varchar(255) not null PRIMARY KEY,
	modificable bit default 0
)  ENGINE=InnoDB;
