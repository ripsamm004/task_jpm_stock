package com.jpm.stock.persistence;

import com.jpm.stock.domain.Stock;
import java.util.Optional;

/**
 * This interface is for stock data repository implementation.
 */
public interface StockRepository {

  /**
   * Add new stock to the data repository.
   * @param stock
   */
  void addStock(Stock stock);

  /**
   * Get the Stock by stock symbol.
   * @param symbol
   * @return an Optional of Stock
   */
  Optional<Stock> getStockBySymbol(String symbol);

}
