package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Stock;
import PerfulandiaSpA.Servicio.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@Tag(name="Servicio Stock", description="Servicios de gestión de stock")
public class StockController {

    @Autowired
    private StockService stockService;

    //CRUD

    //CREATE stock
    @PostMapping
    @Operation(summary= "Crear stock", description = "Servicio POST para crear stock")
    @ApiResponse(responseCode = "200", description="Confirmación sobre la creación del stock")
    public String addStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    //Obtener en formato ToString
    @GetMapping
    @Operation(summary= "Obtener stocks", description = "Servicio GET para obtener información sobre los stocks en formato String")
    @ApiResponse(responseCode = "200", description="Registro de stocks en formato texto simple")
    public String listarStocks() {
        return stockService.getStocks();
    }

    //obtener en json
    @GetMapping("/json")
    @Operation(summary= "Obtener stocks JSON", description = "Servicio GET para obtener información sobre los stocks en formato JSON")
    @ApiResponse(responseCode = "200", description="Registro de stocks en formato JSON")
    public List<Stock> getStockJSON() {
        return stockService.getStocksJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    @Operation(summary= "Obtener stocks por ID", description = "Servicio GET para obtener información sobre un stock específico en formato String")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Registro del stock en formato texto simple o información sobre inexistencia del stock"),
            //@ApiResponse(responseCode = "404", description="Stock no encontrado")
    })
    public String getStockById(@PathVariable int id) {
        return stockService.getStockById(id);
    }

    //Update
    @PutMapping("/{id}")
    @Operation(summary= "Modificar stock", description = "Servicio PUT para modificar información sobre un stock específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de modificación exitosa o información sobre inexistencia del stock"),
            //@ApiResponse(responseCode = "404", description="Stock no encontrado")
    })
    public String updateStock(@RequestBody Stock Stock, @PathVariable int id) {
        return stockService.updateStock(Stock, id);
    }

    //deletear stock
    @DeleteMapping("/{id}")
    @Operation(summary= "Eliminar stock", description = "Servicio DELETE para eliminar registro de un stock en específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Confirmación de eliminación exitosa o información sobre inexistencia del stock"),
            //@ApiResponse(responseCode = "404", description="Stock no encontrado")
    })
    public String deleteStock(@PathVariable int id) {
        return stockService.deleteStock(id);
    }
}

