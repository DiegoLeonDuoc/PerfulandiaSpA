package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.PedidoModelAssembler;
import PerfulandiaSpA.DTO.PedidoDTO;
import PerfulandiaSpA.Entidades.Pedido;
import PerfulandiaSpA.Servicio.PedidoService;
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
@RequestMapping("/pedidos")
@Tag(name="Controlador Pedido", description="Servicios de gestión de productos en pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    PedidoModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Pedido", description = "Permite registrar un pedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Pedido>> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.crearPedido(pedidoDTO);
        if (pedidoService.getPedidoByID(pedido.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(pedido), HttpStatus.CREATED);
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
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidos(){
        List<Pedido> pedidos = pedidoService.getPedidos();
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(pedidos), HttpStatus.OK);
        }
    }

//    @GetMapping("/{rut}")
//    @Operation(summary = "Buscar productos en pedidos por cliente", description = "Obtiene un producto en pedido según la ID registrada en el sistema")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Retorna producto en pedido"),
//            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
//    })
//    @Parameter(description = "RUT del cliente SIN dígito verificador", example = "2")
//    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getPedidoByRut(@PathVariable int rut) {
//        List<Pedido> pedidos = pedidoService.getPedidosByRut(rut);
//        if (pedidos.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(assembler.toCollectionModel(pedidos), HttpStatus.OK);
//        }
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto en pedido por ID", description = "Obtiene un producto en pedido según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna producto en pedido"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del reabastecimiento", example = "2")
    public ResponseEntity<EntityModel<Pedido>> getPedidoByID(@PathVariable int id) {
        Optional<Pedido> pedido = pedidoService.getPedidoByID(id);
        if (pedido.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(pedido.get()), HttpStatus.OK);
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
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del producto en pedido", example = "1")
    public ResponseEntity<Pedido> updatePedido(@PathVariable int id, @RequestBody PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoOptional = pedidoService.getPedidoByID(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoService.updatePedido(pedidoDTO, id);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Parchar producto en pedido", description = "Permite actualizar parcialmente los datos de un producto en pedido según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto en pedido modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del producto en pedido", example = "1")
    public ResponseEntity<Pedido> patchPedido(@PathVariable int id, @RequestBody PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoOptional = pedidoService.getPedidoByID(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoService.patchPedido(pedidoDTO, id);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
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
    public ResponseEntity<EntityModel<Pedido>> eliminarPedido(@PathVariable int id) {
        Optional<Pedido> pedidoOptional = pedidoService.getPedidoByID(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedidoService.deletePedido(id);
            return new ResponseEntity<>(assembler.toModel(pedido), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}