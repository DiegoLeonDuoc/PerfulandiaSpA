package PerfulandiaSpA.Controlador;

import PerfulandiaSpA.Entidades.Stock;
import PerfulandiaSpA.Servicio.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    //CRUD

    //CREATE stock
    @PostMapping()
    public String addStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    //Obtener en formato ToString
    @GetMapping
    public String listarStockes() {
        return stockService.getStocks();
    }

    //obtener en json
    @GetMapping("/json")
    public List<Stock> getStockJSON() {
        return stockService.getStocksJSON();
    }

    //Buscar por id
    @GetMapping("/{id}")
    public String getStockById(@PathVariable int id) {
        return stockService.getStockById(id);
    }

    //Update
    @PutMapping("/{id}")
    public String updateStock(@RequestBody Stock Stock, @PathVariable int id) {
        return stockService.updateStock(Stock, id);
    }

    //deletear horario
    @DeleteMapping("/{id}")
    public String deleteStock(@PathVariable int id) {
        return stockService.deleteStock(id);
    }
}

