package PerfulandiaSpA.Assembler;

import PerfulandiaSpA.Controlador.StockController;
import PerfulandiaSpA.Entidades.Stock;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StockModelAssembler implements RepresentationModelAssembler<Stock, EntityModel<Stock>> {

    @Override
    public EntityModel<Stock> toModel(Stock stock) {
        return EntityModel.of(stock,
                linkTo(methodOn(StockController.class).getStockById(stock.getId())).withSelfRel(),
                linkTo(methodOn(StockController.class).getStocks()).withRel("stocks"),
                linkTo(methodOn(StockController.class).updateStock(stock.getId(), null)).withRel("PUT"),
                linkTo(methodOn(StockController.class).eliminarStock(stock.getId())).withRel("DELETE")
        );
    }
}
