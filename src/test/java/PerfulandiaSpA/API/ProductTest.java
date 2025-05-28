package PerfulandiaSpA.API;

//Fijarse primero en el servicio, logica de negocio y operaciones con datos
//Controlador despues, manejo de peticiones API

import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductTest {


    @Autowired
    ProductoService productoService;

    //crud
    //C
    @Test
    public void testSaveProducto() {
        Producto producto = new Producto();

    }


    //R
    //U
    //D
}
