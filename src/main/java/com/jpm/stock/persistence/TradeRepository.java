package com.jpm.stock.persistence;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;

import java.util.List;

/**
 * This interface is for trade data repository implementation.
 */
public interface TradeRepository {

  /**
   * add new trade or update the  existing trade
   * @param trade
   */
  void recordTrade(Trade trade);

  /**
   * Get all trade for the given stock and for the last X number of minute
   * @param stock
   * @param minutes
   * @return the list of trades belongs to the given stock
   */
  List<Trade> getTradesByStockAndBeforeXMinutes(Stock stock, int minutes);

  /**
   * Get all trades for all stock from the repository of trade
   * @return the list of trade
   */
  List<Trade> getAllTrades();

}
