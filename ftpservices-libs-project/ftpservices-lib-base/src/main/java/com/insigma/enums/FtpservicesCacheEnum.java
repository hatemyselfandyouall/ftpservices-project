package com.insigma.enums;

import star.modules.cache.enumerate.BaseCacheEnum;

/**
 * redis 缓存key
 * （按业务名称命名定义，大家不要重复了）
 * @author jinxm
 * @since:2019年5月15日下午15:08
 */
public enum FtpservicesCacheEnum implements BaseCacheEnum {

	/**
	 * APPkey对应的接口列表
	 */
    BARCODE_CACHE("barcode_cache"),

	QRCODE_CACHE("qrcode_cache");


	private String type;

	FtpservicesCacheEnum(String type) {
		this.type = type;
	}

	@Override
	public String getAnchor() {
		return this.type;
	}

	@Override
	public BaseCacheEnum get(String key) {
		if (key != null && key.length() > 0) {
			for (FtpservicesCacheEnum type : FtpservicesCacheEnum.values()) {
				if (key.equals(type.getAnchor())) {
					return type;
				}
			}
		}
		return null;
	}

}
