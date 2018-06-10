package com.jpm.stock;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.TradeType;
import com.jpm.stock.exception.CustomStockException;
import com.jpm.stock.util.CommonUtil;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationMain {
    private final static Logger LOG = Logger.getLogger(ApplicationMain.class.getName());
    private static Scanner scanner;

    public static void main(String[] args) throws InterruptedException {

        LOG.log(Level.INFO, "ApplicationMain running ....");
        Thread.sleep(10);
        displayAppCommand();

        scanner = new Scanner(System.in);
        String inputType = null;

        while (true) {

            inputType = scanner.nextLine();

            if ("q".equals(inputType)) {

                scanner.close();
                LOG.log(Level.INFO, "ApplicationMain closing ....");
                System.exit(0);

            } else {

                try {

                    int option = Integer.parseInt(inputType);
                    String stockSymbol;
                    String stockPrice;
                    Stock inputStock;
                    double inputPrice;
                    String response;
                    switch (option) {
                        case 1:
                            System.out.println("Please input stock symbol");
                            stockSymbol = scanner.nextLine();
                            inputStock = CommonUtil.validateStockBySymbol(stockSymbol);

                            System.out.println("Please input stock price");
                            stockPrice = scanner.nextLine();
                            inputPrice = CommonUtil.validateStockPrice(stockPrice);
                            response = CommonUtil.calculateDividendYield(inputStock, inputPrice);
                            displayAppResponse(response);
                            break;
                        case 2:
                            System.out.println("Please input stock symbol");
                            stockSymbol = scanner.nextLine();
                            inputStock = CommonUtil.validateStockBySymbol(stockSymbol);

                            System.out.println("Please input stock price");
                            stockPrice = scanner.nextLine();
                            inputPrice = CommonUtil.validateStockPrice(stockPrice);

                            response = CommonUtil.calculatePERatio(inputStock, inputPrice);
                            displayAppResponse(response);
                            break;
                        case 3:
                            System.out.println("Please input stock symbol");
                            stockSymbol = scanner.nextLine();
                            inputStock = CommonUtil.validateStockBySymbol(stockSymbol);

                            System.out.println("Please input date with time [yyyy-MM-dd HH:mm:ss]");
                            String dateTime = scanner.nextLine();
                            LocalDateTime localDateTime = CommonUtil.validateDateTimestamp(dateTime);

                            System.out.println("Please input quantity");
                            String quantity = scanner.nextLine();
                            int quantityFromUser = CommonUtil.validateQuantity(quantity);

                            System.out.println("Please input trade type (BUY/SELL)");
                            String type = scanner.nextLine();
                            TradeType tradeTypeFromUser = CommonUtil.validateTradeType(type);

                            System.out.println("Please input stock price");
                            stockPrice = scanner.nextLine();
                            inputPrice = CommonUtil.validateStockPrice(stockPrice);

                            response = CommonUtil.recordTrade(inputStock, localDateTime, quantityFromUser, tradeTypeFromUser, inputPrice);
                            displayAppResponse(response);
                            break;
                        case 4:
                            System.out.println("Please input stock symbol");
                            stockSymbol = scanner.nextLine();
                            inputStock = CommonUtil.validateStockBySymbol(stockSymbol);

                            response = CommonUtil.calculateVolumeWeightedStockPrice(inputStock);
                            displayAppResponse(response);
                            break;
                        case 5:
                            response = CommonUtil.calculateGBCE();
                            displayAppResponse(response);
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    displayAppResponse("Invalid command");
                } catch (CustomStockException e1) {
                    LOG.log(Level.SEVERE, e1.getBeautyMessage());
                }
                displayAppCommand();
            }
        }
    }

    public static void displayAppCommand() {
        System.out.println("Application Commands");
        System.out.println("1: Calculate dividend yield [input: Stock, price]");
        System.out.println("2: Calculate P/E ratio [input: Stock, price]");
        System.out.println("3: Record a trade for stock [input: Stock, datetime, quantity, type of trade, price]");
        System.out.println("4: Calculate Volume Weighted Stock Price based on trades in past 15 minutes [input: Stock]");
        System.out.println("5: Calculate GBCE All Share Index");
        System.out.println("q: Exit");
        System.out.println("Press any key from [1-5] or q to Exit");
    }

    public static void displayAppResponse(String response) {
        System.out.println(response);
    }
}
