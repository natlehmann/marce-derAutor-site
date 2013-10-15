CREATE USER 'derechos'@'localhost' IDENTIFIED BY 'd3r3ch0s';
create database derechos_autor;
GRANT ALL ON derechos_autor.* TO 'derechos'@'localhost';
FLUSH PRIVILEGES;
