package com.jpm.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 *  This class represent a Trade of a stock. Trade can have a
 *  stock (the stock for which this trade is initiated)
 *  timestamp (this is LocalDateTime when the trade is initiate, This is not valid if the timestamp is after than current time)
 *  quantity (represent the number of stock in integer)
 *  type (type of trade, It can be TradeType.BUY or TradeType.SELL)
 *  price (a decimal value, the price of the trade)
 */
@AllArgsConstructor
@Getter
@lombok.EqualsAndHashCode(of = {"stock", "timestamp", "quantity", "type", "price"})
@ToString
public class Trade {

  private Stock stock;

  private LocalDateTime timestamp;

  private int quantity;

  private TradeType type;

  private double price;

}
