-- Oracle. Ejecutar con el usuario de la práctica.
CREATE SEQUENCE seq_localidad_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tipo_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_alojamiento_id START WITH 1 INCREMENT BY 1;

CREATE TABLE localidad (
    id NUMBER,
    nombre VARCHAR2(120) NOT NULL,
    CONSTRAINT pk_localidad PRIMARY KEY (id),
    CONSTRAINT uk_localidad_nombre UNIQUE (nombre)
);

CREATE OR REPLACE TRIGGER seq_localidad_id
BEFORE INSERT ON localidad
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT seq_localidad_id.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;
/

CREATE TABLE tipo (
    id NUMBER,
    nombre VARCHAR2(120) NOT NULL,
    CONSTRAINT pk_tipo PRIMARY KEY (id),
    CONSTRAINT uk_tipo_nombre UNIQUE (nombre)
);

CREATE OR REPLACE TRIGGER seq_tipo_id
BEFORE INSERT ON tipo
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT seq_tipo_id.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;
/

CREATE TABLE alojamiento (
    id NUMBER,
    nombre VARCHAR2(180) NOT NULL,
    descripcion VARCHAR2(1200),
    precio_noche NUMBER(9,2) NOT NULL,
    capacidad NUMBER(2) NOT NULL,
    imagen VARCHAR2(300) NOT NULL,
    destacado NUMBER(1) DEFAULT 0 NOT NULL,
    localidad_id NUMBER NOT NULL,
    tipo_id NUMBER NOT NULL,
    CONSTRAINT pk_alojamiento PRIMARY KEY (id),
    CONSTRAINT ck_alojamiento_precio_noche CHECK (precio_noche >= 0),
    CONSTRAINT ck_alojamiento_capacidad CHECK (capacidad >= 0),
    CONSTRAINT ck_alojamiento_destacado CHECK (destacado IN (0,1)),
    CONSTRAINT fk_alojamiento_localidad FOREIGN KEY (localidad_id) REFERENCES localidad(id),
    CONSTRAINT fk_alojamiento_tipo FOREIGN KEY (tipo_id) REFERENCES tipo(id)
);

CREATE OR REPLACE TRIGGER seq_alojamiento_id
BEFORE INSERT ON alojamiento
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT seq_alojamiento_id.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;
/