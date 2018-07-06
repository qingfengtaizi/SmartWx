/*
 * FileName：MediaType.java 
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
package com.wxmp.wxapi.process;

/**
 * 多媒体文件类型
 * 
 */

public enum MediaType {

	News("news"),//图文
	Image("image"),//图片
	Voice("voice"),//语音
	Video("video"),//视频
	Thumb("thumb");//缩略图
	
	private String name;
	
	private MediaType(String name) {
	     this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
