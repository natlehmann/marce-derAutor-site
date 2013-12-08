create table VisitaTecnica(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	fuente_id BIGINT NOT NULL,
	fecha datetime NOT NULL,
	FOREIGN KEY (fuente_id) REFERENCES Fuente(id)
)  ENGINE=InnoDB;

ALTER TABLE PuntoAuditoria
drop column fuente_id;

ALTER TABLE PuntoAuditoria
add visitaTecnica_id BIGINT NOT NULL;

ALTER TABLE PuntoAuditoria
add FOREIGN KEY (visitaTecnica_id) REFERENCES VisitaTecnica(id);

