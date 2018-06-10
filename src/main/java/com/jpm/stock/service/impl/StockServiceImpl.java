package com.jpm.stock.service.impl;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.exception.CustomStockException;
import com.jpm.stock.persistence.StockRepository;
import com.jpm.stock.persistence.impl.StockRepositoryImpl;
import com.jpm.stock.service.StockService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * This is a singleton design implementation of interface StockService
 */
public class StockServiceImpl implements StockService {

  private static StockServiceImpl instance = null;

  private StockRepository stockRepository = new StockRepositoryImpl();

  public static StockServiceImpl getInstance() {
    if (instance == null) {
      // Will be protect from multi threading not allow to create a new instance
      synchronized (StockServiceImpl.class){
        if (instance == null) {
          instance = new StockServiceImpl();
        }
      }
    }
    return instance;
  }


  /**
   * @{@inheritDoc}
   */
  public void addStock(Stock stock) {
    stockRepository.addStock(stock);
  }

  /**
   * @{@inheritDoc}
   */
  public Stock getStockBySymbol(String symbol) throws CustomStockException{
    return stockRepository.getStockBySymbol(symbol).orElseThrow(() -> new CustomStockException("Stock not found"));
  }

  /**
   * @{@inheritDoc}
   */
  public double calculateDividendYield(Stock stock, double price) {
    double result;
    if (StockType.PREFERRED.equals(stock.getType())) {
      result = (stock.getFixedDividend() * stock.getParValue()) / price;
      return fixedRoundValue(result, 2);
    }
    result = stock.getLastDividend() / price;
    return fixedRoundValue(result, 2);
  }

  /**
   * @{@inheritDoc}
   */
  public double calculatePERatio(Stock stock, double price) {
    double result = price / stock.getLastDividend();
    return fixedRoundValue(result, 2);
  }

  /**
   * @{@inheritDoc}
   */
  public double calculateVolumeWeightedStockPrice(List<Trade> trades) {
    double totalSumOfPriceAndQuantity = 0;
    int totalSumOfQuantity = 0;
    for (Trade trade : trades) {
      totalSumOfPriceAndQuantity = totalSumOfPriceAndQuantity + (trade.getPrice() * trade.getQuantity());
      totalSumOfQuantity = totalSumOfQuantity + trade.getQuantity();
    }
    double result = totalSumOfPriceAndQuantity / totalSumOfQuantity;
    return fixedRoundValue(result, 2);
  }

  /**
   * @{@inheritDoc}
   */
  public double calculateGBCE(List<Trade> trades) {
    double totalPrice = 1;
    for (Trade trade : trades) {
      totalPrice = totalPrice * trade.getPrice();
    }
    double result = Math.pow(totalPrice, (1d / trades.size()));
    return fixedRoundValue(result, 2);
  }

  private static double fixedRoundValue(double value, int places) {
    BigDecimal valueBg = new BigDecimal(value);
    valueBg = valueBg.setScale(places, RoundingMode.HALF_UP);
    return valueBg.doubleValue();
  }

}
