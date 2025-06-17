-- Usuarios base
INSERT INTO USUARIO VALUES
                        (10000001,'1','Ana',NULL,'Pérez',NULL,'F','Av. Siempre Viva 123','1990-01-01','912345678',NULL,'ana@correo.com','$2a$10$hashCLI','CLIENTE'),
                        (20000002,'2','Juan',NULL,'Gómez',NULL,'M','Calle Falsa 456','1985-05-10','987654321',NULL,'juan@correo.com','$2a$10$hashEMP','EMPLEADO'),
                        (30000003,'3','Luis',NULL,'Soto',NULL,'M','Pasaje Real 789','1980-12-20','912345679',NULL,'luis@correo.com','$2a$10$hashADM','ADMINISTRADOR');

-- Roles
INSERT INTO CLIENTE VALUES (10000001,'ACTIVO');
INSERT INTO EMPLEADO VALUES (20000002,1);
INSERT INTO ADMINISTRADOR VALUES (30000003);

-- Sucursales
INSERT INTO SUCURSAL(nombre_sucursal,direccion_sucursal) VALUES
                                                             ('Sucursal Central','Av. Principal 100'),
                                                             ('Sucursal Norte','Calle Norte 200');

-- Proveedores
INSERT INTO PROVEEDOR(nombre_proveedor,telefono_proveedor,email_proveedor) VALUES
                                                                               ('Proveedor Uno','912000001','prov1@correo.com'),
                                                                               ('Proveedor Dos','912000002','prov2@correo.com');

-- Productos
INSERT INTO PRODUCTO(cod_barr_prod,nom_prod,descrip_prod,precio_prod) VALUES
                                                                          (10001,'Lápiz','Lápiz grafito HB',200),
                                                                          (10002,'Cuaderno','Cuaderno universitario',1200),
                                                                          (10003,'Regla','Regla plástica 30cm',500);

-- Carrito
INSERT INTO CARRITO(rut_usuario) VALUES (10000001);

-- Stock
INSERT INTO STOCK(cant_stock,id_producto,id_sucursal) VALUES
                                                          (100,1,1),(50,2,1),(30,3,2);

-- Descuentos
INSERT INTO DESCUENTO(tipo_descuento,valor_descuento,fec_ini_descuento,fec_fin_descuento,id_producto) VALUES
                                                                                                          ('FIJO',100,'2024-05-01','2024-06-01',1),
                                                                                                          ('PORCENTUAL',10,'2024-05-15','2024-06-15',2);

-- Pedido
INSERT INTO PEDIDO(id_sucursal,fec_pedido,precio_pedido,metodo_pago,dir_envio,dir_facturacion,costo_envio,anotaciones,rut_usuario) VALUES
    (1,'2024-05-20',1600,'CREDITO','Av. Siempre Viva 123','Av. Siempre Viva 123',500,'Entrega rápida',10000001);

-- Productos en Carrito
INSERT INTO PRODUCTOS_CARRITO(id_producto,id_carrito,cant_prod) VALUES
                                                                    (1,1,2),(2,1,1);

-- Productos en Pedido
INSERT INTO PRODUCTOS_PEDIDO(cant_prod,precio_unitario,descuento_unitario,precio_total_prod,id_descuento,id_pedido,id_producto) VALUES
                                                                                                                                    (2,200,100,300,1,1,1),(1,1200,120,1080,2,1,2);

-- Reabastecimientos
INSERT INTO REABASTECIMIENTO(cant_productos,fecha_reabas,estado_reabas,id_sucursal,id_producto,id_proveedor) VALUES
                                                                                                                 (50,'2024-05-10','RECIBIDO',1,1,1),(20,'2024-05-15','EN ESPERA',2,2,2);

-- Envíos
INSERT INTO ENVIO(codigo_envio,id_pedido,fecha_envio,fecha_llegada_estim,transportista,num_seguimiento,metodo_envio,id_sucursal) VALUES
    (12345,1,'2024-05-21','2024-05-23','TransporteX',10001,'STANDARD',1);

-- Horarios
INSERT INTO HORARIO_TRABAJO(dia_semana,horario_apertura,horario_cierre,id_sucursal) VALUES
                                                                                        (1,'09:00','18:00',1),(2,'09:00','18:00',1);

-- Devoluciones
INSERT INTO DEVOLUCION(id_producto_pedido,cantidad_devuelta,motivo_devo,estado_devo,monto_devuelto,restock) VALUES
    (1,1,'Producto defectuoso','SOLICITADA',200,1);
