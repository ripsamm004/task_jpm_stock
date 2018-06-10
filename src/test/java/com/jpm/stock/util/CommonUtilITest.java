package com.jpm.stock.util;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.TradeType;
import com.jpm.stock.exception.CustomStockException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class CommonUtilITest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @BeforeClass
  public static void oneTimeSetUp() {
    // one-time initialization code
    LocalDateTime dateTime = LocalDateTime.now();
    String inputDateTime = dateTime.format(formatter);

    // Trade 1
    Stock inputStock1  = CommonUtil.validateStockBySymbol("TEA");
    LocalDateTime localDateTime1 = CommonUtil.validateDateTimestamp(inputDateTime);
    int quantityFromUser1 = CommonUtil.validateQuantity("10");
    TradeType tradeTypeFromUser1 = CommonUtil.validateTradeType("BUY");
    Double inputPrice1 = CommonUtil.validateStockPrice("5");
    String response1 = CommonUtil.recordTrade(inputStock1, localDateTime1, quantityFromUser1, tradeTypeFromUser1, inputPrice1);

    // Trade 2
    Stock inputStock2  = CommonUtil.validateStockBySymbol("TEA");
    LocalDateTime localDateTime2 = CommonUtil.validateDateTimestamp(inputDateTime);
    int quantityFromUser2 = CommonUtil.validateQuantity("1");
    TradeType tradeTypeFromUser2 = CommonUtil.validateTradeType("BUY");
    Double inputPrice2 = CommonUtil.validateStockPrice("7");
    String response2 = CommonUtil.recordTrade(inputStock2, localDateTime2, quantityFromUser2, tradeTypeFromUser2, inputPrice2);

    // Trade 3
    Stock inputStock3  = CommonUtil.validateStockBySymbol("TEA");
    LocalDateTime localDateTime3 = CommonUtil.validateDateTimestamp(inputDateTime);
    int quantityFromUser3 = CommonUtil.validateQuantity("1");
    TradeType tradeTypeFromUser3 = CommonUtil.validateTradeType("BUY");
    Double inputPrice3 = CommonUtil.validateStockPrice("5");
    String response3 = CommonUtil.recordTrade(inputStock3, localDateTime3, quantityFromUser3, tradeTypeFromUser3, inputPrice3);
  }


  /**
   * Valid date :
   * Not Advance date than current DateTime
   */

  @Test
  public void testGivenAdvanceDateTimeWhenValidateThenThrowException() {
    String dateTime = LocalDateTime.now().plusMinutes(1).format(formatter);
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid Date [Date can not be after current time]");
    CommonUtil.validateDateTimestamp(dateTime);
  }

  /**
   * Valid date :
   * Input datetime should be in format yyyy-MM-dd HH:mm:ss
   */

  @Test
  public void testGivenInvalidFormatDateTimeWhenValidateThenThrowException() {
    DateTimeFormatter invalidFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
    String dateTime = LocalDateTime.now().format(invalidFormatter);
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid date format");
    CommonUtil.validateDateTimestamp(dateTime);
  }

  @Test
  public void testGivenValidDateTimeWhenValidateThenReturnLocalDateTime() {
    LocalDateTime dateTime = LocalDateTime.now();
    String dateTimeString = dateTime.format(formatter);
    LocalDateTime localDateTime = CommonUtil.validateDateTimestamp(dateTimeString);
    assertEquals(localDateTime.getYear(), dateTime.getYear());
    assertEquals(localDateTime.getMonth(), dateTime.getMonth());
    assertEquals(localDateTime.getDayOfMonth(), dateTime.getDayOfMonth());
    assertEquals(localDateTime.getHour(), dateTime.getHour());
    assertEquals(localDateTime.getMinute(), dateTime.getMinute());
  }

  @Test
  public void testGivenInvalidQuantityWhenValidateThenThrowException() {
    String quantity = "20T";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid quantity: Not a number");
    CommonUtil.validateQuantity(quantity);
  }

  @Test
  public void testGivenZeroQuantityWhenValidateThenThrowException() {
    String quantity = "0";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid quantity [ Must be grater than 0 ]");
    CommonUtil.validateQuantity(quantity);
  }

  @Test
  public void testGivenInvalidTradeTypeWhenValidateThenThrowException() {
    String tradeType = "RENT";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid trade type [ Must BUY or SELL ]");
    CommonUtil.validateTradeType(tradeType);
  }

  @Test
  public void testGivenTradeTypeWhenValidateThenReturnTradeType() {
    String tradeType = "SELL";
    TradeType result = CommonUtil.validateTradeType(tradeType);
    assertEquals(result, TradeType.SELL);
  }

  @Test
  public void testGivenInvalidStockPriceWhenValidateThenThrowException() {
    String tradeType = "2.0T";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid price");
    CommonUtil.validateStockPrice(tradeType);
  }

  @Test
  public void testGivenZeroStockPriceWhenValidateThenThrowException() {
    String tradeType = "0.0";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Invalid price < 0 !");
    CommonUtil.validateStockPrice(tradeType);
  }

  @Test
  public void testGivenValidStockPriceWhenValidateThenReturnDoubleValue() {
    String tradeType = "2.0";
    double price = CommonUtil.validateStockPrice(tradeType);
    assertThat(price, equalTo(2.0));
  }

  @Test
  public void testGivenStockSymbolIfNotExistWhenValidateThenThrowException() {
    String unknownStockSymbol = "unknown_stock_symbol";
    expectedEx.expect(CustomStockException.class);
    expectedEx.expectMessage("Stock not found");
    CommonUtil.validateStockBySymbol(unknownStockSymbol);
  }

  @Test
  public void testGivenStockSymbolIfExistWhenValidateThenReturnStock() {
    String knownStockSymbol = "TEA";
    Stock result = CommonUtil.validateStockBySymbol(knownStockSymbol);
    assertEquals(result.getSymbol(),"TEA");
    assertEquals(result.getType(),StockType.COMMON);
    assertThat(result.getFixedDividend(), equalTo(0.0));
    assertThat(result.getLastDividend(), equalTo(0.0));
    assertThat(result.getParValue(), equalTo(100.0));
  }


  @Test
  public void testGivenCommonStockAndPriceWhenCalculateDividendYieldThenReturnCorrectResult() {

    Stock inputStock1 = CommonUtil.validateStockBySymbol("TEA");
    Double inputPrice1 = CommonUtil.validateStockPrice("4.5");
    String response1 = CommonUtil.calculateDividendYield(inputStock1, inputPrice1);

    Stock inputStock2 = CommonUtil.validateStockBySymbol("POP");
    Double inputPrice2 = CommonUtil.validateStockPrice("4.5");
    String response2 = CommonUtil.calculateDividendYield(inputStock2, inputPrice2);

    assertEquals("Dividend Yield: 0.0", response1);
    assertEquals("Dividend Yield: 1.78", response2);
  }

  @Test
  public void testGivenPreferredStockAndPriceWhenCalculateDividendYieldThenReturnCorrectResult() {
    Stock inputStock1 = CommonUtil.validateStockBySymbol("GIN");
    Double inputPrice1 = CommonUtil.validateStockPrice("4.5");
    String response1 = CommonUtil.calculateDividendYield(inputStock1, inputPrice1);
    assertEquals("Dividend Yield: 44.44", response1);
  }

  @Test
  public void testGivenStockWhenCalculatePERatioThenReturnCorrectResult() {
    Stock inputStock1 = CommonUtil.validateStockBySymbol("GIN");
    Double inputPrice1 = CommonUtil.validateStockPrice("4.5");
    assertEquals("PE Ratio: 0.56", CommonUtil.calculatePERatio(inputStock1, inputPrice1));
  }

  @Test
  public void testGivenStockWithTradesWhenCalculateVolumeWeightedStockPriceThenReturnCorrectResult() {

    Stock stock  = CommonUtil.validateStockBySymbol("TEA");
    String response = CommonUtil.calculateVolumeWeightedStockPrice(stock);
    assertEquals("Volume Weighted Stock Price: 5.17", response);

  }

  @Test
  public void testGivenTradesWhenCalculateGBCEThenReturnCorrectResult() {
    String response = CommonUtil.calculateGBCE();
    assertEquals("GBCE: 5.59", response);
  }

}
