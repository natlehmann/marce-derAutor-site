INSERT INTO Rol (id,nombre) VALUES (1,'administrador');
INSERT INTO Rol (id,nombre) VALUES (2,'usuario');

INSERT INTO Usuario (id,nombre,password) VALUES (1,'administrador','admin');
INSERT INTO Usuario (id,nombre,password) VALUES (2,'usuario','usuario');

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (1,1);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (2,2);


