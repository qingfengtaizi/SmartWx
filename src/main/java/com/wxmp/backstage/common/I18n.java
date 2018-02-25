package com.wxmp.backstage.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.wxmp.backstage.util.ExcptUtil;
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