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
