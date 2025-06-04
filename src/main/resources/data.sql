-- 1. USUARIO
INSERT INTO USUARIO (rut_usuario, dv_usuario, nom_usuario, nom2_usuario, apellido_paterno, apellido_materno, sexo_usuario, dir_usuario, fecha_nacimiento, telefono_usuario, tel2_usuario, email_usuario, pass_usuario, tipo_usuario)
VALUES (10000001, '1', 'Juan', 'Carlos', 'Pérez', 'García', 'M', 'Av. Siempre Viva 123', '1990-01-01', '912345678', NULL, 'juan.perez@correo.com', 'pass123', 'CLIENTE');
INSERT INTO USUARIO (rut_usuario, dv_usuario, nom_usuario, nom2_usuario, apellido_paterno, apellido_materno, sexo_usuario, dir_usuario, fecha_nacimiento, telefono_usuario, tel2_usuario, email_usuario, pass_usuario, tipo_usuario)
VALUES (20000002, '2', 'Ana', NULL, 'Martínez', NULL, 'F', 'Calle Falsa 456', '1985-05-10', '987654321', NULL, 'ana.martinez@correo.com', 'pass456', 'EMPLEADO');
INSERT INTO USUARIO (rut_usuario, dv_usuario, nom_usuario, nom2_usuario, apellido_paterno, apellido_materno, sexo_usuario, dir_usuario, fecha_nacimiento, telefono_usuario, tel2_usuario, email_usuario, pass_usuario, tipo_usuario)
VALUES (30000003, '3', 'Luis', NULL, 'Soto', NULL, 'M', 'Pasaje Real 789', '1980-12-20', '912345679', NULL, 'luis.soto@correo.com', 'pass789', 'ADMINISTRADOR');

-- 2. SUCURSAL
INSERT INTO SUCURSAL (id_sucursal, nombre_sucursal, direccion_sucursal) VALUES (1, 'Sucursal Central', 'Av. Principal 100');
INSERT INTO SUCURSAL (id_sucursal, nombre_sucursal, direccion_sucursal) VALUES (2, 'Sucursal Norte', 'Calle Norte 200');

-- 3. PROVEEDOR
INSERT INTO PROVEEDOR (id_proveedor, nombre_proveedor, telefono_proveedor, email_proveedor) VALUES (1, 'Proveedor Uno', '912000001', 'prov1@correo.com');
INSERT INTO PROVEEDOR (id_proveedor, nombre_proveedor, telefono_proveedor, email_proveedor) VALUES (2, 'Proveedor Dos', '912000002', 'prov2@correo.com');

-- 4. PRODUCTO (sin id_producto, H2 lo autogenera)
INSERT INTO PRODUCTO (cod_barr_prod, nom_prod, descrip_prod, precio_prod) VALUES (10001, 'Lápiz', 'Lápiz grafito HB', 200);
INSERT INTO PRODUCTO (cod_barr_prod, nom_prod, descrip_prod, precio_prod) VALUES (10002, 'Cuaderno', 'Cuaderno universitario', 1200);
INSERT INTO PRODUCTO (cod_barr_prod, nom_prod, descrip_prod, precio_prod) VALUES (10003, 'Regla', 'Regla plástica 30cm', 500);

-- 5. CLIENTE / EMPLEADO / ADMINISTRADOR
INSERT INTO CLIENTE (rut_usuario, estado_cuenta) VALUES (10000001, 'ACTIVO');
INSERT INTO EMPLEADO (rut_usuario, id_sucursal) VALUES (20000002, 1);
INSERT INTO ADMINISTRADOR (rut_usuario) VALUES (30000003);

-- 6. CARRITO (sin id_carrito, H2 lo autogenera)
INSERT INTO CARRITO (rut_usuario) VALUES (10000001);

-- 7. PEDIDO (sin id_pedido, H2 lo autogenera)
INSERT INTO PEDIDO (id_sucursal, fec_pedido, precio_pedido, metodo_pago, dir_envio, dir_facturacion, costo_envio, anotaciones, rut_usuario)
VALUES (1, '2024-05-20', 1600, 'CREDITO', 'Av. Siempre Viva 123', 'Av. Siempre Viva 123', 500, 'Entrega rápida', 10000001);

-- 8. DESCUENTO (sin id_descuento, H2 lo autogenera)
INSERT INTO DESCUENTO (tipo_descuento, valor_descuento, fec_ini_descuento, fec_fin_descuento, id_producto)
VALUES ('FIJO', 100, TIMESTAMP '2024-05-01 00:00:00', TIMESTAMP '2024-06-01 00:00:00', 1);
INSERT INTO DESCUENTO (tipo_descuento, valor_descuento, fec_ini_descuento, fec_fin_descuento, id_producto)
VALUES ('PORCENTUAL', 10, TIMESTAMP '2024-05-15 00:00:00', TIMESTAMP '2024-06-15 00:00:00', 2);

-- 9. STOCK (sin id_stock, H2 lo autogenera)
INSERT INTO STOCK (cant_stock, id_producto, id_sucursal) VALUES (100, 1, 1);
INSERT INTO STOCK (cant_stock, id_producto, id_sucursal) VALUES (50, 2, 1);
INSERT INTO STOCK (cant_stock, id_producto, id_sucursal) VALUES (30, 3, 2);

-- 10. HORARIO_TRABAJO (sin id_horario, H2 lo autogenera)
INSERT INTO HORARIO_TRABAJO (dia_semana, horario_apertura, horario_cierre, id_sucursal)
VALUES (1, TIME '09:00:00', TIME '18:00:00', 1);
INSERT INTO HORARIO_TRABAJO (dia_semana, horario_apertura, horario_cierre, id_sucursal)
VALUES (2, TIME '09:00:00', TIME '18:00:00', 1);

-- 11. PRODUCTOS_CARRITO (sin id_producto_carr, H2 lo autogenera)
INSERT INTO PRODUCTOS_CARRITO (id_producto, id_carrito, cant_prod) VALUES (1, 1, 2);
INSERT INTO PRODUCTOS_CARRITO (id_producto, id_carrito, cant_prod) VALUES (2, 1, 1);

-- 12. PRODUCTOS_PEDIDO (sin id_producto_pedido, H2 lo autogenera)
INSERT INTO PRODUCTOS_PEDIDO (cant_prod, precio_unitario, descuento_unitario, precio_total_prod, id_descuento, id_pedido, id_producto)
VALUES (2, 200, 100, 300, 1, 1, 1);
INSERT INTO PRODUCTOS_PEDIDO (cant_prod, precio_unitario, descuento_unitario, precio_total_prod, id_descuento, id_pedido, id_producto)
VALUES (1, 1200, 120, 1080, 2, 1, 2);

-- 13. REABASTECIMIENTO (sin id_reabas, H2 lo autogenera)
INSERT INTO REABASTECIMIENTO (cant_productos, fecha_reabas, estado_reabas, id_sucursal, id_producto, id_proveedor)
VALUES (50, '2024-05-10', 'RECIBIDO', 1, 1, 1);
INSERT INTO REABASTECIMIENTO (cant_productos, fecha_reabas, estado_reabas, id_sucursal, id_producto, id_proveedor)
VALUES (20, '2024-05-15', 'EN ESPERA', 2, 2, 2);

-- 14. ENVIO (sin id_envio, H2 lo autogenera)
INSERT INTO ENVIO (codigo_envio, id_pedido, fecha_envio, fecha_llegada_estim, fecha_llegada_real, transportista, num_seguimiento, metodo_envio)
VALUES (12345, 1, '2024-05-21', '2024-05-23', NULL, 'TransporteX', 10001, 'STANDARD');

-- 15. DEVOLUCION (sin id_devolucion, H2 lo autogenera)
INSERT INTO DEVOLUCION (id_pedido, id_producto_pedido, cantidad_devuelta, motivo_devo, estado_devo, monto_devuelto, restock)
VALUES (1, 1, 1, 'Producto defectuoso', 'SOLICITADA', 200, 1);

