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



DROP TABLE Usuario_Rol;
DROP TABLE Usuario;

create table Usuario(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombreApellido varchar(255),
	username varchar(255),
	password varchar(255),
	email varchar(255)
)  ENGINE=InnoDB;

CREATE UNIQUE INDEX UK_usuario_nombre ON Usuario(username);

create table Usuario_Rol(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	usuario_id BIGINT NOT NULL,
	rol_id BIGINT NOT NULL,
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (rol_id) REFERENCES Rol(id)
)  ENGINE=InnoDB;

INSERT INTO Usuario (id,username,password) VALUES (1,'administrador','42f9d4da1fa9e7e357d9ae38a2bd1afc09f45cbf399f1e54426744f0d8b70528c9e7e9a5bd7aa150');
INSERT INTO Usuario (id,username,password) VALUES (2,'usuario','802979c8e6d476ddfb82a881e62c272f9d4445a7465d66c893ee9e70d4a70622719684a0d44012ca');

INSERT INTO Usuario (id,nombreApellido,email) VALUES (3,'prueba1','natlehmann@yahoo.com');
INSERT INTO Usuario (id,nombreApellido,email) VALUES (4,'prueba2','natlehmann@gmail.com');

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (1,1);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (2,2);

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (3,3);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (4,3);


create table ReceptorNewsletter(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fechaApertura datetime null,
	usuario_id BIGINT NOT NULL,
	envioNewsletter_id BIGINT NOT NULL,
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (envioNewsletter_id) REFERENCES EnvioNewsletter(id)
)  ENGINE=InnoDB;