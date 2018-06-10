package com.jpm.stock.persistence;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.persistence.impl.StockRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServicePersistenceTest {

  private StockRepository stockRepository;
  private Stock stock;

  @Before
  public void init() {
    stockRepository = new StockRepositoryImpl();
    stock = new Stock("TEST", StockType.COMMON, 1, 0, 1);
  }

  @Test
  public void testWhenNotStockExistThenGetStockReturnEmpty() {
    String unknownStockSymbol = "BITCOIN";
    Optional<Stock> optionalStock = stockRepository.getStockBySymbol(unknownStockSymbol);
    assertFalse(optionalStock.isPresent());
  }

  @Test
  public void testWhenAddStockThenGetStock() {
    stockRepository.addStock(stock);
    Optional<Stock> optionalStock = stockRepository.getStockBySymbol(stock.getSymbol());
    assertEquals(stock, optionalStock.get());
  }

}
