package com.jpm.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represent a Stock. Stock can have a
 * symbol (is use to uniquely identify a stock object)
 * type (type of stock, It can be StockType.PREFERRED or StockType.COMMON)
 * lastDividend (a decimal value)
 * fixedDividend (a decimal value)
 * parValue (a decimal value)
 */
@AllArgsConstructor
@Getter
@ToString
@lombok.EqualsAndHashCode(of = {"symbol"})
public class Stock {

  private String symbol;

  private StockType type;

  private double lastDividend;

  private double fixedDividend;

  private double parValue;

}
