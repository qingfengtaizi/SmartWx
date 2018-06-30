package com.wxmp.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * echarts图表实体
 *
 * @author mengLei
 * @date 2017 -08-21 16:38:43
 */
public class EchartsData {
    
    /**
     * 图例组件数据集合
     */
    public List<String> legendData = new ArrayList<>();
    
    /**
     * 类目轴数据集合
     */
    public List<String> categoryData = new ArrayList<>();
    
    /**
     * 列表数据结合
     */
    public List<Series> seriesList = new ArrayList<>();
    
    public EchartsData() {
    }
    
    /**
     * @param categoryData 类目轴数据集合
     * @param seriesList 列表数据结合
     * @author mengLei
     * @date 2017 -08-21 19:04:58
     */
    public EchartsData(List<String> categoryData, List<Series> seriesList) {
        this.categoryData = categoryData;
        this.seriesList = seriesList;
    }
    
    /**
     * @param legendData 图例组件数据集合
     * @param categoryData 类目轴数据集合
     * @param seriesList 列表数据结合
     * @author mengLei
     * @date 2017 -08-21 19:05:04
     */
    public EchartsData(List<String> legendData, List<String> categoryData, List<Series> seriesList) {
        this.legendData = legendData;
        this.categoryData = categoryData;
        this.seriesList = seriesList;
    }
    
}
