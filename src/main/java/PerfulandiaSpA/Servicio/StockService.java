package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Stock;

import PerfulandiaSpA.Repositorio.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;
    // MÉTODOS CRUD

    //CREATE
    public String saveStock(Stock stock) {
        if (stock.getId() == null) {
            stockRepository.save(stock); // Guarda el stock en la BD
            return "Stock agregado con éxito";
        } else {
            if (stockRepository.existsById(stock.getId())){
                return "Stock ya existe";
            } else { // Si no existe el stock, se crea
                stockRepository.save(stock);
                return "Stock agregado con éxito";
            }
        }
    }

    //READ
    // Retorna una lista de stockes en formato toString
    public String getStocks() {
        String output = "";
        for (Stock stock : stockRepository.findAll()) {
            output = datosStock(output, stock); // Acumulando datos de cada stock
        }

        if (output.isEmpty()) {
            return "No hay stocks registrados";
        } else {
            return output; // retorna lista completa
        }
    }

    // MÉTODO READ (LISTAR TODOS EN FORMATO JSON)
    public List<Stock> getStocksJSON() {
        return stockRepository.findAll();
    }

    // MÉTODO READ (BUSCAR POR ID)
    public String getStockById(int id) {
        if (stockRepository.existsById(id)) {
            Stock stock = stockRepository.findById(id).get();
            return datosStock("", stock);
        }
        return "Stock no encontrado";
    }

    // MÉTODO toString
    private String datosStock(String output, Stock stock) {
        // Si getIdProducto() y getIdSucursal() devuelven entidades:
        Integer idProducto = stock.getIdProducto() != null ? stock.getIdProducto().getId() : null;
        Integer idSucursal = stock.getIdSucursal() != null ? stock.getIdSucursal().getId() : null;

        output += "ID Stock: " + stock.getId() + "\n";
        output += "Cantidad en Stock: " + stock.getCantStock() + "\n";
        output += "ID Producto: " + (idProducto != null ? idProducto : "No asignado") + "\n";
        output += "ID Sucursal: " + (idSucursal != null ? idSucursal : "No asignada") + "\n";
        output += "\n";
        return output;
    }


    //UPDATE
    public String updateStock(Stock stock, int id) {
        if (stockRepository.existsById(id)) {
            stock.setId(id);
            stockRepository.save(stock);
            return "Stock actualizado con éxito";
        }
        return "Stock no encontrado";
    }



    //DELETE
    public String deleteStock(int id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return "Stock eliminado con éxito";
        }
        return "Stock no encontrado";
    }

}
