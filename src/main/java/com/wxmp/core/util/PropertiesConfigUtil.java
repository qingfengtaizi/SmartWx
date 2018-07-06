/*
 * FileName：PropertiesConfigUtil.java 
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesConfigUtil {
	private static Map<String,Properties> propMap = new HashMap<String,Properties>();
	
	public static Object getProperty(String file,String key){
		Properties prop = getProperties(file);
		if(prop != null && prop.get(key) != null){
			return prop.get(key);
		}
		return null;
	}
    
    public static Properties getProperties(String file){
    	try {
    		if(propMap.get(file) == null){
    			Properties prop = new Properties();
    			prop.load(PropertiesConfigUtil.class.getClassLoader().getResourceAsStream(file));
    			propMap.put(file,prop);
    			return prop;
    		}else{
    			return propMap.get(file);
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return null;
    }
    
    public static void updateProperties(Properties prop,String filePath){
    	
    	FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
        	URI fileUri = PropertiesConfigUtil.class.getClassLoader().getResource(filePath).toURI();
        	File file = new File(fileUri);
        	
        	Properties tmpProp = new Properties();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            tmpProp.load(bis);
            
            FileOutputStream fos = new FileOutputStream(file);
            for(Object key : prop.keySet()){
            	tmpProp.setProperty(String.valueOf(key),String.valueOf(prop.get(key)));
            }
            tmpProp.store(fos, null);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
