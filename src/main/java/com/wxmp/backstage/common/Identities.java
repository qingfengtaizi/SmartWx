package com.wxmp.backstage.common;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.wxmp.backstage.util.ConvertUtil;
import com.wxmp.backstage.util.DateUtil;
import com.wxmp.backstage.util.StringUtil;
/**
 * @author : hermit
 */
public class Identities
{
  private static SecureRandom random = new SecureRandom();
  private static AtomicLong atomicLongID = new AtomicLong(10000L);

  public static String uuid()
  {
    return UUID.randomUUID().toString();
  }

  public static String uuid2()
  {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static long randomLong()
  {
    return Math.abs(random.nextLong());
  }

  public static String randomBase62(int length)
  {
    byte[] randomBytes = new byte[length];
    random.nextBytes(randomBytes);
    return ConvertUtil.convertEncodeBase62(randomBytes);
  }

  public static String getTimeMillisSequence()
  {
    long nanoTime = System.nanoTime();
    String preFix = null;
    if (nanoTime < 0L)
    {
      preFix = "A";
      nanoTime = nanoTime + 9223372036854775807L + 1L;
    } else {
      preFix = "Z";
    }
    String nanoTimeStr = String.valueOf(nanoTime);
    int difBit = String.valueOf(9223372036854775807L).length() - nanoTimeStr.length();
    for (int i = 0; i < difBit; i++) {
      preFix = StringUtil.concat(new Object[] { preFix, "0" });
    }
    long last = atomicLongID.getAndIncrement() % 10000L + 10000L;
    return StringUtil.concat(new Object[] { DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"), preFix, nanoTimeStr, Long.valueOf(last) });
  }
}