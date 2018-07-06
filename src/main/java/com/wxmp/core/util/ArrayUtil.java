/*
 * FileName：ArrayUtil.java 
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
package com.wxmp.core.util;

import java.lang.reflect.Array;

/**
 * 功能：数组操作通用类
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class ArrayUtil {
	
	/**
	 * 添加int到原有数组
	 * @param ai 原数组
	 * @param i 要填元素
	 * @param first 是否first
	 * @return
	 */
	public static int[] append(int ai[], int i, boolean first) {
        return append(ai, new int[] {
            i
        }, first);
    }
	/**
	 * 添加int数组到原有数组里面
	 * @param ai 原数组
	 * @param ai1 新数组
	 * @param first 是否first
	 * @return
	 */
    public static int[] append(int ai[], int ai1[], boolean first) {
        if(ai == null)
            return ai1;
        if(ai1 == null || ai1.length == 0)
            return ai;
        int ai2[] = new int[ai.length + ai1.length];
        if(!first) {
            System.arraycopy(ai, 0, ai2, 0, ai.length);
            System.arraycopy(ai1, 0, ai2, ai.length, ai1.length);
        } else {
            System.arraycopy(ai1, 0, ai2, 0, ai1.length);
            System.arraycopy(ai, 0, ai2, ai1.length, ai.length);
        }
        return ai2;
    }
    
    /**
     * 添加long到原有数组
     * @param al
     * @param l
     * @param first 是否first
     * @return
     */
    public static long[] append(long al[], long l, boolean first) {
        return append(al, new long[] {
            l
        }, first);
    }
    
    /**
     * 添加long数组到原有数组
     * @param al
     * @param al1
     * @param first 是否first
     * @return
     */
    public static long[] append(long al[], long al1[], boolean first) {
        if(al == null)
            return al1;
        if(al1 == null || al1.length == 0)
            return al;
        long al2[] = new long[al.length + al1.length];
        if(!first)
        {
            System.arraycopy(al, 0, al2, 0, al.length);
            System.arraycopy(al1, 0, al2, al.length, al1.length);
        } else
        {
            System.arraycopy(al1, 0, al2, 0, al1.length);
            System.arraycopy(al, 0, al2, al1.length, al.length);
        }
        return al2;
    }
    /**
     * 添加Object到原有数组
     * @param aobj
     * @param obj
     * @param first
     * @return
     */
    public static Object[] append(Object aobj[], Object obj, boolean first){
        return append(aobj, new Object[] {
            obj
        }, first);
    }
        
    /**
     * 添加Object数组到原有数组
     * @param aobj
     * @param aobj1
     * @param first
     * @return
     */
    public static Object[] append(Object aobj[], Object aobj1[], boolean first) {
        if(aobj == null)
            return aobj1;
        if(aobj1 == null)
            return aobj;
        Object aobj2[] = (Object[])Array.newInstance(((Object) (aobj)).getClass().getComponentType(), aobj.length + aobj1.length);
        if(!first)
        {
            System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj2)), 0, aobj.length);
            System.arraycopy(((Object) (aobj1)), 0, ((Object) (aobj2)), aobj.length, aobj1.length);
        } else
        {
            System.arraycopy(((Object) (aobj1)), 0, ((Object) (aobj2)), 0, aobj1.length);
            System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj2)), aobj1.length, aobj.length);
        }
        return aobj2;
    }
    /**
     * 判断数组中是否包含i
     * @param ai
     * @param i
     * @return
     */
    public static boolean contain(int ai[], int i) {
        for(int j = 0; j < ai.length; j++)
            if(ai[j] == i)
                return true;
        return false;
    }
    /**
     * 判断数组中是否包含i
     * @param al
     * @param l
     * @return
     */
    public static boolean contain(long al[], long l) {
        for(int i = 0; i < al.length; i++)
            if(al[i] == l)
                return true;
        return false;
    }

    /**
     * 判断数组中是否包含s
     * @param as
     * @param s
     * @return
     */
    public static boolean contain(String as[], String s) {
        for(int i = 0; i < as.length; i++)
            if(as[i].equals(s))
                return true;
        return false;
    }

    /**
     * int数组转Integer数组
     * @param ai
     * @return
     */
    public static Integer[] toObjectArray(int ai[]) {
        Integer ainteger[] = null;
        if(ai != null)
        {
            ainteger = new Integer[ai.length];
            for(int i = 0; i < ai.length; i++)
                ainteger[i] = new Integer(ai[i]);

        }
        return ainteger;
    }

    /**
     * 在数组中删除第一个i元素
     * @param ai
     * @param i 元素
     * @return
     */
    public static int[] removeItem(int ai[], int i) {
        if(ai == null || ai.length == 0)
            return ai;
        int ai1[] = new int[ai.length - 1];
        int j;
        for(j = 0; j < ai1.length; j++)
        {
            if(ai[j] == i)
                if(j == ai1.length)
                {
                    return ai1;
                } else
                {
                    System.arraycopy(ai, j + 1, ai1, j, ai1.length - j);
                    return ai1;
                }
            ai1[j] = ai[j];
        }

        if(ai[j] == i)
            return ai1;
        else
            return ai;
    }
    /**
     * 从数组中删除第一个l元素
     * @param al
     * @param l
     * @return
     */
    public static long[] removeItem(long al[], long l) {
        if(al == null || al.length == 0)
            return al;
        long al1[] = new long[al.length - 1];
        int i;
        for(i = 0; i < al1.length; i++)
        {
            if(al[i] == l)
                if(i == al1.length)
                {
                    return al1;
                } else
                {
                    System.arraycopy(al, i + 1, al1, i, al1.length - i);
                    return al1;
                }
            al1[i] = al[i];
        }

        if(al[i] == l)
            return al1;
        else
            return al;
    }
    /**
     * 数组中删除第一obj元素
     * @param aobj
     * @param obj
     * @return
     */
    public static Object[] removeItem(Object aobj[], Object obj) {
        if(aobj == null || aobj.length == 0)
            return aobj;
        Object aobj1[] = (Object[])Array.newInstance(((Object) (aobj)).getClass().getComponentType(), aobj.length - 1);
        int i;
        for(i = 0; i < aobj1.length; i++)
        {
            if(aobj[i].equals(obj))
                if(i == aobj1.length)
                {
                    return aobj1;
                } else
                {
                    System.arraycopy(((Object) (aobj)), i + 1, ((Object) (aobj1)), i, aobj1.length - i);
                    return aobj1;
                }
            aobj1[i] = aobj[i];
        }

        if(aobj[i] == obj)
            return aobj1;
        else
            return aobj;
    }
    /**
     * 在数组中删除索引index
     * @param ai
     * @param index
     * @return
     */
    public static int[] removeIndex(int ai[], int index){
        if(ai == null || ai.length == 0)
            return ai;
        if(index < 0 || index >= ai.length)
            return ai;
        int ai1[] = new int[ai.length - 1];
        if(index == 0)
            System.arraycopy(ai, 1, ai1, 0, ai1.length);
        else
        if(index == ai1.length)
        {
            System.arraycopy(ai, 0, ai1, 0, ai1.length);
        } else
        {
            System.arraycopy(ai, 0, ai1, 0, index);
            System.arraycopy(ai, index + 1, ai1, index, ai1.length - index);
        }
        return ai1;
    }
    /**
     * 在数组中删除索引index
     * @param ai
     * @param index
     * @return
     */
    public static long[] removeIndex(long al[], int index)
    {
        if(al == null || al.length == 0)
            return al;
        if(index < 0 || index >= al.length)
            return al;
        long al1[] = new long[al.length - 1];
        if(index == 0)
            System.arraycopy(al, 1, al1, 0, al1.length);
        else
        if(index == al1.length)
        {
            System.arraycopy(al, 0, al1, 0, al1.length);
        } else
        {
            System.arraycopy(al, 0, al1, 0, index);
            System.arraycopy(al, index + 1, al1, index, al1.length - index);
        }
        return al1;
    }
    /**
     * 数组中删除索引index
     * @param aobj
     * @param index
     * @return
     */
    public static Object[] removeIndex(Object aobj[], int index) {
        if(aobj == null || aobj.length == 0)
            return aobj;
        if(index < 0 || index >= aobj.length)
            return aobj;
        Object aobj1[] = (Object[])Array.newInstance(((Object) (aobj)).getClass().getComponentType(), aobj.length - 1);
        if(index == 0)
            System.arraycopy(((Object) (aobj)), 1, ((Object) (aobj1)), 0, aobj1.length);
        else
        if(index == aobj1.length)
        {
            System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj1)), 0, aobj1.length);
        } else
        {
            System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj1)), 0, index);
            System.arraycopy(((Object) (aobj)), index + 1, ((Object) (aobj1)), index, aobj1.length - index);
        }
        return aobj1;
    }

    /**
     * 上移一位
     * @param ai
     * @param i
     * @return
     */
    public static int[] moveItemUp(int ai[], int i) {
        int j = 0;
        do
        {
            if(j >= ai.length)
                break;
            if(ai[j] == i)
            {
                if(j > 0)
                {
                    ai[j] = ai[j - 1];
                    ai[j - 1] = i;
                }
                break;
            }
            j++;
        } while(true);
        return ai;
    }

    /**
     * 下移一位
     * @param ai
     * @param i
     * @return
     */
    public static int[] moveItemDown(int ai[], int i){
        int j = 0;
        do
        {
            if(j >= ai.length)
                break;
            if(ai[j] == i)
            {
                if(j + 1 < ai.length)
                {
                    ai[j] = ai[j + 1];
                    ai[j + 1] = i;
                }
                break;
            }
            j++;
        } while(true);
        return ai;
    }

}
