ALTER TABLE Usuario 
add email varchar(255);

INSERT INTO Rol (id,nombre) VALUES (3,'newsletter');

INSERT INTO Usuario (id,nombre,password, email) VALUES (3,'prueba1','null', 'natlehmann@yahoo.com');
INSERT INTO Usuario (id,nombre,password, email) VALUES (4,'prueba2','null', 'natlehmann@gmail.com');

INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (3,3);
INSERT INTO Usuario_Rol (usuario_id,rol_id) VALUES (4,3);