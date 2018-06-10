package com.jpm.stock.persistence;


import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.domain.TradeType;
import com.jpm.stock.persistence.impl.TradeRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TradeRepositoryUTest {

  private TradeRepository tradeRepository = null;
  private Stock stock1;
  private Stock stock2;
  private Stock stock3;

  @Before
  public void inti() {
    tradeRepository = new TradeRepositoryImpl();
    stock1 = new Stock("stock1", StockType.COMMON, 1, 0, 150);
    stock2 = new Stock("stock2", StockType.PREFERRED, 1, 0, 100);
    stock3 = new Stock("stock3", StockType.COMMON, 1, 0, 250);
  }

  @Test
  public void testGivenTradeWhenAddThenAddedTradeSuccessfully() {
    Trade trade = new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 5.0);
    tradeRepository.recordTrade(trade);
    assertEquals(1, tradeRepository.getAllTrades().size());
  }

  @Test
  public void testGivenAnExistStockTradeWhenAddThenUpdateTradeListSuccessfully() {
    Trade trade1 = new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 5.0);
    tradeRepository.recordTrade(trade1);
    Trade trade2 = new Trade(stock1, LocalDateTime.now(), 2, TradeType.BUY, 5.0);
    tradeRepository.recordTrade(trade2);
    assertEquals(2, tradeRepository.getAllTrades().size());
  }

  @Test
  public void testGivenTradesWhenGetTradeLessThan15MinuteShouldReturnOnlyTradesAfter15Minute() {
    Trade trade1 = new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 10.0);
    tradeRepository.recordTrade(trade1);

    Trade trade2 = new Trade(stock1, LocalDateTime.now(), 2, TradeType.BUY, 12.0);
    tradeRepository.recordTrade(trade2);

    Trade trade3 = new Trade(stock1, LocalDateTime.now().minusMinutes(15), 3, TradeType.BUY, 13.0);
    tradeRepository.recordTrade(trade3);

    List<Trade> trades = tradeRepository.getTradesByStockAndBeforeXMinutes(stock1, 15);
    assertEquals(2, trades.size());
  }

  @Test
  public void testGivenGetAllTrades() {

    Trade trade = new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 4.0);
    tradeRepository.recordTrade(trade);

    Trade trade2 = new Trade(stock2, LocalDateTime.now(), 1, TradeType.BUY, 2.0);
    tradeRepository.recordTrade(trade2);

    Trade trade3 = new Trade(stock3, LocalDateTime.now(), 1, TradeType.BUY, 1.0);
    tradeRepository.recordTrade(trade3);

    assertEquals(3, tradeRepository.getAllTrades().size());
  }

}
