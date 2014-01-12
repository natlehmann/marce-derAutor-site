ALTER TABLE Usuario 
add email varchar(255);

INSERT INTO Rol (id,nombre) VALUES (3,'newsletter');

INSERT INTO Usuario (id,nombre,password, email) VALUES (3,'prueba1','null', 'natlehmann@yahoo.com');
INSERT INTO Usuario (id,nombre,password, email) VALUES (4,'prueba2','null', 'natlehmann@gmail.com');

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (3,3);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (4,3);


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