package com.wxmp.backstage.common;
/**
 * @author : hermit
 */
public class XMLDocException extends Exception
{
  private static final long serialVersionUID = 1L;
  private Exception exception;

  public XMLDocException(String message, Exception exception)
  {
    super(message);
    this.exception = exception;
  }

  public XMLDocException(String message)
  {
    this(message, null);
  }

  public XMLDocException(Exception exception)
  {
    this(null, exception);
  }

  public Exception getException()
  {
    return this.exception;
  }

  public Exception getRootCause()
  {
    if ((this.exception instanceof XMLDocException)) {
      return ((XMLDocException)this.exception).getRootCause();
    }
    return this.exception == null ? this : this.exception;
  }

  public String toString() {
    if ((this.exception instanceof XMLDocException)) {
      return ((XMLDocException)this.exception).toString();
    }
    return this.exception == null ? super.toString() : this.exception.toString();
  }
}