/*
 * FileName：Series.java 
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

import java.util.List;

/**
 * echarts图表实体-Series
 *
 * @param <T>
 * @author mengLei
 * @date 2017 -08-21 16:38:10
 */
public class Series<T> {
    
    /**
     * 系列类型
     */
    public String type;
    
    /**
     * 系列名称
     */
    public String name;
    
    /**
     * 数据集合
     */
    public List<T> data;
    
    /**
     * 是否平滑曲线显示
     */
    public boolean smooth = true;
    
    /**
     * @author mengLei
     * @date 2017 -08-21 19:07:29
     */
    public Series() {
        
    }

    /**
     * @param type 系列类型
     * @param seriesData 数据集合
     * @author mengLei
     * @date 2017 -08-21 19:07:29
     */
    public Series(String type, List<T> seriesData) {
        this.type = type;
        this.data = seriesData;
    }
    
    /**
     * @param type 系列类型
     * @param name 系列名称
     * @param seriesData 数据集合
     * @author mengLei
     * @date 2017 -08-21 19:07:29
     */
    public Series(String type, String name, List<T> seriesData) {
        this.name = name;
        this.type = type;
        this.data = seriesData;
    }
    
    /**
     * @param type 系列类型
     * @param name 系列名称
     * @param seriesData 数据集合
     * @param smooth 是否平滑曲线显示
     * @author mengLei
     * @date 2017 -08-21 19:07:29
     */
    public Series(String type, String name, List<T> seriesData, boolean smooth) {
        this.name = name;
        this.type = type;
        this.data = seriesData;
        this.smooth = smooth;
    }
}
