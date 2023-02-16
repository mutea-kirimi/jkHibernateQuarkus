BEGIN;

ALTER TABLE IF EXISTS Quarkus
    RENAME TO towns;

TRUNCATE TABLE towns;

ALTER TABLE IF EXISTS towns
    ADD COLUMN country VARCHAR(10),
    ADD CONSTRAINT towns_pk PRIMARY KEY(id),
    ADD CONSTRAINT towns_unique UNIQUE(name , country);

END;