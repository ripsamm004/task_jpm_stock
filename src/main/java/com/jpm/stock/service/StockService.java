package com.jpm.stock.service;


import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.exception.CustomStockException;
import java.util.List;

/**
 * This service is a interface for stock
 */

public interface StockService {

  /**
   * add a stock
   * @param stock
   */
  void addStock(Stock stock);

  /**
   * Get Stock for a given StockSymbol
   * @param symbol (StockSymbol)
   * @return Stock
   * @throws CustomStockException
   */
  Stock getStockBySymbol(String symbol) throws CustomStockException;

  /**
   * Calculate DividendYield for a given stock and price
   * @param stock
   * @param price
   * @return return result of DividendYield in decimal
   */
  double calculateDividendYield(Stock stock, double price);

  /**
   * Calculate PERatio for a given stock and price
   * @param stock
   * @param price
   * @return return result of PERatio in decimal
   */
  double calculatePERatio(Stock stock, double price);

  /**
   * Calculate VolumeWeightedStockPrice for a given stock and price
   * @param trades
   * @return return result of VolumeWeightedStockPrice in decimal
   */
  double calculateVolumeWeightedStockPrice(List<Trade> trades);

  /**
   * Calculate GBCE all Trade geometric mean of prices for all stocks
   * @param trades
   * @return return result of GBCE in decimal
   */
  double calculateGBCE(List<Trade> trades);

}
