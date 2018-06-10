package com.jpm.stock.persistence.impl;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.persistence.TradeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This is the implementation of the TradeRepository
 */

public class TradeRepositoryImpl implements TradeRepository {

  private Map<String, List<Trade>> tradeMap = new ConcurrentHashMap<String, List<Trade>>();

  /**
   * @{@inheritDoc}
   */
  public void recordTrade(Trade trade) {
    List<Trade> trades = new ArrayList<Trade>();
    trades.add(trade);
    trades = tradeMap.putIfAbsent(trade.getStock().getSymbol(), trades);
    //If stock already exist we just update the list of trade for that stock
    if(trades!=null) trades.add(trade);
  }

  /**
   * @{@inheritDoc}
   */
  public List<Trade> getTradesByStockAndBeforeXMinutes(Stock stock, int minutes) {
    LocalDateTime afterDate = LocalDateTime.now().minusMinutes(minutes);
    List<Trade> trades = tradeMap.get(stock.getSymbol());
    if(trades == null) return null;
    return trades.stream().filter(curr -> curr.getTimestamp().isAfter(afterDate)).collect(Collectors.toList());
  }

  /**
   * @{@inheritDoc}
   */

  public List<Trade> getAllTrades() {
    List<Trade> result = new ArrayList<Trade>();
    for (String stockSymbol: tradeMap.keySet()) {
      result.addAll(tradeMap.get(stockSymbol));
    }
    return result;
  }

}
