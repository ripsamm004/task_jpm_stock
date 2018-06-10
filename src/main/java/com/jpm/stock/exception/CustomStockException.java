package com.jpm.stock.exception;

/**
 * This is a custom exception handling class
 */
public class CustomStockException extends RuntimeException {

  protected String beautyMessage;

  public CustomStockException(){
    super();
  }

  public CustomStockException(String message) {
    super(message);
    this.beautyMessage = message;
  }

  public String getBeautyMessage() {
    return beautyMessage;
  }

}
