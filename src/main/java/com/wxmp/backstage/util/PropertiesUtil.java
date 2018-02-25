/**
 * 
 */
package com.wxmp.backstage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
 
/**
 * java.util.Properties工具类
 *      1、封装了对属性文件的简单读写
 *      2、拓展加入了对List、Map数据格式的支持
 * 
 * 注意：
 *      1、由于在属性值读取过程中，字符分割是采用“特殊字符+向后断言”的方式
 *          所以被分割的key/value结束不允许以“\”作为结尾，当前逻辑是replace掉后缀的“\”符号
 * 
 * @author xiongliang
 *
 */
public class PropertiesUtil {
     public static void main(String[] args) {
		
	}
    /*
     * 单属性读写
     */
     
    public static boolean setProperty(String filePath, String fileName, String propertyName, String propertyValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, propertyValue);
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean clearProperty(String filePath, String fileName, String propertyName){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, "");
            String comment = propertyName;
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean removeProperty(String filePath, String fileName, String propertyName){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.remove(propertyName);
            String comment = propertyName;
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static String getProperty(String filePath, String fileName, String propertyName){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            return p.getProperty(propertyName);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    public static String getProperty(String filePath, String fileName, String propertyName, String defaultValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            return p.getProperty(propertyName, defaultValue);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    /*
     * 多属性读写
     */
 
    public static boolean setProperty(String filePath, String fileName, Map<String, String> propertyMap){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            for(String name:propertyMap.keySet()){
                p.setProperty(name, propertyMap.get(name));
            }
            String comment = "Update '" + propertyMap.keySet().toString() + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 这么随便的代码，却是极好的！
     * 
     * @param filePath
     * @param fileName
     * @param propertyArray
     * @return
     */
    public static boolean setProperty(String filePath, String fileName, String... propertyArray){
        if(propertyArray == null || propertyArray.length%2 != 0){
            throw new IllegalArgumentException("make sure 'propertyArray' argument is 'ket/value' pairs");
        }
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            for(int i=0;i<propertyArray.length/2;i++){
                p.setProperty(propertyArray[i*2], propertyArray[i*2+1]);
            }
            String comment =  "Update '" + propertyArray[0] + "..." + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 方法返回布尔值，为true时表示成功
     *  详细数据见传入参数propertyMap（此处采用传引用的方式）
     * 
     * @param filePath
     * @param fileName
     * @param propertyMap
     * @return
     */
    public static boolean getProperty(String filePath, String fileName, Map<String, String> propertyMap){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            for(String name:propertyMap.keySet()){
                propertyMap.put(name, p.getProperty(name, propertyMap.get(name)));
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /*
     * List属性值读写
     */
     
    public static boolean setProperty(String filePath, String fileName, String propertyName, List<String> propertyValueList){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            StringBuilder propertyValue = new StringBuilder();
            if(propertyValueList != null && propertyValueList.size() > 0){
                for(String value:propertyValueList){
                    propertyValue.append(
                            value.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") +
                            ";");
                }
            }
            p.setProperty(propertyName, propertyValue.toString());
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, List<String> propertyValueList){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            StringBuilder propertyValue = new StringBuilder();
            for(String value:propertyValueList){
                propertyValue.append(
                        value.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") +
                        ";");
            }
            p.setProperty(propertyName, p.getProperty(propertyName) + propertyValue.substring(1));
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, String propertyValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, p.getProperty(propertyName, "") +
                    propertyValue.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll(";", "\\\\;") +
                    ";");
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static List<String> getPropertyList(String filePath, String fileName, String propertyName, String defaultValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            String v = p.getProperty(propertyName, defaultValue);
            String[] iA = v.split("(?<!\\\\);");
            for(int i=0;i<iA.length;i++){
                iA[i] = iA[i].replaceAll("(\\\\)+$", "").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\");
            }
            return Arrays.asList(iA);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    /*
     * MAP属性值读写
     */
     
    public static boolean setProperty(String filePath, String fileName, String propertyName, Map<String, String> propertyValueMap){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            StringBuilder propertyValue = new StringBuilder();
            if(propertyValueMap != null && propertyValueMap.size() > 0){
                for(String key:propertyValueMap.keySet()){
                    propertyValue.append(
                            key.replaceAll("\\\\", "\\\\\\\\").replaceAll("(\\\\)+$", "").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") + 
                            "," +
                            propertyValueMap.get(key).replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") +
                            ";");
                }
            }
            p.setProperty(propertyName, propertyValue.toString());
            String comment = "Update '" + propertyName + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, Map<String, String> propertyValueMap){
        try{
            Map<String, String> combinePropertyValueMap = getPropertyMap(filePath, fileName, propertyName, "");
            combinePropertyValueMap.putAll(propertyValueMap);
            return setProperty(filePath, fileName, propertyName, combinePropertyValueMap);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    public static boolean appendProperty(String filePath, String fileName, String propertyName, String propertyKey, String propertyValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            p.setProperty(propertyName, p.getProperty(propertyName, "") +
                    propertyKey.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") + 
                    "," +
                    propertyValue.replaceAll("(\\\\)+$", "").replaceAll("\\\\", "\\\\\\\\").replaceAll("\\,", "\\\\,").replaceAll(";", "\\\\;") +
                    ";");
            String comment = "Update '" + propertyName + "." + propertyKey + "' value";
            return storePropertyInstance(filePath, fileName, p, comment);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
    /**
     * 返回Map格式封装的属性值
     *  其中，Map以HashMap创建，若要求排序的话，推荐外部排序
     * 
     * @param filePath
     * @param fileName
     * @param propertyName
     * @param defaultValue
     * @return
     */
    public static Map<String, String> getPropertyMap(String filePath, String fileName, String propertyName, String defaultValue){
        try{
            Properties p = loadPropertyInstance(filePath, fileName);
            String v = p.getProperty(propertyName, defaultValue);
             
            Map<String, String> retMap = new HashMap<String, String>();
            String[] iA = v.split("(?<!\\\\);");
            for(String i:iA){
                String[] jA = i.split("(?<!\\\\),");
                if(jA.length == 2){
                    retMap.put(
                            jA[0].replaceAll("(\\\\)+$", "").replaceAll("\\\\\\,", "\\,").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\"), 
                            jA[1].replaceAll("(\\\\)+$", "").replaceAll("\\\\\\,", "\\,").replaceAll("\\\\;", ";").replaceAll("\\\\\\\\", "\\\\"));
                }
            }
            return retMap;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    /*
     * 基本
     */
 
    public static Properties loadPropertyInstance(String filePath, String fileName){
        try{
            File d = new File(filePath);
            if(!d.exists()){
                d.mkdirs();
            }
            File f = new File(d, fileName);
            if(!f.exists()){
                f.createNewFile();
            }
            Properties p = new Properties();
            InputStream is = new FileInputStream(f);
            p.load(is);
            is.close();
            return p;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
     
    public static boolean storePropertyInstance(String filePath, String fileName, Properties p, String comment){
        try{
            File d = new File(filePath);
            if(!d.exists()){
                d.mkdirs();
            }
            File f = new File(d, fileName);
            if(!f.exists()){
                f.createNewFile();
            }
            OutputStream os = new FileOutputStream(f);
            p.store(os, comment);
            os.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
     
}