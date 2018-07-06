/*
 * FileName：I18n.java 
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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.wxmp.core.util.ExcptUtil;
/**
 * @author : hermit
 */
public class I18n
{
  public static final String FILED_RPEAT = getMessage("core.field.rpeat");
  public static final String FILED_EXISTS = getMessage("core.field.exists");
  public static final String FILED_REQUIRED = getMessage("core.field.required");
  public static final String FILED_UNLAWFUL = getMessage("core.field.unlawful");
  private static final String MSG_BASE_NAME = "i18n/messages";

  public static String getMessage(String key, Object[] params)
  {
    if ((params == null) || (params.length < 1)) {
      return ResourceBundle.getBundle("i18n/messages", Locale.CHINA).getString(key);
    }

    return MessageFormat.format(ResourceBundle.getBundle("i18n/messages", Locale.CHINA).getString(key), params);
  }

  public static String getMessage(String key)
  {
    String value = key;
    try {
      ResourceBundle resource = ResourceBundle.getBundle("i18n/messages", Locale.CHINA);
      String temp = resource.getString(key);
      if (temp != null) value = temp; 
    }
    catch (Exception e) { ExcptUtil.unchecked(e); }

    return value;
  }
}
