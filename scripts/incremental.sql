create table FuenteAuditada(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	nombre varchar(255) not null
)  ENGINE=InnoDB;

create table VisitaTecnica(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fuenteAuditada_id BIGINT NOT NULL,
	fecha datetime NOT NULL,
	FOREIGN KEY (fuenteAuditada_id) REFERENCES FuenteAuditada(id)
)  ENGINE=InnoDB;

ALTER TABLE PuntoAuditoria
drop column fuente_id;

ALTER TABLE PuntoAuditoria
add visitaTecnica_id BIGINT NOT NULL;

ALTER TABLE PuntoAuditoria
add FOREIGN KEY (visitaTecnica_id) REFERENCES VisitaTecnica(id);

--drop table PuntoAuditoria;

--create table PuntoAuditoria(
--	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--	visitaTecnica_id BIGINT NOT NULL,
--	itemAuditoria_id BIGINT NOT NULL,
--	puntajeAsignado int NOT NULL,
--	FOREIGN KEY (visitaTecnica_id) REFERENCES VisitaTecnica(id),
--	FOREIGN KEY (itemAuditoria_id) REFERENCES ItemAuditoria(id)
--)  ENGINE=InnoDB;




