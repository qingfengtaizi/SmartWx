package com.wxmp.wxapi.vo;

import lombok.Data;

/**
 * 素材总数
 */
@Data
public class MaterialCount {
	private Integer voiceCount;//语音总数量
	private Integer videoCount;//视频总数
	private Integer imageCount;//图片总数
	private Integer newsCount;//图文总数
}
