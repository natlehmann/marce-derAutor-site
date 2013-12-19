INSERT INTO Rol (id,nombre) VALUES (1,'administrador');
INSERT INTO Rol (id,nombre) VALUES (2,'usuario');
INSERT INTO Rol (id,nombre) VALUES (3,'newsletter');

INSERT INTO Usuario (id,nombre,password) VALUES (1,'administrador','42f9d4da1fa9e7e357d9ae38a2bd1afc09f45cbf399f1e54426744f0d8b70528c9e7e9a5bd7aa150');
INSERT INTO Usuario (id,nombre,password) VALUES (2,'usuario','802979c8e6d476ddfb82a881e62c272f9d4445a7465d66c893ee9e70d4a70622719684a0d44012ca');

INSERT INTO Usuario (id,nombre,password,email) VALUES (3,'prueba1','null','natlehmann@yahoo.com');
INSERT INTO Usuario (id,nombre,password,email) VALUES (4,'prueba2','null','natlehmann@gmail.com');

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (1,1);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (2,2);

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (3,3);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (4,3);


