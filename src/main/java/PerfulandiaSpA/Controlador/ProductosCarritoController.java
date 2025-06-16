package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.ProductosCarritoModelAssembler;
import PerfulandiaSpA.DTO.ProductosCarritoDTO;
import PerfulandiaSpA.Entidades.ProductosCarrito;
import PerfulandiaSpA.Servicio.ProductosCarritoService;
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
import java.util.Optional;

@RestController
@RequestMapping("/productosCarritos")
@Tag(name="Controlador ProductosCarrito", description="Servicios de gestión de productos en carritos")
public class ProductosCarritoController {

    @Autowired
    ProductosCarritoService productosCarritoService;

    @Autowired
    ProductosCarritoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar ProductosCarrito", description = "Permite registrar un productosCarrito en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ProductosCarrito creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductosCarrito.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<ProductosCarrito>> crearProductosCarrito(@RequestBody ProductosCarritoDTO productosCarritoDTO) {
        ProductosCarrito productosCarrito = productosCarritoService.crearProductosCarrito(productosCarritoDTO);
        if (productosCarritoService.getProductoCarritoByID(productosCarrito.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(productosCarrito), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener productos en carritos", description = "Obtiene la lista de productos en carritos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de productos en carritos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<ProductosCarrito>>> getProductosCarritos(){
        List<ProductosCarrito> productosCarritos = productosCarritoService.getProductosCarritos();
        if (productosCarritos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(productosCarritos), HttpStatus.OK);
        }
    }

//    @GetMapping("/{rut}")
//    @Operation(summary = "Buscar productos en carritos por cliente", description = "Obtiene un producto en carrito según la ID registrada en el sistema")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Retorna producto en carrito"),
//            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
//    })
//    @Parameter(description = "RUT del cliente SIN dígito verificador", example = "2")
//    public ResponseEntity<CollectionModel<EntityModel<ProductosCarrito>>> getProductosCarritoByRut(@PathVariable int rut) {
//        List<ProductosCarrito> productosCarritos = productosCarritoService.getProductosCarritosByRut(rut);
//        if (productosCarritos.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(assembler.toCollectionModel(productosCarritos), HttpStatus.OK);
//        }
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto en carrito por ID", description = "Obtiene un producto en carrito según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna producto en carrito"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del reabastecimiento", example = "2")
    public ResponseEntity<EntityModel<ProductosCarrito>> getProductoCarritoByID(@PathVariable int id) {
        Optional<ProductosCarrito> productosCarrito = productosCarritoService.getProductoCarritoByID(id);
        if (productosCarrito.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(productosCarrito.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto en carrito", description = "Permite actualizar los datos de un producto en carrito según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto en carrito modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductosCarrito.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del producto en carrito", example = "1")
    public ResponseEntity<ProductosCarrito> updateProductosCarrito(@PathVariable int id, @RequestBody ProductosCarritoDTO productosCarritoDTO) {
        Optional<ProductosCarrito> productosCarritoOptional = productosCarritoService.getProductoCarritoByID(id);
        if (productosCarritoOptional.isPresent()) {
            ProductosCarrito productosCarrito = productosCarritoService.updateProductosCarrito(productosCarritoDTO, id);
            return new ResponseEntity<>(productosCarrito, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Parchar producto en carrito", description = "Permite actualizar parcialmente los datos de un producto en carrito según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto en carrito modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductosCarrito.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del producto en carrito", example = "1")
    public ResponseEntity<ProductosCarrito> patchProductosCarrito(@PathVariable int id, @RequestBody ProductosCarritoDTO productosCarritoDTO) {
        Optional<ProductosCarrito> productosCarritoOptional = productosCarritoService.getProductoCarritoByID(id);
        if (productosCarritoOptional.isPresent()) {
            ProductosCarrito productosCarrito = productosCarritoService.patchProductosCarrito(productosCarritoDTO, id);
            return new ResponseEntity<>(productosCarrito, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar producto en carrito", description = "Elimina un producto en carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el producto en carrito eliminado"),
            @ApiResponse(responseCode = "404", description="Producto en carrito no encontrado")
    })
    @Parameter(description = "La ID del producto en carrito", example = "1")
    public ResponseEntity<EntityModel<ProductosCarrito>> eliminarProductosCarrito(@PathVariable int id) {
        Optional<ProductosCarrito> productosCarritoOptional = productosCarritoService.getProductoCarritoByID(id);
        if (productosCarritoOptional.isPresent()) {
            ProductosCarrito productosCarrito = productosCarritoOptional.get();
            productosCarritoService.deleteProductosCarrito(id);
            return new ResponseEntity<>(assembler.toModel(productosCarrito), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}