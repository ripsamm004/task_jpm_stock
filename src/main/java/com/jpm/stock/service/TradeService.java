package com.jpm.stock.service;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import java.util.List;

/**
 * This service is a interface for trade
 */
public interface TradeService {

  /**
   * Record a Trade
   * @param trade
   */
  void recordTrade(Trade trade);

  /**
   * Get Trade of last X number of minute for a for a Given stock
   * @param stock
   * @param minute
   * @return list of trade
   */
  List<Trade> getTradesByStockAndBeforeXMinute(Stock stock, int minute);

  /**
   * Get all trade of all stock
   * @return
   */
  List<Trade> getAllTrades();

}
