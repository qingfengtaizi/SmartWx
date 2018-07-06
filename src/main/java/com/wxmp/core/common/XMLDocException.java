/*
 * FileName：XMLDocException.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.wxmp.core.common;
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
