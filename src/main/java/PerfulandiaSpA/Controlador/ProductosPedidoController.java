package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.ProductosPedidoModelAssembler;
import PerfulandiaSpA.DTO.ProductosPedidoDTO;
import PerfulandiaSpA.Entidades.ProductosPedido;
import PerfulandiaSpA.Servicio.ProductosPedidoService;
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
@RequestMapping("/productosPedidos")
@Tag(name="Controlador ProductosPedido", description="Servicios de gestión de productos en pedidos")
public class ProductosPedidoController {

    @Autowired
    ProductosPedidoService productosPedidoService;

    @Autowired
    ProductosPedidoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar ProductosPedido", description = "Permite registrar un productosPedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ProductosPedido creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductosPedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<ProductosPedido>> crearProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) {
        ProductosPedido productosPedido = productosPedidoService.crearProductosPedido(productosPedidoDTO);
        if (productosPedidoService.getProductoPedidoByID(productosPedido.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(productosPedido), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener productos en pedidos", description = "Obtiene la lista de productos en pedidos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de productos en pedidos"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<ProductosPedido>>> getProductosPedidos(){
        List<ProductosPedido> productosPedidos = productosPedidoService.getProductosPedidos();
        if (productosPedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(productosPedidos), HttpStatus.OK);
        }
    }

//    @GetMapping("/{rut}")
//    @Operation(summary = "Buscar productos en pedidos por cliente", description = "Obtiene un producto en pedido según la ID registrada en el sistema")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Retorna producto en pedido"),
//            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
//    })
//    @Parameter(description = "RUT del cliente SIN dígito verificador", example = "2")
//    public ResponseEntity<CollectionModel<EntityModel<ProductosPedido>>> getProductosPedidoByRut(@PathVariable int rut) {
//        List<ProductosPedido> productosPedidos = productosPedidoService.getProductosPedidosByRut(rut);
//        if (productosPedidos.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(assembler.toCollectionModel(productosPedidos), HttpStatus.OK);
//        }
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto en pedido por ID", description = "Obtiene un producto en pedido según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna producto en pedido"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del reabastecimiento", example = "2")
    public ResponseEntity<EntityModel<ProductosPedido>> getProductoPedidoByID(@PathVariable int id) {
        Optional<ProductosPedido> productosPedido = productosPedidoService.getProductoPedidoByID(id);
        if (productosPedido.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(productosPedido.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto en pedido", description = "Permite actualizar los datos de un producto en pedido según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto en pedido modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductosPedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del producto en pedido", example = "1")
    public ResponseEntity<ProductosPedido> updateProductosPedido(@PathVariable int id, @RequestBody ProductosPedidoDTO productosPedidoDTO) {
        Optional<ProductosPedido> productosPedidoOptional = productosPedidoService.getProductoPedidoByID(id);
        if (productosPedidoOptional.isPresent()) {
            ProductosPedido productosPedido = productosPedidoService.updateProductosPedido(productosPedidoDTO, id);
            return new ResponseEntity<>(productosPedido, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Parchar producto en pedido", description = "Permite actualizar parcialmente los datos de un producto en pedido según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto en pedido modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductosPedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del producto en pedido", example = "1")
    public ResponseEntity<ProductosPedido> patchProductosPedido(@PathVariable int id, @RequestBody ProductosPedidoDTO productosPedidoDTO) {
        Optional<ProductosPedido> productosPedidoOptional = productosPedidoService.getProductoPedidoByID(id);
        if (productosPedidoOptional.isPresent()) {
            ProductosPedido productosPedido = productosPedidoService.patchProductosPedido(productosPedidoDTO, id);
            return new ResponseEntity<>(productosPedido, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar producto en pedido", description = "Elimina un producto en pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el producto en pedido eliminado"),
            @ApiResponse(responseCode = "404", description="Producto en pedido no encontrado")
    })
    @Parameter(description = "La ID del producto en pedido", example = "1")
    public ResponseEntity<EntityModel<ProductosPedido>> eliminarProductosPedido(@PathVariable int id) {
        Optional<ProductosPedido> productosPedidoOptional = productosPedidoService.getProductoPedidoByID(id);
        if (productosPedidoOptional.isPresent()) {
            ProductosPedido productosPedido = productosPedidoOptional.get();
            productosPedidoService.deleteProductosPedido(id);
            return new ResponseEntity<>(assembler.toModel(productosPedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}