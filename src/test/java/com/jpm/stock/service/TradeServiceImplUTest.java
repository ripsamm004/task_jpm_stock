package com.jpm.stock.service;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.domain.TradeType;
import com.jpm.stock.service.impl.TradeServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class TradeServiceImplUTest {

  private TradeService tradeService;


  @Before
  public void init() {
    tradeService = new TradeServiceImpl();
  }

  @Test
  public void testSingletonStockServiceObject() {
    TradeService tradeService1 = TradeServiceImpl.getInstance();
    assertEquals(tradeService1, TradeServiceImpl.getInstance());
  }

  @Test
  public void testGivenTradeWhenRecordTradeThenTradeAddSuccess() {
    Stock stock1 = new Stock("TEST1", StockType.COMMON, 1, 0, 1);
    Trade trade = new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 1.0);
    tradeService.recordTrade(trade);
    List<Trade> result = tradeService.getTradesByStockAndBeforeXMinute(stock1, 15);
    assertEquals(1, result.size());
  }

  @Test
  public void testGivenStockWhenGetAllTradesInLast15MinutesThenReturnTradeList() {
    Stock stock2 = new Stock("TEST2", StockType.COMMON, 1, 0, 1);
    Trade trade1 = new Trade(stock2, LocalDateTime.now().minusMinutes(15), 10, TradeType.SELL, 2.0);
    tradeService.recordTrade(trade1);

    LocalDateTime localDateTime = LocalDateTime.now();
    Trade trade2 = new Trade(stock2, localDateTime, 1, TradeType.BUY, 1.0);
    tradeService.recordTrade(trade2);

    List<Trade> trades = tradeService.getTradesByStockAndBeforeXMinute(stock2, 15);
    assertEquals(1, trades.size());
    assertEquals(localDateTime, trades.get(0).getTimestamp());
  }

  @Test
  public void testGivenTradesWhenGetAllTradesThenReturnAllTrades() {
    Stock stock3 = new Stock("TEST3", StockType.COMMON, 1, 0, 1);
    tradeService.recordTrade(new Trade(stock3, LocalDateTime.now(), 1, TradeType.BUY, 1.0));
    tradeService.recordTrade(new Trade(stock3, LocalDateTime.now(), 1, TradeType.BUY, 1.0));
    tradeService.recordTrade(new Trade(stock3, LocalDateTime.now(), 1, TradeType.BUY, 1.0));
    List<Trade> result = tradeService.getAllTrades();
    List<Trade> expectedResult =result.stream().filter(t -> t.getStock().equals(stock3)).collect(Collectors.toList());
    assertEquals(3, expectedResult.size());
  }

}
