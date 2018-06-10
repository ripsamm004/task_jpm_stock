package com.jpm.stock.util;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.domain.TradeType;
import com.jpm.stock.exception.CustomStockException;
import com.jpm.stock.service.StockService;
import com.jpm.stock.service.TradeService;
import com.jpm.stock.service.impl.StockServiceImpl;
import com.jpm.stock.service.impl.TradeServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CommonUtil {

    private final static StockService stockService = StockServiceImpl.getInstance();
    private final static TradeService tradeService = TradeServiceImpl.getInstance();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // initialize stocks
    static {
        stockService.addStock(new Stock("TEA", StockType.COMMON, 0, 0, 100));
        stockService.addStock(new Stock("POP", StockType.COMMON, 8, 0, 100));
        stockService.addStock(new Stock("ALE", StockType.COMMON, 23, 0, 60));
        stockService.addStock(new Stock("GIN", StockType.PREFERRED, 8, 2, 100));
        stockService.addStock(new Stock("JOE", StockType.PREFERRED, 13, 0, 250));
    }

    public static String calculateDividendYield(Stock stock, double price) {
        double result = stockService.calculateDividendYield(stock, price);
        return "Dividend Yield: " + result;
    }

    public static String calculatePERatio(Stock stock, double price) {
        double result = stockService.calculatePERatio(stock, price);
        return "PE Ratio: " + result;
    }

    public static String calculateVolumeWeightedStockPrice(Stock stock) {
        List<Trade> trades = tradeService.getTradesByStockAndBeforeXMinute(stock, 15);
        if (trades == null || trades.isEmpty()) {
           return String.format("Volume Weighted Stock Price: No trades found in last %d minute", 15);
        } else {
            double result = stockService.calculateVolumeWeightedStockPrice(trades);
            return String.format("Volume Weighted Stock Price: %.2f", result);
        }
    }

    public static String recordTrade(Stock stock, LocalDateTime localDateTime, int quantity, TradeType type, double price) {
        tradeService.recordTrade(new Trade(stock, localDateTime, quantity, type, price));
        return "Trade inserted";
    }

    public static String calculateGBCE() {
        List<Trade> allTrades = tradeService.getAllTrades();
        if (allTrades == null || allTrades.isEmpty()) {
            return "GBCE: No trades found";
        } else {
            return String.format("GBCE: %.2f", stockService.calculateGBCE(allTrades));
        }
    }

    public static Stock validateStockBySymbol(String stockSymbol) throws CustomStockException {
        Stock stock = stockService.getStockBySymbol(stockSymbol);
        return stock;
    }

    public static double validateStockPrice(String stockPrice) throws CustomStockException {
        double result = 0d;
        try {
            result = Double.parseDouble(stockPrice);
        } catch (NumberFormatException e) {
            throw new CustomStockException("Invalid price");
        }

        if (result <= 0) {
            throw new CustomStockException("Invalid price < 0 !");
        }
        return result;
    }

    public static TradeType validateTradeType(String type) throws CustomStockException {
        try {
            return TradeType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomStockException("Invalid trade type [ Must BUY or SELL ]");
        }
    }

    public static int validateQuantity(String quantity) throws CustomStockException {
        int result = 0;

        try {
            result = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            throw new CustomStockException("Invalid quantity: Not a number");
        }

        if (result <= 0) {
            throw new CustomStockException("Invalid quantity [ Must be grater than 0 ]");
        }
        return result;
    }

    public static LocalDateTime validateDateTimestamp(String date) throws CustomStockException {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            if(dateTime.isAfter(LocalDateTime.now())) throw new CustomStockException("Invalid Date [Date can not be after current time]");
            return dateTime;
        } catch (DateTimeParseException e) {
            throw new CustomStockException("Invalid date format");
        }
    }

}
