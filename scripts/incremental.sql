create table DerechoExternoReplica(
	id BIGINT NOT NULL PRIMARY KEY,
	nombre varchar(255),
	padre_id BIGINT NULL,
	FOREIGN KEY (padre_id) REFERENCES DerechoExternoReplica(id)
)  ENGINE=InnoDB;