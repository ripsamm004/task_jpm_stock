package com.jpm.stock.service.impl;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.persistence.TradeRepository;
import com.jpm.stock.persistence.impl.TradeRepositoryImpl;
import com.jpm.stock.service.TradeService;

import java.util.List;

/**
 * This is a singleton design implementation of interface TradeService
 */

public class TradeServiceImpl implements TradeService {

  private static TradeServiceImpl instance = null;

  private TradeRepository tradeRepository = new TradeRepositoryImpl();

  public static TradeServiceImpl getInstance() {
    if (instance == null) {
      // Will be protect from multi threading not allow to create a new instance
      synchronized (TradeServiceImpl.class){
        if (instance == null) {
          instance = new TradeServiceImpl();
        }
      }
    }
    return instance;
  }

  /**
   * @{@inheritDoc}
   */
  public void recordTrade(Trade trade) {
    if (trade != null && trade.getStock() != null) {
      tradeRepository.recordTrade(trade);
    }
  }

  /**
   * @{@inheritDoc}
   */
  public List<Trade> getTradesByStockAndBeforeXMinute(Stock stock, int minute) {
    return tradeRepository.getTradesByStockAndBeforeXMinutes(stock, minute);
  }

  /**
   * @{@inheritDoc}
   */
  public List<Trade> getAllTrades() {
    return tradeRepository.getAllTrades();
  }

}
