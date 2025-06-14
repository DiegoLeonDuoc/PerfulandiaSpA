package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Assembler.StockModelAssembler;
import PerfulandiaSpA.DTO.StockDTO;
import PerfulandiaSpA.Entidades.Stock;
import PerfulandiaSpA.Servicio.StockService;
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
@RequestMapping("/stock")
@Tag(name="Controlador Stock", description="Servicios de gestión de stock")
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    StockModelAssembler assembler;

    // C
    @PostMapping
    @Operation(summary = "Agregar Stock", description = "Permite registrar un stock en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock creado",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Stock.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    public ResponseEntity<EntityModel<Stock>> crearStock(@RequestBody StockDTO stockDTO) {
        Stock stock = stockService.saveStock(stockDTO);
        if (stockService.getStockByID(stock.getId()).isPresent()) {
            return new ResponseEntity<>(assembler.toModel(stock), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // R
    @GetMapping
    @Operation(summary= "Obtener stocks", description = "Obtiene la lista de stocks registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista completa de stocks"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    public ResponseEntity<CollectionModel<EntityModel<Stock>>> getStocks(){
        List<Stock> stocks = stockService.getStocks();
        if (stocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(assembler.toCollectionModel(stocks), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar stock por ID", description = "Obtiene un stock según la ID registrada en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna Stock"),
            @ApiResponse(responseCode = "404", description = "No se encuentran datos", content = @Content)
    })
    @Parameter(description = "ID del stock", example = "2")
    public ResponseEntity<EntityModel<Stock>> getStockById(@PathVariable int id) {
        Optional<Stock> stockOptional = stockService.getStockByID(id);
        if (stockOptional.isPresent()) {
            return new ResponseEntity<>(assembler.toModel(stockOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // U
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Stock", description = "Permite actualizar los datos de un stock según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock modificado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Stock.class))),
            @ApiResponse(responseCode = "204", description = "No hay contenido en la solicitud"),
            @ApiResponse(responseCode = "400", description = "JSON con mal formato")
    })
    @Parameter(description = "El ID del stock", example = "1")
    public ResponseEntity<Stock> updateStock(@PathVariable int id, @RequestBody StockDTO stockDTO) {
        Optional<Stock> stockOptional = stockService.getStockByID(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockService.updateStock(id, stockDTO);
            return new ResponseEntity<>(stock, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar stock", description = "Elimina un stock específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Retorna el stock eliminado"),
            @ApiResponse(responseCode = "404", description="Stock no encontrado")
    })
    @Parameter(description = "La ID del stock", example = "1")
    public ResponseEntity<EntityModel<Stock>> eliminarStock(@PathVariable int id) {
        Optional<Stock> stockOptional = stockService.getStockByID(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stockService.deleteStock(id);
            return new ResponseEntity<>(assembler.toModel(stock), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

