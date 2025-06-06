-- DROP TABLES (hijas primero, padres al final)
DROP TABLE IF EXISTS DEVOLUCION;
DROP TABLE IF EXISTS PRODUCTOS_PEDIDO;
DROP TABLE IF EXISTS PRODUCTOS_CARRITO;
DROP TABLE IF EXISTS STOCK;
DROP TABLE IF EXISTS REABASTECIMIENTO;
DROP TABLE IF EXISTS ENVIO;
DROP TABLE IF EXISTS PEDIDO;
DROP TABLE IF EXISTS CARRITO;
DROP TABLE IF EXISTS DESCUENTO;
DROP TABLE IF EXISTS HORARIO_TRABAJO;
DROP TABLE IF EXISTS EMPLEADO;
DROP TABLE IF EXISTS CLIENTE;
DROP TABLE IF EXISTS ADMINISTRADOR;
DROP TABLE IF EXISTS PROVEEDOR;
DROP TABLE IF EXISTS SUCURSAL;
DROP TABLE IF EXISTS PRODUCTO;
DROP TABLE IF EXISTS USUARIO;

-- CREACIÓN DE TABLAS (padres primero, hijas después)

CREATE TABLE USUARIO (
                         rut_usuario         NUMBER(8) NOT NULL,
                         dv_usuario          CHAR(1) NOT NULL,
                         nom_usuario         VARCHAR2(30) NOT NULL,
                         nom2_usuario        VARCHAR2(30),
                         apellido_paterno    VARCHAR2(30) NOT NULL,
                         apellido_materno    VARCHAR2(30),
                         sexo_usuario        CHAR(1) NOT NULL,
                         dir_usuario         VARCHAR2(100) NOT NULL,
                         fecha_nacimiento    DATE NOT NULL,
                         telefono_usuario    VARCHAR(9) NOT NULL,
                         tel2_usuario        VARCHAR(9),
                         email_usuario       VARCHAR2(100) NOT NULL,
                         pass_usuario        VARCHAR2(200) NOT NULL,
                         tipo_usuario        VARCHAR2(13) NOT NULL
);

CREATE TABLE SUCURSAL (
                          id_sucursal  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          nombre_sucursal VARCHAR2(50) NOT NULL,
                          direccion_sucursal VARCHAR2(100) NOT NULL
);

CREATE TABLE PROVEEDOR (
                           id_proveedor           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           nombre_proveedor       VARCHAR2(100) NOT NULL,
                           telefono_proveedor     VARCHAR(9) NOT NULL,
                           email_proveedor        VARCHAR2(50) NOT NULL
);

CREATE TABLE PRODUCTO (
                          id_producto     INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          cod_barr_prod   INTEGER NOT NULL,
                          nom_prod        VARCHAR2(50) NOT NULL,
                          descrip_prod    VARCHAR2(200),
                          precio_prod     INTEGER NOT NULL
);

CREATE TABLE ADMINISTRADOR (
                               rut_usuario NUMBER(10) NOT NULL
);

CREATE TABLE CLIENTE (
                         rut_usuario   NUMBER(8) NOT NULL,
                         estado_cuenta VARCHAR2(20) NOT NULL
);

CREATE TABLE EMPLEADO (
                          rut_usuario NUMBER(8) NOT NULL,
                          id_sucursal INTEGER NOT NULL
);

CREATE TABLE CARRITO (
                         id_carrito INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         rut_usuario INTEGER NOT NULL
);

CREATE TABLE HORARIO_TRABAJO (
                                 id_horario       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                 dia_semana       NUMBER(1) NOT NULL,
                                 horario_apertura TIME NOT NULL,
                                 horario_cierre   TIME NOT NULL,
                                 id_sucursal      INTEGER NOT NULL
);

CREATE TABLE PEDIDO (
                        id_pedido       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        id_sucursal     INTEGER NOT NULL,
                        fec_pedido      DATE NOT NULL,
                        precio_pedido   INTEGER NOT NULL,
                        metodo_pago     VARCHAR2(50) NOT NULL,
                        dir_envio       VARCHAR2(100),
                        dir_facturacion VARCHAR2(100),
                        costo_envio     INTEGER,
                        anotaciones     VARCHAR2(75),
                        rut_usuario     NUMBER(8) NOT NULL
);

CREATE TABLE DESCUENTO (
                           id_descuento      INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           tipo_descuento    VARCHAR(10) NOT NULL,
                           valor_descuento   INTEGER NOT NULL,
                           fec_ini_descuento TIMESTAMP NOT NULL,
                           fec_fin_descuento TIMESTAMP,
                           id_producto       INTEGER NOT NULL
);

CREATE TABLE ENVIO (
                       id_envio             INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       codigo_envio         INTEGER NOT NULL,
                       id_pedido            INTEGER NOT NULL,
                       fecha_envio          DATE NOT NULL,
                       fecha_llegada_estim  DATE NOT NULL,
                       fecha_llegada_real   DATE,
                       transportista        VARCHAR2(30) NOT NULL,
                       num_seguimiento      INTEGER NOT NULL,
                       metodo_envio         VARCHAR2(20) NOT NULL,
                       id_sucursal          INTEGER
);

CREATE TABLE REABASTECIMIENTO (
                                  id_reabas       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                  cant_productos  INTEGER NOT NULL,
                                  fecha_reabas    DATE NOT NULL,
                                  estado_reabas   VARCHAR2(13) NOT NULL,
                                  id_sucursal     INTEGER NOT NULL,
                                  id_producto     INTEGER NOT NULL,
                                  id_proveedor    INTEGER NOT NULL
);

CREATE TABLE STOCK (
                       id_stock        INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       cant_stock      INTEGER NOT NULL,
                       id_producto     INTEGER NOT NULL,
                       id_sucursal     INTEGER NOT NULL
);

CREATE TABLE PRODUCTOS_CARRITO (
                                   id_producto_carr INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                   id_producto      INTEGER NOT NULL,
                                   id_carrito       INTEGER NOT NULL,
                                   cant_prod        INTEGER NOT NULL
);

CREATE TABLE PRODUCTOS_PEDIDO (
                                  id_producto_pedido     INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                  cant_prod              INTEGER NOT NULL,
                                  precio_unitario        INTEGER NOT NULL,
                                  descuento_unitario     INTEGER,
                                  precio_total_prod      INTEGER NOT NULL,
                                  id_descuento           INTEGER,
                                  id_pedido              INTEGER NOT NULL,
                                  id_producto            INTEGER NOT NULL
);

CREATE TABLE DEVOLUCION (
                            id_devolucion       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                            id_pedido           INTEGER NOT NULL,
                            id_producto_pedido  INTEGER NOT NULL,
                            cantidad_devuelta   INTEGER NOT NULL,
                            motivo_devo         VARCHAR2(100),
                            estado_devo         VARCHAR2(30) NOT NULL,
                            monto_devuelto      INTEGER NOT NULL,
                            restock             NUMBER NOT NULL
);

-- PRIMARY KEYS
ALTER TABLE USUARIO ADD CONSTRAINT USUARIO_PK PRIMARY KEY (rut_usuario);
ALTER TABLE ADMINISTRADOR ADD CONSTRAINT ADMINISTRADOR_PK PRIMARY KEY (rut_usuario);
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_PK PRIMARY KEY (rut_usuario);
ALTER TABLE EMPLEADO ADD CONSTRAINT EMPLEADO_PK PRIMARY KEY (rut_usuario);

-- RELACIONES Y CONSTRAINTS (en orden de dependencias)
ALTER TABLE ADMINISTRADOR ADD CONSTRAINT Herencia_usuario_administrador FOREIGN KEY (rut_usuario) REFERENCES USUARIO (rut_usuario);
ALTER TABLE CLIENTE ADD CONSTRAINT Herencia_usuario_cliente FOREIGN KEY (rut_usuario) REFERENCES USUARIO (rut_usuario);
ALTER TABLE EMPLEADO ADD CONSTRAINT Herencia_usuario_empleado FOREIGN KEY (rut_usuario) REFERENCES USUARIO (rut_usuario);
ALTER TABLE EMPLEADO ADD CONSTRAINT Empleado_Sucursal FOREIGN KEY (id_sucursal) REFERENCES SUCURSAL (id_sucursal);
ALTER TABLE CARRITO ADD CONSTRAINT Carrito_Cliente FOREIGN KEY (rut_usuario) REFERENCES CLIENTE (rut_usuario);
ALTER TABLE HORARIO_TRABAJO ADD CONSTRAINT Sucursal_Horario FOREIGN KEY (id_sucursal) REFERENCES SUCURSAL (id_sucursal);
ALTER TABLE STOCK ADD CONSTRAINT Stock_Producto FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto);
ALTER TABLE STOCK ADD CONSTRAINT Stock_Sucursal FOREIGN KEY (id_sucursal) REFERENCES SUCURSAL (id_sucursal);
ALTER TABLE ENVIO ADD CONSTRAINT Envio_Sucursal FOREIGN KEY (id_sucursal) REFERENCES SUCURSAL (id_sucursal);
ALTER TABLE ENVIO ADD CONSTRAINT Envio_Pedido FOREIGN KEY (id_pedido) REFERENCES PEDIDO (id_pedido);
ALTER TABLE REABASTECIMIENTO ADD CONSTRAINT Reabas_Sucursal FOREIGN KEY (id_sucursal) REFERENCES SUCURSAL (id_sucursal);
ALTER TABLE REABASTECIMIENTO ADD CONSTRAINT Reabas_Producto FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto);
ALTER TABLE REABASTECIMIENTO ADD CONSTRAINT Reabas_Proveedor FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor);
ALTER TABLE PEDIDO ADD CONSTRAINT Pedido_Cliente FOREIGN KEY (rut_usuario) REFERENCES CLIENTE (rut_usuario);
ALTER TABLE PEDIDO ADD CONSTRAINT Pedido_Sucursal FOREIGN KEY (id_sucursal) REFERENCES SUCURSAL (id_sucursal);
ALTER TABLE PRODUCTOS_CARRITO ADD CONSTRAINT ProdCarrito_Producto FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto);
ALTER TABLE PRODUCTOS_CARRITO ADD CONSTRAINT ProdCarrito_Carrito FOREIGN KEY (id_carrito) REFERENCES CARRITO (id_carrito);
ALTER TABLE PRODUCTOS_PEDIDO ADD CONSTRAINT ProdPedido_Descuento FOREIGN KEY (id_descuento) REFERENCES DESCUENTO (id_descuento);
ALTER TABLE PRODUCTOS_PEDIDO ADD CONSTRAINT ProdPedido_Pedido FOREIGN KEY (id_pedido) REFERENCES PEDIDO (id_pedido);
ALTER TABLE PRODUCTOS_PEDIDO ADD CONSTRAINT ProdPedido_Producto FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto);
ALTER TABLE DESCUENTO ADD CONSTRAINT Descuento_Producto FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto);
ALTER TABLE DEVOLUCION ADD CONSTRAINT Devolucion_Pedido FOREIGN KEY (id_pedido) REFERENCES PEDIDO (id_pedido);
ALTER TABLE DEVOLUCION ADD CONSTRAINT Devolucion_ProdPedido FOREIGN KEY (id_producto_pedido) REFERENCES PRODUCTOS_PEDIDO (id_producto_pedido);

-- UNIQUE
ALTER TABLE PRODUCTO ADD CONSTRAINT unique_cod_barr_prod UNIQUE (cod_barr_prod);
ALTER TABLE PROVEEDOR ADD CONSTRAINT unique_email_prov UNIQUE (email_proveedor);
ALTER TABLE USUARIO ADD CONSTRAINT unique_email_us UNIQUE (email_usuario);

-- Defaults y checks (H2 en modo Oracle soporta la mayoría)
ALTER TABLE ENVIO ALTER COLUMN metodo_envio SET DEFAULT 'STANDARD';
ALTER TABLE USUARIO ALTER COLUMN tipo_usuario SET DEFAULT 'CLIENTE';

ALTER TABLE USUARIO ADD CONSTRAINT tipo_usuario_check CHECK (tipo_usuario IN ('CLIENTE', 'EMPLEADO', 'ADMINISTRADOR'));
ALTER TABLE DEVOLUCION ADD CONSTRAINT estado_devo_check CHECK (estado_devo IN ('SOLICITADA', 'EN PROCESO', 'APROBADA', 'COMPLETADA'));
ALTER TABLE DESCUENTO ADD CONSTRAINT tipo_descuento_check CHECK (tipo_descuento IN ('FIJO', 'PORCENTUAL'));
ALTER TABLE ENVIO ADD CONSTRAINT metodo_envio_check CHECK (metodo_envio IN ('STANDARD', 'EXPRESS', 'INTERNACIONAL'));
ALTER TABLE REABASTECIMIENTO ADD CONSTRAINT estado_reabastecimiento_check CHECK (estado_reabas IN ('SOLICITADO', 'CONFIRMADO', 'EN TRÁNSITO', 'EN ESPERA', 'RECIBIDO', 'CANCELADO', 'EN INSPECCIÓN', 'RECHAZADO'));
ALTER TABLE USUARIO ADD CONSTRAINT dv_usuario_check CHECK (dv_usuario IN ('1', '2', '3', '4', '5', '6', '7', '8', '9', 'K'));
ALTER TABLE PEDIDO ADD CONSTRAINT metodo_pago_check CHECK (metodo_pago IN ('EFECTIVO', 'DEBITO', 'CREDITO', 'WEBPAY', 'TRANSFERENCIA'));

COMMIT;
