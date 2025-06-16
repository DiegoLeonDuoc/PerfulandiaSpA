package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.ProductoModelAssembler;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Servicio.ProductoService;
import PerfulandiaSpA.Servicio.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@Tag(name="Controlador productos", description="Servicios de gestión de productos")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @Autowired
    ProductoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Producto", description = "Permite registrar una producto en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    public ResponseEntity<EntityModel<Producto>> crearProducto(@RequestBody Producto producto) {
        productoService.saveProducto(producto);
        if (productoService.getProductoByID(producto.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(producto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener productos", description = "Obtiene la lista de productos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de productos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductos(){
        List<Producto> productos = productoService.getProductos();
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(productos), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Obtiene un producto según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Producto"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del producto", example = "2")
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable int id) {
        if (productoService.getProductoByID(id).isPresent()) {
            Producto producto = productoService.getProductoByID(id).get();
            return new ResponseEntity<>(assembler.toModel(producto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Producto", description = "Permite actualizar los datos de un producto según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud", content = @Content),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato", content = @Content)
    })
    @Parameter(description = "El ID del producto", example = "1")
    public ResponseEntity<Producto> updateProducto(@PathVariable int id, @RequestBody Producto producto) {
        if (productoService.getProductoByID(id).isPresent()) {
            productoService.updateProducto(producto, id);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar producto", description = "Elimina un producto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el producto eliminado"),
            @ApiResponse(responseCode = "404", description="Producto no encontrado", content = @Content)
    })
    @Parameter(description = "La ID del producto", example = "1")
    public ResponseEntity<EntityModel<Producto>> eliminarProducto(@PathVariable int id) {
        if (productoService.getProductoByID(id).isPresent()) {
            Producto producto  = productoService.getProductoByID(id).get();
            productoService.deleteProducto(id);
            return new ResponseEntity<>(assembler.toModel(producto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

