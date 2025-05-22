package PerfulandiaSpA.Repositorio;

import PerfulandiaSpA.Entidades.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {

}
