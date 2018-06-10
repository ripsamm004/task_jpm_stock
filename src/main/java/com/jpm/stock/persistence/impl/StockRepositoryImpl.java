package com.jpm.stock.persistence.impl;


import com.jpm.stock.domain.Stock;
import com.jpm.stock.persistence.StockRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is the implementation of the StockRepository
 */
public class StockRepositoryImpl implements StockRepository {

  private Map<String, Stock> stockMap = new ConcurrentHashMap<String, Stock>();

  /**
   * @{@inheritDoc}
   */
  public void addStock(Stock stock) {
    stockMap.put(stock.getSymbol(), stock);
  }

  /**
   * @{@inheritDoc}
   */
  public Optional<Stock> getStockBySymbol(String symbol) {
    return Optional.ofNullable(stockMap.get(symbol));
  }

}
