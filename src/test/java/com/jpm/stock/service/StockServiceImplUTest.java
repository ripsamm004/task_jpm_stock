package com.jpm.stock.service;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.domain.TradeType;
import com.jpm.stock.exception.CustomStockException;
import com.jpm.stock.service.impl.StockServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class StockServiceImplUTest {

  private StockService stockService;
  private Stock stock1;
  private Stock stock2;
  private Stock stock3;

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();


  @Before
  public void init() {
    stockService = new StockServiceImpl();
    stock1 = new Stock("TEST_STOCK_1", StockType.COMMON, 4, 0, 10);
    stock2 = new Stock("TEST_STOCK_2", StockType.PREFERRED, 3, 2, 1);
    stock3 = new Stock("TEST_STOCK_3", StockType.PREFERRED, 3, 2, 1);
  }

  @Test
  public void testSingletonStockServiceObject() {
    StockService stockService1 = StockServiceImpl.getInstance();
    assertEquals(stockService1,StockServiceImpl.getInstance());
  }

  @Test
  public void testGivenStockWhenAddThenAddedSuccessfully() {
    stockService.addStock(stock1);
    Stock stockObj = stockService.getStockBySymbol(stock1.getSymbol());
    assertEquals(stock1, stockObj);
    assertEquals(stock1.getSymbol(), stockObj.getSymbol());
    assertEquals(stock1.getType(), stockObj.getType());
  }

  @Test
  public void testGivenStockSymbolWhenStockNotExistThenThrowException() {
    String unknownStockSymbol = "unknown_stock_symbol";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Stock not found");
    Stock stockObj = stockService.getStockBySymbol(unknownStockSymbol);
  }

  @Test
  public void testGivenCommonStockAndPriceWhenCalculateDividendYieldThenReturnCorrectResult() {
    assertThat(stockService.calculateDividendYield(stock1, 4.5), equalTo(0.89));
    assertThat(stockService.calculateDividendYield(stock1, 2.1), equalTo(1.9));
  }

  @Test
  public void testGivenPreferredStockAndPriceWhenCalculateDividendYieldThenReturnCorrectResult() {
    assertThat(stockService.calculateDividendYield(stock2, 4.5), equalTo(0.44));
  }

  @Test
  public void testGivenStockWhenCalculatePERatioThenReturnCorrectResult() {
    assertThat(stockService.calculatePERatio(stock1, 4.5), equalTo(1.13));
  }

  @Test
  public void testGivenStockWithTradesWhenCalculateVolumeWeightedStockPriceThenReturnCorrectResult() {
    List<Trade> trades = new ArrayList<>();
    trades.add(new Trade(stock1, LocalDateTime.now(), 10, TradeType.BUY, 5));
    trades.add(new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 7));
    trades.add(new Trade(stock1, LocalDateTime.now(), 1, TradeType.BUY, 5));
    assertThat(stockService.calculateVolumeWeightedStockPrice(trades), equalTo(5.17));
  }

  @Test
  public void testGivenTradesWhenCalculateGBCEThenReturnCorrectResult() {
    List<Trade> trades = new ArrayList<>();
    trades.add(new Trade(stock1, LocalDateTime.now(), 10, TradeType.BUY, 4.23));
    trades.add(new Trade(stock2, LocalDateTime.now(), 2, TradeType.BUY, 1.75));
    trades.add(new Trade(stock3, LocalDateTime.now(), 1, TradeType.BUY, 3.25));
    assertThat(stockService.calculateGBCE(trades), equalTo(2.89));
  }

}
