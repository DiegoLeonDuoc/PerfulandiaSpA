package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.StockDTO;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.Stock;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.StockRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    public Stock saveStock(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setCantStock(stockDTO.getCantStock());

        Optional<Producto> producto = productoRepository.findById(stockDTO.getIdProducto());
        Optional<Sucursal> sucursal = sucursalRepository.findById(stockDTO.getIdSucursal());

        if (producto.isPresent() && sucursal.isPresent()) {
            stock.setProducto(producto.get());
            stock.setSucursal(sucursal.get());
            return stockRepository.save(stock);
        } else {
            throw new EntityNotFoundException("Producto o Sucursal no encontrado");
        }
    }

    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockByID(int id) {
        return stockRepository.findById(id);
    }

    public Stock updateStock(Integer id, StockDTO stockDTO) {
        Stock stockExistente = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock no encontrado"));

        stockExistente.setCantStock(stockDTO.getCantStock());

        Optional<Producto> producto = productoRepository.findById(stockDTO.getIdProducto());
        Optional<Sucursal> sucursal = sucursalRepository.findById(stockDTO.getIdSucursal());

        if (producto.isPresent() && sucursal.isPresent()) {
            stockExistente.setId(id);
            stockExistente.setProducto(producto.get());
            stockExistente.setSucursal(sucursal.get());
        } else {
            throw new EntityNotFoundException("Producto o Sucursal no encontrado");
        }

        return stockRepository.save(stockExistente);
    }

    public void deleteStock(int id) {
        stockRepository.deleteById(id);
    }

}
