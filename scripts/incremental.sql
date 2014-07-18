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