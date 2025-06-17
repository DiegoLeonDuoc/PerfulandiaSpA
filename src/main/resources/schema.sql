-- ===========================================================
-- 1. Limpieza de tablas (hijas → padres)
-- ===========================================================
DROP TABLE IF EXISTS DEVOLUCION;
DROP TABLE IF EXISTS PRODUCTOS_PEDIDO;
DROP TABLE IF EXISTS PRODUCTOS_CARRITO;
DROP TABLE IF EXISTS STOCK;
DROP TABLE IF EXISTS REABASTECIMIENTO;
DROP TABLE IF EXISTS ENVIO;
DROP TABLE IF EXISTS HORARIO_TRABAJO;
DROP TABLE IF EXISTS PEDIDO;
DROP TABLE IF EXISTS CARRITO;
DROP TABLE IF EXISTS DESCUENTO;
DROP TABLE IF EXISTS EMPLEADO;
DROP TABLE IF EXISTS CLIENTE;
DROP TABLE IF EXISTS ADMINISTRADOR;
DROP TABLE IF EXISTS PROVEEDOR;
DROP TABLE IF EXISTS SUCURSAL;
DROP TABLE IF EXISTS PRODUCTO;
DROP TABLE IF EXISTS USUARIO;

-- USUARIO: tabla padre de roles
CREATE TABLE USUARIO (
                         rut_usuario       INT PRIMARY KEY,
                         dv_usuario        CHAR(1) NOT NULL,
                         nom_usuario       VARCHAR(30) NOT NULL,
                         nom2_usuario      VARCHAR(30),
                         apellido_paterno  VARCHAR(30) NOT NULL,
                         apellido_materno  VARCHAR(30),
                         sexo_usuario      CHAR(1) NOT NULL,
                         dir_usuario       VARCHAR(100) NOT NULL,
                         fecha_nacimiento  DATE NOT NULL,
                         telefono_usuario  VARCHAR(9) NOT NULL,
                         tel2_usuario      VARCHAR(9),
                         email_usuario     VARCHAR(100) NOT NULL,
                         pass_usuario      VARCHAR(200) NOT NULL,
                         tipo_usuario      VARCHAR(13) NOT NULL DEFAULT 'CLIENTE',
                         CONSTRAINT uq_email_us UNIQUE(email_usuario),
                         CONSTRAINT chk_tipo_usuario CHECK (tipo_usuario IN ('CLIENTE','EMPLEADO','ADMINISTRADOR'))
);

-- SUCURSAL: almacena sucursales
CREATE TABLE SUCURSAL (
                          id_sucursal       INT IDENTITY PRIMARY KEY,
                          nombre_sucursal   VARCHAR(50) NOT NULL,
                          direccion_sucursal VARCHAR(100) NOT NULL
);

-- PROVEEDOR: datos de proveedores
CREATE TABLE PROVEEDOR (
                           id_proveedor      INT IDENTITY PRIMARY KEY,
                           nombre_proveedor  VARCHAR(100) NOT NULL,
                           telefono_proveedor VARCHAR(9) NOT NULL,
                           email_proveedor   VARCHAR(50) NOT NULL,
                           CONSTRAINT uq_email_prov UNIQUE(email_proveedor)
);

-- PRODUCTO: catálogo de productos
CREATE TABLE PRODUCTO (
                          id_producto       INT IDENTITY PRIMARY KEY,
                          cod_barr_prod     BIGINT NOT NULL,
                          nom_prod          VARCHAR(50) NOT NULL,
                          descrip_prod      VARCHAR(200),
                          precio_prod       INT NOT NULL,
                          CONSTRAINT uq_cod_barr UNIQUE(cod_barr_prod)
);

-- Roles basados en USUARIO (herencia JOINED)
CREATE TABLE ADMINISTRADOR (
                               rut_usuario       INT PRIMARY KEY,
                               FOREIGN KEY(rut_usuario) REFERENCES USUARIO(rut_usuario) ON DELETE CASCADE
);
CREATE TABLE CLIENTE (
                         rut_usuario       INT PRIMARY KEY,
                         estado_cuenta     VARCHAR(20) NOT NULL,
                         FOREIGN KEY(rut_usuario) REFERENCES USUARIO(rut_usuario) ON DELETE CASCADE
);
CREATE TABLE EMPLEADO (
                          rut_usuario       INT PRIMARY KEY,
                          id_sucursal       INT,
                          FOREIGN KEY(rut_usuario) REFERENCES USUARIO(rut_usuario) ON DELETE CASCADE,
                          FOREIGN KEY(id_sucursal) REFERENCES SUCURSAL(id_sucursal) ON DELETE SET NULL
);

-- CARRITO: relación con CLIENTE
CREATE TABLE CARRITO (
                         id_carrito        INT IDENTITY PRIMARY KEY,
                         rut_usuario       INT NOT NULL,
                         FOREIGN KEY(rut_usuario) REFERENCES CLIENTE(rut_usuario) ON DELETE CASCADE
);

-- PEDIDO: relación con SUCURSAL y CLIENTE
CREATE TABLE PEDIDO (
                        id_pedido         INT IDENTITY PRIMARY KEY,
                        id_sucursal       INT,
                        fec_pedido        DATE NOT NULL,
                        precio_pedido     INT NOT NULL,
                        metodo_pago       VARCHAR(50) NOT NULL,
                        dir_envio         VARCHAR(100),
                        dir_facturacion   VARCHAR(100),
                        costo_envio       INT,
                        anotaciones       VARCHAR(75),
                        rut_usuario       INT NOT NULL,
                        FOREIGN KEY(id_sucursal) REFERENCES SUCURSAL(id_sucursal) ON DELETE SET NULL,
                        FOREIGN KEY(rut_usuario) REFERENCES CLIENTE(rut_usuario) ON DELETE CASCADE,
                        CONSTRAINT chk_metodo_pago CHECK (metodo_pago IN ('EFECTIVO','DEBITO','CREDITO','WEBPAY','TRANSFERENCIA'))
);

-- DESCUENTO: posibles descuentos por producto
CREATE TABLE DESCUENTO (
                           id_descuento      INT IDENTITY PRIMARY KEY,
                           tipo_descuento    VARCHAR(10) NOT NULL,
                           valor_descuento   INT NOT NULL,
                           fec_ini_descuento DATE NOT NULL,
                           fec_fin_descuento DATE,
                           id_producto       INT NOT NULL,
                           FOREIGN KEY(id_producto) REFERENCES PRODUCTO(id_producto),
                           CONSTRAINT chk_tipo_descuento CHECK (tipo_descuento IN ('FIJO','PORCENTUAL'))
);

-- STOCK: inventario por sucursal
CREATE TABLE STOCK (
                       id_stock          INT IDENTITY PRIMARY KEY,
                       cant_stock        INT NOT NULL,
                       id_producto       INT NOT NULL,
                       id_sucursal       INT NOT NULL,
                       FOREIGN KEY(id_producto) REFERENCES PRODUCTO(id_producto),
                       FOREIGN KEY(id_sucursal) REFERENCES SUCURSAL(id_sucursal)
);

-- PRODUCTOS_CARRITO: unión Carrito–Producto
CREATE TABLE PRODUCTOS_CARRITO (
                                   id_prod_carrito   INT IDENTITY PRIMARY KEY,
                                   id_producto       INT NOT NULL,
                                   id_carrito        INT NOT NULL,
                                   cant_prod         INT NOT NULL,
                                   FOREIGN KEY(id_producto) REFERENCES PRODUCTO(id_producto),
                                   FOREIGN KEY(id_carrito) REFERENCES CARRITO(id_carrito)
);

-- PRODUCTOS_PEDIDO: detalla ítems del pedido
CREATE TABLE PRODUCTOS_PEDIDO (
                                  id_prod_pedido    INT IDENTITY PRIMARY KEY,
                                  cant_prod         INT NOT NULL,
                                  precio_unitario   INT NOT NULL,
                                  descuento_unitario INT,
                                  precio_total_prod INT NOT NULL,
                                  id_descuento      INT,
                                  id_pedido         INT NOT NULL,
                                  id_producto       INT,
                                  FOREIGN KEY(id_descuento) REFERENCES DESCUENTO(id_descuento) ON DELETE SET NULL,
                                  FOREIGN KEY(id_pedido) REFERENCES PEDIDO(id_pedido),
                                  FOREIGN KEY(id_producto) REFERENCES PRODUCTO(id_producto) ON DELETE SET NULL
);

-- REABASTECIMIENTO: pedidos a proveedores
CREATE TABLE REABASTECIMIENTO (
                                  id_reabas         INT IDENTITY PRIMARY KEY,
                                  cant_productos    INT NOT NULL,
                                  fecha_reabas      DATE NOT NULL,
                                  estado_reabas     VARCHAR(13) NOT NULL,
                                  id_sucursal       INT NOT NULL,
                                  id_producto       INT NOT NULL,
                                  id_proveedor      INT,
                                  FOREIGN KEY(id_sucursal) REFERENCES SUCURSAL(id_sucursal),
                                  FOREIGN KEY(id_producto) REFERENCES PRODUCTO(id_producto),
                                  FOREIGN KEY(id_proveedor) REFERENCES PROVEEDOR(id_proveedor) ON DELETE SET NULL,
                                  CONSTRAINT chk_estado_reabas CHECK (
                                      estado_reabas IN (
                                                        'SOLICITADO','CONFIRMADO','EN TRÁNSITO','EN ESPERA',
                                                        'RECIBIDO','CANCELADO','EN INSPECCIÓN','RECHAZADO'
                                          )
                                      )
);

-- ENVIO: seguimiento de envíos
CREATE TABLE ENVIO (
                       id_envio           INT IDENTITY PRIMARY KEY,
                       codigo_envio       INT NOT NULL,
                       id_pedido          INT NOT NULL,
                       fecha_envio        DATE NOT NULL,
                       fecha_llegada_estim DATE NOT NULL,
                       fecha_llegada_real DATE,
                       transportista      VARCHAR(30) NOT NULL,
                       num_seguimiento    INT NOT NULL,
                       metodo_envio       VARCHAR(20) NOT NULL DEFAULT 'STANDARD',
                       id_sucursal        INT,
                       FOREIGN KEY(id_pedido) REFERENCES PEDIDO(id_pedido),
                       FOREIGN KEY(id_sucursal) REFERENCES SUCURSAL(id_sucursal),
                       CONSTRAINT chk_metodo_envio CHECK (metodo_envio IN ('STANDARD','EXPRESS','INTERNACIONAL'))
);

-- HORARIO_TRABAJO: horas de operación
CREATE TABLE HORARIO_TRABAJO (
                                 id_horario         INT IDENTITY PRIMARY KEY,
                                 dia_semana         INT NOT NULL,
                                 horario_apertura   TIME NOT NULL,
                                 horario_cierre     TIME NOT NULL,
                                 id_sucursal        INT,
                                 FOREIGN KEY(id_sucursal) REFERENCES SUCURSAL(id_sucursal)
);

-- DEVOLUCION: registro de devoluciones
CREATE TABLE DEVOLUCION (
                            id_devolucion      INT IDENTITY PRIMARY KEY,
                            id_producto_pedido INT NOT NULL,
                            cantidad_devuelta  INT NOT NULL,
                            motivo_devo        VARCHAR(100),
                            estado_devo        VARCHAR(30) NOT NULL,
                            monto_devuelto     INT NOT NULL,
                            restock            INT NOT NULL,
                            FOREIGN KEY(id_producto_pedido) REFERENCES PRODUCTOS_PEDIDO(id_prod_pedido),
                            CONSTRAINT chk_estado_devo CHECK (
                                estado_devo IN ('SOLICITADA','EN PROCESO','APROBADA','COMPLETADA','DENEGADA')
                                )
);
