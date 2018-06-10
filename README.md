# Super Simple Stocks

## Requirements

* Java 8 or Above
* Maven
* GIT

## Dependencies
**Lombok** version 1.16.16.
  > [Project Lombok](https://projectlombok.org/) is a java library that automatically plugs into your editor and build tools, spicing up your java.
Never write another getter or equals method again.

## Installation
  > git clone https://github.com/ripsamm004/task_jpm_stock.git
  > cd task_jpm_stock
  > mvn clean install


## Run the application
```
 > java -jar target/super-simple-stock-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Application details

```
Application Commands
1: Calculate dividend yield [input: Stock, price]
2: Calculate P/E ratio [input: Stock, price]
3: Record a trade for stock [input: Stock, datetime, quantity, type of trade, price]
4: Calculate Volume Weighted Stock Price based on trades in past 15 minutes [input: Stock]
5: Calculate GBCE All Share Index
q: Exit
Press any key from [1-5] or q to Exit
