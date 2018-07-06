/*
 * FileName：J2CacheUtil.java 
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
//
//import net.oschina.j2cache.*;
//
//import java.io.IOException;
//
///**
// * @author hermit
// */
//public class J2CacheUtil{
//
//    private final static String CONFIG_FILE = "/j2cache/j2cache.properties";
//
//    private final static J2CacheBuilder builder;
//
//    static {
//        try {
//            J2CacheConfig config = J2CacheConfig.initFromConfig(CONFIG_FILE);
//            builder = J2CacheBuilder.init(config);
//        } catch (IOException e) {
//            throw new CacheException("Failed to load j2cache configuration " + CONFIG_FILE, e);
//        }
//    }
//
//    /**
//     * 返回缓存操作接口
//     * @return CacheChannel
//     */
//    public static CacheChannel getChannel(){
//        return builder.getChannel();
//    }
//
//    /**
//     * 关闭 J2Cache
//     */
//    public static void close() {
//        builder.close();
//    }
//}
