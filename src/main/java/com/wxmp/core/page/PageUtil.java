/*
 * FileName：PageUtil.java 
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
package com.wxmp.core.page;

import java.lang.reflect.Field;

/**
 * 分页工具类
 *
 * @author hermit
 * @date 2017 -06-28 14:32:45
 */
public class PageUtil {
    
    /**
     * 获取obj对象fieldName的Field
     * 
     * @author hermit
     * @date 2016年3月15日 下午4:33:03
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 获取obj对象fieldName的属性值
     * 
     * @author hermit
     * @date 2016年3月15日 下午4:33:09
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
        throws Exception {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }
    
    /**
     * 设置obj对象fieldName的属性值
     * 
     * @author hermit
     * @date 2016年3月15日 下午4:33:17
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value)
        throws Exception {
        Field field = getFieldByFieldName(obj, fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    /**
     * object转换为int类型
     *
     * @author hermit
     * @date 2016年11月23日
     * @param obj
     * @return
     */
    public static int obj2Int(Object obj) {
        return isEmpty(obj) ? 0 : Integer.parseInt(obj.toString());
    }

    /**
     *
     * @Description 判断对象是否为空
     * @author hermit
     * @date 2016-2-4 上午11:58:40
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return obj == null || "".equals(obj);
    }

}
