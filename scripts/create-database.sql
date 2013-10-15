CREATE USER 'derechos'@'localhost' IDENTIFIED BY 'XXXXX';
create database derechos_autor;
GRANT ALL ON derechos_autor.* TO 'derechos'@'localhost';
FLUSH PRIVILEGES;
